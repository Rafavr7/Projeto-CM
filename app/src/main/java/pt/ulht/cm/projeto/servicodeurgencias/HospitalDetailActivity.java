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

import java.util.List;

import pt.ulht.cm.projeto.servicodeurgencias.model.Hospital;
import pt.ulht.cm.projeto.servicodeurgencias.model.IHospitalProvider;
import pt.ulht.cm.projeto.servicodeurgencias.services.TemposHospitalProvider;

public class HospitalDetailActivity extends AppCompatActivity implements IHospitalProvider.HospitalProviderObserver {

    public static String HOSPITAL_ID = "pt.ulht.cm.projeto.servicodeurgencias.HOSPITAL_ID_EXTRA";

    private Hospital hospital;
    private TextView hospitalName, hospitalAddress, hospitalPhone, hospitalMail, hospitalWebSite,
                     hospitalDistance;

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

        final String hospitalID = this.getIntent().getStringExtra(HospitalDetailActivity.HOSPITAL_ID);
        Log.d("CREATE_DETAIL", "Detail with id passed: " + hospitalID);
        if(hospitalID != null) {
            // Find hospital
            hospital = TemposHospitalProvider.getInstance().getHospital(Integer.parseInt(hospitalID));

            // Populate UI with found hospital data
            hospitalName.setText(hospital.getName());
            hospitalAddress.setText(hospital.getAddress());
            hospitalPhone.setText(hospital.getPhone());
            hospitalMail.setText(hospital.getEmail());
            hospitalWebSite.setText(hospital.getWebSite());
            hospitalDistance.setText(hospital.getDistanceTextView());
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

    @Override
    public void onRestoreInstanceState(Bundle savedInstance) {
        super.onRestoreInstanceState(savedInstance);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
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
        // TODO
    }

    public void doCheckIn(View view) {
    }

    public void doGoing(View view) {
    }

    public void doCheckOut(View view) {
    }
}
