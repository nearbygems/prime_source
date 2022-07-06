package kz.earbygems.ps.service.impl;

import kz.earbygems.ps.config.SecurityConfig;
import kz.earbygems.ps.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserDetailsManager users;
  private final PasswordEncoder passwordEncoder;

  @Override
  public String create(String username, String password, String role) {
    if (!role.equals(SecurityConfig.ADMIN) && !role.equals(SecurityConfig.USER)) {
      throw new RuntimeException("Unexpected role");
    }
    users.createUser(User.builder()
                         .passwordEncoder(passwordEncoder::encode)
                         .username(username)
                         .password(password)
                         .roles(role)
                         .build());
    return username;
  }

  @Override
  public String delete(String username) {
    users.deleteUser(username);
    return username;
  }

}
