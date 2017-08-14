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

public class AddUser extends AppCompatActivity {
    EditText etusername, etemail, etrole, etfullname, etunit, etblock, etpw;
    Button btnsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        etusername = (EditText) findViewById(R.id.editTextusername);
        etemail = (EditText) findViewById(R.id.editTextEmail);
        etrole = (EditText) findViewById(R.id.editTextRole);
        etfullname = (EditText) findViewById(R.id.editTextFullName);
        etunit = (EditText) findViewById(R.id.editTextUnit);
        etblock = (EditText) findViewById(R.id.editTextBlock);
        etpw = (EditText) findViewById(R.id.editTextPassword);
        btnsave = (Button) findViewById(R.id.buttonSave);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int id = pref.getInt("isLoggedIn", -1);
        String role = pref.getString("role", "");

        Intent i = getIntent();
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            btnsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/addUser.php");
                    request.addData("user_name", etusername.getText().toString());
                    request.addData("email_address", etemail.getText().toString());
                    request.addData("password", etpw.getText().toString());
                    request.addData("user_role", etrole.getText().toString());
                    request.addData("full_name", etfullname.getText().toString());
                    request.addData("unit_address", etunit.getText().toString());
                    request.addData("block_number", etblock.getText().toString());

                    request.setMethod("POST");
                    request.execute();

                    try {
                        String jsonString = request.getResponse();
                        Log.d("JsonString", "jsonString: " + jsonString);

                        Toast.makeText(AddUser.this, "Added successfully!", Toast.LENGTH_SHORT).show();
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
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(AddUser.this);
            SharedPreferences.Editor edit = pref.edit();
            edit.putInt("isLoggedIn", -1).commit();
            Intent i = new Intent(AddUser.this, MainActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
