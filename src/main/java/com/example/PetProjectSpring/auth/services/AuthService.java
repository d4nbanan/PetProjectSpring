package com.example.PetProjectSpring.auth.services;

import com.example.PetProjectSpring.auth.dto.RefreshTokenDto;
import com.example.PetProjectSpring.auth.dto.SignUpDto;
import com.example.PetProjectSpring.auth.dto.TokenPayloadDto;
import com.example.PetProjectSpring.auth.entities.SessionEntity;
import com.example.PetProjectSpring.auth.exceptions.IncorrectCredentialsException;
import com.example.PetProjectSpring.auth.exceptions.UserAlreadyRegistered;
import com.example.PetProjectSpring.auth.repositories.SessionRepository;
import com.example.PetProjectSpring.auth.responses.SignUpResponse;
import com.example.PetProjectSpring.auth.types.AccessToken;
import com.example.PetProjectSpring.auth.types.FullSessionData;
import com.example.PetProjectSpring.auth.types.JwtTokens;
import com.example.PetProjectSpring.auth.types.TokenPayload;
import com.example.PetProjectSpring.core.exceptions.CoreRestException;
import com.example.PetProjectSpring.user.entities.RoleEntity;
import com.example.PetProjectSpring.user.entities.UserEntity;
import com.example.PetProjectSpring.user.models.User;
import com.example.PetProjectSpring.user.repositories.RoleRepository;
import com.example.PetProjectSpring.user.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.persistence.Access;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final RoleRepository roleRepository;

    private boolean comparePasswords(String password, String hash) {
        return encoder.matches(password, hash);
    }

    public JwtTokens refreshToken(RefreshTokenDto dto) throws CoreRestException {
        Map<String, Object> tokenData;

        try {
            tokenData = (Map<String, Object>) this.jwtService.decodeToken(dto.getRefreshToken());
        } catch (Exception err) {
            throw new CoreRestException(HttpStatus.BAD_REQUEST, "Invalid token");
        }

        TokenPayloadDto payload = new TokenPayloadDto();
        payload.setSessionId((String) tokenData.get("sessionId"));
        payload.setSub((String) tokenData.get("sub"));
        payload.setRoles((List<String>) tokenData.get("roles"));

        SessionEntity session = this.sessionRepository.findById(payload.getSessionId())
            .orElseThrow(() -> new CoreRestException(HttpStatus.BAD_REQUEST, "Session not found"));

        if(session.getClosedAt() != null) {
            throw new CoreRestException(HttpStatus.BAD_REQUEST, "Session is closed");
        }

        JwtTokens newTokens;

        try {
            newTokens = this.jwtService.generateTokens(payload);
        } catch (Exception err) {
            throw new CoreRestException(HttpStatus.BAD_REQUEST, "Error while generating jwt tokens");
        }

        return newTokens;
    }

    private FullSessionData createNewSession(UserEntity user, String userAgent) throws CoreRestException {
        SessionEntity newSession = new SessionEntity();
        newSession.setUser(user);
        newSession.setUserAgent(userAgent);

        System.out.println(newSession.getClosedAt());
        System.out.println(newSession.getId());
        System.out.println(newSession.getCreatedAt());
        System.out.println(newSession.getRefreshToken());
        System.out.println(newSession.getLastActivity());
        System.out.println(newSession.getUserAgent());
        System.out.println(newSession.getUser());

        this.sessionRepository.save(newSession);

        TokenPayloadDto tokenPayloadDto = new TokenPayloadDto();
        tokenPayloadDto.setSessionId(newSession.getId());
        tokenPayloadDto.setSub(user.getId());

        List<String> roles = user.getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toList());

        tokenPayloadDto.setRoles(roles);

        FullSessionData result = new FullSessionData();
        result.session = newSession;

        try {
            JwtTokens tokens = jwtService.generateTokens(tokenPayloadDto);
            System.out.println(tokens);
            newSession.setRefreshToken(tokens.getRefreshToken());

            result.tokens = tokens;
        } catch (Exception err) {
            throw new CoreRestException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while generating authorization tokens");
        }

        return result;
    }

    public SignUpResponse signInLocal(@RequestBody SignUpDto dto, String userAgent) throws CoreRestException {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        } catch (BadCredentialsException err) {
            throw new IncorrectCredentialsException();
        }

        UserEntity user = this.userRepository.findByEmail(dto.getEmail()).orElseThrow(
            () -> new CoreRestException(HttpStatus.NOT_FOUND, "User not registered")
        );

        FullSessionData sessionData = this.createNewSession(user, userAgent);

        sessionRepository.save(sessionData.session);

        SignUpResponse result = new SignUpResponse();
        result.user = new User();
        result.user.setId(user.getId());
        result.user.setEmail(user.getEmail());
        result.tokens = sessionData.tokens;

        return result;
    }

    @Transactional
    public SignUpResponse signUpLocal(
        @RequestBody SignUpDto dto, String userAgent
    ) throws
        BadCredentialsException,
        CoreRestException
    {
        Optional<UserEntity> foundUser = this.userRepository.findByEmail(dto.getEmail());

        if(!foundUser.isEmpty()) throw new UserAlreadyRegistered();

        UserEntity newUser = new UserEntity();
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(encoder.encode(dto.getPassword()));

        RoleEntity defaultRole = this.roleRepository.findByName("User").orElseThrow(
            () -> new CoreRestException(HttpStatus.INTERNAL_SERVER_ERROR, "Not found defauult role in database")
        );

        List<RoleEntity> userRoles = new ArrayList<>();
        userRoles.add(defaultRole);

        newUser.setRoles(userRoles);

        newUser = userRepository.save(newUser);

        FullSessionData sessionData = this.createNewSession(newUser, userAgent);
        System.out.println(sessionData.session);
        System.out.println(sessionData.tokens);
        sessionRepository.save(sessionData.session);

        SignUpResponse result = new SignUpResponse();
        result.user = new User();
        result.user.setId(newUser.getId());
        result.user.setEmail(newUser.getEmail());
        result.tokens = sessionData.tokens;

        return result;
    }
}
