package pt.ulht.cm.projeto.servicodeurgencias;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pt.ulht.cm.projeto.servicodeurgencias.model.VisitLog;

public class VisitListAdapter extends RecyclerView.Adapter<VisitListAdapter.ViewHolder> {
    private List<VisitLog> visitsDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View visitLogView;
        final TextView textViewHospitalName, textViewVisitDate, textViewEntryTime,
                textViewExitTime, textViewElapsedTime;

        ViewHolder(View view) {
            super(view);

            visitLogView = view;
            textViewHospitalName = (TextView) view.findViewById(R.id.visit_list_item_hospital_name);
            textViewVisitDate = (TextView) view.findViewById(R.id.visit_list_item_visit_date);
            textViewEntryTime = (TextView) view.findViewById(R.id.visit_list_item_entry_time);
            textViewExitTime = (TextView) view.findViewById(R.id.visit_list_item_exit_time);
            textViewElapsedTime = (TextView) view.findViewById(R.id.visit_list_item_elapsed_time);
        }
    }

    public VisitListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.visit_list_item, parent, false);

        return new ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        VisitLog temp = visitsDataSet.get(position);

        holder.textViewHospitalName.setText(temp.getHospitalName());
        holder.textViewVisitDate.setText("Data: " + temp.getVisitDateTextView());
        holder.textViewEntryTime.setText("Entrada: " + temp.getEntryTimeTextView());
        holder.textViewExitTime.setText("Saída: " + temp.getExitTimeTextView());
        holder.textViewElapsedTime.setText(temp.getElapsedTimeTextView());
    }

    public int getItemCount() {
        if(visitsDataSet == null) {
            return 0;
        }

        return visitsDataSet.size();
    }

    public VisitListAdapter(List<VisitLog> myDataSet) {
        visitsDataSet = myDataSet;
    }

}
