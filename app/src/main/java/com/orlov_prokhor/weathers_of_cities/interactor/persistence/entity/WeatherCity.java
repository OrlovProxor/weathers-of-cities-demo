package com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity;


import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.weather_api.yahoo_apis_pojos.Astronomy;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.weather_api.yahoo_apis_pojos.Atmosphere;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.weather_api.yahoo_apis_pojos.Channel;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.weather_api.yahoo_apis_pojos.Condition;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.weather_api.yahoo_apis_pojos.Location;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.weather_api.yahoo_apis_pojos.Wind;
import lombok.Builder;
import lombok.Data;

@Entity(tableName = "weathers")

@Data
//https://sqlify.io/convert
public class WeatherCity {

  @NonNull
  @PrimaryKey()
  public                                   String     locationFull = "";
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
    locationFull = location.country + ", " + location.region + ", " + location.city;
  }

  @Builder
  public WeatherCity(@NonNull String locationFull,
                     Atmosphere atmosphere,
                     Condition weather,
                     Location location,
                     Astronomy astronomy,
                     Wind wind) {
    this.locationFull = locationFull;
    this.atmosphere = atmosphere;
    this.weather = weather;
    this.location = location;
    this.astronomy = astronomy;
    this.wind = wind;
  }
}

