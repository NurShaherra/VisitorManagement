package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

public class EditVisitor extends AppCompatActivity {

    private String visitorId;
    EditText etsignedin,etEmail, etFullname, etmode, etNumber, etunitid;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_visitor);

        Intent intent = getIntent();
        btnSave = (Button) findViewById(R.id.buttonSave);
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
                etFullname = (EditText) findViewById(R.id.editTextfullname);
                etEmail = (EditText) findViewById(R.id.editTextEmail);
                etsignedin = (EditText) findViewById(R.id.editTextSignedIn);
                etmode = (EditText) findViewById(R.id.editTextMode);
                etNumber = (EditText) findViewById(R.id.editTextNumber);
                etunitid = (EditText) findViewById(R.id.editTextUnit_id);

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

        }
    }
}
