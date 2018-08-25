

package com.orlov_prokhor.weathers_of_cities.interactor.persistence.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.dao.WeathersDao;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.WeatherCity;
import com.orlov_prokhor.weathers_of_cities.utils.RoomDateTimeTypeConverter;


/**
 * The Room database that contains the Users table
 */
@Database(
    entities = {WeatherCity.class},
    version = 1,
    exportSchema = false
)
@TypeConverters({RoomDateTimeTypeConverter.class})
public abstract class WeathersDatabase extends RoomDatabase {

  private static final    String           WORDS_DB            = "weathers.db";
  private static final    String           DATABASES           = "databases/";
  private static final    String           WORDS_DB_ASSET_PATH = DATABASES + WORDS_DB;
  private static volatile WeathersDatabase INSTANCE;

  public static WeathersDatabase getInstance(Context context) {
    if (INSTANCE == null) {
      synchronized (WeathersDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE =
              Room.databaseBuilder(context.getApplicationContext(), WeathersDatabase.class,
                                   WORDS_DB)
                  .build();
        }
      }
    }
    return INSTANCE;
  }

  public abstract WeathersDao weathersDao();
}
