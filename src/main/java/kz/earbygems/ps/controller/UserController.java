package kz.earbygems.ps.controller;

import kz.earbygems.ps.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService service;

  @PostMapping
  public String create(@RequestParam String username,
                       @RequestParam String password,
                       @RequestParam String role) {
    return service.create(username, password, role);
  }

  @DeleteMapping
  public String delete(@RequestParam String username) {
    return service.delete(username);
  }

}
