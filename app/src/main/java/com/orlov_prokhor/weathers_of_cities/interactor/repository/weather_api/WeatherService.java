package com.orlov_prokhor.weathers_of_cities.interactor.repository.weather_api;

import com.orlov_prokhor.weathers_of_cities.interactor.repository.weather_api.yahoo_apis_pojos.YahooResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

  @GET("v1/public/yql")
  public Observable<String> getWeather(@Query("q") String query,
                                       @Query("format") String format);

  @GET("v1/public/yql")
  public Observable<YahooResponse> getWeatherYahooResponse(@Query("q") String query,
                                                           @Query("format") String format);
}