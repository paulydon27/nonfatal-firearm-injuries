package com.example.nonfatalfirearminjuries2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirearmInjuryDashboard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firearm_injury_dashboard);

        // Button to navigate to Sign Out
        Button sgbutton = findViewById(R.id.sg_button);
        sgbutton.setOnClickListener(v -> {
            Intent intent = new Intent(FirearmInjuryDashboard.this, MainActivity.class);
            startActivity(intent);
        });

        // Button to navigate to Summary Statistics
        Button btnSummaryStatistics = findViewById(R.id.btn_summary_statistics);
        btnSummaryStatistics.setOnClickListener(v -> {
            Intent intent = new Intent(FirearmInjuryDashboard.this, com.example.nonfatalfirearminjuries2.SummaryStatisticsActivity.class);
            startActivity(intent);
        });

        // Button to navigate to Reports Page
        Button btnViewReports = findViewById(R.id.btn_view_reports);
        btnViewReports.setOnClickListener(v -> {
            Intent intent = new Intent(FirearmInjuryDashboard.this, ReportsActivity.class);
            startActivity(intent);
        });
        Button btnSearch;
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(FirearmInjuryDashboard.this, SearchActivity.class);
            startActivity(intent);
        });
        Button gvtbutton;
        gvtbutton = findViewById(R.id.gvt_button);
        gvtbutton.setOnClickListener(v -> {
            Intent intent = new Intent(FirearmInjuryDashboard.this, GraphActivity.class);
            startActivity(intent);
        });

    }
}