package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VisitorInformation extends AppCompatActivity {
    ImageButton ibVInfo;
    TextView tvTitle;
    Intent intent;
    ArrayList<String> al;
    ArrayAdapter<String> aa;
    ListView lvVisitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_information);

        ibVInfo = (ImageButton) findViewById(R.id.ib);
        tvTitle = (TextView) findViewById(R.id.textViewName);
        lvVisitor = (ListView) findViewById(R.id.lvVisitor);
        intent = getIntent();
        al = new ArrayList<String>();

        //when the logo clicked, it will goes back to the main page of Manager Activity
        ibVInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VisitorInformation.this, ManagerActivity.class);
                startActivity(i);
            }
        });

        // Check if there is network access
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            //helper class - parse in the http url
            final HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/getAllVisitors.php");
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

                // Populate the arraylist personList
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    al.add("Full Name: " + jObj.getString("full_name"));
                    al.add("Email: " + jObj.getString("email_address"));
                    al.add("Arrived By: " + jObj.getString("mode_of_transport"));
                    al.add("Signed-in: " + jObj.getString("signed_in"));
                    al.add("Mobile Number: " + jObj.getString("mobile_number"));
                    al.add(" ");
                }
                aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
                lvVisitor.setAdapter(aa);
                aa.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
