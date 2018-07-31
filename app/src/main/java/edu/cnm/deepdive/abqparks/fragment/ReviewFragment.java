package edu.cnm.deepdive.abqparks.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import edu.cnm.deepdive.abqparks.R;

public class ReviewFragment extends Fragment {

  private Button reviewSaveButton;
  private RecyclerView reviewList;
  private EditText reviewText;

  public ReviewFragment() {
    //Required empty public constructor
  }

  public static ReviewFragment reviewFragment() {
    ReviewFragment fragment = new ReviewFragment();
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_review, container, false);


    reviewList = view.findViewById(R.id.review_list);
    reviewText = view.findViewById(R.id.review_text);
    reviewSaveButton = view.findViewById(R.id.review_save_button);

    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }
}
