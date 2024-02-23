//package com.example.PetProjectSpring.core.aspects;
//
//import com.example.PetProjectSpring.auth.services.JwtService;
//import com.example.PetProjectSpring.core.exceptions.CoreRestException;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.JwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Collection;
//import java.util.stream.Collectors;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class JwtRequestFilter extends OncePerRequestFilter {
//    private JwtService jwtService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//        String token = null;
//        String userId = null;
//
//
//        try {
//            token = authHeader.substring(7);
//            userId = this.jwtService.getUserId(token);
//
//        } catch (ExpiredJwtException err) {
//            log.debug("Authorization token expired");
//        } catch (JwtException err) {
//            log.debug("Authorization token is invalid");
//        }
//
//        if(userId != null) {
//            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(
//                userId,
//                null,
//                (Collection<? extends GrantedAuthority>) jwtService.getUserRoles(token).stream().map(role -> {
//                    return new SimpleGrantedAuthority(role);
//                }).collect(Collectors.toList())
//            );
//
//            SecurityContextHolder.getContext().setAuthentication(upToken);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
