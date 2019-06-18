package pt.ulht.cm.projeto.servicodeurgencias.model;

import android.location.Location;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class Hospital {
    private static final int EARTH_RADIUS = 6371;
    private static final int HEIGHT = 0;

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

    public double calculateDistance(double userLatitude, double userLongitude) {
        /*double c1Squared = Math.pow(getLatitude() - userLocation.getLatitude(), 2);
        double c2Squared = Math.pow(getLongitude() - userLocation.getLongitude(), 2);
        double h = Math.sqrt(c1Squared + c2Squared);*/
        double distLat = Math.toRadians(userLatitude - latitude);
        double distLon = Math.toRadians(userLongitude - longitude);

        double a = Math.sin(distLat / 2) * Math.sin(distLat * 2) +
                Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(userLatitude)) *
                Math.sin(distLon / 2) * Math.sin(distLon / 2);

        double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        // Convert to human units
        double c = EARTH_RADIUS * b;

        double returnDist = Math.pow(c, 2) + Math.pow(HEIGHT, 2);
        return Math.sqrt(returnDist);
    }

    public String getDistanceTextView() {
        if(distance < 1) {
            int meters = (int) (10 * distance);
            String distanceView = meters + "0 metros";
            return distanceView;
        }
        else if(distance >= 100) {
            String km = String.format("%.0f", distance);
            km += " Km";
            Log.d("DISTANCE_TEXT_VIEW", "Hospital ID: " + id + ", Distance: " + km + ", From: " + distance);
            return km;
        }

        String km = String.format("%.1f", distance);
        km += " Km";
        Log.d("DISTANCE_TEXT_VIEW", "Hospital ID: " + id + ", Distance: " + km + ", From: " + distance);
        return km;
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
