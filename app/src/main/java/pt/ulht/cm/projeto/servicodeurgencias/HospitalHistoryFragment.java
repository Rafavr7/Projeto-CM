package pt.ulht.cm.projeto.servicodeurgencias;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class HospitalHistoryFragment extends Fragment {
    private Button historyBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_hospital_history_fragment, container, false);
        historyBtn = (Button) view.findViewById(R.id.tab_hospital_history_btn);

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "HISTÓRICO DE HOSPITAIS", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
