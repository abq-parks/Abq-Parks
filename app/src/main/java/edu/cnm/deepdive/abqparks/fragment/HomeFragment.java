package edu.cnm.deepdive.abqparks.fragment;


import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import edu.cnm.deepdive.abqparks.R;


/**
 * A simple {@link Fragment} subclass. Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  private static final int FINE_LOCATION_REQUEST_CODE = 1;

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;


  public HomeFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment HomeFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static HomeFragment newInstance(String param1, String param2) {
    HomeFragment fragment = new HomeFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
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
