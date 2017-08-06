package com.example.a15031777.visitormanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LicenseActivity extends AppCompatActivity {
    EditText etLicense;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        Intent i = getIntent();
        final String ic = i.getStringExtra("nric");
        etLicense = (EditText) findViewById(R.id.editTextIC);
        btnNext = (Button) findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plateNum = etLicense.getText().toString();
                Intent i = new Intent(LicenseActivity.this, ConfirmActivity.class);
                i.putExtra("by", "Car");
                i.putExtra("license", plateNum);
                i.putExtra("nric", ic);
                i.putExtra("sign","in");
                startActivity(i);
            }
        });
    }

}

