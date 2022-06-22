package com.abdiahmed.springbootblog.security;

import com.abdiahmed.springbootblog.error.AccessDeniedHandlerJwt;
import com.abdiahmed.springbootblog.service.impl.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

  @Autowired CustomUserDetailService userService;
  @Autowired JwtTokenProvider jwtTokenProvider;
  @Autowired private JWTAuthEntryPoint jwtAuthEntryPoint;
  @Autowired private AccessDeniedHandlerJwt accessDeniedHandlerJwt;
  @Autowired private JwtAuthFilter jwtAuthFilter;

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http =
        http.csrf()
            .disable()
            .formLogin()
            .disable()
            .httpBasic()
            .disable()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthEntryPoint)
            .accessDeniedHandler(accessDeniedHandlerJwt)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            //            .antMatchers("/api/v1/**")
            //            .permitAll()
            .antMatchers("/api/v1/auth/**")
            .permitAll()
            .antMatchers("/api/v1/posts/1/comments")
            .hasAuthority("User:Read")
            .anyRequest()
            .authenticated()
            .and();

    http = http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
