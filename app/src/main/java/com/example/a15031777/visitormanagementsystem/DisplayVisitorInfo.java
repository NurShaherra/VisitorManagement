package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayVisitorInfo extends AppCompatActivity {
    Intent intent;
    ArrayList<String> al;
    ArrayAdapter<String> aa;
    ListView listView;
    Spinner spn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_visitor_info);

        listView = (ListView) findViewById(R.id.lvVisitor);
        spn = (Spinner) findViewById(R.id.spinnerVisitor);
        intent = getIntent();
        al = new ArrayList<String>();



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spn.setAdapter(adapter);

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

            aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);

            spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    if (selectedItem.equalsIgnoreCase("Administrator")) {
                        al.clear();
                        try {
                            //get the response back
                            String jsonString = request.getResponse();
                            System.out.println(">>" + jsonString);

                            //do something with json string
                            JSONArray jsonArray = new JSONArray(jsonString);

                            // Populate the arraylist personList
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jObj = jsonArray.getJSONObject(i);
                                int userid = jObj.getInt("user_id");
                                if (userid == 0){
                                    al.add(jObj.getString("full_name"));
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                    } else if (selectedItem.equalsIgnoreCase("Security Guard")){
//                        al.clear();
//                        try {
//                            //get the response back
//                            String jsonString = request.getResponse();
//                            System.out.println(">>" + jsonString);
//
//                            //do something with json string
//                            JSONArray jsonArray = new JSONArray(jsonString);
//
//                            // Populate the arraylist personList
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jObj = jsonArray.getJSONObject(i);
//                                String role = jObj.getString("user_role");
//                                if (role.equalsIgnoreCase("security guard")){
//                                    al.add(jObj.getString("user_name"));
//                                }
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else if (selectedItem.equalsIgnoreCase("Manager")){
//                        al.clear();
//                        try {
//                            //get the response back
//                            String jsonString = request.getResponse();
//                            System.out.println(">>" + jsonString);
//
//                            //do something with json string
//                            JSONArray jsonArray = new JSONArray(jsonString);
//
//                            // Populate the arraylist personList
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jObj = jsonArray.getJSONObject(i);
//                                String role = jObj.getString("user_role");
//                                if (role.equalsIgnoreCase("manager")){
//                                    al.add(jObj.getString("user_name"));
//                                }
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else if (selectedItem.equalsIgnoreCase("Host")) {
//                        al.clear();
//                        try {
//                            //get the response back
//                            String jsonString = request.getResponse();
//                            System.out.println(">>" + jsonString);
//
//                            //do something with json string
//                            JSONArray jsonArray = new JSONArray(jsonString);
//
//                            // Populate the arraylist personList
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jObj = jsonArray.getJSONObject(i);
//                                String role = jObj.getString("user_role");
//                                if (role.equalsIgnoreCase("host")){
//                                    al.add(jObj.getString("user_name"));
//                                }
//                            }
//
//
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        al.clear();
//                        try {
//                            //get the response back
//                            String jsonString = request.getResponse();
//                            System.out.println(">>" + jsonString);
//
//                            //do something with json string
//                            JSONArray jsonArray = new JSONArray(jsonString);
//
//                            // Populate the arraylist personList
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jObj = jsonArray.getJSONObject(i);
//                                al.add(jObj.getString("user_name"));
//
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }

                    listView.setAdapter(aa);
                    aa.notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }


    }
}
