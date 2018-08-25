package com.orlov_prokhor.weathers_of_cities.ui.main_activity;

import android.arch.lifecycle.ViewModel;
import com.orlov_prokhor.weathers_of_cities.App;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.WeatherRepository;
import javax.inject.Inject;
import timber.log.Timber;

public class WeatherByCitiesViewModel extends ViewModel {


  @Inject WeatherRepository weatherRepository;

  public WeatherByCitiesViewModel() {

    App.getInstance().getAppComponent().inject(this);
    Timber.i("WeatherByCitiesViewModel.create");

  }


}

//https://query.yahooapis.com/v1/public/yql?q=select%20item.condition%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text=%27omsk%27)%20and%20u=%27c%27&format=json