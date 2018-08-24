
package com.orlov_prokhor.weathers_of_cities.di.components;

import android.content.Context;
import com.orlov_prokhor.weathers_of_cities.di.modules.AppModule;
import com.orlov_prokhor.weathers_of_cities.domains.UserCurrent;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.UserRepository;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.YahooWeatherRepository;
import com.orlov_prokhor.weathers_of_cities.ui.main_activity.WeatherByCityViewModel;
import dagger.Component;
import javax.inject.Singleton;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = AppModule.class)
public interface AppComponent {


  //Exposed to sub-graphs.
  Context context();

  UserRepository getUserRepository();

  UserCurrent getUser();

  YahooWeatherRepository getYahooWeatherRepository();

  void inject(WeatherByCityViewModel weatherByCityViewModel);
}
