package com.example.a15031777.visitormanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class EditUser extends AppCompatActivity {

    private String userId;
    EditText etName,etEmail, etUsername, etUnit, etNumber;
    Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Intent intent = getIntent();
        btnSave = (Button) findViewById(R.id.buttonSave);
        userId = intent.getStringExtra("user");
        HttpRequest request= new HttpRequest("https://pyramidal-drift.000webhostapp.com/getUserById.php?userId=" + userId);
//        HttpRequest request= new HttpRequest("http://localhost/FYP/getUserById.php?userId=" + userId);
        request.setMethod("GET");
        request.execute();

        try{
//            //get the getcontactdetails
            String jsonString = request.getResponse();
            Log.d("Test", jsonString);

            JSONObject jsonObj = new JSONObject(jsonString);
//            // TODO 01: Set values in the EditText fields
//            //use getcontactdetails.php - needs user id
            etName = (EditText) findViewById(R.id.editTextName);
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

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpRequest request= new HttpRequest("https://pyramidal-drift.000webhostapp.com/updateUser.php");
                request.setMethod("POST");

                request.addData("id", userId);
                request.addData("user_name", etUsername.getText().toString());
                request.addData("email_address", etEmail.getText().toString());
                request.addData("full_name", etName.getText().toString());
                request.addData("unit_address", etUnit.getText().toString());
                request.addData("block_number", etNumber.getText().toString());
                request.execute();

                try{
                    Toast.makeText(EditUser.this, "Saved successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
