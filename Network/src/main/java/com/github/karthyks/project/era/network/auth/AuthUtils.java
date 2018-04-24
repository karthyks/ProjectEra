package com.github.karthyks.project.era.network.auth;

import java.security.MessageDigest;

public class AuthUtils {

  public static String digest(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(password.getBytes());
      byte[] byteData = md.digest();
      StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < byteData.length; i++) {
        String hex = Integer.toHexString(0xff & byteData[i]);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
      }
      System.out.println("Password is " + hexString.toString());
      return hexString.toString();
    } catch (Exception e) {
      throw new IllegalStateException("Cannot encode credentials");
    }
  }
}
