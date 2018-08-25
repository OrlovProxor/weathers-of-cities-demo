package com.orlov_prokhor.weathers_of_cities.utils;

import static android.test.MoreAsserts.assertNotEqual;
import static com.orlov_prokhor.weathers_of_cities.utils.DateDeserializerRfc822.strRfc822ToDate;

import java.util.Date;
import org.junit.Test;
import timber.log.Timber;

public class DateDeserializerRfc822Test {

  @Test
  public void strRfc822ToDateTest() {

    //"Fri, 24 Aug 2018 12:00 AM EDT"   "Fri, 24 Aug 2018 10:00 AM OMST"
    Date d = strRfc822ToDate("Fri, 24 Aug 2018 12:13 AM EDT");
    Timber.i("strRfc822ToDate: %tc", d);
    assertNotEqual(null, d);
    Date d2 = strRfc822ToDate("Fri, 24 Aug 2018 10:11 AM OMST");
    assertNotEqual(null, d2);
    Timber.i("strRfc822ToDate2: %tc", d2);

    Date d3 = strRfc822ToDate("Fri, 24 Aug 2018 12:00 PM NOVT");
    assertNotEqual(null, d3);
    Timber.i("strRfc822ToDate3: %tc", d3);

  }
}
