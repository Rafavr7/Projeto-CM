package pt.ulht.cm.projeto.servicodeurgencias.model;

import com.google.gson.annotations.SerializedName;

public class Hospital {
    /** Attributes provided by the API **/
    @SerializedName("Id") private int id;
    @SerializedName("Name") private String name;
    @SerializedName("Longitude") private double longitude;
    @SerializedName("Latitude") private double latitude;
    @SerializedName("Address") private String address;
    @SerializedName("Phone") private String phone;
    @SerializedName("Email") private String email;
    @SerializedName("InstitutionURL") private String webSite;
    @SerializedName("HasEmergency") private boolean hasEmergency;
    /************************************/

    private double distance;

    public Hospital() {
        // EMPTY
    }

    public Hospital(String name, int id, double longitude, double latitude, String address,
                    String phone, String email, String webSite, boolean hasEmergency) {

        this.name = name;
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.webSite = webSite;
        this.hasEmergency = hasEmergency;
        distance = 5.2;
        //calculateDistance()
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public boolean isHasEmergency() {
        return hasEmergency;
    }

    public void setHasEmergency(boolean hasEmergency) {
        this.hasEmergency = hasEmergency;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getDistanceTextView() {
        // TODO
        return "5.2 Km";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Hospital) {
            return this.getId() == ((Hospital) obj).getId();
        }

        return false;
    }

    @Override
    public String toString() {
        return Hospital.class.toString() + " {"
                + "Name: " + name
                + ", Longitude: " + longitude
                + ", Latitude: " + latitude
                + ", Address: " + address
                + ", Phone: " + phone
                + ", Email: " + email
                + ", WebSite: " + webSite
                + ", Has Emergency: " + hasEmergency + "}";
    }

}
