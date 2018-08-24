package com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity;


import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.weather_api.yahoo_apis_pojos.Astronomy;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.weather_api.yahoo_apis_pojos.Atmosphere;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.weather_api.yahoo_apis_pojos.Channel;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.weather_api.yahoo_apis_pojos.Condition;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.weather_api.yahoo_apis_pojos.Location;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.weather_api.yahoo_apis_pojos.Wind;
import lombok.Data;

@Entity(tableName = "weathers")
@Data
public class WeatherCity {

  @PrimaryKey(autoGenerate = true) public  int        _Id;
  @Embedded(prefix = "atmosphere_") public Atmosphere atmosphere;
  @Embedded(prefix = "weather_") public    Condition  weather;
  @Embedded(prefix = "location_") public   Location   location;
  @Embedded(prefix = "astronomy_") public  Astronomy  astronomy;
  @Embedded(prefix = "wind_") public       Wind       wind;

  public WeatherCity() {
  }

  public WeatherCity(Channel channel) {
    super();
    this.astronomy = channel.astronomy;
    this.atmosphere = channel.atmosphere;
    this.weather = channel.item.condition;
    this.location = channel.location;
    this.wind = channel.wind;
  }


}

