package edu.cnm.deepdive.abqparks.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.google.gson.Gson;
import edu.cnm.deepdive.abqparks.BuildConfig;
import edu.cnm.deepdive.abqparks.R;
import edu.cnm.deepdive.abqparks.model.Review;
import edu.cnm.deepdive.abqparks.service.ParksService;
import java.io.IOException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Allows users to post park reviews.
 */
public class ReviewDialogFragment extends DialogFragment {

  private static final String PARK_ID_KEY = "PARKID";

  private long parkId;
  private long userId;
  private String reviewId;

  public ReviewDialogFragment() {
    //Required empty public constructor
  }

  /**
   * Factory method for creating new ReviewDialogFragment instances.
   * @return
   */
  public static ReviewDialogFragment reviewFragment() {
    ReviewDialogFragment fragment = new ReviewDialogFragment();
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = this.getArguments();
    if (bundle != null) {
      parkId = bundle.getLong(PARK_ID_KEY, -1);
      if (parkId == -1) {
        // TODO Set to 1 for testing. Change!!
        parkId = 1;
      }
    }
    SharedPreferences sharedPreferences;
    sharedPreferences = getContext().getApplicationContext().getSharedPreferences("ABQPARKS", MODE_PRIVATE);
    userId = sharedPreferences.getLong("USERID", -1l);
    if (userId == -1l) {

    }
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
            EditText reviewText = getDialog().findViewById(R.id.review_text);
            Review review = new Review();
            review.setReview(reviewText.getText().toString());
            new PostReviewTask().execute(review);
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

  private class PostReviewTask extends AsyncTask<Review, Void, Void> {

    @Override
    protected Void doInBackground(Review... reviews) {
      reviewId = String.valueOf(parkId) + "," + String.valueOf(userId);
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(BuildConfig.BASE_URL)
          .addConverterFactory(GsonConverterFactory.create(new Gson()))
          .build();
      ParksService parksService = retrofit.create(ParksService.class);
      try {
        Response<Review> response = parksService.createReview(reviewId, reviews[0]).execute();
        if (response.isSuccessful() && response != null) {
          // TODO Send response.
        }
      } catch (IOException e) {
        // TODO Nothing.
        e.printStackTrace();
      }
      return null;
    }
  }

}
