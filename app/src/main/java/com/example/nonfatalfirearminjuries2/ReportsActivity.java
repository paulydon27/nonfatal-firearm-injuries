package com.example.nonfatalfirearminjuries2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ReportsActivity extends AppCompatActivity {

    private Spinner spinnerState, spinnerSeverity;
    private EditText etDateRange;
    private RecyclerView recyclerReports;
    private Button btnExportCSV, btnExportPDF, lv_button;
    private ReportsAdapter reportsAdapter;
    private List<ReportModel> reportsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        // Initialize UI Components
        spinnerState = findViewById(R.id.spinner_state);
        etDateRange = findViewById(R.id.et_date_range);
        spinnerSeverity = findViewById(R.id.spinner_severity);
        recyclerReports = findViewById(R.id.recycler_reports);
        btnExportCSV = findViewById(R.id.btn_export_csv);
        btnExportPDF = findViewById(R.id.btn_export_pdf);
        lv_button = findViewById(R.id.lv_button);

        // Enable Nested Scrolling
        recyclerReports.setNestedScrollingEnabled(true);
        recyclerReports.setLayoutManager(new LinearLayoutManager(this));
        reportsList = getSampleReports();
        reportsAdapter = new ReportsAdapter(reportsList);
        recyclerReports.setAdapter(reportsAdapter);

        // Setup State & Severity Filters
        setupFilters();

        // Handle Export Buttons (To Be Implemented)
        btnExportCSV.setOnClickListener(v -> exportToCSV());
        btnExportPDF.setOnClickListener(v -> exportToPDF());

        // ✅ Handle Back Button Click
        lv_button.setOnClickListener(v -> {
            Intent intent = new Intent(ReportsActivity.this, FirearmInjuryDashboard.class);
            startActivity(intent);
            finish(); // Close the current activity
        });
    }

    private void setupFilters() {

        // States
        String[] states = {"All", "Arizona", "California", "Colorado", "Florida", "Georgia", "Illinois", "Louisiana",
                "Maryland", "Michigan", "Missouri", "Nevada", "New York", "Oregon", "Pennsylvania",
                "Tennessee", "Texas", "Washington"};
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, states);
        spinnerState.setAdapter(stateAdapter);

        // Severity Levels
        String[] severityLevels = {"All", "Minor", "Moderate", "Severe", "Fatal"};
        ArrayAdapter<String> severityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, severityLevels);
        spinnerSeverity.setAdapter(severityAdapter);

    }

    private List<ReportModel> getSampleReports() {
        List<ReportModel> list = new ArrayList<>();
        list.add(new ReportModel("2024-06-15", "New York", "Minor"));
        list.add(new ReportModel("2024-07-02", "Los Angeles", "Severe"));
        list.add(new ReportModel("2024-05-20", "Chicago", "Moderate"));
        list.add(new ReportModel("2024-06-22", "Houston", "Fatal"));
        list.add(new ReportModel("2024-04-18", "Miami", "Minor"));
        list.add(new ReportModel("2024-07-10", "Philadelphia", "Severe"));
        list.add(new ReportModel("2024-03-11", "Phoenix", "Moderate"));
        list.add(new ReportModel("2024-06-01", "San Antonio", "Severe"));
        list.add(new ReportModel("2024-05-27", "San Diego", "Minor"));
        list.add(new ReportModel("2024-04-05", "Dallas", "Fatal"));
        list.add(new ReportModel("2024-07-14", "Atlanta", "Moderate"));
        list.add(new ReportModel("2024-06-28", "Denver", "Severe"));
        list.add(new ReportModel("2024-05-15", "Seattle", "Fatal"));
        list.add(new ReportModel("2024-04-23", "Las Vegas", "Minor"));
        list.add(new ReportModel("2024-06-30", "Detroit", "Severe"));
        list.add(new ReportModel("2024-07-05", "Baltimore", "Fatal"));
        list.add(new ReportModel("2024-05-09", "St. Louis", "Moderate"));
        list.add(new ReportModel("2024-06-18", "Portland", "Severe"));
        list.add(new ReportModel("2024-03-30", "Nashville", "Fatal"));
        list.add(new ReportModel("2024-07-08", "New Orleans", "Moderate"));
        return list;
    }

    private void exportToCSV() {
        // TODO: Implement CSV Export
    }

    private void exportToPDF() {
        // TODO: Implement PDF Export
    }
}


