package com.example.nonfatalfirearminjuries2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ViewHolder> {
    private List<com.example.nonfatalfirearminjuries2.ReportModel> reportsList;

    public ReportsAdapter(List<com.example.nonfatalfirearminjuries2.ReportModel> reportsList) {
        this.reportsList = reportsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        com.example.nonfatalfirearminjuries2.ReportModel report = reportsList.get(position);
        holder.dateText.setText(report.getDate());
        holder.detailsText.setText(report.getLocation() + " - " + report.getSeverity());
    }

    @Override
    public int getItemCount() {
        return reportsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, detailsText;

        ViewHolder(View itemView) {
            super(itemView);
            dateText = itemView.findViewById(android.R.id.text1);  // First TextView in simple_list_item_2
            detailsText = itemView.findViewById(android.R.id.text2);  // Second TextView in simple_list_item_2
        }
    }
}
