package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SignedInUserInfo_generateEReport extends GenerateEvacuationReport {
    ListView lv;
    ArrayAdapter<GenerateInfo> aa;
    ArrayList<GenerateInfo> al;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in_user_info_generate_ereport);

        lv = (ListView) this.findViewById(R.id.lv);
        intent = getIntent();

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

                    GenerateInfo info = new GenerateInfo();
                    // info.setVisitor_id(Integer.parseInt("Visitor ID: "+jObj.getString("visitor_id")));
                    info.setVisitor_id("Visitor ID: " + jObj.getString("visitor_id"));
                    info.setFullName("Full Name:" + jObj.getString("full_name"));
                    info.setEmail("Email: " + jObj.getString("email_address"));
                    info.setMode_of_transport("Arrival by: " + jObj.getString("mode_of_transport"));
                    info.setMobile_number("Mobile Number: " + jObj.getString("mobile_number"));

                    al.add(info);
                }
                aa = new GenerateInfoAdapter(this, R.layout.row_generate_report2, al);
                lv.setAdapter(aa);
                aa.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

