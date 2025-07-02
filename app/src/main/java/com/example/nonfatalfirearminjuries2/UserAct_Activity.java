package com.example.nonfatalfirearminjuries2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserAct_Activity extends AppCompatActivity {

    TextView info, login, begin;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_act);

        info = findViewById(R.id.tv_info);
        login = findViewById(R.id.sw_login);
        begin = findViewById(R.id.begin_view);


        String firstname = getIntent().getStringExtra("firstname");
        String lastname = getIntent().getStringExtra("lastname");

        Log.d("UserAct_Activity", "Received firstname: " + firstname);
        Log.d("UserAct_Activity", "Received lastname: " + lastname);

        if (firstname == null) firstname = "Guest";
        if (lastname == null) lastname = "User";

        info.setText("Hello, " + firstname + " " + lastname + "!");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainActivityIntent = new Intent(UserAct_Activity.this, MainActivity.class);
                startActivity(MainActivityIntent);
            }
        });
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FirearmInjuryDashboardIntent = new Intent(UserAct_Activity.this, FirearmInjuryDashboard.class);
                startActivity(FirearmInjuryDashboardIntent);
            }
        });

    }
}