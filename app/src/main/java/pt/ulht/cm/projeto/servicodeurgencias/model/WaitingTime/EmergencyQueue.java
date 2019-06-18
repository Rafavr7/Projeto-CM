package pt.ulht.cm.projeto.servicodeurgencias.model.WaitingTime;

import com.google.gson.annotations.SerializedName;

public class EmergencyQueue {
    // Waiting time comes in seconds from the API
    @SerializedName("Time") private int waitingTime;
    @SerializedName("Length") private int queueLength;

    public int getWaitingTimeInSeconds() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getQueueLength() {
        return queueLength;
    }

    public void setQueueLength(int queueLength) {
        this.queueLength = queueLength;
    }
}
