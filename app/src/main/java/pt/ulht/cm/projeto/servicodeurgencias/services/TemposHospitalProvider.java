package pt.ulht.cm.projeto.servicodeurgencias.services;

import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pt.ulht.cm.projeto.servicodeurgencias.model.Hospital;
import pt.ulht.cm.projeto.servicodeurgencias.model.HospitalProviderAbstract;
import pt.ulht.cm.projeto.servicodeurgencias.model.WaitingTime.WaitingTime;
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
    }
            INSTANCE.init();

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

    public void getHospitalWaitingTimesAsync(final int hospitalID) {
        String id = String.valueOf(hospitalID);
        Log.d("GET_WAITING_TIMES", "Find times for ID: " + id);
        Call<HospitalWaitingTimesResponse> call = temposAPIService.getWaitingTimes(id);

        call.enqueue(new Callback<HospitalWaitingTimesResponse>() {
            @Override
            public void onResponse(Call<HospitalWaitingTimesResponse> call, Response<HospitalWaitingTimesResponse> response) {
                List<WaitingTime> waitingTimes = response.body().getWaitingTimes();
                Log.d("RETROFIT,search", "Number of times received: " + (waitingTimes != null ? waitingTimes.size() : 0));
                getHospital(hospitalID).setWaitingTimes(waitingTimes);

                notifyObserverDataChanged();
            }

            @Override
            public void onFailure(Call<HospitalWaitingTimesResponse> call, Throwable t) {
                Log.d("WAITING_TIMES_FAILURE", "Failed to get Waiting Times from the API " +
                        "for the hospital with id " + hospitalID);

                notifyObserverDataChanged();
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
                if(h1.getDistance() < h2.getDistance()) {
                    return -1;
                }
                else if(h1.getDistance() > h2.getDistance()) {
                    return 1;
                }

                return 0;
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
