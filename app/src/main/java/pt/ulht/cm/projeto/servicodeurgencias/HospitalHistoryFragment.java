package pt.ulht.cm.projeto.servicodeurgencias;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import pt.ulht.cm.projeto.servicodeurgencias.services.VisitLogRealmProvider;

public class HospitalHistoryFragment extends Fragment {
    private VisitLogRealmProvider visitProvider;

    public HospitalHistoryFragment() {
        // To leave empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.tab_hospital_history_fragment, container, false);

        RecyclerView visitsRecyclerView = (RecyclerView) view.findViewById();
    }
}
