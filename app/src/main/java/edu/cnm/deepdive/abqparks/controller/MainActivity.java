package edu.cnm.deepdive.abqparks.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import edu.cnm.deepdive.abqparks.ParksApplication;
import edu.cnm.deepdive.abqparks.R;
import edu.cnm.deepdive.abqparks.fragment.AmenitySearchFragment;
import edu.cnm.deepdive.abqparks.fragment.HomeFragment;
import edu.cnm.deepdive.abqparks.fragment.LocalParkFragment;
import edu.cnm.deepdive.abqparks.model.User;
import edu.cnm.deepdive.abqparks.service.ParksService;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Controller for all fragments handling user interface components.
 */
public class MainActivity extends AppCompatActivity {

  private GoogleSignInAccount googleAccount;
  ParksService parkService;

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
      displaySelectedScreen(item.getItemId());
      return true;
    }

    private void displaySelectedScreen(int itemId) {
      Fragment fragment = null;
      switch (itemId) {
        case R.id.navigation_home:
          fragment = new HomeFragment();
          break;
        case R.id.local_search:
          fragment = new LocalParkFragment();
          break;
        case R.id.amenity_search:
          fragment = new AmenitySearchFragment();
          break;
      }
      if (fragment != null) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        ft.replace(R.id.main_frame, fragment);
        ft.commit();
      }
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    googleAccount = ParksApplication.getInstance().getSignInAccount();
    setupService();

    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    navigation.setSelectedItemId(R.id.navigation_home);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_logout:
        SignOut();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }

  }

  private void SignOut() {
    getParksApplication().getSignInClient().signOut()
        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
            getParksApplication().setSignInAccount(null);
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
          }
        });
  }

  private void setupService() {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(Level.BODY);
    OkHttpClient.Builder httpClient = new Builder();
    httpClient.addInterceptor(loggingInterceptor);
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://10.0.2.2:25052/rest/abq_park/") // TODO: replace with buildconfig or constant
        .addConverterFactory(GsonConverterFactory.create(new Gson()))
        .client(httpClient.build())
        .build();
    parkService = retrofit.create(ParksService.class);
    new UserAsync().execute();
  }

  private ParksApplication getParksApplication() {
    return (ParksApplication) getApplication();
  }

  private class UserAsync extends AsyncTask<Void, Void, User> {

    @Override
    protected User doInBackground(Void... voids) {
      User user = new User();
      user.setGoogleID(googleAccount.getId());
      user.setFirstName(googleAccount.getGivenName());
      user.setLastName(googleAccount.getFamilyName());
      user.setUserEmail(googleAccount.getEmail());
      try {
        String token = ParksApplication.getInstance().getSignInAccount().getIdToken();
        parkService.createUser(user, getString(R.string.oauth2_header_format, token)).execute();
        Response<User> response = parkService.createUser(user, token).execute();
        if (response.isSuccessful() && response.body() != null) {
          return response.body();
        }
      } catch (IOException e) {
        cancel(true); // TODO: Implement onCanceled
      }
      return null;
    }

    @Override
    protected void onPostExecute(User user) {
      if (user != null) {
        Editor editor;
        SharedPreferences sharedPreferences;
        sharedPreferences = getApplicationContext().getSharedPreferences("ABQPARKS", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putLong("USERID", user.getId());
        editor.commit();
      }
    }
  }

}
