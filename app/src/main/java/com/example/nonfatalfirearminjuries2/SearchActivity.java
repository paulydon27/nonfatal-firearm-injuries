package com.example.nonfatalfirearminjuries2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText etSearchKeyword;
    private Spinner spinnerSeverity, spinnerAgeGroup, spinnerGender;
    private Button btnSearch, btnAdvancedFilters, btnBackPPV;
    private ListView listViewResults;
    private FirearmDatabaseHelper firearmDatabaseHelper;
    private ArrayAdapter<String> resultsAdapter;
    private List<String> resultsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialize UI Components
        etSearchKeyword = findViewById(R.id.etSearchKeyword);
        spinnerSeverity = findViewById(R.id.spinnerSeverity);
        spinnerAgeGroup = findViewById(R.id.spinnerAgeGroup);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnSearch = findViewById(R.id.btnSearch);
        btnAdvancedFilters = findViewById(R.id.btnAdvancedFilters);
        listViewResults = findViewById(R.id.listViewResults);
        btnBackPPV = findViewById(R.id.btn_back_ppv);

        // Initialize and copy database from assets if needed
        firearmDatabaseHelper = new FirearmDatabaseHelper(this);

        // Set up results adapter
        resultsList = new ArrayList<>();
        resultsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resultsList);
        listViewResults.setAdapter(resultsAdapter);

        // Set up spinner options
        setupSpinners();

        // Search button action
        btnSearch.setOnClickListener(v -> performSearch());

        // Advanced Filters button (toggle visibility)
        btnAdvancedFilters.setOnClickListener(v -> toggleFilters());

        // Handle Back Button Click
        btnBackPPV.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, FirearmInjuryDashboard.class);
            startActivity(intent);
            finish();
        });
    }

    private void setupSpinners() {
        ArrayAdapter<CharSequence> severityAdapter = ArrayAdapter.createFromResource(this,
                R.array.severity_options, android.R.layout.simple_spinner_item);
        severityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSeverity.setAdapter(severityAdapter);

        ArrayAdapter<CharSequence> ageAdapter = ArrayAdapter.createFromResource(this,
                R.array.age_group_options, android.R.layout.simple_spinner_item);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAgeGroup.setAdapter(ageAdapter);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);
    }

    private void performSearch() {
        String keyword = etSearchKeyword.getText().toString().trim();
        String severity = spinnerSeverity.getSelectedItem().toString();
        String ageGroup = spinnerAgeGroup.getSelectedItem().toString();
        String gender = spinnerGender.getSelectedItem().toString();

        // Optional: Inform the user when showing all results
        if (keyword.isEmpty() && severity.equals("All") && ageGroup.equals("All") && gender.equals("All")) {
            Toast.makeText(this, "Showing all results...", Toast.LENGTH_SHORT).show();
        }

        resultsList.clear();
        Cursor cursor = null;
        try {
            cursor = firearmDatabaseHelper.getReadableDatabase().rawQuery(
                    "SELECT Injuries.id, Injuries.date, Locations.city, Locations.state, Injuries.type, Injuries.severity, " +
                            "Victims.age, Victims.gender, Injuries.description " +
                            "FROM Injuries " +
                            "INNER JOIN Locations ON Injuries.location_id = Locations.id " +
                            "INNER JOIN Victims ON Injuries.id = Victims.injury_id " +
                            "WHERE (LOWER(Injuries.type) LIKE LOWER(?) OR LOWER(Locations.city) LIKE LOWER(?) OR LOWER(Locations.state) LIKE LOWER(?)) " +
                            "AND (Injuries.severity LIKE ? OR ? = 'All') " +
                            "AND (Victims.age LIKE ? OR ? = 'All') " +
                            "AND (Victims.gender LIKE ? OR ? = 'All') " +
                            "ORDER BY Injuries.date DESC",
                    new String[]{
                            "%" + keyword.toLowerCase() + "%", "%" + keyword.toLowerCase() + "%", "%" + keyword.toLowerCase() + "%",
                            severity, severity, ageGroup, ageGroup, gender, gender
                    });

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String result = "📍 " + cursor.getString(2) + ", " + cursor.getString(3) + " | " +
                            "📅 " + cursor.getString(1) + " | " +
                            "🔫 Type: " + cursor.getString(4) + " | " +
                            "🩸 Severity: " + cursor.getString(5) + " | " +
                            "👤 Age: " + cursor.getString(6) + " | " +
                            "🚻 Gender: " + cursor.getString(7) + "\n" +
                            "📖 Description: " + cursor.getString(8);
                    resultsList.add(result);
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(this, "No results found.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving data", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        resultsAdapter.notifyDataSetChanged();
    }

    private void toggleFilters() {
        int visibility = (spinnerSeverity.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE;
        spinnerSeverity.setVisibility(visibility);
        spinnerAgeGroup.setVisibility(visibility);
        spinnerGender.setVisibility(visibility);
    }
}
