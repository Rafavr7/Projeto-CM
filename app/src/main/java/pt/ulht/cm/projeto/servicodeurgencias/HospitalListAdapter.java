package pt.ulht.cm.projeto.servicodeurgencias;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import pt.ulht.cm.projeto.servicodeurgencias.model.IHospitalProvider;
import pt.ulht.cm.projeto.servicodeurgencias.model.Hospital;

public class HospitalListAdapter extends RecyclerView.Adapter<HospitalListAdapter.ViewHolder> implements IHospitalProvider.HospitalProviderObserver {

    private List<Hospital> hospitalDataSet;
    private List<Hospital> filteredHospitalList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View hospitalView;
        final TextView textViewHospitalName, textViewHospitalAddress, textViewHospitalDistance;

        ViewHolder(View view) {
            super(view);
            hospitalView = view;
            textViewHospitalName = (TextView) view.findViewById(R.id.hospital_list_item_name);
            textViewHospitalAddress = (TextView) view.findViewById(R.id.hospital_list_item_address);
            textViewHospitalDistance = (TextView) view.findViewById(R.id.hospital_list_item_distance);
        }
    }

    // Create new views. Invoked by the Layout Manager
    @Override
    public HospitalListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hospital_list_item, parent,false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view. Invoked by the Layout Manager
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get element from Dataset at this position
        // Replace the contents of the view with that element
        Hospital temp = hospitalDataSet.get(position);

        holder.textViewHospitalName.setText(temp.getName());
        holder.textViewHospitalAddress.setText(temp.getAddress());
        holder.textViewHospitalDistance.setText(temp.getDistanceTextView());

        holder.hospitalView.setOnClickListener(new HospitalListFragment.RecyclerViewItemClickListener( temp.getId()) );
    }

    // Return the size of the Dataset. Invoked by the Layout Manager
    @Override
    public int getItemCount() {
        if(hospitalDataSet == null) {
            return 0;
        }

        return hospitalDataSet.size();
    }

    public HospitalListAdapter(List<Hospital> myDataSet) {
        hospitalDataSet = myDataSet;
    }

    // Enable the update and notification of the Dataset
    public void updateData(List<Hospital> newDataSet) {
        hospitalDataSet = newDataSet;
        notifyDataSetChanged();
    }

}
