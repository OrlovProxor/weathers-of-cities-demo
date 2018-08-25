package com.orlov_prokhor.weathers_of_cities.ui.main_activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.orlov_prokhor.weathers_of_cities.App;
import com.orlov_prokhor.weathers_of_cities.R;
import com.orlov_prokhor.weathers_of_cities.databinding.ActivityMainWeatherByCitiesFragmentBinding;
import com.orlov_prokhor.weathers_of_cities.utils.ActivityUtils;
import timber.log.Timber;


public class WeatherByCitiesFragment extends Fragment {

  WeatherByCitiesViewModel                   weatherByCitiesViewModel;
  ActivityMainWeatherByCitiesFragmentBinding mBinding;
  WeatherByCitiesAdapter                     weatherByCitiesAdapter;

  public WeatherByCitiesFragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Timber.i("WeatherByCitiesFragment.onCreateView");
    mBinding = DataBindingUtil
                   .inflate(inflater, R.layout.activity_main_weather_by_cities_fragment, container,
                            false);
    ActivityUtils.hideSoftKeyboardForAllUI(this.getActivity(), mBinding.getRoot());
    return mBinding.getRoot();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Timber.i("WeatherByCitiesFragment.onActivityCreated");
    weatherByCitiesViewModel = ViewModelProviders.of(this.getActivity())
                                                 .get(WeatherByCitiesViewModel.class);
    mBinding.setViewModel(weatherByCitiesViewModel);

    weatherByCitiesAdapter = new WeatherByCitiesAdapter(this.getActivity());

  /*  postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
    postViewModel.getAllPosts().observe(this, posts -> postsAdapter.setData(posts));*/
    App.getInstance()
       .getAppComponent()
       .getWeatherRepository()
       .getAll()
       .subscribe((weathers) -> {
         Timber.i("WeatherByCitiesFragment.onActivityCreated.getAll");
         weatherByCitiesAdapter.setData(weathers);
       });

    RecyclerView recyclerView = mBinding.weatherRecyclerView;
    recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    recyclerView.setHasFixedSize(true);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(weatherByCitiesAdapter);

  }


}