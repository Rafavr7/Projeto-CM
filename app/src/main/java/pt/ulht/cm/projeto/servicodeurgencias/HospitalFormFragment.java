package pt.ulht.cm.projeto.servicodeurgencias;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import io.realm.RealmList;
import pt.ulht.cm.projeto.servicodeurgencias.model.Hospital;
import pt.ulht.cm.projeto.servicodeurgencias.model.WaitingTime.WaitingTime;
import pt.ulht.cm.projeto.servicodeurgencias.services.TemposHospitalProvider;

public class HospitalFormFragment extends Fragment {
    private Button formBtn;
    private RadioGroup radioGroup;
    private RadioButton selectedButton;
    private Spinner spinner;
    private TemposHospitalProvider hospitalProvider;
    private HospitalListAdapter hospitalListAdapter;
    private List<Hospital> hospitals;
    private List<WaitingTime> hospitalWaitingTimes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_hospital_form_fragment, container, false);
        radioGroup = view.findViewById(R.id.radio_group);

        formBtn = (Button) view.findViewById(R.id.tab_hospital_form_btn);
        spinner = (Spinner) view.findViewById(R.id.spinner_urgencia);

        formBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                List<Hospital> list = new ArrayList<>();
                List<Hospital> dados = new ArrayList<>();

                String textSpinner = spinner.getSelectedItem().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                selectedButton = view.findViewById(selectedId);
                String radioText = (String) selectedButton.getText();

                final RecyclerView hospitalRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_hospital_form_list);
                RecyclerView.LayoutManager hospitalManager = new LinearLayoutManager(getContext());
                hospitalRecyclerView.setLayoutManager(hospitalManager);

                DividerItemDecoration itemDecoration = new DividerItemDecoration(hospitalRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
                hospitalRecyclerView.addItemDecoration(itemDecoration);

                hospitalProvider = TemposHospitalProvider.getInstance();
                hospitalListAdapter = new HospitalListAdapter(dados);

                hospitals = hospitalProvider.getHospitals();

                Log.d("SEARCH_HOSPITAL", "Urgência: " + textSpinner + ", Gravidade: " + radioText
                        + ", Radio Option: " + selectedId);

                list.addAll(hospitals);

                for (Hospital hospital : list) {
                    hospitalProvider.getHospitalWaitingTimesAsync(hospital.getId());
                    hospitalWaitingTimes = hospital.getWaitingTimes();

                    if (hospitalWaitingTimes == null) {
                        continue;
                    }

                    Log.d("WAITING TIMES HOSPITAL", "Hospital: " + hospital.getName() + " Waiting Times: " + hospitalWaitingTimes);
                    if (hospital.isSharingStandbyTimes()) {
                        boolean spinner = false;
                        for (int x = 0; x < hospitalWaitingTimes.size(); x++) {
                            if (Normalizer.normalize(hospital.getWaitingTimes().get(x).getEmergency().getDescription(), Normalizer.Form.NFD)
                                    .replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}+", "").contains(textSpinner)) {
                                if (selectedId==2131296401 && hospital.getWaitingTimes().get(x).hasGrave()){
                                    dados.add(hospital);
                                }
                                if (selectedId==2131296403 && hospital.getWaitingTimes().get(x).hasNotGrave()){
                                    dados.add(hospital);
                                }
                                break;
                            }
                        }
                        /*
                        boolean radioGrave = false;
                        for (int x = 0; x < hospitalWaitingTimes.size(); x++) {
                            if (selectedId==2131296401 && hospital.getWaitingTimes().get(x).hasGrave()){
                                radioGrave = true;
                                break;
                            }
                        }
                        boolean radioNaoGrave = false;
                        for (int x = 0; x < hospitalWaitingTimes.size(); x++) {
                            if(selectedId==2131296403 && hospital.getWaitingTimes().get(x).hasNotGrave()){
                                radioNaoGrave = true;
                                break;
                            }
                        }
                        if (spinner == true && radioGrave == true) {
                            dados.add(hospital);
                            Log.d("DADOS GRAVE DEBUG", dados.toString());
                            continue;
                        }
                        if (spinner == true && radioNaoGrave == true){
                            dados.add(hospital);
                            Log.d("DADOS NAO GRAVE DEBUG", dados.toString());
                            continue;
                        }*/
                    } else {
                        Toast.makeText(getContext(), "Hospital não partilha informação de espera", Toast.LENGTH_SHORT).show();
                    }
                }

                if (hospitalRecyclerView == null) {
                    hospitalListAdapter = new HospitalListAdapter(dados);
                    Log.d("NEW ADAPTER", hospitalListAdapter.toString());
                    hospitalListAdapter.updateData(dados);
                    hospitalRecyclerView.setAdapter(hospitalListAdapter);
                } else {
                    hospitalListAdapter.updateData(dados);
                    Log.d("UPDATE ADAPTER", hospitalListAdapter.toString());
                    hospitalRecyclerView.setAdapter(hospitalListAdapter);
                }

            }
        });

        return view;
    }

    // TODO Fazer função de pesquisa a partir dos parametros do spinner e dos radios
}
