package pt.ulht.cm.projeto.servicodeurgencias.services;

import pt.ulht.cm.projeto.servicodeurgencias.model.Hospital;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Tempos API endpoints:
 * - http://tempos.min-saude.pt/api.php/institution
 * - http://tempos.min-saude.pt/api.php/standbyTime/HOSPITAL_ID
 */
public interface TemposAPIService {

    @GET("institution")
    Call<HospitalSearchResponse> searchHospitals();

    @GET("standbyTime/{ID}")
    Call<HospitalWaitingTimesResponse> getWaitingTimes(@Path("ID") String hospitalID);

}
