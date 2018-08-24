package com.orlov_prokhor.weathers_of_cities.interactor.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.User;

public class UserRepository {

  public static final String LAST_USER_NAME   = "lastUserName";
  public static final String GENERAL_SETTINGS = "general_settings";
  Context           context;
  SharedPreferences prefsGeneralSettings;
  SharedPreferences prefsUsers;


  public UserRepository(Context context) {
    this.context = context;
    prefsGeneralSettings = context.getSharedPreferences(GENERAL_SETTINGS, context.MODE_PRIVATE);
    prefsUsers = context.getSharedPreferences("users", context.MODE_PRIVATE);
  }

  public User getUser(String userName) {
    if ("".equals(userName)) {
      return null;
    }
    String uJson = prefsUsers.getString(userName, null);
    if (uJson == null) {
      return null;
    }
    Gson gson = new Gson();
    User u    = gson.fromJson(uJson, User.class);
    return u;
  }

  public void saveUser(User user) {
    Editor prefsEditor = prefsUsers.edit();
    Gson   gson        = new Gson();
    String json        = gson.toJson(user);
    prefsEditor.putString(user.getUserName(), json);
    prefsEditor.commit();
  }

  public User getLastUser() {
    String lastUserName = prefsGeneralSettings.getString(LAST_USER_NAME, null);
    if (lastUserName == null) {
      return null;
    }
    User u = getUser(lastUserName);
    return u;
  }

  public void setLastUser(User user) {
    Editor prefsEditor = prefsGeneralSettings.edit();

    prefsEditor.putString(LAST_USER_NAME, user.getUserName());
    prefsEditor.commit();
  }
}
