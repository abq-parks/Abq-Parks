package edu.cnm.deepdive.abqparks.fragment;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import edu.cnm.deepdive.abqparks.BuildConfig;
import edu.cnm.deepdive.abqparks.R;
import edu.cnm.deepdive.abqparks.model.Amenity;
import edu.cnm.deepdive.abqparks.model.Park;
import edu.cnm.deepdive.abqparks.service.ParksService;
import java.io.IOException;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  Displays parks nearby user in embedded Google MapView.
 */
public class LocalParkFragment extends Fragment implements OnMapReadyCallback,
    OnMarkerClickListener {

  private static final float CAMERA_ZOOM = 15.0F;
  private static final double ABQ_LNG = -106.5282598;
  private static final double ABQ_LAT = 35.1462006;
  private static final String PARK_ID_KEY = "PARKID";
  private static final String PARK_NAME_KEY = "PARKNAME";

  private LocationManager locationManager;
  private double deviceLat;
  private double deviceLng;
  private MapView mapView;
  private GoogleMap map;
  private List<Park> parks;
  private Button reviewButton;
  private ParksService parkService;
  private List<Amenity> amenities;
  private ArrayAdapter<Amenity> amenityAdapter;
  private ListView listview;
  private Park park;
  private TextView parkName;

  public LocalParkFragment() {
    // Required empty public constructor
  }

  /**
   * Factory method for creating new LocalParkFragment instances.
   * @return
   */
  public static LocalParkFragment newInstance(String param1, String param2) {
    LocalParkFragment fragment = new LocalParkFragment();
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_local_park, container, false);
    mapView = view.findViewById(R.id.local_map_view);
    listview = view.findViewById(R.id.amenities_list);
    parkName = view.findViewById(R.id.park_name);
    mapView.onCreate(savedInstanceState);

    reviewButton = view.findViewById(R.id.review_button);
    reviewButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (park != null) {
          Bundle bundle = new Bundle();
          bundle.putLong(PARK_ID_KEY, park.getId());
          bundle.putString(PARK_NAME_KEY, park.getName());
          ReviewsFragment reviewsFragment = new ReviewsFragment();
          reviewsFragment.setArguments(bundle);
          FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
          fragmentManager.beginTransaction().replace(R.id.main_frame, reviewsFragment)
              .commit();
        } else {
          Toast.makeText(getContext(), "Please select park.", Toast.LENGTH_SHORT).show();
        }
      }
    });

    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    if (ContextCompat.checkSelfPermission(getActivity(),
        permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      getLocation();
    } else {
      Toast.makeText(getActivity(), getString(R.string.location_permission_denial),
          Toast.LENGTH_SHORT)
          .show();
      defaultLocation();
    }
    super.onViewCreated(view, savedInstanceState);
  }

  private void showSearchedPark() {
    Bundle bundle = this.getArguments();
    if (bundle != null) {
      long parkId = bundle.getLong(PARK_ID_KEY, -1);
      if (parkId != -1) {
        for (Park park : parks) {
          if (parkId == park.getId()) {
            amenities = park.getAmenities();
            LocalParkFragment.this.park = park;
            parkName.setText(park.getName());
            populateList();
            deviceLat = park.getLatitude();
            deviceLng = park.getLongitude();
            break;
          }
        }
      }
    }
  }

  private void getLocation() {
    @SuppressLint("MissingPermission") // getLocation is only called if permission has been granted
    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    if (location != null) {
      deviceLat = location.getLatitude();
      deviceLng = location.getLongitude();
      setupServices();
    } else {
      Toast.makeText(getActivity(), getString(R.string.device_location_failure), Toast.LENGTH_SHORT).show();
      defaultLocation();
    }
  }

  private void defaultLocation() {
    deviceLat = ABQ_LAT;
    deviceLng = ABQ_LNG;
    setupServices();
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    if (googleMap != null) {
      map = googleMap;
      map.setOnMarkerClickListener(LocalParkFragment.this);
      updateMap();
    }
  }

  private void updateMap(){
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(deviceLat, deviceLng), CAMERA_ZOOM));
    if (parks != null) {
      // Iterate through parks, extract lat/lng, and place marker on map.
      for (Park park : parks) {
        map.addMarker(
            new MarkerOptions().position(
                new LatLng(park.getLatitude(), park.getLongitude())
            ).title(park.getName())
        ).setTag(park.getId());
      }
    }
  }

  // TODO: consider more efficient lookup than iterating through entire collection
  @Override
  public boolean onMarkerClick(Marker marker) {
    if (marker.getTag() != null) {
      for (Park park : parks) {
        if (park.getId() == (long)marker.getTag()) {
          amenities = park.getAmenities();
          LocalParkFragment.this.park = park;
          parkName.setText(park.getName());
          populateList();
        }
      }
    }
    return false;
  }

  private void populateList(){
    int id = getResources().getIdentifier("@layout/amenity_item", "layout", getActivity().getPackageName());
    amenityAdapter = new ArrayAdapter<>(getActivity(), R.layout.amenity_item, R.id.amenity_name);
    amenityAdapter.addAll(amenities);
    listview.setAdapter(amenityAdapter);
  }

  private void setupServices() {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(Level.BODY);
    OkHttpClient.Builder httpClient = new Builder();
    httpClient.addInterceptor(loggingInterceptor);
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(new Gson()))
        .client(httpClient.build())
        .build();
    parkService = retrofit.create(ParksService.class);
    new ParksAsync().execute();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }

  @Override
  public void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override
  public void onStart() {
    super.onStart();
    mapView.onStart();
  }

  @Override
  public void onStop() {
    super.onStop();
    mapView.onStart();
  }

  @Override
  public void onPause() {
    super.onPause();
    mapView.onPause();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  private class ParksAsync extends AsyncTask<Void, Void, List<Park>> {

    @Override
    protected void onCancelled() {
      super.onCancelled();
      Toast.makeText(getActivity(), getString(R.string.parks_request_failure), Toast.LENGTH_SHORT)
          .show();
    }

    @Override
    protected void onPostExecute(List<Park> parks) {
      super.onPostExecute(parks);
      if (parks != null) {
        LocalParkFragment.this.parks = parks;
        showSearchedPark();
        // request GoogleMap object
        LocalParkFragment.this.mapView.getMapAsync(LocalParkFragment.this);
      }
    }

    @Override
    protected List<Park> doInBackground(Void... voids) {
      try {
        Response<List<Park>> response = parkService.getAllParks().execute();
        if (response.isSuccessful() && response.body() != null) {
          return response.body();
        } else {
          cancel(true);
        }
      } catch (IOException e) {
        e.printStackTrace();
        cancel(true);
      }
      return null;
    }
  }
}
