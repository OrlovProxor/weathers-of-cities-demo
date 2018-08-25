package com.orlov_prokhor.weathers_of_cities.interactor.repository;

import static com.orlov_prokhor.weathers_of_cities.utils.Utils.objectToStr;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.content.Context;
import androidx.test.runner.AndroidJUnit4;
import com.orlov_prokhor.weathers_of_cities.App;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.WeatherCity;
import io.reactivex.observers.TestObserver;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import timber.log.Timber;


@RunWith(AndroidJUnit4.class)
public class YahooWeatherRepositoryTest {

  public static final String  OMSK = "Omsk";
  private             Context context;

  @Before
  public void setUp() {

    context = App.getInstance();

  }


  @Test
  public void getWeatherYahooResponseTest() {
    TestObserver testObserver = new TestObserver();
    YahooWeatherRepository weatherRepository = App.getInstance().getAppComponent()
                                                  .getYahooWeatherRepository();

    weatherRepository.getWeatherYahooResponse(OMSK).subscribe(testObserver);

    testObserver.awaitDone(50, TimeUnit.SECONDS);
    assertEquals(1, testObserver.valueCount());
    WeatherCity yahooResponse = (WeatherCity) testObserver.values().get(0);

    assertNotEquals(null, yahooResponse);

    Timber.i("yahooResponse: %s", objectToStr(yahooResponse));

    Timber.i("yahooResponse.query.results.channel: %s", objectToStr(yahooResponse));
    Timber.i("yahooResponse.query.results.channel.item: %s", objectToStr(yahooResponse.weather));
    Timber.i("yahooResponse.query.results.channel.astronomy: %s",
             objectToStr(yahooResponse.astronomy));
    Timber.i("yahooResponse.query.results.channel.atmosphere: %s",
             objectToStr(yahooResponse.atmosphere));
    Timber
        .i("yahooResponse.query.results.channel.location: %s", objectToStr(yahooResponse.location));
    Timber.i("yahooResponse.query.results.channel.wind: %s", objectToStr(yahooResponse.wind));

    assertNotEquals(null, yahooResponse);

    assertNotEquals(null, yahooResponse.weather);
    assertNotEquals(null, yahooResponse.astronomy);
    assertNotEquals(null, yahooResponse.atmosphere);
    assertNotEquals(null, yahooResponse.location);
    assertNotEquals(null, yahooResponse.wind);
    assertNotEquals(null, yahooResponse.weather.date);
  }
}
