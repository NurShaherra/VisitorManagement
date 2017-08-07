package com.example.a15031777.visitormanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.json.JSONObject;

public class EditUser extends AppCompatActivity {

    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user");
        HttpRequest request= new HttpRequest("https://pyramidal-drift.000webhostapp.com/getUserById.php?userId=" + userId);
        request.setMethod("GET");
        request.execute();

        try{
//            //get the getcontactdetails
//            String jsonString = request.getResponse();
//            JSONObject jsonObj = new JSONObject(jsonString);
//            // TODO 01: Set values in the EditText fields
//            //use getcontactdetails.php - needs user id
//            EditText firstName = (EditText) findViewById(R.id.editTextFirstName);
//            //get string - is the webservice from the output eg:
//            firstName.setText(jsonObj.getString("firstname"));
//
//            EditText lastName = (EditText) findViewById(R.id.editTextLastName);
//            //(String) jsonObj.get
//            lastName.setText(jsonObj.getString("lastname"));
//
//            EditText home = (EditText) findViewById(R.id.editTextHome);
//            home.setText(jsonObj.getString("home"));
//
//            EditText mobile = (EditText) findViewById(R.id.editTextMobile);
//            mobile.setText(jsonObj.getString("mobile"));
//
//            EditText address = (EditText) findViewById(R.id.editTextAddress);
//            address.setText(jsonObj.getString("address"));
//
//            EditText country = (EditText) findViewById(R.id.editTextCountry);
//            country.setText(jsonObj.getString("country"));
//
//            EditText postalcode = (EditText) findViewById(R.id.editTextPostalCode);
//            postalcode.setText(jsonObj.getString("postalcode"));
//
//            EditText email = (EditText) findViewById(R.id.editTextEmail);
//            email.setText(jsonObj.getString("email"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
