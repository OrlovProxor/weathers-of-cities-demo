package com.orlov_prokhor.weathers_of_cities.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.orlov_prokhor.weathers_of_cities.R;
import com.orlov_prokhor.weathers_of_cities.databinding.ActivityLoginBinding;
import com.orlov_prokhor.weathers_of_cities.utils.ActivityUtils;

public class LoginActivity extends AppCompatActivity {

  LoginActivityModel   model;
  ActivityLoginBinding binding;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    model = ViewModelProviders.of(this).get(LoginActivityModel.class);

    binding.setViewModel(model);
    ActivityUtils.hideSoftKeyboardForAllUI(this, findViewById(R.id.parent));
    if (!"".equals(model.user.getUserName())) {
      binding.password.requestFocus();
    }
  }
}
