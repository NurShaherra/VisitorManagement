package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
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

public class EditUser extends AppCompatActivity {

    private String userId;
    EditText etName,etEmail, etUsername, etUnit, etNumber;
    Button btnSave, btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Intent intent = getIntent();
        btnSave = (Button) findViewById(R.id.buttonSave);
        btnDelete = (Button) findViewById(R.id.buttonDelete);

        userId = intent.getStringExtra("user");

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/getUserById.php?userId=" + userId);
//        HttpRequest request= new HttpRequest("http://localhost/FYP/getUserById.php?userId=" + userId);
            request.setMethod("GET");
            request.execute();

            try {
//            //get the getcontactdetails
                String jsonString = request.getResponse();
                Log.d("Test", jsonString);

                JSONObject jsonObj = new JSONObject(jsonString);
                etName = (EditText) findViewById(R.id.editTextfullname);
                etEmail = (EditText) findViewById(R.id.editTextEmail);
                etUsername = (EditText) findViewById(R.id.editTextUsername);
                etUnit = (EditText) findViewById(R.id.editTextUnitAdd);
                etNumber = (EditText) findViewById(R.id.editTextNumber);
                //get string - is the webservice from the output eg:
                etName.setText(jsonObj.getString("full_name"));
                etEmail.setText(jsonObj.getString("email_address"));
                etUsername.setText(jsonObj.getString("user_name"));
                etUnit.setText(jsonObj.getString("unit_address"));
                etNumber.setText(jsonObj.getString("block_number"));

            } catch (Exception e) {
                e.printStackTrace();
            }

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/updateUser.php");
                    request.setMethod("POST");
                    request.addData("id", userId);
                    request.addData("user_name", etUsername.getText().toString());
                    request.addData("email_address", etEmail.getText().toString());
                    request.addData("full_name", etName.getText().toString());
                    request.addData("unit_address", etUnit.getText().toString());
                    request.addData("block_number", etNumber.getText().toString());
                    request.execute();

                    try {
                        String jsonString = request.getResponse();
                        Log.d("JsonString", "jsonString: " + jsonString);
                        Toast.makeText(EditUser.this, "Saved successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getApplicationContext())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Delete visitor")
                            .setMessage("Are you sure you want to delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    HttpRequest request= new HttpRequest("https://pyramidal-drift.000webhostapp.com/deleteUser.php?userId=" + userId);

                                    request.setMethod("POST");
                                    request.addData("user_id", userId);
                                    request.execute();

                                    try{

                                        finish();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            })
                            .setNegativeButton("No", null)
                            .show();
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
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(EditUser.this);
            SharedPreferences.Editor edit = pref.edit();
            edit.putInt("isLoggedIn", -1).commit();
            Intent i = new Intent(EditUser.this, MainActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
