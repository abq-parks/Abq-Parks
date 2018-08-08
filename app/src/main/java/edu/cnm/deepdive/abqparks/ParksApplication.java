package edu.cnm.deepdive.abqparks;

import android.app.Application;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/**
 * Defines logic for Google sign-in.
 */
public class ParksApplication extends Application {

  private static ParksApplication instance;

  GoogleSignInClient signInClient;
  GoogleSignInAccount signInAccount;

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

  /**
   * Get instance of ParksApplication.
   * @return
   */
  public static ParksApplication getInstance() {
    return instance;
  }

  /**
   * Get GoogleSignInClient.
   * @return
   */
  public GoogleSignInClient getSignInClient() {
    return signInClient;
  }

  /**
   * Set GoogleSignInClient.
   * @param signInClient
   */
  public void setSignInClient(GoogleSignInClient signInClient) {
    this.signInClient = signInClient;
  }

  /**
   * Get GoogleSignInAccount with profile information.
   * @return
   */
  public GoogleSignInAccount getSignInAccount() {
    return signInAccount;
  }

  /**
   * Set GoogleSignInAccount.
   * @param signInAccount
   */
  public void setSignInAccount(GoogleSignInAccount signInAccount) {
    this.signInAccount = signInAccount;
  }
}
