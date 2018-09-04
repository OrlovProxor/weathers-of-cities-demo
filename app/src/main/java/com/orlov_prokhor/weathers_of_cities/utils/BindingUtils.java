package com.orlov_prokhor.weathers_of_cities.utils;

import static com.orlov_prokhor.weathers_of_cities.utils.DateTimeUtils.fromISO8601UTC;
import static com.orlov_prokhor.weathers_of_cities.utils.DateTimeUtils.toISO8601UTC;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.widget.TextView;
import java.util.Date;

public class BindingUtils {

  @BindingAdapter("android:text")
  public static void setFloat(TextView view, Float value) {
    if (value == null) {
      view.setText("");
    } else if (Float.isNaN(value)) {
      view.setText("");
    } else {
      view.setText(String.valueOf(value));
    }
  }

  @InverseBindingAdapter(attribute = "android:text")
  public static Float getFloat(TextView view) {
    String num = view.getText().toString();
    if (num.isEmpty()) {
      return 0.0F;
    }
    try {
      return Float.parseFloat(num);
    } catch (NumberFormatException e) {
      return 0.0F;
    }
  }


  @BindingAdapter("android:text")
  public static void setInteger(TextView view, Integer value) {
    if (value == null) {
      view.setText("");
    } else {
      view.setText(String.valueOf(value));
    }
  }

  @InverseBindingAdapter(attribute = "android:text")
  public static Integer getInteger(TextView view) {
    String num = view.getText().toString();
    if (num.isEmpty()) {
      return null;
    }
    try {
      return Integer.valueOf(num);
    } catch (NumberFormatException e) {
      return null;
    }
  }


  @BindingAdapter("android:text")
  public static void setDate(TextView view, Date value) {
    if (value == null) {
      view.setText("");
    } else {
      view.setText(toISO8601UTC(value));
    }
  }

  @InverseBindingAdapter(attribute = "android:text")
  public static Date getDate(TextView view) {
    String num = view.getText().toString();
    if (num.isEmpty()) {
      return null;
    }
    try {
      return fromISO8601UTC(num);
    } catch (NumberFormatException e) {
      return null;
    }
  }
}
