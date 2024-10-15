package com.test.backend.Config;

import com.test.backend.Service.CustomUserDetailsService;
import com.test.backend.Util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;




@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private JwtUtil jwUtil;

    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public JwtRequestFilter(JwtUtil jwUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwUtil = jwUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwUtil.extractUsername(jwt);
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);


            if (jwUtil.validateToken(jwt, username)) {

                String roles = jwUtil.extractRole(jwt);


                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();


                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);


                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }


        filterChain.doFilter(request, response);
    }
}
