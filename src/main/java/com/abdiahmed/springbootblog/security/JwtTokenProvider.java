package com.abdiahmed.springbootblog.security;

import com.abdiahmed.springbootblog.error.BlogAPIException;
import com.abdiahmed.springbootblog.service.impl.CustomUserDetailService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
  final CustomUserDetailService userService;

  @Value("${app.jwtSecretKey}")
  private String key;

  @Value("${app.jwt-expiration-milliseconds}")
  private Long miliseconds;

  public JwtTokenProvider(CustomUserDetailService userService) {
    this.userService = userService;
  }

  public String createJwtToken(Authentication auth) {
    String username = auth.getName();
    Date currentTime = new Date();
    Date expiration = new Date(currentTime.getTime() + miliseconds);

    UserDetails user = userService.loadUserByUsername(username);
    String jws =
        Jwts.builder()
            .setSubject(user.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS512, key)
            .compact();
    return jws;
  }

  public String getUsernameFromToken(String token) {
    Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

  public boolean validateToken(String token) {
    try {

      Jwts.parser().setSigningKey(key).parseClaimsJws(token);

      return true;

    } catch (SignatureException ex) {
      throw new SignatureException("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
    } catch (JwtException e) {

      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Bad JWT token");
    } catch (InsufficientAuthenticationException e) {

      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Bad JWT token insuf");
    } catch (Exception e) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Bad JWT request");
    }
  }
}
