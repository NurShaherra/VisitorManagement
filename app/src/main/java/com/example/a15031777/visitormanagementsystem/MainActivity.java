package com.example.a15031777.visitormanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

//
//        if (userLoggedIn == -1) {
//            btnLogin.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//                    SharedPreferences.Editor edit = pref.edit();
//                    String username = etUName.getText().toString();
//                    String pw = etPw.getText().toString();
//                    Boolean userId = db.checkUser(username, pw);
//
//                    if (userId == true) {
//                        int id = db.getUserId(username, pw);
//                        edit.putInt("isLoggedIn", id).commit();
//                        User currentUser = db.getUserWithId(id);
//                        db.close();
//                        String role = currentUser.getUserRole();
//                        if (role.equalsIgnoreCase("Administrator")) {
//                            Intent i = new Intent(MainActivity.this, AdminActivity.class);
//                            startActivity(i);
//                        } else if (role.equalsIgnoreCase("Host")) {
//                            Intent i = new Intent(MainActivity.this, HostActivity.class);
//                            startActivity(i);
//                        } else if (role.equalsIgnoreCase("Manager")) {
//                            Intent i = new Intent(MainActivity.this, ManagerActivity.class);
//                            startActivity(i);
//                        } else {
//                            Intent i = new Intent(MainActivity.this, SecurityGuardActivity.class);
//                            startActivity(i);
//                        }
//
//                    } else {
//                        Toast.makeText(MainActivity.this, "Your username/password is wrong.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        } else {
//            DBHandlerUser db = new DBHandlerUser(MainActivity.this);
//            User currentUser = db.getUserWithId(userLoggedIn);
//            db.close();
//            String role = currentUser.getUserRole();
//            if (role.equalsIgnoreCase("Administrator")) {
//                Intent i = new Intent(MainActivity.this, AdminActivity.class);
//                startActivity(i);
//            } else if (role.equalsIgnoreCase("Host")) {
//                Intent i = new Intent(MainActivity.this, HostActivity.class);
//                startActivity(i);
//            } else if (role.equalsIgnoreCase("Manager")) {
//                Intent i = new Intent(MainActivity.this, ManagerActivity.class);
//                startActivity(i);
//            } else {
//                Intent i = new Intent(MainActivity.this, SecurityGuardActivity.class);
//                startActivity(i);
//            }
//        }
    }
}
