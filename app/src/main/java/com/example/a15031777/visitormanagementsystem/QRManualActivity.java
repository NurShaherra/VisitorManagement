package com.example.a15031777.visitormanagementsystem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/* DONE BY 15017484 */
public class QRManualActivity extends AppCompatActivity {
    TextView tv;
    Button btnQr, btnManual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrmanual);
        tv = (TextView) findViewById(R.id.textViewName);
        btnManual = (Button) findViewById(R.id.buttonManual);
        btnQr = (Button) findViewById(R.id.buttonQR);
        final Activity activity = this;
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(QRManualActivity.this);
        String sign = pref.getString("sign", "");
        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
        if (sign.equalsIgnoreCase("Sign In")) {
            tv.setText(sign);
            btnManual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent next = new Intent(QRManualActivity.this, SignInActivity.class);
                    startActivity(next);
                }
            });
        } else {
            tv.setText(sign);
            btnManual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent next = new Intent(QRManualActivity.this, SignOutManuallyActivity.class);
                    startActivity(next);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            } else {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(QRManualActivity.this);
                String sign = pref.getString("sign", "");
                if (sign.equalsIgnoreCase("Sign In")) {
                    Intent i = new Intent(QRManualActivity.this, SignInActivity.class);
                    i.putExtra("id", result.getContents());
                    startActivity(i);
                } else {
                    Intent i = new Intent(QRManualActivity.this, SignOutActivity.class);
                    i.putExtra("id", result.getContents());
                    startActivity(i);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
