package pt.ulht.cm.projeto.servicodeurgencias.services;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import pt.ulht.cm.projeto.servicodeurgencias.model.VisitLog;

public class VisitLogRealmProvider {
    private static VisitLogRealmProvider INSTANCE;

    private VisitLogRealmProvider() {
        // EMPTY
    }

    private static final String REALM_ASSET_FILEPATH = "visitlog_import.realm";
    private static final String REALM_FILENAME = "visitlig_data_v01.realm";
    private static final boolean SEARCH_IN_MEMORY = false;
    private Realm realm;

    ArrayList<VisitLog> visitLogData = new ArrayList<>();


    public static VisitLogRealmProvider getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new VisitLogRealmProvider();
            INSTANCE.initializeData(context);
        }

        return INSTANCE;
    }

    private void initializeData(Context context) {
        Realm.init(context);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(REALM_FILENAME)
                .assetFile(REALM_ASSET_FILEPATH)
                .schemaVersion(0)
                .build();

        realm = Realm.getInstance(config);
        Log.d("REALM_INITIALIZE_DATA", "Realm file path: " + realm.getPath());

        RealmResults<VisitLog> visitLogRealmResults = realm.where(VisitLog.class).findAll();
        Log.d("RETRIEVE_REALM_DATA", "Read " + visitLogRealmResults.size() + " visitLog " +
                "objects from Realm file");

        visitLogData = new ArrayList<VisitLog>( visitLogRealmResults );
    }

    public void beginTransaction() {
        realm.beginTransaction();
    }

    public void commitTransaction() {
        realm.commitTransaction();
    }

    public void addVisit(VisitLog newVisitLog) {
        Log.d("REALM_ADD_VISIT", "Transaction Init for adding visitLog for hospital: " +
                newVisitLog.getHospitalName());

        realm.beginTransaction();
        realm.copyToRealm(newVisitLog);
        realm.commitTransaction();

        Log.d("REALM_ADD_VISIT", "Commited new VisitLog");

        visitLogData.add(newVisitLog);
    }

    public ArrayList<VisitLog> getVisits() {
        if(SEARCH_IN_MEMORY) {
            sortVisits();
            return visitLogData;
        }
        else {
            RealmResults<VisitLog> visitLogRealmResults = realm.where(VisitLog.class).findAll();
            visitLogData = new ArrayList<VisitLog>(visitLogRealmResults);
            sortVisits();
            return visitLogData;
        }
    }

    private void sortVisits() {
        Collections.sort(visitLogData, new Comparator<VisitLog>() {
            @Override
            public int compare(VisitLog visit1, VisitLog visit2) {
                long time1 = visit1.getEntryTime().getTime();
                long time2 = visit2.getEntryTime().getTime();

                if(time1 < time2) {
                    return 1;
                }
                else if(time1 > time2) {
                    return -1;
                }

                return 0;
            }
        });
    }

}
