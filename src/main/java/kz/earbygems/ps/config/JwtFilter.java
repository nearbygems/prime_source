package kz.earbygems.ps.config;

import kz.earbygems.ps.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

  private final TokenService tokenService;

  @Override
  public void doFilter(ServletRequest request,
                       ServletResponse response,
                       FilterChain chain) throws ServletException, IOException {

    var token = getTokenFromRequest((HttpServletRequest) request);

    if (token != null && tokenService.validateToken(token)) {

      var user = tokenService.getUserFromToken(token);

      var auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user, user.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    chain.doFilter(request, response);

  }

  private String getTokenFromRequest(HttpServletRequest request) {

    var bearer = request.getHeader("Authorization");

    if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
      return bearer.substring(7);
    }

    return null;
  }

}
