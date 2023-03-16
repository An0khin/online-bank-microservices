package com.home.security.config;

import com.home.security.SecretType;
import com.home.security.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

@Component
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    public static final String LOGIN_URL = "http://localhost:8082/auth/login";
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RestTemplate template;
    @Value("${auth.enabled}")
    private boolean enabled;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info(request.getRequestURI());
        log.info(request.getMethod());

        if(!enabled || request.getRequestURI().equals("/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        if(request.getCookies() == null) {
            log.info("No cookies");
            response.sendRedirect(LOGIN_URL);
            return;
        }

//        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String authCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("Authorization"))
                .findFirst()
                .get()
                .getValue();

        String refreshCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("Refresh"))
                .findFirst()
                .get()
                .getValue();

        log.info(authCookie);
        log.info(refreshCookie);

        if(refreshCookie == null || refreshCookie.isBlank() || !checkAuthorization(refreshCookie, SecretType.REFRESH)) {
            response.sendRedirect(LOGIN_URL);
        } else {
            if(authCookie == null || authCookie.isBlank() || !checkAuthorization(authCookie, SecretType.ACCESS)) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                log.info("Cookie is null or authorization failed");

                String name = tokenService.getName(refreshCookie, SecretType.REFRESH);
                String role = tokenService.getRole(refreshCookie, SecretType.REFRESH);

                String newRefreshToken = template.getForObject("http://localhost:8082/token/new_refresh_token?username={username}&role={role}",
                        String.class,
                        name,
                        role);

                String newAccessToken = template.getForObject("http://localhost:8082/token/new_token?username={username}&role={role}",
                        String.class,
                        name,
                        role);

                Cookie newAuthorizationCookie = new Cookie("Authorization", newAccessToken);
                newAuthorizationCookie.setPath("/");
                Cookie newRefreshCookie = new Cookie("Refresh", newRefreshToken);
                newRefreshCookie.setPath("/");

                response.addCookie(newAuthorizationCookie);
                response.addCookie(newRefreshCookie);

                log.info("new token - " + newAccessToken);
                authCookie = newAccessToken;
                log.info("new auth cookie - " + authCookie);
            }

            log.info("new auth cookie after if - " + authCookie);
            HashSet<GrantedAuthority> set = new HashSet<>();
            set.add(new SimpleGrantedAuthority(tokenService.getRole(getToken(authCookie), SecretType.ACCESS)));

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    tokenService.getName(getToken(authCookie), SecretType.ACCESS),
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

    public boolean checkAuthorization(String auth, SecretType type) {
        if(type == SecretType.ACCESS) {
            if(!auth.startsWith("Bearer_")) {
                return false;
            }

            String token = getToken(auth);
            log.info(token);
            return tokenService.checkToken(token, type);
        } else {
            return tokenService.checkToken(auth, type);
        }
    }
}
