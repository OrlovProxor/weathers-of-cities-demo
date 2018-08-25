package com.orlov_prokhor.weathers_of_cities.ui.main_activity;


import android.support.v7.widget.RecyclerView;
import com.orlov_prokhor.weathers_of_cities.databinding.ActivityMainWeatherByCitiesFragmentItemBinding;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.WeatherCity;
import timber.log.Timber;

public class WeatherByCitiesViewHolder extends RecyclerView.ViewHolder {

  WeatherByCitiesAdapter                         weatherByCitiesAdapter;
  ActivityMainWeatherByCitiesFragmentItemBinding binding;
  WeatherCity                                    weatherCity;

  WeatherByCitiesViewHolder(WeatherByCitiesAdapter weatherByCitiesAdapter,
                            ActivityMainWeatherByCitiesFragmentItemBinding bind) {
    super(bind.getRoot());
    this.weatherByCitiesAdapter = weatherByCitiesAdapter;
    this.binding = bind;
    Timber.i("WeatherByCitiesViewHolder.create : %s", itemView);

  }

  void bind(WeatherCity weatherCity) {
    this.weatherCity = weatherCity;
    Timber.i("WeatherByCitiesViewHolder.binding : %s", weatherCity.toString());
    this.binding.setWeatherCity(weatherCity);
    this.binding.setViewHolder(this);

  }

  public void deleteWeatherCity() {
    Timber.i("WeatherByCitiesViewHolder.deleteWeatherCity  ");
    weatherByCitiesAdapter.deleteWeatherCity(this);
  }

  public void showDetails() {

    WeatherCityDetailsDialog dialog = new WeatherCityDetailsDialog(weatherByCitiesAdapter.activity,
                                                                   weatherCity);

    dialog.show();
  }
}