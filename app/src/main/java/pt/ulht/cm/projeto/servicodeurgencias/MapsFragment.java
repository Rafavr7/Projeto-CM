package pt.ulht.cm.projeto.servicodeurgencias;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MapsFragment extends Fragment {
    private Button mapsBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_maps_fragment, container, false);
        mapsBtn = (Button) view.findViewById(R.id.tab_maps_btn);

        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "MAPA DE HOSPITAIS", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
