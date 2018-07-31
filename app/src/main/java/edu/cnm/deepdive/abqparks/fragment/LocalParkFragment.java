package edu.cnm.deepdive.abqparks.fragment;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import edu.cnm.deepdive.abqparks.R;
//implements OnMapReadyCallback
public class LocalParkFragment extends Fragment {

  private static final String POSTAL_KEY = "postal_key";
  private static final int FINE_LOCATION_REQUEST_CODE = 1;

  private LocationManager locationManager;
  private double deviceLat;
  private double deviceLng;
  private MapView mapView;
  private GoogleMap map;

  private Button reviewButton;

  public LocalParkFragment() {
    // Required empty public constructor
  }

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
//    mapView = view.findViewById(R.id.local_map_view);
//    mapView.onCreate(savedInstanceState);
    reviewButton = view.findViewById(R.id.review_button);
    reviewButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        showReviewFragment();
      }
    });
    return view;
  }

  private void showReviewFragment() {
    Fragment fragment = new ReviewFragment();
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.container, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }
  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    if (ContextCompat.checkSelfPermission(getActivity(), permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      requestPermissions();
    } else {
      getLocation();
    }
    super.onViewCreated(view, savedInstanceState);
  }

  private void requestPermissions() {
    // Explanation required
    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
        permission.ACCESS_FINE_LOCATION)) {
      // TODO: implement asynchronous permission prompt with explanation
    } else {
      ActivityCompat.requestPermissions(getActivity(),
          new String[]{permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_REQUEST_CODE);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case FINE_LOCATION_REQUEST_CODE:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          getLocation();
        } else {
          Toast.makeText(getActivity(), getString(R.string.location_permission_denial),
              Toast.LENGTH_SHORT).show();
        }
    }
  }

  private void getLocation() {
    @SuppressLint("MissingPermission") // getLocation is only called if permission has been granted
    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    if (location != null) {
      deviceLat = location.getLatitude();
      deviceLng = location.getLongitude();
//      mapView.getMapAsync(this); // onMapyReady() callback will be called in response
    } else {
      Toast.makeText(getActivity(), getString(R.string.device_location_failure), Toast.LENGTH_SHORT).show();
    }
  }

//  @Override
//  public void onMapReady(GoogleMap googleMap) {
//    if (googleMap != null) {
//      map = googleMap;
//      updateMap();
//    }
//  }

//  // TODO: Add markers for list of parks returned from request to backend.
//  private void updateMap(){
//    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(deviceLat, deviceLng), 15));
//  }

  // TODO: save postal code to recover from fragment/activity destruction.
//  @Override
//  public void onSaveInstanceState(Bundle outState) {
//    super.onSaveInstanceState(outState);
//    mapView.onSaveInstanceState(outState);
//  }
//
//  @Override
//  public void onResume() {
//    super.onResume();
//    mapView.onResume();
//  }
//
//  @Override
//  public void onStart() {
//    super.onStart();
//    mapView.onStart();
//  }
//
//  @Override
//  public void onStop() {
//    super.onStop();
//    mapView.onStart();
//  }
//
//  @Override
//  public void onPause() {
//    super.onPause();
//    mapView.onPause();
//  }
//
//  @Override
//  public void onDestroy() {
//    super.onDestroy();
//    mapView.onDestroy();
//  }
}
