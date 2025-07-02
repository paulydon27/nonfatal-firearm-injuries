package com.example.nonfatalfirearminjuries2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button login;
    TextView signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        username = findViewById(R.id.er_Username);
        password = findViewById(R.id.rd_Password);
        login = findViewById(R.id.lg_button);
        signup = findViewById(R.id.op_signup);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo = databaseHelper.check(username.getText().toString(), password.getText().toString());
                if (userInfo == null) {
                    Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent userActivityIntent = new Intent(MainActivity.this, UserAct_Activity.class);
                    userActivityIntent.putExtra("firstname", userInfo.getFirstname());
                    userActivityIntent.putExtra("lastname", userInfo.getLastname());
                    startActivity(userActivityIntent);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Regis_ActivityIntent = new Intent(MainActivity.this, Regis_Activity.class);
                startActivity(Regis_ActivityIntent);
            }
        });
    }
}
