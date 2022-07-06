package kz.earbygems.ps.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {

  String generateToken(String username);

  boolean validateToken(String token);

  UserDetails getUserFromToken(String token);

}
