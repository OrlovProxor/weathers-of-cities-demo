package com.orlov_prokhor.weathers_of_cities.ui.main_activity;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.orlov_prokhor.weathers_of_cities.App;
import com.orlov_prokhor.weathers_of_cities.R;
import com.orlov_prokhor.weathers_of_cities.databinding.ActivityMainWeatherByCitiesFragmentItemBinding;
import com.orlov_prokhor.weathers_of_cities.interactor.persistence.entity.WeatherCity;
import com.orlov_prokhor.weathers_of_cities.interactor.repository.WeatherRepository;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import lombok.NonNull;
import timber.log.Timber;

public class WeatherByCitiesAdapter extends
                                    RecyclerView.Adapter<WeatherByCitiesViewHolder> {


  Activity activity;
  @Inject
  WeatherRepository weatherRepository;
  private List<WeatherCity> data;
  private LayoutInflater    layoutInflater;


  public WeatherByCitiesAdapter(@NonNull Activity activity) {

    Timber.i("WeatherByCitiesAdapter.WeatherByCitiesAdapter");
    this.activity = activity;
    App.getInstance().getAppComponent().inject(this);
    this.data = new ArrayList<>();

    this.layoutInflater = (LayoutInflater) activity.getSystemService(
        Context.LAYOUT_INFLATER_SERVICE);

  }

  @Override
  public WeatherByCitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Timber.i("WeatherByCitiesAdapter.onCreateViewHolder");

    ActivityMainWeatherByCitiesFragmentItemBinding binding =
        DataBindingUtil
            .inflate(layoutInflater, R.layout.activity_main_weather_by_cities_fragment_item, parent,
                     false);
    return new WeatherByCitiesViewHolder(this, binding);
  }

  @Override
  public void onBindViewHolder(WeatherByCitiesViewHolder holder, int position) {
    Timber.i("WeatherByCitiesAdapter.onBindViewHolder");

    holder.bind(data.get(position));
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  public void setData(List<WeatherCity> newData) {
    Timber.i("WeatherByCitiesAdapter.setData");
    if (data != null) {
      WeatherCityDiffCallback weatherCityDiffCallback = new WeatherCityDiffCallback(data, newData);
      DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
          weatherCityDiffCallback);
      Timber.i("WeatherByCitiesAdapter.setData.data.clear");
      data.clear();
      data.addAll(newData);
      diffResult.dispatchUpdatesTo(this);
    } else {
      // first initialization
      data = newData;
    }
  }

  public void deleteWeatherCity(WeatherByCitiesViewHolder holder) {
    Timber.i("WeatherByCitiesAdapter.deleteWeatherCity");
    weatherRepository.delete(holder.binding.getWeatherCity());
    //this.removeAt(holder.getAdapterPosition() );
  }

  public void removeAt(int position) {
    data.remove(position);
    notifyItemRemoved(position);
    notifyItemRangeChanged(position, data.size());
  }

  class WeatherCityDiffCallback extends DiffUtil.Callback {

    private final List<WeatherCity> oldPosts, newPosts;

    public WeatherCityDiffCallback(List<WeatherCity> oldPosts, List<WeatherCity> newPosts) {
      this.oldPosts = oldPosts;
      this.newPosts = newPosts;
    }

    @Override
    public int getOldListSize() {
      return oldPosts.size();
    }

    @Override
    public int getNewListSize() {
      return newPosts.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
      Timber.i("WeatherCityDiffCallback.areItemsTheSame oldItemPosition: %d newItemPosition: %d ",
               oldItemPosition, newItemPosition);
      return oldPosts.get(oldItemPosition).getLocationFull() == newPosts.get(newItemPosition)
                                                                        .getLocationFull();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
      Timber
          .i("WeatherCityDiffCallback.areContentsTheSame oldItemPosition: %d newItemPosition: %d ",
             oldItemPosition, newItemPosition);
      return oldPosts.get(oldItemPosition).equals(newPosts.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
      Timber.i("WeatherCityDiffCallback.getChangePayload oldItemPosition: %d newItemPosition: %d ",
               oldItemPosition, newItemPosition);
      //you can return particular field for changed item.
      return super.getChangePayload(oldItemPosition, newItemPosition);
    }
  }
}
