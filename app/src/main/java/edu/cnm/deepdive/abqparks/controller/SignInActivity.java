package edu.cnm.deepdive.abqparks.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import edu.cnm.deepdive.abqparks.ParksApplication;
import edu.cnm.deepdive.abqparks.R;

public class SignInActivity extends AppCompatActivity {

  private static final int REQUEST_CODE = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_in);
    SignInButton signIn = findViewById(R.id.sign_in);
    signIn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        signIn();
      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    if (account != null) {
      ParksApplication.getInstance().setSignInAccount(account);
      switchToMain();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE) {
      try {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        GoogleSignInAccount account = task.getResult(ApiException.class);
        ParksApplication.getInstance().setSignInAccount(account);
        switchToMain();
      } catch (ApiException e) {
        Toast.makeText(this, "Unable to sign in. Please check your credentials and connection.",
            Toast.LENGTH_LONG).show();
      }
    }
  }

  private void signIn() {
    Intent intent = ParksApplication.getInstance().getSignInClient().getSignInIntent();
    startActivityForResult(intent, REQUEST_CODE);
  }

  private void switchToMain() {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }
}
