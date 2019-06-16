package pt.ulht.cm.projeto.servicodeurgencias;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pt.ulht.cm.projeto.servicodeurgencias.services.TemposHospitalProvider;

public class HospitalListFragment extends Fragment {
    private TemposHospitalProvider hospitalProvider;
    private HospitalListAdapter hospitalAdapter;

    public HospitalListFragment() {
        // To leave empty
    }

    public static class RecyclerViewItemClickListener implements View.OnClickListener {
        private int itemID;

        public RecyclerViewItemClickListener(int itemID) {
            this.itemID = itemID;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), HospitalDetailActivity.class);
            intent.putExtra(HospitalDetailActivity.HOSPITAL_ID, String.valueOf(itemID));
            view.getContext().startActivity(intent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_hospital_list_fragment, container, false);

        RecyclerView hospitalRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_hospital_list);

        RecyclerView.LayoutManager hospitalLayoutManager = new LinearLayoutManager(getActivity());
        hospitalRecyclerView.setLayoutManager(hospitalLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(hospitalRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        hospitalRecyclerView.addItemDecoration(itemDecoration);

        // CHECK FOR USER LOCATION PERMISSION AND STUFF
        // ...

        hospitalProvider = TemposHospitalProvider.getInstance();
        hospitalProvider.searchHospitalsAsync();
        hospitalAdapter = new HospitalListAdapter(hospitalProvider.getHospitals());
        // Log de controlo para garantir que os hospitais estejam a ser corretamente lidos do provider
        Log.d("HOSPITALS_LIST", "Hospitals imported from the provider: " + (hospitalProvider.getHospitals() != null ?
                hospitalProvider.getHospitals().size() : 0));

        hospitalProvider.addObserver(hospitalAdapter);
        hospitalRecyclerView.setAdapter(hospitalAdapter);
        return view;
    }

    // A PARTIR DAQUI FAZER O LOCATION LISTENER
}
