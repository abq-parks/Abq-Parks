package edu.cnm.deepdive.abqparks;

import android.app.Application;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class ParksApplication extends Application {

  private static ParksApplication instance;

  GoogleSignInClient signInClient;
  GoogleSignInAccount singInAccount;



  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    GoogleSignInOptions options = new GoogleSignInOptions.Builder()
        .requestEmail()
        .requestId()
        .requestIdToken(getString(R.string.client_id))
        .build();
    signInClient = GoogleSignIn.getClient(this, options);
  }

  public static ParksApplication getInstance() {
    return instance;
  }

  public GoogleSignInClient getSignInClient() {
    return signInClient;
  }

  public void setSignInClient(GoogleSignInClient signInClient) {
    this.signInClient = signInClient;
  }

  public GoogleSignInAccount getSingInAccount() {
    return singInAccount;
  }

  public void setSingInAccount(GoogleSignInAccount singInAccount) {
    this.singInAccount = singInAccount;
  }
}
