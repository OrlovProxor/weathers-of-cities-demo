package com.orlov_prokhor.weathers_of_cities.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.orlov_prokhor.weathers_of_cities.App;
import java.util.Locale;

public class StringUtils {
/*
  public static String  capitalize(String string){
    if (string == null || string.length() == 0) {
      return string;
    }
    char c[] = string.toCharArray();
    c[0] = Character.toUpperCase(c[0]);
    return new String(c);
  }
  public static String decapitalize(String string){
    if (string == null || string.length() == 0) {
    return string;
  }
  char c[] = string.toCharArray();
  c[0] = Character.toLowerCase(c[0]);
    return new String(c);
}*/
  public static String getStringWithDef(Context context,int id){
    try {
     return context.getResources().getString(id);
    }catch ( Exception e){
      try {
      Configuration conf = context.getResources().getConfiguration();
      conf.locale = new Locale("en");
      DisplayMetrics metrics = new DisplayMetrics();
      WindowManager wm      = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

      wm.getDefaultDisplay().getMetrics(metrics);
      Resources resources = new Resources(context.getAssets(), metrics, conf);
      return resources.getString(id);}
      catch (Exception e1){
        return null;
      }
    }

  }
  public static String getResStr(int id){
    return  getStringWithDef(App.getInstance(), id);
  }
}
