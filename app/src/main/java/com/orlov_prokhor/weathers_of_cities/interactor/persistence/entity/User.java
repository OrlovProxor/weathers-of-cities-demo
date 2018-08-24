package com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.orlov_prokhor.weathers_of_cities.BR;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
public class User extends BaseObservable {

  @Bindable private String userName = "";
  @Bindable private String name     = "";
  @Bindable private String password = "";

  @Builder
  public User(@NonNull String userName, @NonNull String name, @NonNull String password) {
    this.userName = userName;
    this.name = name;
    this.password = password;
  }

  public User() {
  }

  public void setUserName(String userName) {
    this.userName = userName;
    notifyPropertyChanged(BR.userName);
  }

  public void setPassword(String password) {
    this.password = password;
    notifyPropertyChanged(BR.password);
  }

  public void setName(String name) {
    this.name = name;
    notifyPropertyChanged(BR.name);
  }
}
