package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/updateVisitor.php");
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
                    builder.setTitle("Enter Bio:")
                            .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    HttpRequest request= new HttpRequest("https://pyramidal-drift.000webhostapp.com/deleteVisitor.php?visitorId=" + visitorId);

                                    request.setMethod("POST");
                                    request.addData("visitorId", visitorId);
                                    request.execute();

                                    try{

                                        finish();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            });

        }
    }
}
