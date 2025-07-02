package com.example.nonfatalfirearminjuries2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SummaryStatisticsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_statistics);

        // Initialize TextViews
        TextView tvTotalCases = findViewById(R.id.tv_total_cases);
        TextView tvFatalCases = findViewById(R.id.tv_fatal_cases);
        TextView tvNonFatalCases = findViewById(R.id.tv_non_fatal_cases);
        TextView tvAvgAge = findViewById(R.id.tv_avg_age);
        TextView tvMostAffectedGender = findViewById(R.id.tv_most_affected_gender);
        TextView tvMostCommonLocation = findViewById(R.id.tv_most_common_location);

        // Initialize database helper
        FirearmDatabaseHelper dbHelper = new FirearmDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Total Cases
        Cursor totalCursor = db.rawQuery("SELECT COUNT(*) FROM Injuries", null);
        if (totalCursor.moveToFirst()) {
            int total = totalCursor.getInt(0);
            tvTotalCases.setText("Total Cases: " + total);
        }
        totalCursor.close();

        // Fatal Cases
        Cursor fatalCursor = db.rawQuery("SELECT COUNT(*) FROM Injuries WHERE severity = 'Fatal'", null);
        if (fatalCursor.moveToFirst()) {
            int fatalCount = fatalCursor.getInt(0);
            tvFatalCases.setText("Fatal Cases: " + fatalCount);
        }
        fatalCursor.close();

        // Non-Fatal Cases
        Cursor nonFatalCursor = db.rawQuery("SELECT COUNT(*) FROM Injuries WHERE severity != 'Fatal'", null);
        if (nonFatalCursor.moveToFirst()) {
            int nonFatalCount = nonFatalCursor.getInt(0);
            tvNonFatalCases.setText("Non-Fatal Cases: " + nonFatalCount);
        }
        nonFatalCursor.close();

        // Average Age
        Cursor ageCursor = db.rawQuery("SELECT AVG(age) FROM Victims", null);
        if (ageCursor.moveToFirst()) {
            double avgAge = ageCursor.getDouble(0);
            tvAvgAge.setText("Average Age: " + String.format("%.1f", avgAge) + " years");
        }
        ageCursor.close();

        // Most Affected Gender
        Cursor genderCursor = db.rawQuery("SELECT gender, COUNT(*) AS count FROM Victims GROUP BY gender ORDER BY count DESC LIMIT 1", null);
        if (genderCursor.moveToFirst()) {
            String gender = genderCursor.getString(0);
            tvMostAffectedGender.setText("Most Affected Gender: " + gender);
        }
        genderCursor.close();

        // Most Common Location (City)
        Cursor locationCursor = db.rawQuery(
                "SELECT L.city, COUNT(*) as count FROM Injuries I " +
                        "JOIN Locations L ON I.location_id = L.id " +
                        "GROUP BY L.city ORDER BY count DESC LIMIT 1", null);
        if (locationCursor.moveToFirst()) {
            String city = locationCursor.getString(0);
            tvMostCommonLocation.setText("Most Common Location: " + city);
        }
        locationCursor.close();

        db.close();

        // Back Button
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(SummaryStatisticsActivity.this, FirearmInjuryDashboard.class);
            startActivity(intent);
            finish();
        });
    }
}
