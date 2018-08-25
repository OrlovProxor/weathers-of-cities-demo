package com.orlov_prokhor.weathers_of_cities.interactor.repository;

import com.orlov_prokhor.weathers_of_cities.App;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.WeatherCity;
import org.junit.Test;

public class WeatherRepositoryTest {

  @Test
  public void upsertWeatherCityTest() {
    WeatherRepository w = App.getInstance().getAppComponent().getWeatherRepository();

    w.upsertWeatherCity(WeatherCity.builder()
                                   .locationFull("omsk")
                                   .build());
  }
}
