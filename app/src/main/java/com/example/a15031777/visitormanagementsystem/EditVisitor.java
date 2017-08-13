package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class EditVisitor extends AppCompatActivity {

    private String visitorId;
    EditText etsignedin,etEmail, etFullname, etmode, etNumber, etunitid;
    Button btnSave, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_visitor);

        Intent intent = getIntent();
        btnSave = (Button) findViewById(R.id.buttonSave);
        btnDelete = (Button) findViewById(R.id.buttonDelete);
        visitorId = intent.getStringExtra("visitor");

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/getVisitorByIdGet.php?visitorId=" + visitorId);
//        HttpRequest request= new HttpRequest("http://localhost/FYP/getUserById.php?userId=" + userId);
            request.setMethod("GET");
            request.execute();

            try {
//            //get the getcontactdetails
                String jsonString = request.getResponse();
                Log.d("Test", jsonString);

                JSONObject jsonObj = new JSONObject(jsonString);
                etFullname = (EditText) findViewById(R.id.editTextusername);
                etEmail = (EditText) findViewById(R.id.editTextEmail);
                etsignedin = (EditText) findViewById(R.id.editTextFullName);
                etmode = (EditText) findViewById(R.id.editTextRole);
                etNumber = (EditText) findViewById(R.id.editTextUnit);
                etunitid = (EditText) findViewById(R.id.editTextBlock);

                //get string - is the webservice from the output eg:
                etFullname.setText(jsonObj.getString("full_name"));
                etEmail.setText(jsonObj.getString("email_address"));
                etmode.setText(jsonObj.getString("mode_of_transport"));
                etNumber.setText(jsonObj.getString("mobile_number"));
                etunitid.setText(jsonObj.getString("user_id"));
                etsignedin.setText(jsonObj.getString("signed_in"));


            } catch (Exception e) {
                e.printStackTrace();
            }

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/editVisitor.php");
                    request.setMethod("POST");
                    request.addData("visitorId", visitorId);
                    request.addData("full_name", etFullname.getText().toString());
                    request.addData("email_address", etEmail.getText().toString());
                    request.addData("mode_of_transport", etmode.getText().toString());
                    request.addData("mobile_number", etNumber.getText().toString());
                    request.addData("user_id", etunitid.getText().toString());
                    request.addData("signed_in", etsignedin.getText().toString());

                    request.execute();

                    try {
                        String jsonString = request.getResponse();
                        Log.d("JsonString", "jsonString: " + jsonString);
                        Toast.makeText(EditVisitor.this, "Saved successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                                    HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/deleteVisitor.php?visitorId=" + visitorId);

                                    request.setMethod("POST");
                                    request.addData("visitorId", visitorId);
                                    request.execute();

                                    try{
                                        Toast.makeText(EditVisitor.this, "Delete successfully!", Toast.LENGTH_SHORT).show();

                                        finish();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }                                }
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
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(EditVisitor.this);
            SharedPreferences.Editor edit = pref.edit();
            edit.putInt("isLoggedIn", -1).commit();
            Intent i = new Intent(EditVisitor.this, MainActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

