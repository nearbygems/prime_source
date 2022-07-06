package kz.earbygems.ps.service;

public interface AuthService {

  String login(String username, String password);

  String currentUser();

}
