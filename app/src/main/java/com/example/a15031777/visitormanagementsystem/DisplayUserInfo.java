package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayUserInfo extends AppCompatActivity {
    Intent intent;
    ArrayList<String> al;
    ArrayAdapter<String> aa;
    ListView listView;
    Spinner spn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_info);

        listView = (ListView) findViewById(R.id.lvUser);
        spn = (Spinner) findViewById(R.id.spinnerUser);
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
            final HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/getAllUsers.php");
            //specify the method
            request.setMethod("GET");
            //call the webservice
            request.execute();
//            try {
//                //get the response back
//                String jsonString = request.getResponse();
//                System.out.println(">>" + jsonString);
//
//                //do something with json string
//                JSONArray jsonArray = new JSONArray(jsonString);
//
//                // Populate the arraylist personList
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jObj = jsonArray.getJSONObject(i);
//                    al.add(jObj.getString("user_name"));
//                }
//                aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
//                listView.setAdapter(aa);
//                aa.notifyDataSetChanged();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }



            spn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(spn.getSelectedItem().toString().equalsIgnoreCase("Administrator")) {
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
                                String role = jObj.getString("user_role");
                                if(role.equalsIgnoreCase("admin")){
                                    al.add(jObj.getString("user_name"));
                                }

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }


            });

            aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
            listView.setAdapter(aa);
            aa.notifyDataSetChanged();


        }
    }
    }

