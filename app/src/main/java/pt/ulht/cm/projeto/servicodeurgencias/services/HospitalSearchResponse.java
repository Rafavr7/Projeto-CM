package pt.ulht.cm.projeto.servicodeurgencias.services;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import pt.ulht.cm.projeto.servicodeurgencias.model.Hospital;

public class HospitalSearchResponse {
    @SerializedName("Result")
    private List<Hospital> hospitals;

    public List<Hospital> getHospitals() {
        return hospitals;
    }
}
