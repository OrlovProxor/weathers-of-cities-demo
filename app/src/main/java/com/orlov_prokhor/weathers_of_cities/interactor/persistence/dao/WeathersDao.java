package com.orlov_prokhor.weathers_of_cities.interactor.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.WeatherCity;
import io.reactivex.Flowable;
import java.util.List;

/**
 * Created by orlov_proxor@mail.ru on 14.03.2018.
 */


@Dao
public interface WeathersDao {

  String WEATHERS = "weathers";

  @Query("Select * FROM " + WEATHERS)
  List<WeatherCity> loadAll();

  @Query("Select * FROM " + WEATHERS + " order by locationFull")
  Flowable<List<WeatherCity>> getAll();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public void UpsertWeatherCity(WeatherCity weatherCity);

  @Delete
  void delete(WeatherCity weatherCity);
}