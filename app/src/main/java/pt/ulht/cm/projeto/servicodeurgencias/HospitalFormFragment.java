package pt.ulht.cm.projeto.servicodeurgencias;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.RealmList;
import pt.ulht.cm.projeto.servicodeurgencias.model.Hospital;
import pt.ulht.cm.projeto.servicodeurgencias.services.TemposHospitalProvider;

public class HospitalFormFragment extends Fragment {
    private Button formBtn;
    private RadioGroup radioGroup;
    private RadioButton selectedButton;
    private Spinner spinner;
    private TemposHospitalProvider hospitalProvider;
    private HospitalListAdapter hospitalListAdapter;
    private List<Hospital> hospitals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_hospital_form_fragment, container, false);
        final RecyclerView hospitalRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_hospital_form_list);
        RecyclerView.LayoutManager hospitalLayoutManager = new LinearLayoutManager(this.getActivity());
        hospitalRecyclerView.setLayoutManager(hospitalLayoutManager);

        hospitalProvider = TemposHospitalProvider.getInstance();
        // hospitalListAdapter = new HospitalListAdapter(hospitalProvider.getHospitals());
        hospitals = TemposHospitalProvider.getInstance().getHospitals();

        formBtn = (Button) view.findViewById(R.id.tab_hospital_form_btn);
        spinner = (Spinner) view.findViewById(R.id.spinner_urgencia);
        radioGroup = view.findViewById(R.id.radio_group);

        formBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String textSpinner = spinner.getSelectedItem().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                selectedButton = view.findViewById(selectedId);
                String radioText = (String) selectedButton.getText();

                 // hospitals.filter
            }
        });

        return view;
    }

    // TODO Fazer função de pesquisa a partir dos parametros do spinner e dos radios
}
