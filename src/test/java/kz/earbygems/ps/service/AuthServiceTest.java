package kz.earbygems.ps.service;

import kz.earbygems.ps.config.SecurityConfig;
import kz.earbygems.ps.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class AuthServiceTest {

  @InjectMocks
  private AuthServiceImpl authService;

  @Mock
  private UserDetailsManager users;

  @Mock
  private TokenService tokenService;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Test
  public void login_thenExpectCorrectToken() {

    var user = User.builder()
                   .username("username")
                   .password("password")
                   .authorities(SecurityConfig.ADMIN)
                   .build();

    var token = "token";

    doReturn(true).when(users).userExists(user.getUsername());
    doReturn(user).when(users).loadUserByUsername(user.getUsername());
    doReturn(true).when(passwordEncoder).matches(user.getPassword(), user.getPassword());
    doReturn(token).when(tokenService).generateToken(user.getUsername());

    //
    //
    var generated = authService.login(user.getUsername(), user.getPassword());
    //
    //

    verify(users).userExists(user.getUsername());
    verify(users).loadUserByUsername(user.getUsername());
    verify(passwordEncoder).matches(user.getPassword(), user.getPassword());
    verify(tokenService).generateToken(user.getUsername());

    assertNotNull(generated);
    assertEquals(generated, token);

  }

  @Test
  public void currentUser_thenExpectCorrectUsername() {

    var user = User.builder()
                   .username("username")
                   .password("password")
                   .authorities(SecurityConfig.ADMIN)
                   .build();

    var auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user, user.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(auth);

    //
    //
    var username = authService.currentUser();
    //
    //

    assertNotNull(username);
    assertEquals(username, user.getUsername());
  }

}
