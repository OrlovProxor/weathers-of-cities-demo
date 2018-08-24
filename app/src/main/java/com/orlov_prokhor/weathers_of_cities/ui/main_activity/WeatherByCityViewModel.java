package com.orlov_prokhor.weathers_of_cities.ui.main_activity;

import static com.orlov_prokhor.weathers_of_cities.utils.Utils.objectToJson;

import android.arch.lifecycle.ViewModel;
import android.databinding.Observable;
import android.databinding.Observable.OnPropertyChangedCallback;
import android.databinding.ObservableField;
import com.orlov_prokhor.weathers_of_cities.App;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.YahooWeatherRepository;
import javax.inject.Inject;

public class WeatherByCityViewModel extends ViewModel {

  public ObservableField<String>  cityName       = new ObservableField<>();
  public ObservableField<Boolean> enableFindCity = new ObservableField<>();
  public ObservableField<String>  weather        = new ObservableField<>();
  @Inject
  YahooWeatherRepository yahooWeatherRepository;

  public WeatherByCityViewModel() {

    App.getInstance().getAppComponent().inject(this);

    enableFindCity.set(false);
    cityName.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
      @Override
      public void onPropertyChanged(Observable sender, int propertyId) {

        if (cityName.get() == null || "".equals(cityName.get())) {
          enableFindCity.set(false);
        } else {
          enableFindCity.set(true);
        }
      }
    });

  }

  public void getCity() {
    String city = this.cityName.get();
    if (city == null || "".equals(city)) {
      return;
    }

    yahooWeatherRepository.getWeatherYahooResponse(city)
                          .subscribe((it) -> { weather.set(objectToJson(it));});
  }

}

//https://query.yahooapis.com/v1/public/yql?q=select%20item.condition%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text=%27omsk%27)%20and%20u=%27c%27&format=json