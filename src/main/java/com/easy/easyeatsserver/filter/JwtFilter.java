package com.easy.easyeatsserver.filter;

import com.easy.easyeatsserver.model.Authority;
import com.easy.easyeatsserver.repository.AuthorityRepository;
import com.easy.easyeatsserver.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// block request，validate JWT token, extract email(subject) from jwt token，get authority by email
// => then doing authorization checking
@Component
public class JwtFilter extends OncePerRequestFilter { // only filter once, when client -> server
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private AuthorityRepository authorityRepository;
    private JwtUtil jwtUtil;

    @Autowired
    public JwtFilter(AuthorityRepository authorityRepository, JwtUtil jwtUtil) {
        this.authorityRepository = authorityRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1. Get the jwt token from header with key=Authorization
        final String authorizationHeader = httpServletRequest.getHeader(HEADER);
        String jwt = null;
        if (authorizationHeader != null && authorizationHeader.startsWith(PREFIX)) {
            jwt = authorizationHeader.substring(PREFIX.length());
        }

        // 2. Get user info from jwt, build an object for further filter checkin and store it in Security Context Holder
        // SecurityContextHolder.getContext().getAuthentication() == null => the token has not authed yet
        // (SecurityContext store the authed infos) => The last condition is to avoid multiple check,
        // if the jwt has pass the filter before, then there is no need to do another check.
        if (jwt != null && jwtUtil.validateToken(jwt) && SecurityContextHolder.getContext().getAuthentication() == null) {
            String email = jwtUtil.extractEmail(jwt);
            Authority authority = authorityRepository.findById(email).orElse(null);

            // store the username and authority into UsernamePasswordAuthenticationToken
            // for UsernamePasswordAuthenticationFilter to check
            if (authority != null) {
                List<GrantedAuthority> grantedAuthorities = Arrays.asList(
                        new GrantedAuthority[]{
                                new SimpleGrantedAuthority(authority.getAuthority())
                        }
                );
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        email, null, grantedAuthorities
                );
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        // Call next filters to do their jobs
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
