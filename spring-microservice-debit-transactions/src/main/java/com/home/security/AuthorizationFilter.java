package com.home.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;

@Component
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Value("${auth.enabled}")
    private boolean enabled;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(!enabled) {
            filterChain.doFilter(request, response);
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info(authHeader);

        if(authHeader == null || authHeader.isBlank()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else if(!checkAuthorization(authHeader)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            HashSet<GrantedAuthority> set = new HashSet<>();
            set.add(new SimpleGrantedAuthority(tokenService.getRole(getToken(authHeader))));

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    null,
                    null,
                    set
            );

            log.info("Auth token is ready");

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            log.info("Auth token has got details");
            SecurityContextHolder.getContext().setAuthentication(authToken);

            log.info("SecurityContextHolder has got authentication");
            filterChain.doFilter(request, response);
        }

    }

    public String getToken(String auth) {
        return auth.substring(7);
    }

    public boolean checkAuthorization(String auth) {
        if(!auth.startsWith("Bearer ")) {
            return false;
        }

        String token = getToken(auth);
        log.info(token);
        return tokenService.checkToken(token);
    }
}
