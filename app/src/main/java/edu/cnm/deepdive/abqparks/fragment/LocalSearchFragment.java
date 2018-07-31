package edu.cnm.deepdive.abqparks.fragment;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import edu.cnm.deepdive.abqparks.R;


public class LocalSearchFragment extends Fragment {

  private static final String POSTAL_KEY = "postal_key";
  private static final int FINE_LOCATION_REQUEST_CODE = 1;

  private LocationManager locationManager;
  private double deviceLat;
  private double deviceLng;

  public LocalSearchFragment() {
    // Required empty public constructor
  }

  public static LocalSearchFragment newInstance(String param1, String param2) {
    LocalSearchFragment fragment = new LocalSearchFragment();
    return fragment;
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    if (ContextCompat.checkSelfPermission(getActivity(), permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      requestPermissions();
    } else {
      getLocation();
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_local_search, container, false);
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
          Toast.makeText(getActivity(), "Cannot locate local parks without location permissions.",
              Toast.LENGTH_SHORT).show();
        }
    }
  }

  private void updateMap() {

  }

  private void getLocation() {
    @SuppressLint("MissingPermission") // getLocation is only called if permission has been granted
    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    if (location != null) {
      deviceLat = location.getLatitude();
      deviceLng = location.getLongitude();
    } else {
      Toast.makeText(getActivity(), "Unable to determine device location.", Toast.LENGTH_SHORT).show();
    }
  }


  // TODO: save postal code to recover from fragment/activity destruction.
  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }
}
