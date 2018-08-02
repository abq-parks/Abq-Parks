package edu.cnm.deepdive.abqparks.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import edu.cnm.deepdive.abqparks.R;
import edu.cnm.deepdive.abqparks.model.Review;
import edu.cnm.deepdive.abqparks.service.ParksService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewDialogFragment extends DialogFragment {

  public static final String BASE_URL = "http://10.0.2.2:25052/rest/abq_park/";

  private ReviewAdapter adapter;
  private RecyclerView recyclerView;
  private EditText reviewText;

  private HashMap<String, Integer> reviews;

  public ReviewDialogFragment() {
    //Required empty public constructor
  }

  public static ReviewDialogFragment reviewFragment() {
    ReviewDialogFragment fragment = new ReviewDialogFragment();
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder bob = new AlertDialog.Builder(getActivity());
    //Get Layout Inflater
    LayoutInflater inflater = getActivity().getLayoutInflater();
    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
    bob.setView(inflater.inflate(R.layout.fragment_review, null))
        .setTitle(R.string.reviews_button)
        .setPositiveButton(R.string.review_save_button, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

          }
        })
        .setNegativeButton(R.string.review_exit_button, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            ReviewDialogFragment.this.getDialog().cancel();
          }
        });
    return bob.create();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  private class GetReviewsTask extends AsyncTask<Void, Void, List<Review>> {

    private Response<List<Review>> response;

    @Override
    protected List<Review> doInBackground(Void... voids) {
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create(new Gson()))
          .build();

      ParksService client = retrofit.create(ParksService.class);

      try {
        response = client.getReviews(1).execute();
      } catch (IOException e) {
        // Do nothing for now.
      }

      return response.body();
    }

    @Override
    protected void onPostExecute(List<Review> reviews) {
      adapter = new ReviewAdapter(reviews);
      recyclerView.setAdapter(adapter);
    }
  }

  private class ReviewHolder extends RecyclerView.ViewHolder {

    private TextView reviewText;

    public ReviewHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.review_row, parent, false));

      reviewText = itemView.findViewById(R.id.review_list_text);
    }

    public void bind(Review review) {
      reviewText.setText(review.getReview());
    }
  }

  private class ReviewAdapter extends RecyclerView.Adapter<ReviewHolder> {

    private List<Review> reviewList;

    public ReviewAdapter(List<Review> review) {
      reviewList = review;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
      return new ReviewHolder(layoutInflater, viewGroup);
    }

    @Override
    public void onBindViewHolder(ReviewHolder reviewHolder, int postion) {
      Review review = reviewList.get(postion);
      reviewHolder.bind(review);
    }

    @Override
    public int getItemCount() {
      return reviewList.size();
    }
  }
}
