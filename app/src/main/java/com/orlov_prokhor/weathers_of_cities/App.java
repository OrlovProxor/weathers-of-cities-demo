package com.orlov_prokhor.weathers_of_cities;


import android.app.Application;
import com.orlov_prokhor.weathers_of_cities.di.components.AppComponent;
import com.orlov_prokhor.weathers_of_cities.di.components.DaggerAppComponent;
import com.orlov_prokhor.weathers_of_cities.di.modules.AppModule;
import timber.log.Timber;
import timber.log.Timber.DebugTree;
import timber.log.Timber.Tree;


/**
 * Created by orlov_proxor@mail.ru on 26.02.2018.
 */
public class App extends Application {

  private static String appDir =
      "/data/data/" + App.class.getPackage().getName()
      + "/";// "/data/data/com.orlov_prokhor.just_look_and_learn/"  ;

  private static volatile App          instance;
  private static          AppComponent appComponent;


  Tree timberLogTree;

  public static String getAppDir() {
    return appDir;
  }


  public static App getInstance() {
    App localInstance = instance;
    if (localInstance == null) {
      synchronized (App.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = new App();
        }
      }
    }

    return instance;
  }


  @Override
  public void onCreate() {
    instance = this;
    super.onCreate();

    if (BuildConfig.DEBUG) {
      timberLogTree = new DebugTree();
    } else {
      timberLogTree = new CrashReportingTree();
    }
    Timber.plant(timberLogTree);

    appComponent = DaggerAppComponent.builder()
                                     .appModule(new AppModule(instance))
                                     .build();
  }


  public static AppComponent getAppComponent() {
    return App.getInstance().appComponent;
  }


  public static class CrashReportingTree extends Tree {

    @Override
    public void i(String message, Object... args) {
      // TODO e.g., Crashlytics.log(String.format(message, args));
    }

    @Override
    public void i(Throwable t, String message, Object... args) {
      i(message, args); // Just add to the Timber.
    }

    @Override
    public void e(String message, Object... args) {
      i("ERROR: " + message, args); // Just add to the Timber.
    }

    @Override
    public void e(Throwable t, String message, Object... args) {
      e(message, args);

      // TODO e.g., Crashlytics.logException(t);
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
      //super.log(priority,tag,message,t);
    }
  }

}