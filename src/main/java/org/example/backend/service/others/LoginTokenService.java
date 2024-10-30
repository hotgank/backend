package org.example.backend.service.others;

import java.util.List;
import org.example.backend.entity.others.LoginToken;

public interface LoginTokenService {
  LoginToken getById(String accountId);
  List<LoginToken> getAll();
  boolean createLoginToken(LoginToken loginToken);
  boolean updateLoginToken(LoginToken loginToken);
  boolean deleteLoginToken(String accountId);
}
