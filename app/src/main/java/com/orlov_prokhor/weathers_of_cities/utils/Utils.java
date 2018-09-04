package com.orlov_prokhor.weathers_of_cities.utils;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Time;
import timber.log.Timber;


/**
 * Created by orlov_proxor@mail.ru on 27.02.2018.
 */
public class Utils {


  public static String varDumpToStr(Object obj) {
    if (obj == null) {
      return "null";
    }
    StringBuilder result  = new StringBuilder();
    String        newLine = System.getProperty("line.separator");

    result.append(obj.getClass().getName());
    result.append(" Object {");
    result.append(newLine);

    // determine fields declared in this class only (no fields of superclass)
    Field[] fields = obj.getClass().getDeclaredFields();

    // print field names paired with their values
    for (Field field : fields) {
      result.append("  ");
      try {
        field.setAccessible(true);
        result.append(field.getGenericType()).append('\t').append(field.getName());

        result.append(": ");
        // requires access to private field:
        result.append(field.get(obj));
      } catch (IllegalAccessException ex) {
        System.out.println(ex.getMessage());
      }
      result.append(newLine);
    }
    result.append('}');

    return result.toString();
  }

  public static void varDump(Object obj) {
    System.out.println(varDumpToStr(obj));
  }

  public static String objectToStr(@NonNull Object obj) {

    return varDumpToStr(obj);
  }

  public static String objectToJson(@NonNull Object obj) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(obj);
  }

  public static Object jsonStrToObject(@NonNull String json, @NonNull Type typeOfT) {

    Gson gson = new Gson();
    return gson.fromJson(json, typeOfT);
  }


  public static Object getPrivateField(Object obj, String fieldName) {

    try {
      Field f = null; //NoSuchFieldException
      f = obj.getClass().getDeclaredField(fieldName);
      f.setAccessible(true);
      return f.get(obj); //IllegalAccessException
    } catch (NoSuchFieldException e) {
      return null;
    } catch (IllegalAccessException e) {
      return null;
    }
  }

  public static Bundle objectToBundle(Object obj) {
    if (obj == null) {
      return null;
    }
    Bundle result = new Bundle();

    // determine fields declared in this class only (no fields of superclass)
    Field[] fields = obj.getClass().getDeclaredFields();

    // print field names paired with their values
    for (Field field : fields) {

      field.setAccessible(true);
/*      Type type = field.getGenericType();
      Timber.i("type: %s", type);*/

      Object o = null;
      try {
        String filedName = field.getName();
        String value     = "";
        o = field.get(obj);
        if (o == null) {
          continue;
        }
        if (o instanceof Boolean) {
          value = String.valueOf((Boolean) o);
        }
        if (o instanceof Long) {
          value = String.valueOf(((Long) o).intValue());
        }
        if (o instanceof Integer) {
          value = String.valueOf((Integer) o);
        }
        if (o instanceof String) {
          value = (String) o;
        }
        if (o instanceof Time) {
          value = ((Time) o).toString();
        }
        result.putString(filedName, value);
      } catch (IllegalAccessException e) {
        Timber.e(e);
        continue;
      }



        /*result.append(field.getGenericType()).append('\t').append(field.getName());

        result.append(": ");
        // requires access to private field:
        result.append(field.get(obj));*/

    }

    return result;
  }

  @SuppressWarnings("deprecation")
  public static Spanned fromHtml(String html) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
    } else {
      return Html.fromHtml(html);
    }
  }
}
