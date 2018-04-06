package com.github.karthyks.project.era.grpc.client.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JwtCreator {

  /**
   * SecureRandom random = new SecureRandom();
   * byte[] bytes = new byte[20];
   * random.nextBytes(bytes);
   * String token = bytes.toString();
   * System.out.println(new String(Base64.getEncoder().encode(bytes)));
   */

  public static String create(String issuer, String subject) {
    final long iat = System.currentTimeMillis();
    final long exp = iat + (48 * 60 * 1000);

    Algorithm algorithm = Algorithm.none();
    try {
      algorithm = Algorithm.HMAC256(Constant.JWT_SECRET);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return JWT.create()
        .withClaim("exp", new Date(exp))
        .withClaim("iat", new Date(iat))
        .withClaim("sub", subject)
        .withIssuer(issuer).sign(algorithm);
  }
}
