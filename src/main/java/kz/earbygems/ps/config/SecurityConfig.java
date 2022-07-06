package kz.earbygems.ps.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  public static final String ADMIN = "ADMIN";
  public static final String USER = "USER";

  private final JwtFilter filter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.httpBasic(Customizer.withDefaults())
               .csrf().disable()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .authorizeRequests()
               .antMatchers("/login").permitAll()
               .antMatchers("/actuator*").permitAll()
               .antMatchers("/users*").hasRole(ADMIN)
               .antMatchers("/tasks*").hasRole(USER)
               .antMatchers("/users/*").hasRole(ADMIN)
               .antMatchers("/tasks/*").hasRole(USER)
               .and()
               .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
               .build();
  }

}
