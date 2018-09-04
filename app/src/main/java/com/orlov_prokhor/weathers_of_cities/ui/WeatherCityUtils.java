package com.orlov_prokhor.weathers_of_cities.ui;

import static com.orlov_prokhor.weathers_of_cities.utils.StringUtils.getResStr;
import static com.orlov_prokhor.weathers_of_cities.utils.Utils.fromHtml;

import android.content.Context;
import android.text.Spanned;
import com.orlov_prokhor.weathers_of_cities.App;
import com.orlov_prokhor.weathers_of_cities.R;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.WeatherCity;
import com.orlov_prokhor.weathers_of_cities.utils.DateTimeUtils;

public class WeatherCityUtils {

  public static Spanned weatherCityToStr(WeatherCity weatherCity) {

    StringBuffer stringBuffer = new StringBuffer();

    stringBuffer
        .append("<h3>" + weatherCity.locationFull + "</h3>")
        /*     .append(addGroup(""))
             .append(addProperty("location", weatherCity.locationFull))*/
        .append(String.format("<h3>%s</h3>", getResStr(R.string.details_wheather)))
        .append(addProperty((R.string.details_wheather_date),
                            DateTimeUtils.toISO8601UTC(weatherCity.weather.date)))
        .append(
            addProperty((R.string.details_wheather_temp), String.valueOf(weatherCity.weather.temp)+" "+weatherCity.units.temperature  ))
        .append(
            addProperty((R.string.details_wheather_code), yahooCodeToStr(App.getInstance(),
                                                                         weatherCity.weather.code)))
        //  .append(addProperty("text", weatherCity.weather.text))
        .append(addGroup(R.string.details_wheather_wind))
        .append(addProperty(R.string.details_wheather_chill,
                            String.valueOf(weatherCity.wind.chill)))
        .append(addProperty((R.string.details_wheather_direction),
                            String.valueOf(weatherCity.wind.direction)))
        .append(
            addProperty(R.string.details_wheather_speed, String.valueOf(weatherCity.wind.speed)+" "+weatherCity.units.speed ))
        .append(addGroup(R.string.details_wheather_atmosphere))
        .append(addProperty(R.string.details_wheather_humidity,
                            String.valueOf(weatherCity.atmosphere.humidity)))
        .append(addProperty(R.string.details_wheather_pressure,
                            String.valueOf(weatherCity.atmosphere.pressure)+" "+weatherCity.units.pressure ))
        .append(
            addProperty(R.string.details_wheather_rising,
                        String.valueOf(weatherCity.atmosphere.rising)))
        .append(
            addProperty(R.string.details_wheather_visibility,
                        String.valueOf(weatherCity.atmosphere.visibility)))
        .append(addGroup(R.string.details_wheather_astronomy))
        .append(addProperty(R.string.details_wheather_sunrise, weatherCity.astronomy.sunrise))
        .append(addProperty(R.string.details_wheather_sunset, weatherCity.astronomy.sunset))
    ;

    return fromHtml(stringBuffer.toString());
  }

  private static String addProperty(int nameId, String value) {
    return String.format("<span>%s</span>: %s<br>", getResStr(nameId), value);
  }

  private static String addGroup(int nameId) {
    return String.format("<br><h3>%s</h3>", getResStr(nameId));
  }

  private static String addLine(String value) {
    return String.format("%s<br>", value);
  }

  static String yahooCodeToStr(Context context, int code) {
    try {
      String[] ids = context.getResources().getStringArray(R.array.yahoo_weather_codes_ids);
      if (code < ids.length) {
        int idOfCode = Integer.valueOf(ids[code]);
        String[] strS = context.getResources()
                               .getStringArray(R.array.yahoo_weather_codes_value);
        if (idOfCode < strS.length) {
          return strS[idOfCode];
        }
      }
      return null;
    } catch (Exception e) {
      return null;
    }
  }

}
