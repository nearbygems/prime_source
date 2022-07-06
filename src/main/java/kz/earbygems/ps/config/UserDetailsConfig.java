package kz.earbygems.ps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class UserDetailsConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsManager users(DataSource dataSource) {
    var users = new JdbcUserDetailsManager(dataSource);
    if (!users.userExists("admin")) {
      users.createUser(User.builder()
                           .passwordEncoder((password) -> passwordEncoder().encode(password))
                           .username("admin")
                           .password("admin")
                           .roles(SecurityConfig.ADMIN)
                           .build());
    }
    return users;
  }

}
