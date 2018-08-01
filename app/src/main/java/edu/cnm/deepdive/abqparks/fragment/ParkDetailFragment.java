package edu.cnm.deepdive.abqparks.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapView;
import edu.cnm.deepdive.abqparks.R;

/**
 * A simple {@link Fragment} subclass. Use the {@link ParkDetailFragment#newInstance} factory method
 * to create an instance of this fragment.
 */
public class ParkDetailFragment extends Fragment {

  private static final String PARK_ID_KEY = "park_id";

  private MapView mapView;
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

}
