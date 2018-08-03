package edu.cnm.deepdive.abqparks.controller;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
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
        ft.replace(R.id.main_frame, fragment);
        ft.commit();
      }
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mTextMessage = (TextView) findViewById(R.id.message);
    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    navigation.setSelectedItemId(R.id.navigation_home);
  }

}
