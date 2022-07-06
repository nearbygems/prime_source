package kz.earbygems.ps.service;

public interface UserService {

  String create(String username, String password, String role);

  String delete(String username);

}
