package com.orlov_prokhor.weathers_of_cities.interactor.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.WeatherCity;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.weather_api.WeatherService;
import com.orlov_prokhor.weathers_of_cities.utils.DateDeserializerRfc822;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.NonNull;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class YahooWeatherRepository {

  public static final String ERROR_DURING_THE_EXECUTION_OF_THE_REQUEST = "error during the execution of the request";
  Retrofit       retrofit;
  WeatherService wService;

  public YahooWeatherRepository() {
    Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateDeserializerRfc822())
                    .create();
    //"Fri, 24 Aug 2018 09:00 AM OMST"   //"EEE, dd MMM yyyy HH:mm z zzz"
//https://stackoverflow.com/questions/32294557/retrofit-intercept-responses-globally
/*    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
    clientBuilder.
                     addInterceptor(new Interceptor() {
                       @Override
                       public Response intercept(Chain chain) throws IOException {
                         Request  request  = chain.request();
                         Response response = chain.proceed(request);
                         Timber.i("request: %s", request.url());
                         //    Timber.i("response: %s", objectToStr(response));
                         return response;
                       }
                     });*/
    retrofit = new Retrofit.Builder()
                   .baseUrl("https://query.yahooapis.com")
                   .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                   .addConverterFactory(GsonConverterFactory.create(gson))
                   // .addConverterFactory(ScalarsConverterFactory.create())
                   // .client(clientBuilder.build())
                   .build();

    wService = retrofit.create(WeatherService.class);
  }

  public Observable<WeatherCity> getWeatherYahooResponse(@NonNull String cityName) {
    //https://query.yahooapis.com/v1/public/yql?q=select%20yweather%3Awind%2Cyweather%3Alocation%2Cyweather%3Aatmosphere%2C%20item.condition%20from%20weather.forecast%20where%20woeid%20in%20(%20select%20woeid%20from%20geo.places(1)%20where%20text%3D%27omsk%27)%20and%20u%3D%27c%27&format=json
//https://developer.yahoo.com/weather/documentation.html?guccounter=1#image
    String query =
        "select yweather:wind,yweather:location,yweather:atmosphere, item.condition,yweather:astronomy from weather.forecast where woeid in ( select woeid from geo.places(1) where text='"
        + cityName + "') and u='c'";

    return wService.getWeatherYahooResponse(query, "json")
                   .timeout(3, TimeUnit.SECONDS)
                   .doOnError((err) -> {
                     Timber.i("getWeather.doOnError %s", err.getMessage());
                     err.printStackTrace();
                   })
                   .subscribeOn(Schedulers.io())
                   .map((it) -> {
                     if (it.query.results == null) {
                       throw new YahooNotFoundCityException(cityName);
                     }
                     return new WeatherCity(it.query.results.channel);
                   })
                   .observeOn(AndroidSchedulers.mainThread());

  }

  public class YahooNotFoundCityException extends Exception {

    public String message;

    public YahooNotFoundCityException(String message) {
      this.message = message;
    }

  }
}
