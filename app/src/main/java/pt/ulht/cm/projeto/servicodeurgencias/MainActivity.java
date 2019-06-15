package pt.ulht.cm.projeto.servicodeurgencias;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager.beginTransaction().add(R.id.main_container, hospitalListFragment, "1").commit();
        fragmentManager.beginTransaction().add(R.id.main_container, mapsFragment, "2").hide(mapsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, hospitalFormFragment, "3").hide(hospitalFormFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, hospitalHistoryFragment, "4").hide(hospitalHistoryFragment).commit();
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
}