package com.example.nonfatalfirearminjuries2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Regis_Activity extends AppCompatActivity {

    EditText fn_name, lln_name, email_editText, pw_editText;
    Button cre_button, larbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_regis);

        fn_name = findViewById(R.id.fn_name);
        lln_name = findViewById(R.id.lln_name);
        email_editText = findViewById(R.id.email_editText);
        pw_editText = findViewById(R.id.pw_editText);
        cre_button = findViewById(R.id.cre_button);
        larbutton = findViewById(R.id.lar_button);

        DatabaseHelper databaseHelper = new DatabaseHelper(Regis_Activity.this);

        cre_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UserInfo userInfo = new UserInfo(fn_name.getText().toString(), lln_name.getText().toString(), email_editText.getText().toString(), pw_editText.getText().toString());
                boolean res = databaseHelper.register(userInfo);
                if (res) {
                    Toast.makeText(Regis_Activity.this, "Registration Succeed!", Toast.LENGTH_SHORT).show();
                    Intent MainActivityIntent = new Intent(Regis_Activity.this, MainActivity.class);
                    startActivity(MainActivityIntent);
                } else {
                    Toast.makeText(Regis_Activity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        });
        larbutton.setOnClickListener(v -> {
            Intent intent = new Intent(Regis_Activity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

}