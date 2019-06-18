package pt.ulht.cm.projeto.servicodeurgencias.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class VisitLog extends RealmObject {
    private static final double MILISECINDS_TO_HOURS = 3600000.0;

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
        double time = elapsedTime.getTime() / MILISECINDS_TO_HOURS;
        String textView = "";

        if(time < 1) {
            int minutes = (int) (time * 60);
            textView += minutes + " min";
            return textView;
        }

        String horas = String.format("%.0f", time);
        textView += horas + " horas";
        return textView;
    }

    public String getEntryTimeTextView() {
        String date = DATE_FORMAT.format(entryTime);
        String time = TIME_FORMAT.format(entryTime);
        String textView = date + " - " + time;
        return textView;
    }

    public String getExitTimeTextView() {
        String date = DATE_FORMAT.format(exitTime);
        String time = TIME_FORMAT.format(exitTime);
        String textView = date + " - " + time;
        return textView;
    }
}
