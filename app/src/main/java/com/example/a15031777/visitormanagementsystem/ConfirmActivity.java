package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONTokener;

public class ConfirmActivity extends AppCompatActivity {
    TextView tvTitle, tvName, tvEmail, tvNum, tvArrived, tvLicense;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        Intent i = getIntent();
        final String id = i.getStringExtra("id");
        final String license = i.getStringExtra("license");
        final String arrivedBy = i.getStringExtra("by");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ConfirmActivity.this);
        String sign = pref.getString("sign", "");
        tvTitle = (TextView) findViewById(R.id.textView);
        tvName = (TextView) findViewById(R.id.textViewFullName);
        tvEmail = (TextView) findViewById(R.id.textViewEmail);
        tvNum = (TextView) findViewById(R.id.textViewNum);
        tvArrived = (TextView) findViewById(R.id.textViewMode);
        tvLicense = (TextView) findViewById(R.id.textViewLicense);
        btnConfirm = (Button) findViewById(R.id.buttonConfirm);
        String by = "";
        tvTitle.setText(sign);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/getVisitorById.php");
            request.addData("visitorId", id);
            request.setMethod("POST");
            request.execute();

            /******************************/
            try {
                String jsonString = request.getResponse();
                Log.d("JsonString", "jsonString: " + jsonString);
                JSONObject jsonObj = (JSONObject) new JSONTokener(jsonString).nextValue();
                tvName.setText(jsonObj.getString("full_name"));
                tvEmail.setText(jsonObj.getString("email_address"));
                tvNum.setText(jsonObj.getString("mobile_number"));
                by = jsonObj.getString("mode_of_transport");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(ConfirmActivity.this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
        if (sign.equalsIgnoreCase("Sign In")) {
            tvArrived.setText(arrivedBy);
            tvLicense.setText(license);
            btnConfirm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/signInVisitor.php");
                        request.addData("id", id);
                        request.addData("mode",arrivedBy);
                        request.setMethod("POST");
                        request.execute();

                        /******************************/
                        try {
                            String jsonString = request.getResponse();
                            Log.d("JsonString", "jsonString: " + jsonString);
                            JSONObject jsonObj = (JSONObject) new JSONTokener(jsonString).nextValue();
                            String message = jsonObj.getString("message");
                            Toast.makeText(ConfirmActivity.this, message, Toast.LENGTH_SHORT).show();
                            Intent back = new Intent(ConfirmActivity.this, SecurityGuardActivity.class);
                            startActivity(back);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ConfirmActivity.this, "No network connection available.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            tvArrived.setText(by);
            tvLicense.setText("");
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/signOutVisitor.php");
                        request.addData("id", id);
                        request.setMethod("POST");
                        request.execute();

                        /******************************/
                        try {
                            String jsonString = request.getResponse();
                            Log.d("JsonString", "jsonString: " + jsonString);
                            JSONObject jsonObj = (JSONObject) new JSONTokener(jsonString).nextValue();
                            String message = jsonObj.getString("message");
                            Toast.makeText(ConfirmActivity.this, message, Toast.LENGTH_SHORT).show();
                            Intent back = new Intent(ConfirmActivity.this, SecurityGuardActivity.class);
                            startActivity(back);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ConfirmActivity.this, "No network connection available.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


    }
}
