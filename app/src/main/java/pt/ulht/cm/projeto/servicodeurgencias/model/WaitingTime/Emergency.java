package pt.ulht.cm.projeto.servicodeurgencias.model.WaitingTime;

import com.google.gson.annotations.SerializedName;

public class Emergency {
    @SerializedName("Code") private String emergencyCode;
    @SerializedName("Description") private String description;

    public String getEmergencyCode() {
        return emergencyCode;
    }

    public void setEmergencyCode(String emergencyCode) {
        this.emergencyCode = emergencyCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
