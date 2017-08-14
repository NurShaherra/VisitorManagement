package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.attr.start;

public class GenerateEvacuationReport extends AppCompatActivity {
    ImageButton ibGReport;
    TextView tvTitle;
    ArrayList<String> al;
    ArrayAdapter<String> aa;
    ListView lvGReport;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_evacuation_report);

        ibGReport = (ImageButton) findViewById(R.id.ib);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        lvGReport = (ListView) findViewById(R.id.lvGReport);
        intent = getIntent();
        al = new ArrayList<String>();

        //when the logo clicked, it will goes back to the main page of Manager Activity
        ibGReport.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GenerateEvacuationReport.this, ManagerActivity.class);
                startActivity(i);
            }
        });

        // Check if there is network access
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            //helper class - parse in the http url
            final HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/create_E_Report.php");
            //specify the method
            request.setMethod("GET");
            //call the webservice
            request.execute();
            try {
                //get the response back
                String jsonString = request.getResponse();
                System.out.println(">>" + jsonString);

                //do something with json string
                JSONArray jsonArray = new JSONArray(jsonString);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    al.add("Number of visitor signed-in: " + jObj.getString("num_of_people_signed_in"));
                    al.add("Visitor ID: " + jObj.getString("visitor_id"));
                    al.add("Full Name: " + jObj.getString("full_name"));
                    al.add("Email: " + jObj.getString("email_address"));
                    al.add("Arrived By: " + jObj.getString("mode_of_transport"));
                    al.add("Mobile Number: " + jObj.getString("mobile_number"));
                    al.add(" ");
                }

                aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
                lvGReport.setAdapter(aa);
                aa.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}