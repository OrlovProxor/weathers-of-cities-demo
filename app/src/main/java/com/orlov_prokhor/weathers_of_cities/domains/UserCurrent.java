package com.orlov_prokhor.weathers_of_cities.domains;

import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.User;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.UserRepository;

public class UserCurrent {

  private UserRepository userRepository;
  private User           user = new User();

  public UserCurrent(
      UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void setUser(User user) {
    this.user = user;
    userRepository.setLastUser(user);
  }
}
