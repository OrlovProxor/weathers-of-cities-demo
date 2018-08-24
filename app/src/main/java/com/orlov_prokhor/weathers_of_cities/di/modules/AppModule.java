
package com.orlov_prokhor.weathers_of_cities.di.modules;

import android.content.Context;
import com.orlov_prokhor.weathers_of_cities.domains.UserCurrent;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.UserRepository;
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


/*
  public static AppComponent getAppComponent(Context context) {
    return DaggerAppComponent.builder()
                             .appModule(new AppModule(context))
                             .build();
  }
*/

  @Provides
  Context getContext() {
    return context;
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

}
