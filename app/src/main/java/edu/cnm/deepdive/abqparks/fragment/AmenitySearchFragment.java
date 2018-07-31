package edu.cnm.deepdive.abqparks.fragment;



import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.gson.Gson;
import edu.cnm.deepdive.abqparks.R;
import edu.cnm.deepdive.abqparks.model.Amenity;
import edu.cnm.deepdive.abqparks.model.Park;
import edu.cnm.deepdive.abqparks.service.ParksService;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass. Use the {@link AmenitySearchFragment#newInstance} factory
 * method to create an instance of this fragment.
 */
public class AmenitySearchFragment extends Fragment {

  private RecyclerView recyclerView;
  private ParkAdapter adapter;
  private List<Amenity> amenities;

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;


  public AmenitySearchFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment AmenitySearchFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static AmenitySearchFragment newInstance(String param1, String param2) {
    AmenitySearchFragment fragment = new AmenitySearchFragment();
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
    View view = inflater.inflate(R.layout.fragment_amenity_search, container, false);
    recyclerView = view.findViewById(R.id.search_result_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    new GetAmenitesTask().execute();
    return view;
  }

  private class ParkHolder extends RecyclerView.ViewHolder {

    private TextView parkText;

    public ParkHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.park_row, parent, false));

      parkText = itemView.findViewById(R.id.park_text);
    }

    public void bind(Park park) {
      parkText.setText(park.getName());
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


  private class GetAmenitesTask extends AsyncTask<Void, Void, List<Amenity>> {

    private Response<List<Amenity>> response;

    @Override
    protected List<Amenity> doInBackground(Void... voids) {
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("http://10.0.2.2:25052/rest/abq_park/")
          .addConverterFactory(GsonConverterFactory.create(new Gson()))
          .build();

      ParksService client = retrofit.create(ParksService.class);

      try {
        response = client.getAmenities().execute();
      } catch (IOException e) {
        e.printStackTrace();
      }

      return response.body();
    }

    @Override
    protected void onPostExecute(List<Amenity> amenities) {
      AmenitySearchFragment.this.amenities = amenities;
    }
  }

}
