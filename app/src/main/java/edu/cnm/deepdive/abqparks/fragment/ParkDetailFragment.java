package edu.cnm.deepdive.abqparks.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.abqparks.R;
import edu.cnm.deepdive.abqparks.model.Park;
import edu.cnm.deepdive.abqparks.service.ParksService;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParkDetailFragment extends Fragment implements OnMapReadyCallback {

  private static final String PARK_ID_KEY = "park_id";
  public static final float CAMERA_ZOOM = 15.0f;

  private MapView mapView;
  private GoogleMap map;
  private Park park;
  private ParksService parkService;
  private long parkId;

  public ParkDetailFragment() {
    // Required empty public constructor
  }

  public static ParkDetailFragment newInstance(Long parkId) {
    ParkDetailFragment fragment = new ParkDetailFragment();
    Bundle args = new Bundle();
    args.putLong(PARK_ID_KEY, parkId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      parkId = savedInstanceState.getLong(PARK_ID_KEY);
    }
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_park_detail, container, false);
    mapView = view.findViewById(R.id.detail_map_view);
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (parkId != 0) {
      setupServices();
      new ParkAsync().execute();
    }
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

  private void setupServices(){
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(Level.BODY);
    OkHttpClient.Builder httpClient = new Builder();
    httpClient.addInterceptor(interceptor);
    Gson gson = new GsonBuilder().
        excludeFieldsWithoutExposeAnnotation()
        .create();
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://localhost:25052//rest/abq_park/") // TODO: replace with buildconfig or constant
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient.build())
        .build();
    parkService = retrofit.create(ParksService.class);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    if (googleMap != null) {
      map = googleMap;
      updateMap();
    }
  }

  private void updateMap(){
    map.addMarker(new MarkerOptions().
        position(new LatLng(park.getLatitude(),
            park.getLongitude())
        )
    );
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(
        new LatLng(park.getLatitude(), park.getLongitude()),
        CAMERA_ZOOM));
  }

  private class ParkAsync extends AsyncTask<Void, Void, Park> {

    @Override
    protected void onCancelled() {
      Toast.makeText(getActivity(),
          getString(R.string.single_park_request_failure),
          Toast.LENGTH_SHORT).show();
      super.onCancelled();
    }

    @Override
    protected void onPostExecute(Park park) {
      ParkDetailFragment.this.park = park;
      mapView.getMapAsync(ParkDetailFragment.this);
    }

    @Override
    protected Park doInBackground(Void... voids) {
      try {
        Response<Park> response = parkService.getPark(parkId).execute();
        if (response.isSuccessful() && response.body() != null) {
          return response.body();
        } else {
          cancel(true);
        }
      } catch (IOException e){
        e.printStackTrace();
        cancel(true);
      }
      return null;
    }
  }
}
