package com.orlov_prokhor.weathers_of_cities.interactor.repository;


import static com.orlov_prokhor.weathers_of_cities.utils.Utils.fromHtml;

import android.text.Spanned;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.dao.WeathersDao;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.WeatherCity;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import timber.log.Timber;

public class WeatherRepository {

  WeathersDao weathersDao;

  public WeatherRepository(WeathersDao weathersDao) {
    this.weathersDao = weathersDao;
  }



  public void upsertWeatherCity(WeatherCity weatherCity) {

    Single.just("")
          .subscribeOn(Schedulers.newThread())
          .subscribe((it) -> weathersDao.UpsertWeatherCity(weatherCity));

  }

  public Flowable<List<WeatherCity>> getAll() {
    return weathersDao.getAll()
                      .subscribeOn(Schedulers.newThread())
                      .observeOn(AndroidSchedulers.mainThread());
  }

  public void delete(WeatherCity weatherCity) {
    Timber.i("WeatherRepository.delete: %s", weatherCity.locationFull);
    Single.just("")
          .subscribeOn(Schedulers.newThread())
          .subscribe((it) -> weathersDao.delete(weatherCity));
  }
}
