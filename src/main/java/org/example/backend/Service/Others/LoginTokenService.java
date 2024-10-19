package org.example.backend.Service.Others;

import java.util.List;
import org.example.backend.Entity.Others.LoginToken;

public interface LoginTokenService {
  LoginToken getById(String accountId);
  List<LoginToken> getAll();
  boolean createLoginToken(LoginToken loginToken);
  boolean updateLoginToken(LoginToken loginToken);
  boolean deleteLoginToken(String accountId);
}
