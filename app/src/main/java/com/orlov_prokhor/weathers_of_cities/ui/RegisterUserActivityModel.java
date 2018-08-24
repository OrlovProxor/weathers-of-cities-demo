package com.orlov_prokhor.weathers_of_cities.ui;

import static com.orlov_prokhor.weathers_of_cities.utils.PasswordValidator.validatePassword;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.Observable;
import android.databinding.Observable.OnPropertyChangedCallback;
import android.databinding.ObservableField;
import android.view.View;
import com.orlov_prokhor.weathers_of_cities.App;
import com.orlov_prokhor.weathers_of_cities.R;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.RegisterUser;
import com.orlov_prokhor.weathers_of_cities.ui.main_activity.MainActivity;

public class RegisterUserActivityModel extends ViewModel {

  public static final String                   USER_NAME      = "userName";
  public static final String                   OK             = "ok";
  public              RegisterUser             registerUser   = new RegisterUser();
  public              ObservableField<String>  status         = new ObservableField<>();
  public              ObservableField<Boolean> enableRegister = new ObservableField<>();
  private             Resources                resources;

  public RegisterUserActivityModel() {
    super();

    resources = App.getInstance().getResources();

    registerUser.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
      @Override
      public void onPropertyChanged(Observable sender, int propertyId) {
        String isCorrect = validateRegisterUserData();

        status.set(isCorrect);
        if (OK.equals(isCorrect)) {
          //save and close
          enableRegister.set(true);
        } else {
          enableRegister.set(false);
        }

      }
    });
  }

  public static boolean validateUserName(String username) {
    return (username != null) && username.matches("[A-Za-z0-9_]+");
  }

  public void setUserNameAndName(@lombok.NonNull String userName) {

    if ("".equals(userName) || userName == null) {
      return;
    }

    if ("".equals(registerUser.getUserName())) {
      registerUser.setUserName(userName);
    }

    if ("".equals(registerUser.getName())) {
      registerUser.setName(userName);
    }
  }

  public String validateRegisterUserData() {
    if (!validateUserName(registerUser.getUserName())) {
      return resources.getString(R.string.incorrect_user_name);
    }
    if (App.getInstance().getAppComponent().getUserRepository().getUser(registerUser.getUserName())
        != null) {

      return resources.getString(R.string.user_name_already_exists);
    }

    if ("".equals(registerUser.getName())) {
      return resources.getString(R.string.incorrect_name);
    }
    String validatePass = validatePassword(registerUser.getPassword(), App.getInstance());
    if (!OK.equals(validatePass)) {
      return validatePass;
    }
    if (!registerUser.getPassword().equals(registerUser.getPasswordConfirmation())) {

      return resources.getString(R.string.not_equal_passwords);
    }

    return OK;
  }

  @Deprecated
  public Boolean validateRegisterUser() {
    String s = validateRegisterUserData();
    return OK.equals(s);
  }

  public void registerUserAndShowMainActivity(View view, Activity activity) {

    String isCorrect = validateRegisterUserData();

    status.set(isCorrect);
    if (!OK.equals(isCorrect)) {
      return;
    }

    App.getInstance().getAppComponent().getUserRepository().saveUser(registerUser);
    App.getInstance().getAppComponent().getUser().setUser(registerUser);
    Intent myIntent = new Intent(App.getInstance(), MainActivity.class);
    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    activity.startActivity(myIntent);
    activity.finish();

  }

}


