package com.orlov_prokhor.weathers_of_cities.ui;


import android.app.Activity;
import android.arch.lifecycle.ViewModel;
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
    RegisterUserActivity.start(activity, user.getUserName());
  }


  public void login(Activity activity) {
    if (login.get()) {
      MainActivity.start(activity, user);
    }
  }

}
