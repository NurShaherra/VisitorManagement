package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONTokener;

public class SignInActivity extends AppCompatActivity {
    EditText etIc;
    Button btnNext;
    Spinner spn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etIc = (EditText) findViewById(R.id.editTextIC);
        btnNext = (Button) findViewById(R.id.buttonNext);
        spn = (Spinner) findViewById(R.id.spinnerTrans);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transport, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Intent i = getIntent();
        String nric = i.getStringExtra("id");
        etIc.setText(nric);
        spn.setAdapter(adapter);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ic = etIc.getText().toString();
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/getVisitorById.php");
                    request.addData("visitorId", ic);
                    request.setMethod("POST");
                    request.execute();

                    /******************************/
                    try {
                        String jsonString = request.getResponse();
                        Log.d("JsonString", "jsonString: " + jsonString);

                        JSONObject jsonObj = (JSONObject) new JSONTokener(jsonString).nextValue();
                        String checkVisitor = jsonObj.getString("success");
                        if (checkVisitor.equalsIgnoreCase("true")) {
                            if (spn.getSelectedItem().toString().equalsIgnoreCase("Foot")) {
                                Intent i = new Intent(SignInActivity.this, ConfirmActivity.class);
                                i.putExtra("by", "Foot");
                                //When you use null, the compiler doesn't know which is the type you want to use and cannot decide which overload of the method to use.
                                //You can cast your null to String to inform compiler which method you use:
                                i.putExtra("license", "" + null);
                                i.putExtra("id", ic);
                                startActivity(i);
                            } else {
                                Intent i = new Intent(SignInActivity.this, LicenseActivity.class);
                                i.putExtra("id", ic);
                                startActivity(i);
                            }
                        } else {
                            Toast.makeText(SignInActivity.this, "Visitor does not exist!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(SignInActivity.this, "No network connection available.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
