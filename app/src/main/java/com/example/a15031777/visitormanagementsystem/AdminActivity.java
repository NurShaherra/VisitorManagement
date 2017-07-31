package com.example.a15031777.visitormanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    ListView lvThings;
    ArrayList<String> al;
    ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        lvThings = (ListView) findViewById(R.id.lvThings);
        Intent intent = getIntent();
        al = new ArrayList<String>();

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
                }
//                } else if(position == 1){
//                    Intent i = new Intent(AdminActivity.this, DisplayVisitorInfo.class);
//                    startActivity(i);
//                } else if(position == 2){
//                    Intent i = new Intent(AdminActivity.this, ViewSummary.class);
//                    startActivity(i);
//                }


            }
        });

    }
}
