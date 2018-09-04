package com.orlov_prokhor.weathers_of_cities.domains;

import android.databinding.ObservableField;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.User;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.WeatherCity;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.UserRepository;
import lombok.Data;

@Data
public class UserCurrent {

  private UserRepository               userRepository;
  private User                         user     = new User();
  private ObservableField<WeatherCity> lastCity = new ObservableField<>();
  public UserCurrent(
      UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void setUser(User user) {
    this.user = user;
    userRepository.setLastUser(user);
  }
}
