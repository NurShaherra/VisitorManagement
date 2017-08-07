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
        setContentView(R.layout.activity_visitor_information);

        ibGReport = (ImageButton) findViewById(R.id.ibsignedInUserInfo);
        tvTitle = (TextView) findViewById(R.id.textViewTitle);
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
            request.setMethod("POST");
            //call the webservice
            request.execute();
            try {
                //get the response back
                String jsonString = request.getResponse();
                System.out.println(">>" + jsonString);

                //do something with json string
                JSONArray jsonArray = new JSONArray(jsonString);

                // Populate the arraylist personList
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    al.add("Number of visitor Signed-in: " + jObj.getString("num_of_people_signed_in"));

                }
                aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
                lvGReport.setAdapter(aa);
                aa.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        lvGReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent a = new Intent(GenerateEvacuationReport.this, signedInUserInfo_generateEReport.class);

            }
        });
    }
}