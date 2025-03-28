package ru.kata.spring.boot_security.demo.configs;

import jakarta.servlet.http.Cookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf",
                UUID.randomUUID().toString());
        Cookie cookie = new Cookie("XSRF-TOKEN", csrfToken.getToken());
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        response.addCookie(cookie);
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ADMIN")) {
            response.sendRedirect("/admin");
        } else if (roles.contains("USER")) {
            response.sendRedirect("/user");
        } else {
            response.sendRedirect("/");
        }
    }
}