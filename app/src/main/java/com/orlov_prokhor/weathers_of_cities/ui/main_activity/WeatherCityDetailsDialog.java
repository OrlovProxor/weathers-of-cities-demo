package com.orlov_prokhor.weathers_of_cities.ui.main_activity;


import static com.orlov_prokhor.weathers_of_cities.ui.WeatherCityUtils.weatherCityToStr;

import android.app.Activity;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Window;
import com.orlov_prokhor.weathers_of_cities.R;
import com.orlov_prokhor.weathers_of_cities.databinding.WeatherCityDetailsDialogBinding;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.WeatherCity;

public class WeatherCityDetailsDialog extends Dialog {

  WeatherCity                     weatherCity;
  WeatherCityDetailsDialogBinding reviewBinding;

  public WeatherCityDetailsDialog(@NonNull Activity activity, WeatherCity weatherCity) {
    super(activity);
    this.weatherCity = weatherCity;
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    reviewBinding = (WeatherCityDetailsDialogBinding) DataBindingUtil
                                                          .inflate(LayoutInflater.from(activity),
                                                                   R.layout.weather_city_details_dialog,
                                                                   null,
                                                                   false);
    setContentView(reviewBinding.getRoot());
    reviewBinding.tvweather.setText(weatherCityToStr(weatherCity));
    reviewBinding.setDialog(this);

  }

  public void close() {
    dismiss();
  }

}
