package kz.earbygems.ps.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kz.earbygems.ps.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

  @Value("${jwt.secret}")
  private String jwtSecret;

  private final UserDetailsManager users;

  @Override
  public String generateToken(String username) {
    return Jwts.builder()
               .setSubject(username)
               .setExpiration(Date.from(LocalDateTime.now()
                                                     .plusHours(1)
                                                     .atZone(ZoneId.of("Asia/Almaty"))
                                                     .toInstant()))
               .signWith(SignatureAlgorithm.HS512, jwtSecret)
               .compact();
  }

  @Override
  public boolean validateToken(String token) {

    var claims = Jwts.parser()
                     .setSigningKey(jwtSecret)
                     .parseClaimsJws(token)
                     .getBody();

    var now = Date.from(LocalDateTime.now()
                                     .atZone(ZoneId.of("Asia/Almaty"))
                                     .toInstant());

    return users.userExists(claims.getSubject()) && claims.getExpiration().after(now);
  }

  @Override
  public UserDetails getUserFromToken(String token) {

    var username = Jwts.parser()
                       .setSigningKey(jwtSecret)
                       .parseClaimsJws(token)
                       .getBody()
                       .getSubject();

    return users.loadUserByUsername(username);
  }

}
