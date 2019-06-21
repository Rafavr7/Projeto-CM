package pt.ulht.cm.projeto.servicodeurgencias.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class VisitLog extends RealmObject {
    private static final int MILISECONDS_TO_SECONDS = 1000;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/HH/yyyy");
    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    @PrimaryKey @Required private String uuid;
    @Required private String hospitalName;
    @Required private Date entryTime;
    @Required private Date exitTime;
    @Required private Date elapsedTime;

    public VisitLog() {
        // EMPTY
    }

    public VisitLog(String uuid, String hospitalName, Date entryTime, Date exitTime, Date elapsedTime) {
        this.uuid = uuid;
        this.hospitalName = hospitalName;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.elapsedTime = elapsedTime;
    }

    public VisitLog(String hospitalName, Date entryTime, Date exitTime) {
        this.hospitalName = hospitalName;
        this.entryTime = entryTime;
        this.exitTime = exitTime;

        elapsedTime = new Date(exitTime.getTime() - entryTime.getTime());
        uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public String getElapsedTimeTextView() {
        double seconds = elapsedTime.getTime() / MILISECONDS_TO_SECONDS;
        int minutes = (int) seconds / 60;
        int hours = (int) seconds / 3600;


        if(hours > 0) {
            int remainingMinutes = minutes - (hours * 60);
            String textView = hours + "h " + remainingMinutes + " min";
            return textView;
        }

        String textView = minutes + " min";
        return textView;
    }

    public String getEntryTimeTextView() {
        String time = TIME_FORMAT.format(entryTime);
        return time;
    }

    public String getExitTimeTextView() {
        String time = TIME_FORMAT.format(exitTime);
        return time;
    }

    public String getVisitDateTextView() {
        String date = DATE_FORMAT.format(entryTime);
        return date;
    }
}
