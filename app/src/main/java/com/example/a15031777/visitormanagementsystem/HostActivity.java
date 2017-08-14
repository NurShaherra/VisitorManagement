package com.example.a15031777.visitormanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/* DONE BY 15017484 */
public class HostActivity extends AppCompatActivity {
    TextView tv;
    ListView lv;
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_guard);
        tv = (TextView) findViewById(R.id.textViewWelcome);
        lv = (ListView) findViewById(R.id.lv);
        ImageButton imgB = (ImageButton) findViewById(R.id.imageButton);

        imgB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int id = pref.getInt("isLoggedIn", -1);
        String role = pref.getString("role", "");
        tv.append(" " + role + "!");


        String[] values = new String[]{"View Visitors", "Register Visitors", "Sign In Visitors", "Sign Out Visitors"};
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        lv.setAdapter(aa);
        final SharedPreferences.Editor edit = pref.edit();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent i = new Intent(HostActivity.this, ViewVisitorsActivity.class);
                    edit.putString("sign", "Display Visitors").commit();
                    startActivity(i);

                } else if (position == 1) {
                    Intent i = new Intent(HostActivity.this, ChooseAddActivity.class);
                    edit.putString("user","host").commit();
                    startActivity(i);
                } else if (position == 2) {
                    Intent i = new Intent(HostActivity.this, SignInManuallyActivity.class);
                    edit.putString("sign", "Sign In").commit();
                    startActivity(i);
                } else {
                    Intent i = new Intent(HostActivity.this, SignOutManuallyActivity.class);
                    edit.putString("sign", "Sign Out").commit();
                    startActivity(i);
                }


            }
        });
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
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(HostActivity.this);
            SharedPreferences.Editor edit = pref.edit();
            edit.putInt("isLoggedIn", -1).commit();
            Intent i = new Intent(HostActivity.this, MainActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
