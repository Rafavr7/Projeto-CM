package pt.ulht.cm.projeto.servicodeurgencias;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import pt.ulht.cm.projeto.servicodeurgencias.model.Hospital;
import pt.ulht.cm.projeto.servicodeurgencias.services.TemposHospitalProvider;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private List<Hospital> hospitals;
    private GoogleMap map;
    private Location userLocation;
    private LocationRequest locationRequest;

    public MapsFragment(){
        // Empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_maps_fragment, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        hospitals = TemposHospitalProvider.getInstance().getHospitals();

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ) {
            map.setMyLocationEnabled(true);
        }
        map.setOnMarkerClickListener(this);

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // TODO addMarker not working dunno
        if (hospitals != null){
            for (Hospital hospital : hospitals){
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(hospital.getLatitude(), hospital.getLongitude()))
                        .title(hospital.getName()));
            }
        }else{
            Log.d("MapsFragment", "Hospitals return null");
        }
        // TODO Move camera to actual position
        // LatLng newLatLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());

        // map.moveCamera(CameraUpdateFactory.newLatLngZoom());
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (Hospital hospital : hospitals){
            LatLng latLng = new LatLng(hospital.getLatitude(), hospital.getLongitude());

            if (marker.getPosition().equals(latLng)) {
                Toast.makeText(getContext(), "Clicou em " + hospital.getName(), Toast.LENGTH_SHORT).show();

                // Open Hospital Detail
                Intent intent = new Intent(getContext(), HospitalDetailActivity.class);
                intent.putExtra(HospitalDetailActivity.HOSPITAL_ID, hospital.getId());
                startActivity(intent);
            }
        }
        return false;
    }
}
