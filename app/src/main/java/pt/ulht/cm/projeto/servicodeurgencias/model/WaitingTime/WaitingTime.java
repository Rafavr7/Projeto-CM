package pt.ulht.cm.projeto.servicodeurgencias.model.WaitingTime;

import com.google.gson.annotations.SerializedName;

public class WaitingTime {
    @SerializedName("Blue") private EmergencyQueue blue;
    @SerializedName("Green") private EmergencyQueue green;
    @SerializedName("Yellow") private EmergencyQueue yellow;
    @SerializedName("Orange") private EmergencyQueue orange;
    @SerializedName("Red") private EmergencyQueue red;

    @SerializedName("Emergency") private Emergency emergency;


    public int getAverageTime() {
        int totalTime = blue.getWaitingTimeInSeconds() +
                        green.getWaitingTimeInSeconds() +
                        yellow.getWaitingTimeInSeconds() +
                        orange.getWaitingTimeInSeconds() +
                        red.getWaitingTimeInSeconds();

        return totalTime / 5;
    }

    public EmergencyQueue getBlue() {
        return blue;
    }

    public void setBlue(EmergencyQueue blue) {
        this.blue = blue;
    }

    public EmergencyQueue getGreen() {
        return green;
    }

    public void setGreen(EmergencyQueue green) {
        this.green = green;
    }

    public EmergencyQueue getYellow() {
        return yellow;
    }

    public void setYellow(EmergencyQueue yellow) {
        this.yellow = yellow;
    }

    public EmergencyQueue getOrange() {
        return orange;
    }

    public void setOrange(EmergencyQueue orange) {
        this.orange = orange;
    }

    public EmergencyQueue getRed() {
        return red;
    }

    public void setRed(EmergencyQueue red) {
        this.red = red;
    }

    public Emergency getEmergency() {
        return emergency;
    }

    public void setEmergency(Emergency emergency) {
        this.emergency = emergency;
    }
}
