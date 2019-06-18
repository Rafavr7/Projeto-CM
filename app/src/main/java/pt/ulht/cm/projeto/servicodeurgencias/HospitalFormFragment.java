package pt.ulht.cm.projeto.servicodeurgencias;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import pt.ulht.cm.projeto.servicodeurgencias.services.TemposHospitalProvider;

public class HospitalFormFragment extends Fragment {
    private Button formBtn;
    private RadioGroup radioGroup;
    private RadioButton selectedButton;
    private Spinner spinner;
    private TemposHospitalProvider hospitalProvider;
    private HospitalListAdapter hospitalListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_hospital_form_fragment, container, false);
        formBtn = (Button) view.findViewById(R.id.tab_hospital_form_btn);

        spinner = (Spinner) view.findViewById(R.id.spinner_urgencia);

        radioGroup = view.findViewById(R.id.radio_group);

        formBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "PESQUISA DE HOSPITAIS", Toast.LENGTH_SHORT).show();

                String textSpinner = spinner.getSelectedItem().toString();

                int selectedId = radioGroup.getCheckedRadioButtonId();

                selectedButton = view.findViewById(selectedId);
                String radioText = (String) selectedButton.getText();

                Toast.makeText(getActivity(), "Seleccionado: " + textSpinner + "/" + radioText, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    // TODO Fazer função de pesquisa a partir dos parametros do spinner e dos radios
}
