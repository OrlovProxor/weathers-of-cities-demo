package com.orlov_prokhor.weathers_of_cities.utils;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


/**
 * Created by orlov_proxor@mail.ru on 03.05.2018.
 */
public class ActivityUtils {

  //https://stackoverflow.com/questions/4165414/how-to-hide-soft-keyboard-on-android-after-clicking-outside-edittext
  public static void hideSoftKeyboard(Activity activity) {
    try {
      if (activity == null) {
        return;
      }
      if (activity.getCurrentFocus() == null) {
        return;
      }
      InputMethodManager inputMethodManager =
          (InputMethodManager) activity.getSystemService(
              Activity.INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(
          activity.getCurrentFocus().getWindowToken(), 0);
    } catch (Exception e) {

    }
  }

  public static void hideSoftKeyboardForAllUI(@NonNull Activity activity, @NonNull View view) {
    if (activity == null || view == null) {
      return;
    }
    // Set up touch listener for non-text box views to hide keyboard.
    if (!(view instanceof EditText)) {
      view.setOnTouchListener(new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
          hideSoftKeyboard(activity);
          return false;
        }
      });
    }

    //If a layout container, iterate over children and seed recursion.
    if (view instanceof ViewGroup) {
      for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
        View innerView = ((ViewGroup) view).getChildAt(i);
        hideSoftKeyboardForAllUI(activity, innerView);
      }

    }
  }


  /**
   * This method converts dp unit to equivalent pixels, depending on device density.
   *
   * @param dp A value in dp (density independent pixels) unit. Which we need to convert into
   * pixels
   * @param context Context to get resources and device specific display metrics
   * @return A float value to represent px equivalent to dp depending on device density
   */
  public static float convertDpToPixel(float dp, Context context) {
    Resources      resources = context.getResources();
    DisplayMetrics metrics   = resources.getDisplayMetrics();
    float          px        = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    return px;
  }

  /**
   * This method converts device specific pixels to density independent pixels.
   *
   * @param px A value in px (pixels) unit. Which we need to convert into db
   * @param context Context to get resources and device specific display metrics
   * @return A float value to represent dp equivalent to px value
   */
  public static float convertPixelsToDp(float px, Context context) {
    Resources      resources = context.getResources();
    DisplayMetrics metrics   = resources.getDisplayMetrics();
    float          dp        = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    return dp;
  }
}