package edu.cnm.deepdive.abqparks.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import edu.cnm.deepdive.abqparks.ParksApplication;
import edu.cnm.deepdive.abqparks.R;
import edu.cnm.deepdive.abqparks.fragment.AmenitySearchFragment;
import edu.cnm.deepdive.abqparks.fragment.HomeFragment;
import edu.cnm.deepdive.abqparks.fragment.LocalParkFragment;

public class MainActivity extends AppCompatActivity {

  private TextView mTextMessage;

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

    mTextMessage = (TextView) findViewById(R.id.message);
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

  private ParksApplication getParksApplication() {
    return (ParksApplication) getApplication();
  }


}
