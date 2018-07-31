package edu.cnm.deepdive.abqparks.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.cnm.deepdive.abqparks.R;


public class LocalSearchFragment extends Fragment {

  public static final String POSTAL_KEY = "postal_key";

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
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_local_search, container, false);
  }

  // TODO: save postal code to recover from fragment/activity destruction.
  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }
}
