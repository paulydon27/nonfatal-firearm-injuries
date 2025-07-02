package com.example.nonfatalfirearminjuries2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphActivity extends AppCompatActivity {
    private Button lenbutton;
    private FirearmDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // Initialize DB helper
        dbHelper = new FirearmDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Set up back button
        lenbutton = findViewById(R.id.len_button);
        lenbutton.setOnClickListener(v -> {
            Intent intent = new Intent(GraphActivity.this, FirearmInjuryDashboard.class);
            startActivity(intent);
            finish();
        });

        // Set up the bar chart
        BarChart barChart = findViewById(R.id.lax_chart);

        // Step 1: Count injuries per severity
        Map<String, Integer> severityCount = new HashMap<>();
        String[] severities = {"Minor", "Moderate", "Severe", "Fatal"};

        // Initialize counts to 0
        for (String severity : severities) {
            severityCount.put(severity, 0);
        }

        // Query the database
        Cursor cursor = db.rawQuery("SELECT severity, COUNT(*) as count FROM Injuries GROUP BY severity", null);
        if (cursor.moveToFirst()) {
            do {
                String severity = cursor.getString(cursor.getColumnIndexOrThrow("severity"));
                int count = cursor.getInt(cursor.getColumnIndexOrThrow("count"));
                severityCount.put(severity, count);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Step 2: Convert to BarEntry list
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < severities.length; i++) {
            String sev = severities[i];
            float count = severityCount.get(sev);
            entries.add(new BarEntry(i, count));
        }

        // Step 3: Create and display chart
        BarDataSet barDataSet = new BarDataSet(entries, "Injuries by Severity");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextSize(14f);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        // Format X-axis
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(severities));
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getXAxis().setLabelRotationAngle(0);

        barChart.getAxisLeft().setGranularity(1f);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisRight().setEnabled(false);

        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);
        barChart.invalidate();
    }
}

