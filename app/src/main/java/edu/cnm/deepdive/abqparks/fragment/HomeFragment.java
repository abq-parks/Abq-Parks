package edu.cnm.deepdive.abqparks.fragment;


import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.cnm.deepdive.abqparks.R;


/**
 * Landing fragment presented to user after sign in.
 */
public class HomeFragment extends Fragment {

  private static final int FINE_LOCATION_REQUEST_CODE = 1;

  public HomeFragment() {
    // Required empty public constructor
  }

  /**
   * Factory method for creating new HomeFragment instances.
   * @return
   */
  public static HomeFragment newInstance() {
    HomeFragment fragment = new HomeFragment();
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    if (ContextCompat.checkSelfPermission(getActivity(), permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      requestPermissions();
    }
    super.onViewCreated(view, savedInstanceState);
  }

  private void requestPermissions() {
    // Explanation required
    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
        permission.ACCESS_FINE_LOCATION)) {
      ActivityCompat.requestPermissions(getActivity(),
          new String[]{permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_REQUEST_CODE);
    } else {
      ActivityCompat.requestPermissions(getActivity(),
          new String[]{permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_REQUEST_CODE);
    }
  }


}
