package edu.cnm.deepdive.abqparks.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.GsonBuildConfig;
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

public class ParkDetailFragment extends Fragment {

  private static final String PARK_ID_KEY = "park_id";

  private MapView mapView;
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
    setupServices();
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

//  private class ParkAsync extends AsyncTask<Void, Void, Park> {
//
//    @Override
//    protected void onPostExecute(Park park) {
//      super.onPostExecute(park);
//    }
//
////    @Override
////    protected Park doInBackground(Void... voids) {
////      try {
////
////      } catch (IOException e){
////
////      }
////    }
////  }
}
