package pt.ulht.cm.projeto.servicodeurgencias.model;

import java.util.Date;

public class VisitLogController {
    // Singleton Pattern
    private static VisitLogController INSTANCE;

    private String hospitalName;
    private Date entryTime;
    private Date exitTime;

    private VisitLogController() {
        // EMPTY
    }

    public static VisitLogController getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new VisitLogController();
        }

        return INSTANCE;
    }
}
