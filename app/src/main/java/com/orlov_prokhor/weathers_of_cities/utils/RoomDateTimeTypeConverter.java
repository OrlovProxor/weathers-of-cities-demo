package com.orlov_prokhor.weathers_of_cities.utils;

import android.arch.persistence.room.TypeConverter;
import java.sql.Time;
import java.util.Date;

/**
 * Created by orlov_proxor@mail.ru on 31.03.2018.
 */


public class RoomDateTimeTypeConverter {

  @TypeConverter
  public static Date toDate(Long value) {
    return value == null ? null : new Date(value);
  }

  @TypeConverter
  public static Long toLong(Date value) {
    return value == null ? null : value.getTime();
  }

  @TypeConverter
  public static Time toTime(Long value) {
    return value == null ? null : new Time(value);
  }

  @TypeConverter
  public static Long toLong(Time value) {
    return value == null ? null : value.getTime();
  }
}