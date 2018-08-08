package edu.cnm.deepdive.abqparks.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.google.gson.Gson;
import edu.cnm.deepdive.abqparks.BuildConfig;
import edu.cnm.deepdive.abqparks.R;
import edu.cnm.deepdive.abqparks.model.Amenity;
import edu.cnm.deepdive.abqparks.model.Park;
import edu.cnm.deepdive.abqparks.service.ParksService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Queries back end for Park objects with amenities matching those selected by the user.
 */
public class AmenitySearchFragment extends Fragment {

  private Button searchButton;
  private ToggleButton basketballToggle;
  private ToggleButton tennisToggle;
  private ToggleButton playAreaToggle;
  private ToggleButton soccerToggle;
  private ToggleButton softballToggle;
  private ToggleButton volleyballToggle;
  private ToggleButton joggingToggle;
  private ToggleButton picnicToggle;
  private ToggleButton indoorPoolToggle;
  private ToggleButton outdoorPoolToggle;
  private ToggleButton shadeAreaToggle;

  private RecyclerView recyclerView;
  private ParkAdapter adapter;
  private String checkedAmenities;
  private HashMap<String, Integer> amenities;

  public AmenitySearchFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_amenity_search, container, false);
    setupUI(view);
    new GetAmenitiesTask().execute();
    searchButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        checkToggled();
        if (checkedAmenities != "") {
          new GetParksTask().execute();
        }
      }
    });
    return view;
  }

  private void checkToggled() {
    checkedAmenities = "";
    if (amenities != null) {
      if (basketballToggle.isChecked()) {
        checkedAmenities += String.valueOf(amenities.get("FULLBASKETBALLCOURTS") + ",");
      }
      if (tennisToggle.isChecked()) {
        checkedAmenities += String.valueOf(amenities.get("LITTENNISCOURTS") + ",");
      }
      if (playAreaToggle.isChecked()) {
        checkedAmenities += String.valueOf(amenities.get("PLAYAREAS") + ",");
      }
      if (soccerToggle.isChecked()) {
        checkedAmenities += String.valueOf(amenities.get("SOCCERFIELDS") + ",");
      }
      if (softballToggle.isChecked()) {
        checkedAmenities += String.valueOf(amenities.get("LITSOFTBALLFIELDS") + ",");
      }
      if (volleyballToggle.isChecked()) {
        checkedAmenities += String.valueOf(amenities.get("VOLLEYBALLCOURTS") + ",");
      }
      if (joggingToggle.isChecked()) {
        checkedAmenities += String.valueOf(amenities.get("JOGGINGPATHS") + ",");
      }
      if (picnicToggle.isChecked()) {
        checkedAmenities += String.valueOf(amenities.get("PICNICTABLES") + ",");
      }
      if (indoorPoolToggle.isChecked()) {
        checkedAmenities += String.valueOf(amenities.get("INDOORPOOLS") + ",");
      }
      if (outdoorPoolToggle.isChecked()) {
        checkedAmenities += String.valueOf(amenities.get("OUTDOORPOOLS") + ",");
      }
      if (shadeAreaToggle.isChecked()) {
        checkedAmenities += String.valueOf(amenities.get("SHADESTRUCTURES") + ",");
      }
      if (checkedAmenities.length() > 1) {
        checkedAmenities = checkedAmenities.substring(0, checkedAmenities.length() - 1);
      }
    }
  }

  private void setupUI(View view) {
    recyclerView = view.findViewById(R.id.search_result_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    basketballToggle = view.findViewById(R.id.basketball_button);
    tennisToggle = view.findViewById(R.id.tennis_button);
    playAreaToggle = view.findViewById(R.id.play_area_button);
    soccerToggle = view.findViewById(R.id.soccer_button);
    softballToggle = view.findViewById(R.id.softball_button);
    volleyballToggle = view.findViewById(R.id.volleyball_button);
    joggingToggle = view.findViewById(R.id.jogging_button);
    picnicToggle = view.findViewById(R.id.picnic_button);
    indoorPoolToggle = view.findViewById(R.id.indoor_pool_button);
    outdoorPoolToggle = view.findViewById(R.id.outdoor_pool_button);
    shadeAreaToggle = view.findViewById(R.id.shade_area_button);
    searchButton = view.findViewById(R.id.search_button);
  }

  private class ParkHolder extends RecyclerView.ViewHolder implements OnClickListener{

    private TextView parkText;
    private Park park;

    public ParkHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.park_row, parent, false));

      itemView.setOnClickListener(this);
      parkText = itemView.findViewById(R.id.park_text);
    }

    public void bind(Park park) {
      parkText.setText(park.getName());
      this.park = park;
    }

    @Override
    public void onClick(View v) {
      Bundle bundle = new Bundle();
      bundle.putLong("PARKID", park.getId());
      LocalParkFragment localParkFragment = new LocalParkFragment();
      localParkFragment.setArguments(bundle);
      FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
      fragmentManager.beginTransaction().replace(R.id.main_frame, localParkFragment)
          .commit();
    }
  }

  private class ParkAdapter extends RecyclerView.Adapter<ParkHolder> {

    private List<Park> parkList;

    public ParkAdapter(List<Park> park) {
      parkList = park;
    }

    @Override
    public ParkHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
      return new ParkHolder(layoutInflater, viewGroup);
    }

    @Override
    public void onBindViewHolder(ParkHolder parkHolder, int postion) {
      Park park = parkList.get(postion);
      parkHolder.bind(park);
    }

    @Override
    public int getItemCount() {
      return parkList.size();
    }
  }

  private class GetAmenitiesTask extends AsyncTask<Void, Void, List<Amenity>> {

    private Response<List<Amenity>> response;

    @Override
    protected void onCancelled() {
      super.onCancelled();
      Toast.makeText(getActivity(), getString(R.string.amenities_request_failure), Toast.LENGTH_SHORT)
          .show();
    }

    @Override
    protected List<Amenity> doInBackground(Void... voids) {
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(BuildConfig.BASE_URL)
          .addConverterFactory(GsonConverterFactory.create(new Gson()))
          .build();
      ParksService client = retrofit.create(ParksService.class);
      try {
        response = client.getAmenities().execute();
        if (response.isSuccessful() && response.body() != null) {
          return response.body();
        } else {
          cancel(true);
        }
      } catch (IOException e) {
        cancel(true);
      }
      return null;
    }

    @Override
    protected void onPostExecute(List<Amenity> amenities) {
      AmenitySearchFragment.this.amenities = new HashMap<>();
      for (Amenity amenity : amenities) {
        AmenitySearchFragment.this.amenities.put(amenity.getName(), amenity.getId());
      }
    }
  }

  private class GetParksTask extends AsyncTask<Void, Void, List<Park>> {

    private Response<List<Park>> response;

    @Override
    protected void onCancelled() {
      super.onCancelled();
      Toast.makeText(getActivity(), getString(R.string.parks_request_failure), Toast.LENGTH_SHORT)
          .show();
    }

    @Override
    protected List<Park> doInBackground(Void... voids) {
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(BuildConfig.BASE_URL)
          .addConverterFactory(GsonConverterFactory.create(new Gson()))
          .build();

      ParksService client = retrofit.create(ParksService.class);

      try {
        response = client.getParks(checkedAmenities).execute();
        if (response.isSuccessful() && response.body() != null) {
          return response.body();
        } else {
          cancel(true);
        }
      } catch (IOException e) {
        cancel(true);
      }
      return null;
    }

    @Override
    protected void onPostExecute(List<Park> parks) {
      if (parks != null) {
        adapter = new ParkAdapter(parks);
        recyclerView.setAdapter(adapter);
      }
    }
  }
}