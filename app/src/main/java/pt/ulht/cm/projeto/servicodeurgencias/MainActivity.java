package pt.ulht.cm.projeto.servicodeurgencias;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    String provider;

    final Fragment hospitalListFragment = new HospitalListFragment();
    final Fragment mapsFragment = new MapsFragment();
    final Fragment hospitalFormFragment = new HospitalFormFragment();
    final Fragment hospitalHistoryFragment = new HospitalHistoryFragment();
    final FragmentManager fragmentManager = getSupportFragmentManager();

    Fragment activeFragment = hospitalListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        checkLocationPermission();

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager.beginTransaction().add(R.id.main_container, hospitalListFragment, "1").commit();
        fragmentManager.beginTransaction().add(R.id.main_container, mapsFragment, "2").hide(mapsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, hospitalFormFragment, "3").hide(hospitalFormFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, hospitalHistoryFragment, "4").hide(hospitalHistoryFragment).commit();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.tab_hospital_list :
                    fragmentManager.beginTransaction().hide(activeFragment).show(hospitalListFragment).commit();
                    activeFragment = hospitalListFragment;
                    return true;
                case R.id.tab_map :
                    fragmentManager.beginTransaction().hide(activeFragment).show(mapsFragment).commit();
                    activeFragment = mapsFragment;
                    return true;
                case R.id.tab_hospital_form :
                    fragmentManager.beginTransaction().hide(activeFragment).show(hospitalFormFragment).commit();
                    activeFragment = hospitalFormFragment;
                    return true;
                case R.id.tab_hospital_history :
                    fragmentManager.beginTransaction().hide(activeFragment).show(hospitalHistoryFragment).commit();
                    activeFragment = hospitalHistoryFragment;
                    return true;
            }
            return false;
        }
    };

    public static final int PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(provider, 400, 1, this);
                }
            }
            else {
                // EMPTY
            }

            return;
        }
    }
}