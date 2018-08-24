package com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity;

import android.databinding.Bindable;
import com.orlov_prokhor.weathers_of_cities.BR;
import lombok.Data;


@Data
public class RegisterUser extends User {

  @Bindable
  private String passwordConfirmation = "";

  public void setPasswordConfirmation(String passwordConfirmation) {
    this.passwordConfirmation = passwordConfirmation;
    notifyPropertyChanged(BR.passwordConfirmation);
  }


}

