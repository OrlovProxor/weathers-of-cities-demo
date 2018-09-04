package com.orlov_prokhor.weathers_of_cities.utils;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//https://stackoverflow.com/questions/25455123/gson-fails-to-parse-using-gsonbuilder-setdateformatyyyy-mm-ddthhmmss-sssz
public class DateDeserializerRfc822 implements JsonDeserializer<Date> {

  public static final String     RFC822_DATE_FORMAT       = "EEE, dd MMM yyyy HH:mm a z";
  public static final String     RFC822_DATE_FORMAT_SHORT = "EEE, dd MMM yyyy HH:mm a";
  public static final DateFormat formatter                = new SimpleDateFormat(
      RFC822_DATE_FORMAT, Locale.ENGLISH);

  public static Date strRfc822ToDate(String strDate) {
    try {
      return formatter.parse(strDate);
    } catch (ParseException exp) {
      try {
        strDate = strDate.substring(0, strDate.lastIndexOf(" "));
        return new SimpleDateFormat(RFC822_DATE_FORMAT_SHORT, Locale.ENGLISH).parse(strDate);
      } catch (ParseException exp2) {
        return null;
      }
    }
  }

  @Override
  public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) {
    return strRfc822ToDate(element.getAsString());
  }
}