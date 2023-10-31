package com.team3.mudjeans.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JWTokenUtils tokenUtils;

    private static final Set<String> UNSECURED_PATHS =
            Set.of("/auth", "/h2-console");

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(req, res);

//        String encodedToken = null;
//
//        try {
//            String path = req.getServletPath();
//
//            if (HttpMethod.OPTIONS.matches(req.getMethod()) || !UNSECURED_PATHS.stream().noneMatch(path::startsWith)) {
//                chain.doFilter(req, res);
//                return;
//            }
//
//            encodedToken = req.getHeader(HttpHeaders.AUTHORIZATION);
//
//            if (encodedToken == null) {
//                throw new AuthenticationException("authentication problem");
//            }
//
//            encodedToken = encodedToken.replace("Bearer ", "");
//
//            JWTokenInfo tokenInfo = tokenUtils.decode(encodedToken, false);
//
//            req.setAttribute(JWTokenInfo.KEY, tokenInfo);
//
//            chain.doFilter(req, res);
//        } catch (AuthenticationException e) {
//            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Error");
//        }
    }
}
