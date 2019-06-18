package pt.ulht.cm.projeto.servicodeurgencias.services;

import android.location.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pt.ulht.cm.projeto.servicodeurgencias.model.Hospital;
import pt.ulht.cm.projeto.servicodeurgencias.model.HospitalProviderAbstract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TemposHospitalProvider extends HospitalProviderAbstract {
    // Class with Singleton Pattern
    private static TemposHospitalProvider INSTANCE;

    private TemposHospitalProvider() {
        // EMPTY
    }

    public static TemposHospitalProvider getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TemposHospitalProvider();
            INSTANCE.init();
        }

        return INSTANCE;
    }

    public static final String TEMPOS_API_BASE_URL = "http://tempos.min-saude.pt/api.php/";
    private TemposAPIService temposAPIService;
    private Location userLocation;

    private void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TEMPOS_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        temposAPIService = retrofit.create(TemposAPIService.class);
    }

    public void searchHospitalsAsync() {
        Call<HospitalSearchResponse> call = temposAPIService.searchHospitals();

        call.enqueue(new Callback<HospitalSearchResponse>() {
            @Override
            public void onResponse(Call<HospitalSearchResponse> call, Response<HospitalSearchResponse> response) {
                List<Hospital> hospitals = response.body().getHospitals();
                hospitalData = hospitals;
                calculateDistance();

                // Sort hospitals by their distance attribute to show the user the closest hospitals first
                sortHospitalsByDIstance();
                notifyObserverDataChanged();
            }

            @Override
            public void onFailure(Call<HospitalSearchResponse> call, Throwable t) {
                // EMPTY
            }
        });
    }

    public void calculateDistance() {
        double lat = userLocation.getLatitude();
        double lon = userLocation.getLongitude();

        if(userLocation != null && hospitalData != null) {
            for(Hospital h : hospitalData) {
                h.setDistance(h.calculateDistance(lat, lon));
            }
        }
    }

    public void sortHospitalsByDIstance() {
        Collections.sort(hospitalData, new Comparator<Hospital>() {
            @Override
            public int compare(Hospital h1, Hospital h2) {
                return (int) (h1.getDistance() - h2.getDistance());
            }
        });
    }

    public List<Hospital> getHospitals() {
        return hospitalData;
    }

    @Override
    public Hospital getHospital(int hospitalID) {
        for(Hospital h : hospitalData) {
            if(h.getId() == hospitalID) {
                return h;
            }
        }

        return null;
    }

    public Location getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(Location location) {
        userLocation = location;
    }
}
