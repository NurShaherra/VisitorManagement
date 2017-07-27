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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONTokener;

public class MainActivity extends AppCompatActivity {
    EditText etUName, etPw;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUName = (EditText) findViewById(R.id.editTextUsername);
        etPw = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.buttonLogin);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int userLoggedIn = pref.getInt("isLoggedIn", -1);


        if (userLoggedIn == -1) {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userName = etUName.getText().toString();
                    String password = etPw.getText().toString();
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/doLogin.php");
                        request.addData("username", userName);
                        request.addData("password", password);
                        request.setMethod("POST");
                        request.execute();

                        /******************************/
                        try {
                            String jsonString = request.getResponse();
                            Log.d("JsonString", "jsonString: " + jsonString);

                            JSONObject jsonObj = (JSONObject) new JSONTokener(jsonString).nextValue();
                            if (jsonObj.getBoolean("authenticated")) {
                                // When authentication is successful
                                String id = jsonObj.getString("user_id");
                                String role = jsonObj.getString("user_role");
                                Log.d("UserId", "userId: " + id);
                                Log.d("UserRole", "userRole: " + id);
                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                SharedPreferences.Editor edit = pref.edit();
                                edit.putInt("isLoggedIn", Integer.parseInt(id));
                                edit.putString("role", role);
                                edit.commit();
//                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                if (role.equalsIgnoreCase("Security Guard")) {
                                    Intent i = new Intent(MainActivity.this, SecurityGuardActivity.class);
                                    startActivity(i);
                                } else if(role.equalsIgnoreCase("admin")){
                                    Intent i = new Intent(MainActivity.this,AdminActivity.class);
                                    startActivity(i);
                                }

                            } else {
                                Toast.makeText(MainActivity.this, "Authentication failed, please login again", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "No network connection available.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {

        }
    }
}
