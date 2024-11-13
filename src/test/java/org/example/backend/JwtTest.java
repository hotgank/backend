package org.example.backend;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.example.backend.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.mysql.cj.protocol.ExportControlled.sign;

public class JwtTest {

  private static final Logger log = LoggerFactory.getLogger(JwtTest.class);
  private JwtUtil jwtUtil;
  @Test
  public void testGen(){
    String jwt = "123456ABCabc";
    JwtUtil jwtUtil = new JwtUtil();
    String token = jwtUtil.generateToken(jwt);
    JwtTest.log.info(token);
    String userId = jwtUtil.extractUserId(token);
    JwtTest.log.info(userId);
  }
}