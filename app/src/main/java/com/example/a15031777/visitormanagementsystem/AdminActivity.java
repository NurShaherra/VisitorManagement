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

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    ListView lvThings;

    ArrayList<String> al;
    ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int id = pref.getInt("isLoggedIn", -1);
        String role = pref.getString("role", "");

        lvThings = (ListView) findViewById(R.id.lvThings);
        Intent intent = getIntent();
        al = new ArrayList<String>();

        ImageButton imgB = (ImageButton) findViewById(R.id.imageButton);

        imgB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        String[] values = new String[] { "Manage Users",
                "Manage Visitors", "View Summary"};
        aa =  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,values);
        lvThings.setAdapter(aa);


        lvThings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Intent i = new Intent(AdminActivity.this, DisplayUserInfo.class);
                    startActivity(i);

                } else if(position == 1){
                    Intent i = new Intent(AdminActivity.this, DisplayVisitorInfo.class);
                    startActivity(i);
                } else if(position == 2){
                    Intent i = new Intent(AdminActivity.this, ViewSummary.class);
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
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(AdminActivity.this);
            SharedPreferences.Editor edit = pref.edit();
            edit.putInt("isLoggedIn", -1).commit();
            Intent i = new Intent(AdminActivity.this, MainActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
