package kz.earbygems.ps.service.impl;

import kz.earbygems.ps.service.AuthService;
import kz.earbygems.ps.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserDetailsManager users;
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public String login(String username, String password) {

    if (!users.userExists(username)) {
      throw new UsernameNotFoundException("User with username `" + username + "` not found");
    }

    var user = users.loadUserByUsername(username);

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new BadCredentialsException("Username or password incorrect");
    }

    return tokenService.generateToken(username);
  }

  @Override
  public String currentUser() {
    return SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getPrincipal()
                                .toString();
  }
}
