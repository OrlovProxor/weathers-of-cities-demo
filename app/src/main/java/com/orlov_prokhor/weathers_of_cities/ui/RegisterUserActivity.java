package com.orlov_prokhor.weathers_of_cities.ui;

import static com.orlov_prokhor.weathers_of_cities.ui.RegisterUserActivityModel.OK;
import static com.orlov_prokhor.weathers_of_cities.ui.RegisterUserActivityModel.USER_NAME;
import static com.orlov_prokhor.weathers_of_cities.ui.RegisterUserActivityModel.validateUserName;
import static com.orlov_prokhor.weathers_of_cities.utils.PasswordValidator.validatePassword;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.Observable.OnPropertyChangedCallback;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.orlov_prokhor.weathers_of_cities.App;
import com.orlov_prokhor.weathers_of_cities.BR;
import com.orlov_prokhor.weathers_of_cities.R;
import com.orlov_prokhor.weathers_of_cities.databinding.ActivityRegisterUserBinding;
import com.orlov_prokhor.weathers_of_cities.utils.ActivityUtils;

public class RegisterUserActivity extends AppCompatActivity {

  RegisterUserActivityModel   model;
  ActivityRegisterUserBinding binding;
  OnPropertyChangedCallback   onInputErrors;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register_user);

    binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_register_user);
    model = ViewModelProviders.of(this).get(RegisterUserActivityModel.class);
    binding.setViewModel(model);
    String userName = this.getIntent().getExtras().getString(USER_NAME);
    model.setUserNameAndName(userName);
    bindAShowErrors();

    ActivityUtils.hideSoftKeyboardForAllUI(this, findViewById(R.id.parent));
  }

  public void bindAShowErrors() {
    onInputErrors = new OnPropertyChangedCallback() {
      @Override
      public void onPropertyChanged(Observable sender, int propertyId) {

        if ("".equals(model.registerUser.getName())) {
          binding.name
              .setError(getResources().getString(R.string.incorrect_name));
        } else {
          binding.name.setError(null);
        }

        if (!validateUserName(model.registerUser.getUserName())) {
          binding.userName
              .setError(getResources().getString(R.string.incorrect_user_name));
        } else {
          binding.userName.setError(null);
        }

        String err = validatePassword(model.registerUser.getPassword(), App.getInstance());
        if (!OK.equals(err)) {
          binding.password.setError(err);
        } else {
          binding.password.setError(null);
        }

        err = validatePassword(model.registerUser.getPasswordConfirmation(), App.getInstance());
        if (!OK.equals(err)) {
          binding.passwordConfirmation.setError(err);
        } else {
          binding.password.setError(null);
        }


      }
    };
    model.registerUser.addOnPropertyChangedCallback(onInputErrors);
    model.registerUser.notifyPropertyChanged(BR._all);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    model.registerUser.removeOnPropertyChangedCallback(onInputErrors);
  }
}
