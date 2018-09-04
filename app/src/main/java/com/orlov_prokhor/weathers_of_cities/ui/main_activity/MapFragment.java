package com.orlov_prokhor.weathers_of_cities.ui.main_activity;

import android.Manifest;
import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.orlov_prokhor.weathers_of_cities.App;
import com.orlov_prokhor.weathers_of_cities.R;
import com.orlov_prokhor.weathers_of_cities.databinding.ActivityMainWeatherByMapFragmentBinding;
import com.orlov_prokhor.weathers_of_cities.domains.UserCurrent;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;
import timber.log.Timber;

public class MapFragment extends Fragment implements OnMapReadyCallback {

  //http://www.zoftino.com/android-mapview-tutorial
  ActivityMainWeatherByMapFragmentBinding mBinding;
  @Inject UserCurrent userCurrent;

  private MapView   mapView;
  private GoogleMap gmap;
  Disposable locationUpdates;
  Marker     lastMarker;
  private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Timber.i("MapFragment.onCreateView");
    App.getInstance().getAppComponent().inject(this);
    mBinding = DataBindingUtil
                   .inflate(inflater, R.layout.activity_main_weather_by_map_fragment, container,
                            false);

    Bundle mapViewBundle = null;
    if (savedInstanceState != null) {
      mapViewBundle = savedInstanceState.getBundle(getString(R.string.google_maps_key));
    }

    mapView = mBinding.mapView;
    mapView.onCreate(mapViewBundle);
    mapView.getMapAsync(this);

    return mBinding.getRoot();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Timber.i("MapFragment.onSaveInstanceState");
    Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
    if (mapViewBundle == null) {
      mapViewBundle = new Bundle();
      outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
    }

    mapView.onSaveInstanceState(mapViewBundle);
  }

  @Override
  public void onResume() {
    super.onResume();
    mapView.onResume();
    Timber.i("MapFragment.onResume");
  }

  @Override
  public void onStart() {
    super.onStart();
    mapView.onStart();
    Timber.i("MapFragment.onStart");
    requestPermissions().subscribe(grant -> this.subscribeOnLocationChanges());

  }

  @Override
  public void onStop() {
    super.onStop();
    mapView.onStop();
    Timber.i("MapFragment.onStop");
    if (locationUpdates != null) {
      locationUpdates.dispose();
    }

  }

  @Override
  public void onPause() {
    mapView.onPause();
    super.onPause();
    Timber.i("MapFragment.onPause");
  }

  @Override
  public void onDestroy() {
    mapView.onDestroy();
    super.onDestroy();
    Timber.i("MapFragment.onDestroy");
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    Timber.i("onMapReady");
    gmap = googleMap;
    UiSettings uiSettings = gmap.getUiSettings();
    uiSettings.setIndoorLevelPickerEnabled(true);
    uiSettings.setMyLocationButtonEnabled(true);
    uiSettings.setMapToolbarEnabled(true);
    uiSettings.setCompassEnabled(true);
    uiSettings.setZoomControlsEnabled(true);
  }

  public Observable<Boolean> requestPermissions() {

    if (ActivityCompat.checkSelfPermission(this.getActivity(), permission.ACCESS_FINE_LOCATION)

        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this.getActivity(), permission.ACCESS_COARSE_LOCATION)
           != PackageManager.PERMISSION_GRANTED) {

      Timber.i("need permissions ACCESS_FINE_LOCATION");
      final RxPermissions rxPermissions = new RxPermissions(this);
      return rxPermissions
                 .request(Manifest.permission.ACCESS_FINE_LOCATION, permission.ACCESS_FINE_LOCATION)
                 .map(granted -> {
                   if (granted) { // Always true pre-M
                     // I can control the camera now
                     Timber.i("MapFragment.rxPermissions.granted");
                   } else {
                     // Oups permission denied
                     Timber.i("MapFragment.rxPermissions.denied");
                   }
                   return granted;
                 });


    } else {
      return Observable.just(true);
    }
  }

  public void subscribeOnLocationChanges() {

    Timber.i("MapFragment.subscribeOnLocationChanges ");
    LocationRequest request = LocationRequest.create() //standard GMS LocationRequest
                                             .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

                                             // .setNumUpdates(5)
                                             .setInterval(2000);

    ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(this.getActivity());

    locationUpdates = locationProvider.getUpdatedLocation(request)

                                      .observeOn(AndroidSchedulers.mainThread())
                                      .subscribe((location) -> {
                                        Timber.i("MapFragment.location:%s", location);
                                        if (gmap != null) {
                                          //  gmap.setMinZoomPreference(12);
                                          LatLng ny = new LatLng(location.getLatitude(),
                                                                 location.getLongitude());
                                          gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
                                          addMarker(location);
                                        }
                                      });
  }

  public void addMarker(Location location) {
    if (lastMarker != null) {
      lastMarker.remove();
    }
    LatLng latLng = new LatLng(location.getLatitude(),
                               location.getLongitude());
    BitmapDescriptor icon = BitmapDescriptorFactory
                                .fromResource(R.drawable.ic_heart);

    MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                                                     .title(getString(R.string.current_location_accuracy)+String.format("  %8.3f",location.getAccuracy()) )
                                                     .icon(icon);
    lastMarker = gmap.addMarker(markerOptions);
    lastMarker.showInfoWindow();

  }

}

