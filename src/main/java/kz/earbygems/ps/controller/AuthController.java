package kz.earbygems.ps.controller;

import kz.earbygems.ps.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService service;

  @PostMapping("/login")
  public String login(@RequestParam String username,
                      @RequestParam String password) {
    return service.login(username, password);
  }

}
