package com.abdiahmed.springbootblog.security;

import com.abdiahmed.springbootblog.service.impl.CustomUserDetailService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtTokenProvider jwtTokenProvider;
  private final CustomUserDetailService userService;

  public JwtAuthFilter(JwtTokenProvider jwtTokenProvider,CustomUserDetailService userService) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = getTokenFromHeader(request);
    if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
      String username = jwtTokenProvider.getUsernameFromToken(token);
      UserDetails user = userService.loadUserByUsername(username);

      UsernamePasswordAuthenticationToken auth =
          new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

      auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);
  }

  private String getTokenFromHeader(HttpServletRequest request) {
    String bearToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearToken) && bearToken.startsWith("Bearer")) {
      return bearToken.substring(7);
    }
    return null;
  }
}
