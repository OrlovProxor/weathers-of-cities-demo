package com.orlov_prokhor.weathers_of_cities;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import org.junit.Test;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleInstrumentedTest {

  @Test
  public void useAppContext() {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getTargetContext();

    assertEquals("com.orlov_prokhor.weathers_of_cities", appContext.getPackageName());

  }
}
