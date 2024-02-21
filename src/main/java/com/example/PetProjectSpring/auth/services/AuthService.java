package com.example.PetProjectSpring.auth.services;

import com.example.PetProjectSpring.auth.dto.SignUpDto;
import com.example.PetProjectSpring.auth.dto.TokenPayloadDto;
import com.example.PetProjectSpring.auth.entities.SessionEntity;
import com.example.PetProjectSpring.auth.errors.UserAlreadyRegistered;
import com.example.PetProjectSpring.auth.repositories.SessionRepository;
import com.example.PetProjectSpring.auth.responses.SignUpResponseBody;
import com.example.PetProjectSpring.auth.types.FullSessionData;
import com.example.PetProjectSpring.auth.types.JwtTokens;
import com.example.PetProjectSpring.user.entities.Role;
import com.example.PetProjectSpring.user.entities.UserEntity;
import com.example.PetProjectSpring.user.models.User;
import com.example.PetProjectSpring.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
//    public AuthService(
//        @Autowired JwtService jwtService,
//        @Autowired UserRepository userRepository,
//        @Autowired SessionRepository sessionRepository
//    ) {
//        this.jwtService = jwtService;
//        this.userRepository = userRepository;
//        this.sessionRepository = sessionRepository;
//    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    private boolean comparePasswords(String password, String hash) {
        return encoder.matches(password, hash);
    }

    private FullSessionData createNewSession(UserEntity user, String userAgent) throws IllegalAccessException {
        SessionEntity newSession = new SessionEntity();
        newSession.setUser(user);
        newSession.setUserAgent(userAgent);

        TokenPayloadDto tokenPayloadDto = new TokenPayloadDto();
        tokenPayloadDto.sessionId = newSession.getId();
        tokenPayloadDto.sub = user.getId();

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        tokenPayloadDto.roles = roles;

        JwtTokens tokens = jwtService.generateTokens(tokenPayloadDto);
        newSession.setRefreshToken(tokens.refreshToken);

        FullSessionData result = new FullSessionData();
        result.session = newSession;
        result.tokens = tokens;

        return result;
    }

    public SignUpResponseBody signUpLocal(@RequestBody SignUpDto dto, String userAgent) throws UserAlreadyRegistered, IllegalAccessException {
        Optional<UserEntity> foundUser = this.userRepository.findByEmail(dto.email);
        if(foundUser == null) throw new UserAlreadyRegistered();

        UserEntity newUser = new UserEntity();
        newUser.setEmail(dto.email);
        newUser.setPassword(encoder.encode(dto.password));

        FullSessionData sessionData = this.createNewSession(newUser, userAgent);

        SessionEntity newSession = sessionRepository.save(sessionData.session);
        newUser = userRepository.save(newUser);

        SignUpResponseBody result = new SignUpResponseBody();
        result.user = new User();
        result.user.setId(newUser.getId());
        result.user.setEmail(newUser.getEmail());
        result.tokens = sessionData.tokens;

        return result;
    }
}
