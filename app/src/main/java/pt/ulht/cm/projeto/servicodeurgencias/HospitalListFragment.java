package pt.ulht.cm.projeto.servicodeurgencias;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class HospitalListFragment extends Fragment {
    private Button listBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_hospital_list_fragment, container, false);
        listBtn = (Button) view.findViewById(R.id.tab_hospital_list_btn);

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "LISTA DE HOSPITAIS", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
