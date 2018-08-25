
package com.orlov_prokhor.weathers_of_cities.di.modules;

import android.content.Context;
import com.orlov_prokhor.weathers_of_cities.domains.UserCurrent;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.dao.WeathersDao;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.database.WeathersDatabase;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.UserRepository;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.WeatherRepository;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.YahooWeatherRepository;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class AppModule {

  private final Context context;

  public AppModule(Context context) {
    this.context = context;
  }

  @Provides
  Context getContext() {
    return context;
  }

  @Provides
  @Singleton
  WeathersDatabase getWeathersDatabase(Context context) {
    return WeathersDatabase.getInstance(context);
  }

  @Provides
  UserRepository getUserRepository(Context context) {
    return new UserRepository(context);
  }

  @Provides
  @Singleton
  UserCurrent getUser(UserRepository userRepository) {
    return new UserCurrent(userRepository);
  }

  @Provides
  @Singleton
  YahooWeatherRepository getYahooWeatherRepository() {
    return new YahooWeatherRepository();
  }

  @Provides
  @Singleton
  WeathersDao getWeathersDao(WeathersDatabase weathersDatabase) {
    return weathersDatabase.weathersDao();
  }

  @Provides
  WeatherRepository getWeatherRepository(WeathersDao weathersDao) {
    return new WeatherRepository(weathersDao);
  }
}
