package com.orlov_prokhor.weathers_of_cities.ui;


import static com.orlov_prokhor.weathers_of_cities.ui.RegisterUserActivityModel.USER_NAME;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.databinding.Observable;
import android.databinding.Observable.OnPropertyChangedCallback;
import android.databinding.ObservableField;
import android.view.View;
import com.orlov_prokhor.weathers_of_cities.App;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.User;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.UserRepository;
import com.orlov_prokhor.weathers_of_cities.ui.main_activity.MainActivity;

public class LoginActivityModel extends ViewModel {


  public User                     user  = new User();
  public ObservableField<Boolean> login = new ObservableField<>();
  UserRepository userRepository;

  public LoginActivityModel() {
    super();
    login.set(false);
    userRepository = App.getInstance().getAppComponent().getUserRepository();
    User lastUser = userRepository.getLastUser();
    if (lastUser != null) {
      user = lastUser;
      user.setPassword("");
    }

    user.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
      @Override
      public void onPropertyChanged(Observable sender, int propertyId) {

        login.set(login(user));


      }
    });
  }

  public Boolean login(User user) {

    User u = userRepository.getUser(user.getUserName());
    if (u == null) {
      return false;
    }

    if (user.getUserName().equals(u.getUserName()) && user.getPassword().equals(u.getPassword())) {
      return true;
    } else {
      return false;
    }
  }

  public void showRegisterUserActivity(View view, Activity activity) {

    Intent myIntent = new Intent(App.getInstance(), RegisterUserActivity.class);
    myIntent.putExtra(USER_NAME, user.getUserName());
    activity.startActivity(myIntent);
    activity.finish();

  }


  public void login() {
    if (login.get()) {
      Intent myIntent = new Intent(App.getInstance(), MainActivity.class);
      myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      App.getInstance().startActivity(myIntent);
    }
  }

}
