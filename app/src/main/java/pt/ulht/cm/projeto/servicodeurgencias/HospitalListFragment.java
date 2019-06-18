package pt.ulht.cm.projeto.servicodeurgencias;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pt.ulht.cm.projeto.servicodeurgencias.services.TemposHospitalProvider;

import static android.content.Context.LOCATION_SERVICE;

public class HospitalListFragment extends Fragment {
    private static final int LOCATION_REFRESH_TIME = 1;
    private static final float LOCATION_REFRESH_DISTANCE = 1;

    private TemposHospitalProvider hospitalProvider;
    private HospitalListAdapter hospitalAdapter;
    private LocationManager locationManager;
    //private Location userLocation;

    public HospitalListFragment() {
        // To leave empty
    }

    public static class RecyclerViewItemClickListener implements View.OnClickListener {
        private int itemID;

        public RecyclerViewItemClickListener(int itemID) {
            this.itemID = itemID;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), HospitalDetailActivity.class);
            intent.putExtra(HospitalDetailActivity.HOSPITAL_ID, String.valueOf(itemID));
            view.getContext().startActivity(intent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_hospital_list_fragment, container, false);

        RecyclerView hospitalRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_hospital_list);

        RecyclerView.LayoutManager hospitalLayoutManager = new LinearLayoutManager(getActivity());
        hospitalRecyclerView.setLayoutManager(hospitalLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(hospitalRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        hospitalRecyclerView.addItemDecoration(itemDecoration);

        // Get location from the user and initialize its Manager

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            Log.d("LOCATION_PERMISSION", "Permission denied...");
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, locationListener);


        hospitalProvider = TemposHospitalProvider.getInstance();
        hospitalProvider.searchHospitalsAsync();
        hospitalAdapter = new HospitalListAdapter(hospitalProvider.getHospitals());
        // Log de controlo para garantir que os hospitais estejam a ser corretamente lidos do provider
        Log.d("HOSPITALS_LIST", "Hospitals imported from the provider: " + (hospitalProvider.getHospitals() != null ?
                hospitalProvider.getHospitals().size() : 0));

        hospitalProvider.addObserver(hospitalAdapter);
        hospitalRecyclerView.setAdapter(hospitalAdapter);
        return view;
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            hospitalProvider.setUserLocation(location);
            hospitalProvider.calculateDistance();
            Log.d("LOCATION_LISTENER", "Lat: " + location.getLatitude() + ", Lon: " + location.getLongitude());
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
    };
}
