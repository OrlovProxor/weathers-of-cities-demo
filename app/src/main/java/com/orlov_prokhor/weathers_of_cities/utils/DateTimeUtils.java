package com.orlov_prokhor.weathers_of_cities.utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by orlov_proxor@mail.ru on 09.05.2018.
 */
public class DateTimeUtils {

  //https://gist.github.com/nickrussler/7527851
  public static String toISO8601UTC(Date date) {
    if(date==null) return  null;
    TimeZone   tz = TimeZone.getTimeZone("UTC");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
    df.setTimeZone(tz);
    return df.format(date);
  }

  //https://gist.github.com/nickrussler/7527851
  public static Date fromISO8601UTC(String dateStr) {
    TimeZone   tz = TimeZone.getTimeZone("UTC");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
    df.setTimeZone(tz);

    try {
      return df.parse(dateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static Time HHmmToTime(String HHmmStr) {

    DateFormat df = new SimpleDateFormat("H:m", Locale.ENGLISH);

    try {
      return new Time(df.parse(HHmmStr).getTime());
    } catch (ParseException e) {

    }
    return null;
  }

  public static Date dateSetTime(Date date, Time setTime) {
    Calendar result = Calendar.getInstance();
    result.setTime(date);
    Calendar timeC = Calendar.getInstance();
    timeC.setTime(setTime);
    result.set(Calendar.HOUR_OF_DAY, timeC.get(Calendar.HOUR_OF_DAY));
    result.set(Calendar.MINUTE, timeC.get(Calendar.MINUTE));
    result.set(Calendar.SECOND, timeC.get(Calendar.SECOND));
    result.set(Calendar.MILLISECOND, timeC.get(Calendar.MILLISECOND));
    return result.getTime();
  }
}
