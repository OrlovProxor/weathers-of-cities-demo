package com.orlov_prokhor.weathers_of_cities.ui.main_activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.Observable.OnPropertyChangedCallback;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.orlov_prokhor.weathers_of_cities.R;
import com.orlov_prokhor.weathers_of_cities.databinding.ActivityMainWeatherByCityFragmentBinding;
import com.orlov_prokhor.weathers_of_cities.utils.ActivityUtils;
import timber.log.Timber;

public class WeatherByCityFragment extends Fragment {

  WeatherByCityViewModel                   weatherByCityViewModel;
  ActivityMainWeatherByCityFragmentBinding mBinding;

  public WeatherByCityFragment() {
    Timber.i("WeatherByCityFragment.create");
  }

  /**
   * Returns a new instance of this fragment for the given section number.
   */
 /*public static PlaceholderFragment newInstance(int sectionNumber) {
    PlaceholderFragment fragment = new PlaceholderFragment();
    Bundle              args     = new Bundle();
    args.putInt(ARG_SECTION_NUMBER, sectionNumber);
    fragment.setArguments(args);
    return fragment;
  }*/
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Timber.i("WeatherByCityFragment.onCreateView");
    mBinding = DataBindingUtil
                   .inflate(inflater, R.layout.activity_main_weather_by_city_fragment, container,
                            false);
    ActivityUtils.hideSoftKeyboardForAllUI(this.getActivity(), mBinding.getRoot());
    return mBinding.getRoot();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Timber.i("WeatherByCityFragment.onActivityCreated");
    weatherByCityViewModel = ViewModelProviders.of(this.getActivity())
                                               .get(WeatherByCityViewModel.class);
    mBinding.setViewModel(weatherByCityViewModel);
    weatherByCityViewModel.weather.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
      @Override
      public void onPropertyChanged(Observable sender, int propertyId) {
        mBinding.weatherOfCity.setText( weatherByCityViewModel.weather.get());
      }
    });
    mBinding.weatherOfCity.setText(weatherByCityViewModel.weather.get());

  }


}

