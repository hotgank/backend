package org.example.backend.Service.ServiceImpl.Others;

import org.example.backend.Entity.Others.LoginToken;
import org.example.backend.Mapper.Others.LoginTokenMapper;
import org.example.backend.Service.Others.LoginTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginTokenServiceImpl implements LoginTokenService {

  @Autowired
  private LoginTokenMapper loginTokenMapper;

  @Override
  public LoginToken getById(String accountId) {
    return loginTokenMapper.selectById(accountId);
  }

  @Override
  public List<LoginToken> getAll() {
    return loginTokenMapper.selectList(null);
  }

  @Override
  public boolean createLoginToken(LoginToken loginToken) {
    return loginTokenMapper.insert(loginToken) > 0;
  }

  @Override
  public boolean updateLoginToken(LoginToken loginToken) {
    return loginTokenMapper.updateById(loginToken) > 0;
  }

  @Override
  public boolean deleteLoginToken(String accountId) {
    return loginTokenMapper.deleteById(accountId) > 0;
  }
}
