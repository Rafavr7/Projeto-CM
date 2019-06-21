package pt.ulht.cm.projeto.servicodeurgencias;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pt.ulht.cm.projeto.servicodeurgencias.model.Hospital;
import pt.ulht.cm.projeto.servicodeurgencias.model.IHospitalProvider;
import pt.ulht.cm.projeto.servicodeurgencias.model.VisitLog;
import pt.ulht.cm.projeto.servicodeurgencias.model.VisitLogController;
import pt.ulht.cm.projeto.servicodeurgencias.model.WaitingTime.WaitingTime;
import pt.ulht.cm.projeto.servicodeurgencias.services.TemposHospitalProvider;
import pt.ulht.cm.projeto.servicodeurgencias.services.VisitLogRealmProvider;

public class HospitalDetailActivity extends AppCompatActivity implements IHospitalProvider.HospitalProviderObserver {

    public static String HOSPITAL_ID = "pt.ulht.cm.projeto.servicodeurgencias.HOSPITAL_ID_EXTRA";

    private Hospital hospital;
    private List<WaitingTime> hospitalWaitingTimes;
    private TextView hospitalName, hospitalAddress, hospitalPhone, hospitalMail, hospitalWebSite,
                     hospitalDistance, hospitalWaitingTimeGeneral, hospitalWaitingTimePeds,
                     hospitalWaitingTimeObstetrics;

    private TemposHospitalProvider hospitalProvider;
    private VisitLogRealmProvider visitLogRealmProvider;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        Log.d("CREATE_DETAIL", this.getLocalClassName() + " - onCreate");
        setContentView(R.layout.activity_hospital_detail);

        hospitalName = (TextView) this.findViewById(R.id.textView_hospital_detail_name);
        hospitalAddress = (TextView) this.findViewById(R.id.textView_hospital_detail_address);
        hospitalPhone = (TextView) this.findViewById(R.id.textView_hospital_detail_phone);
        hospitalMail= (TextView) this.findViewById(R.id.textView_hospital_detail_email);
        hospitalWebSite = (TextView) this.findViewById(R.id.textView_hospital_detail_webSite);
        hospitalDistance = (TextView) this.findViewById(R.id.textView_hospital_detail_distance);

        hospitalWaitingTimeGeneral = (TextView) this.findViewById(R.id.textViewHospitalDetailQueueGeralTime);
        hospitalWaitingTimePeds = (TextView) this.findViewById(R.id.textViewHospitalDetailQueuePediatriaTime);
        hospitalWaitingTimeObstetrics = (TextView) this.findViewById(R.id.textViewHospitalDetailQueueObstetriaTime);


