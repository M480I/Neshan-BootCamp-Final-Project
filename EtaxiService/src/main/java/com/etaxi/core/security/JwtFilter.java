package com.etaxi.core.security;

import com.etaxi.core.exception.NoTokenInHeaderException;
import com.etaxi.core.security.token.Jwt;
import com.etaxi.core.security.token.JwtService;
import com.etaxi.core.security.token.JwtStatus;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtFilter extends OncePerRequestFilter {

    JwtService jwtService;
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        final Jwt jwt;

        try {
            jwt = jwtService.extractTokenFromHeader(header);
        }
        catch (NoTokenInHeaderException exception) {
            filterChain.doFilter(request, response);
            return;
        }

        if (jwt.getStatus() != JwtStatus.VALID) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwt.getClaims().getSubject();

        if (username != null
                && SecurityContextHolder.getContext().getAuthentication() == null
        ) {
            UserDetails user = userDetailsService.loadUserByUsername(
                    jwt.getClaims().getSubject()
            );

            if (user != null) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                user.getAuthorities()
                        );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);

    }
}

