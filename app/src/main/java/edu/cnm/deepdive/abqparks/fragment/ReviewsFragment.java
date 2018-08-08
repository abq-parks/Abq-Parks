package edu.cnm.deepdive.abqparks.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import edu.cnm.deepdive.abqparks.R;
import edu.cnm.deepdive.abqparks.model.Review;
import edu.cnm.deepdive.abqparks.service.ParksService;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Displays all reviews for a park selected by the user.
 */
public class ReviewsFragment extends Fragment {

  public static final String PARK_ID_KEY = "PARKID";
  public static final String PARK_NAME_KEY = "PARKNAME";
  private static final String BASE_URL = "http://10.0.2.2:25052/rest/abq_park/";

  private ListView reviewsList;
  private Button postReviewButton;
  private TextView parkNameText;

  private ArrayAdapter<Review> reviewAdapter;
  private List<Review> reviews;
  private long parkId;
  private String parkName;

  public ReviewsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_reviews, container, false);
    setupUI(view);

    Bundle bundle = this.getArguments();
    if (bundle != null) {
      parkId = bundle.getLong(PARK_ID_KEY, -1);
      parkName = bundle.getString(PARK_NAME_KEY, "Unknown park.");
      parkNameText.setText(parkName);
      if (parkId != -1) {
        new GetReviewsTask().execute();
      }
    }
    return view;
  }

  private void setupUI(View view) {
    parkNameText = view.findViewById(R.id.park_name);
    reviewsList = view.findViewById(R.id.park_reviews_list);
    postReviewButton = view.findViewById(R.id.post_review_button);
    postReviewButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putLong(PARK_ID_KEY, parkId);
        DialogFragment fragment = new ReviewDialogFragment();
        fragment.setArguments(bundle);
        fragment.show(getFragmentManager(), "reviews");
      }
    });
  }

  private void populateList(){
    int id = getResources().getIdentifier("@layout/review_item", "layout", getActivity().getPackageName());
    reviewAdapter = new ArrayAdapter<>(getActivity(), R.layout.review_item, R.id.posted_review_text);
    reviewAdapter.addAll(reviews);
    reviewsList.setAdapter(reviewAdapter);
  }

  private class GetReviewsTask extends AsyncTask<Void, Void, List<Review>> {

    private Response<List<Review>> response;

    @Override
    protected void onCancelled() {
      super.onCancelled();
      // TODO Extract text.
      Toast.makeText(getActivity(), getString(R.string.reviews_request_failure), Toast.LENGTH_SHORT)
          .show();
    }

    @Override
    protected List<Review> doInBackground(Void... voids) {
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create(new Gson()))
          .build();

      ParksService client = retrofit.create(ParksService.class);

      try {
        response = client.getReviews(parkId).execute();
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
    protected void onPostExecute(List<Review> reviews) {
      if (reviews != null) {
        ReviewsFragment.this.reviews = reviews;
        populateList();
      }
    }
  }

}