        final String hospitalID = this.getIntent().getStringExtra(HospitalDetailActivity.HOSPITAL_ID);
        Log.d("CREATE_DETAIL", "Detail with id passed: " + hospitalID);
        if(hospitalID != null) {
            hospitalProvider = TemposHospitalProvider.getInstance();
            visitLogRealmProvider = VisitLogRealmProvider.getInstance(this);
            // Find hospital
            hospital = hospitalProvider.getHospital(Integer.parseInt(hospitalID));

            // Populate UI with found hospital data
            hospitalName.setText(this.hospital.getName());
            hospitalAddress.setText(this.hospital.getAddress());
            hospitalPhone.setText(this.hospital.getPhone());
            hospitalMail.setText(this.hospital.getEmail());
            hospitalWebSite.setText(this.hospital.getWebSite());
            hospitalDistance.setText(this.hospital.getDistanceTextView());

            if(hospital.isSharingStandbyTimes()) {
                hospitalProvider.getHospitalWaitingTimesAsync(hospital.getId());
                hospitalWaitingTimes = hospital.getWaitingTimes();
                Log.d("GET_WAITING_TIME", "Waiting Time: " + hospitalWaitingTimes);
                hospitalProvider.addObserver(this);
            }
            else {
                Toast.makeText(this, "Falha ao obter tempos de espera...", Toast.LENGTH_LONG).show();
            }

            fillWaitingTimes();
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void fillWaitingTimes() {
        hospitalWaitingTimeGeneral.setText(this.hospital.getUrgencyWaitingTimeTextView("Geral"));
        hospitalWaitingTimePeds.setText(this.hospital.getUrgencyWaitingTimeTextView("Pediatrica"));
        hospitalWaitingTimeObstetrics.setText(this.hospital.getUrgencyWaitingTimeTextView("Obstetrica"));
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private String HOSPITAL_NAME = "HOSPITAL_NAME";
    private String HOSPITAL_ADDRESS = "HOSPITAL_ADDRESS";
    private String HOSPITAL_PHONE = "HOSPITAL_PHONE";
    private String HOSPITAL_MAIL = "HOSPITAL_MAIL";
    private String HOSPITAL_WEBSITE = "HOSPITAL_WEBSITE";
    private String HOSPITAL_DISTANCE = "HOSPITAL_DISTANCE";

    private String HOSPITAL_TIME_GENERAL = "HOSPITAL_TIME_GENERAL";
    private String HOSPITAL_TIME_PEDS = "HOSPITAL_TIME_PEDS";
    private String HOSPITAL_TIME_OBSTETRICS = "HOSPITAL_TIME_OBSTETRICS";

    @Override
    public void onRestoreInstanceState(Bundle savedInstance) {
        super.onRestoreInstanceState(savedInstance);

        savedInstance.getString(HOSPITAL_NAME);
        savedInstance.getString(HOSPITAL_ADDRESS);
        savedInstance.getString(HOSPITAL_PHONE);
        savedInstance.getString(HOSPITAL_MAIL);
        savedInstance.getString(HOSPITAL_WEBSITE);
        savedInstance.getString(HOSPITAL_DISTANCE);

        savedInstance.getString(HOSPITAL_TIME_GENERAL);
        savedInstance.getString(HOSPITAL_TIME_PEDS);
        savedInstance.getString(HOSPITAL_TIME_OBSTETRICS);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(hospitalName.toString(), HOSPITAL_NAME);
        outState.putString(hospitalAddress.toString(), HOSPITAL_ADDRESS);
        outState.putString(hospitalPhone.toString(), HOSPITAL_PHONE);
        outState.putString(hospitalMail.toString(), HOSPITAL_MAIL);
        outState.putString(hospitalWebSite.toString(), HOSPITAL_WEBSITE);
        outState.putString(hospitalDistance.toString(), HOSPITAL_DISTANCE);

        outState.putString(hospitalWaitingTimeGeneral.toString(), HOSPITAL_TIME_GENERAL);
        outState.putString(hospitalWaitingTimePeds.toString(), HOSPITAL_TIME_PEDS);
        outState.putString(hospitalWaitingTimeObstetrics.toString(), HOSPITAL_TIME_OBSTETRICS);

        super.onSaveInstanceState(outState);
    }

    public void openWebSiteExternalPage(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(hospital.getWebSite()));
        startActivity(intent);
    }

    public void openEmailExternalPage(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("mailto:" + hospital.getEmail()));
        startActivity(intent);
    }

    public void openPhoneExternalPage(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + hospital.getPhone()));
        startActivity(intent);
    }

    public void openAddressMapExternalPage(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://maps.google.com/maps?saddr=" + hospital.getAddress()));
        startActivity(intent);
    }

    @Override
    public void updateData(List<Hospital> hospitals) {
        Log.d("FUNCTION_CALL", "HospitalDetailActivity.upadetData was called");
        for(Hospital h : hospitals) {
            if(h.getId() == hospital.getId()) {
                hospitalWaitingTimes = h.getWaitingTimes();
            }
        }

        fillWaitingTimes();
    }

    public void doCheckIn(View view) {
        VisitLogController.getInstance().enterHospital(hospital.getListAdapterName());
        Toast.makeText(this, "Bem vindo(a)!", Toast.LENGTH_LONG).show();
    }

    public void doGoing(View view) {
        Toast.makeText(this, "A caminho do hospital!", Toast.LENGTH_SHORT).show();
    }

    public void doCheckOut(View view) {
        VisitLog visit = VisitLogController.getInstance().exitHospital(hospital.getListAdapterName());

        if(visit == null) {
            Toast.makeText(this, "Não pode fazer checkout de hospital sem ter feito" +
                    " check-in no mesmo", Toast.LENGTH_LONG).show();
        }
        else {
            visitLogRealmProvider.addVisit(visit);
            Toast.makeText(this, "Até a próxima!", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }
}
