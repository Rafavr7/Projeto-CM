package pt.ulht.cm.projeto.servicodeurgencias.services;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import pt.ulht.cm.projeto.servicodeurgencias.model.WaitingTime.WaitingTime;

public class HospitalWaitingTimesResponse {
    @SerializedName("Result")
    private List<WaitingTime> waitingTimes;

    public List<WaitingTime> getWaitingTimes() {
        return waitingTimes;
    }
}
