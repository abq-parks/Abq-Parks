package edu.cnm.deepdive.abqparks.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import com.google.gson.Gson;
import edu.cnm.deepdive.abqparks.R;
import edu.cnm.deepdive.abqparks.model.Amenity;
import edu.cnm.deepdive.abqparks.service.ParksService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DynamicButtonsFragment extends Fragment {

  private static final String BASE_URL = "http://10.0.2.2:25052/rest/abq_park/";

  private List<Amenity> checkedAmenityList = new ArrayList<>();
  private List<Amenity> amenityList = new ArrayList<>();
  private List<ToggleButton> toggleButtons = new ArrayList<>();
  private View view;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_dynamic_buttons, container, false);
    setupUI(view);
    new GetAmenitiesTask().execute();
    return view;
  }

  private void setupUI(View view) {
    Button searchButton = view.findViewById(R.id.amenity_search_button);
    searchButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        for (int i = 0; i < toggleButtons.size(); i++) {
          if (toggleButtons.get(i).isChecked()) {
            checkedAmenityList.add(amenityList.get(i));
          }
        }
        checkedAmenityList.size();
      }
    });
  }

  private class GetAmenitiesTask extends AsyncTask<Void, Void, List<Amenity>> {

    private Response<List<Amenity>> response;

    @Override
    protected List<Amenity> doInBackground(Void... voids) {
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create(new Gson()))
          .build();
      ParksService client = retrofit.create(ParksService.class);
      try {
        response = client.getAmenities().execute();
      } catch (IOException e) {
        // Do nothing for now.
      }
      return response.body();
    }

    @Override
    protected void onPostExecute(List<Amenity> amenities) {
      int row = 1;
      amenityList = amenities;
      LinearLayout linearLayout = view.findViewById(R.id.button_layout);
      LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
      LinearLayout newLine = null;
      for (Amenity amenity : amenities) {
        ToggleButton toggleButton = new ToggleButton(getContext());
        toggleButton.setText(amenity.getDisplayName());
        toggleButton.setTextOn(amenity.getDisplayName());
        toggleButton.setTextOff(amenity.getDisplayName());
        toggleButton.setLayoutParams(layoutParams);
        toggleButtons.add(toggleButton);
        if (row % 2 == 0) {
          newLine.setLayoutParams(layoutParams);
          newLine.setOrientation(LinearLayout.HORIZONTAL);
          newLine.addView(toggleButton);
          linearLayout.addView(newLine);
          row++;
        } else {
          row++;
          newLine = new LinearLayout(getContext());
          newLine.setLayoutParams(layoutParams);
          newLine.setOrientation(LinearLayout.HORIZONTAL);
          newLine.addView(toggleButton);
        }
      }
    }
  }
}