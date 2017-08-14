package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONTokener;

/* DONE BY 15017484 */
public class SignOutActivity extends AppCompatActivity {
    EditText etIC;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);
        etIC = (EditText) findViewById(R.id.editTextIC);
        btnNext = (Button) findViewById(R.id.buttonNxt);
        ImageButton imgB = (ImageButton) findViewById(R.id.imageButton);

        imgB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });
        Intent i = getIntent();
        String nric = i.getStringExtra("id");
        etIC.setText(nric);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ic = etIC.getText().toString();
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
                            Intent i = new Intent(SignOutActivity.this, ConfirmActivity.class);
                            i.putExtra("id", ic);
                            i.putExtra("sign", "out");
                            startActivity(i);
                        } else {
                            Toast.makeText(SignOutActivity.this, "Visitor does not exist!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(SignOutActivity.this, "No network connection available.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
