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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class ProfilePage extends AppCompatActivity {

    private String userId;
    EditText etEmail, etUsername, etUnit, etBlock;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        btnSave = (Button) findViewById(R.id.buttonSave);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        final int id = pref.getInt("isLoggedIn", -1);
        String role = pref.getString("role", "");

        Intent i = getIntent();

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/getUserById.php?userId=" + id);
//        HttpRequest request= new HttpRequest("http://localhost/FYP/getUserById.php?userId=" + userId);
            request.setMethod("GET");
            request.execute();

            try {
//            //get the getcontactdetails
                String jsonString = request.getResponse();
                Log.d("Test", jsonString);

                JSONObject jsonObj = new JSONObject(jsonString);
                etEmail = (EditText) findViewById(R.id.editTextEmail);
                etUsername = (EditText) findViewById(R.id.editTextUsername);
                etUnit = (EditText) findViewById(R.id.editTextUnit);
                etBlock = (EditText) findViewById(R.id.editTextBlock);
                //get string - is the webservice from the output eg:
                etEmail.setText(jsonObj.getString("email_address"));
                etUsername.setText(jsonObj.getString("user_name"));
                etUnit.setText(jsonObj.getString("unit_address"));
                etBlock.setText(jsonObj.getString("block_number"));

            } catch (Exception e) {
                e.printStackTrace();
            }

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/updateUserProfile.php");
                    request.setMethod("POST");
                    request.addData("userId", userId);
                    request.addData("user_name", etUsername.getText().toString());
                    request.addData("email_address", etEmail.getText().toString());
                    request.addData("unit_address", etUnit.getText().toString());
                    request.addData("block_number", etBlock.getText().toString());
                    request.execute();

                    try {
                        String jsonString = request.getResponse();
                        Log.d("JsonString", "jsonString: " + jsonString);
                        Toast.makeText(ProfilePage.this, "Saved successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_host, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ProfilePage.this);
            SharedPreferences.Editor edit = pref.edit();
            edit.putInt("isLoggedIn", -1).commit();
            Intent i = new Intent(ProfilePage.this, MainActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
