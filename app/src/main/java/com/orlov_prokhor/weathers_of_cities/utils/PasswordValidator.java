package com.orlov_prokhor.weathers_of_cities.utils;

import android.content.Context;
import com.orlov_prokhor.weathers_of_cities.R;
import java.util.Random;

//https://gist.github.com/afiqiqmal/e2d52e87848d8e414d17a078c85590dc
//https://stackoverflow.com/questions/36097097/password-validate-8-digits-contains-upper-lowercase-and-a-special-character
public class PasswordValidator {

  public static boolean validate(String password) {
    if (password.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})")) {
      return true;
    }

    return false;
  }

  public static boolean symbol(String password) {
    if (password.matches(".*[@#$%].*")) {
      return true;
    }

    return false;
  }

  public static boolean uppcase(String password) {
    if (password.matches(".*[A-Z].*")) {
      return true;
    }

    return false;
  }

  public static boolean lowerCase(String password) {
    if (password.matches(".*[a-z].*")) {
      return true;
    }

    return false;
  }

  public static boolean digit(String password) {
    if (password.matches(".*\\d.*")) {
      return true;
    }

    return false;
  }

  public static String validatePassword(String pass, Context context) {
    boolean flag   = true;
    String  result = "";

    if (pass.length() < 8) {
      result += context.getResources().getString(R.string.password_must_more_than_8);
      flag = false;
    } else if (!digit(pass)) {
      result += context.getResources().getString(R.string.password_must_contains_digit);
      flag = false;
    }/* else if (!symbol(pass)) {
      result+="Password must contains symbol" ;
      flag = false;
    }*/ else if (!uppcase(pass)) {
      result += context.getResources().getString(R.string.password_must_contains_upper_case);

      flag = false;
    } else if (!lowerCase(pass)) {
      result += context.getResources().getString(R.string.password_must_contains_lower_case);

      flag = false;
    }
    if (flag) {
      result = "ok";
    }

    return result;
  }

  public static String randomString(int len) {
    Random        rnd = new Random();
    String        AB  = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz@#$%&";
    StringBuilder sb  = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      sb.append(AB.charAt(rnd.nextInt(AB.length())));
    }
    return sb.toString();
  }
}
