package pt.ulht.cm.projeto.servicodeurgencias.model;

import java.util.Date;

import pt.ulht.cm.projeto.servicodeurgencias.services.VisitLogRealmProvider;

public class VisitLogController {
    // Singleton Pattern
    private static VisitLogController INSTANCE;

    private String hospitalName;
    private Date entryTime;
    private Date exitTime;

    private VisitLogController() {
        hospitalName = "";
    }

    public static VisitLogController getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new VisitLogController();
        }

        return INSTANCE;
    }

    public void enterHospital(String hospitalName) {
        entryTime = new Date();
        this.hospitalName = hospitalName;
    }

    public VisitLog exitHospital(String hospitalName) {
        if(this.hospitalName.equals(hospitalName)) {
            exitTime = new Date();
            VisitLog visitLog = new VisitLog(hospitalName, entryTime, exitTime);

            resetData();
            return visitLog;
        }

        return null;
    }

    private void resetData() {
        hospitalName = "";
        entryTime = null;
        exitTime = null;
    }
}
