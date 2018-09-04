package com.orlov_prokhor.weathers_of_cities.ui.main_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.orlov_prokhor.weathers_of_cities.App;
import com.orlov_prokhor.weathers_of_cities.R;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.User;
import com.orlov_prokhor.weathers_of_cities.utils.ActivityUtils;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
   * sections. We use a {@link FragmentPagerAdapter} derivative, which will keep every loaded
   * fragment in memory. If this becomes too memory intensive, it may be best to switch to a {@link
   * android.support.v4.app.FragmentStatePagerAdapter}.
   */
  private SectionsPagerAdapter mSectionsPagerAdapter;

  /**
   * The {@link ViewPager} that will host the section contents.
   */

  private ViewPager mViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.container);
    mViewPager.setAdapter(mSectionsPagerAdapter);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

    mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    ActivityUtils.hideSoftKeyboardForAllUI(this, findViewById(R.id.main_content));
  }


  public static void start(Activity activity, User user) {
    App.getInstance().getAppComponent().getUser().setUser(user);
    Intent myIntent = new Intent(activity, MainActivity.class);
    activity.startActivity(myIntent);
    activity.finish();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Timber.i("MainActivity.onDestroy");
  }

  /**
   * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the
   * sections/tabs/pages.
   */
  public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      // getItem is called to instantiate the fragment for the given page.
      // Return a PlaceholderFragment (defined as a static inner class below).
      if (position == 0) {
        return new WeatherByCityFragment();
      } else if (position == 1) {
        return new WeatherByCitiesFragment();
      } else {
        return new MapFragment();
      }

    }

    @Override
    public int getCount() {
      // Show 3 total pages.
      return 3;
    }
  }
}
