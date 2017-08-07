package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.a15031777.visitormanagementsystem.R.id.lvViewEvacuationReport;
import static com.example.a15031777.visitormanagementsystem.R.id.lvVisitor;

public class ViewEvacuationReport extends AppCompatActivity {
    ListView lv;
    ArrayAdapter aa;
    ArrayList<Report> reportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_evacuation_report);

        lv = (ListView) this.findViewById(R.id.lvViewEvacuationReport);

        // Check if there is network access
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            //helper class - parse in the http url
            final HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/get_E_Report.php");
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

                    Report report = new Report();
                    report.setManagerName(jObj.getString("manager_on_duty"));
                    report.setCreatedDate(jObj.getString("eReport_created_date"));
                    report.setNumSignedIn(Integer.parseInt(jObj.getString("num_of_people_signed_in")));

                    reportList.add(report);
                }
                aa = new ReportAdapter(this, R.layout.row, reportList);
                lv.setAdapter(aa);
                aa.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

