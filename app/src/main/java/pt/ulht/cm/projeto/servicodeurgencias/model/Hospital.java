package pt.ulht.cm.projeto.servicodeurgencias.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;

import pt.ulht.cm.projeto.servicodeurgencias.model.WaitingTime.WaitingTime;

public class Hospital {
    private static final int EARTH_RADIUS = 6371;
    private static final int HEIGHT = 0;
    private static final String SPLIT_REGEX = "[,(]";

    private static final String[] URGENCY_TYPES = {"Geral", "Obstetrica", "Pediatrica"};

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
    @SerializedName("ShareStandbyTimes") private boolean shareStandbyTimes;
    /************************************/

    private double distance;
    private List<WaitingTime> waitingTimes;
    private HashMap<String, Integer> timesByUrgency;

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

    public boolean hasEmergency() {
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

    public List<WaitingTime> getWaitingTimes() {
        return waitingTimes;
    }

    public void setWaitingTimes(List<WaitingTime> waitingTimes) {
        this.waitingTimes = waitingTimes;
        if(waitingTimes != null) {
            for(String type : URGENCY_TYPES) {
                calculateWaitingTime(type);
            }
        }
    }

    public boolean isSharingStandbyTimes() {
        return shareStandbyTimes;
    }

    public void setShareStandbyTimes(boolean shareStandbyTimes) {
        this.shareStandbyTimes = shareStandbyTimes;
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
            int meters = (int) (100 * distance);
            String distanceView = meters + "0 metros";
            return distanceView;
        }
        else if(distance >= 100) {
            String km = String.format("%.0f", distance);
            km += " Km";
            //Log.d("DISTANCE_TEXT_VIEW", "Hospital ID: " + id + ", Distance: " + km + ", From: " + distance);
            return km;
        }

        String km = String.format("%.1f", distance);
        km += " Km";
        //Log.d("DISTANCE_TEXT_VIEW", "Hospital ID: " + id + ", Distance: " + km + ", From: " + distance);
        return km;
    }

    public String getListAdapterName() {
        String[] smallName = name.split(SPLIT_REGEX);
        return smallName[0] + "...";
    }

    public String getListAdapterAddress() {
        String[] smallAddress = address.split(SPLIT_REGEX);
        return smallAddress[0];
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Hospital) {
            return this.getId() == ((Hospital) obj).getId();
        }

        return false;
    }

    private void calculateWaitingTime(String urgencyType) {
        if(timesByUrgency == null) {
            timesByUrgency = new HashMap<>();
        }

        int resultCount = 0;
        int sum = 0;
        Log.d("CALCULATE_TIMES", "");
        // We want to normalize the data so that we don't have to worry about eventual accents
        // from the JSON response
        for(WaitingTime time : waitingTimes) {
            if(Normalizer.normalize(time.getEmergency().getDescription(), Normalizer.Form.NFD)
                    .replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}+", "").contains(urgencyType)) {

                resultCount++;
                sum += time.getAverageTime();
                Log.d("LOOP_ITERATION", "Count " + resultCount + "   Time: " + time.getAverageTime() +
                        "   Sum: " + sum);
            }
        }

        int average = resultCount == 0 ? 0 : sum / resultCount;
        timesByUrgency.put(urgencyType, average);
    }

    public String getUrgencyWaitingTimeTextView(String urgencyType) {
        // REMINDER: Waiting Times are stored in seconds
        if(timesByUrgency == null) {
            return "Indisponível";
        }

        if(timesByUrgency.containsKey(urgencyType)) {
            int seconds = timesByUrgency.get(urgencyType);
            if(seconds == 0) {
                return "Inexistente";
            }

            int minutes = seconds / 60;
            int hours = seconds / 3600;

            if(hours > 0) {
                int remainingMinutes = minutes - (hours * 60);
                String textView = hours + "h " + remainingMinutes + " min";
                return textView;
            }

            String textView = minutes + " min";
            return textView;
        }

        return "Indisponível";
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
