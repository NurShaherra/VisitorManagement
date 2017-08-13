package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
    ArrayList<Visitor2> al;
    ArrayAdapter<Visitor2> aa;
    ListView listView;
    Spinner spn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_visitor_info);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int id = pref.getInt("isLoggedIn", -1);
        String role = pref.getString("role", "");

        listView = (ListView) findViewById(R.id.lvVisitor);
        spn = (Spinner) findViewById(R.id.spinnerVisitor);
        intent = getIntent();
        al = new ArrayList<Visitor2>();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles2, android.R.layout.simple_spinner_item);
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

            aa = new VisitorAdapter(this, R.layout.rowvisitor, al);
            listView.setAdapter(aa);

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
                                String role = jObj.getString("user_role");
                                if (role.equalsIgnoreCase("admin")){
                                    Visitor2 visitor = new Visitor2();
                                    visitor.setId(jObj.getInt("visitor_id"));
                                    visitor.setFullname(jObj.getString("full_name"));
                                    visitor.setEmail(jObj.getString("email_address"));
                                    visitor.setTransportmode(jObj.getString("mode_of_transport"));
                                    visitor.setSign_in(jObj.getString("signed_in"));
                                    visitor.setMobile(jObj.getString("mobile_number"));
                                    visitor.setUserId(jObj.getString("user_id"));
                                    al.add(visitor);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (selectedItem.equalsIgnoreCase("Security Guard")){
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
                                if (role.equalsIgnoreCase("security guard")){
                                    Visitor2 visitor = new Visitor2();
                                    visitor.setId(jObj.getInt("visitor_id"));
                                    visitor.setFullname(jObj.getString("full_name"));
                                    visitor.setEmail(jObj.getString("email_address"));
                                    visitor.setTransportmode(jObj.getString("mode_of_transport"));
                                    visitor.setSign_in(jObj.getString("signed_in"));
                                    visitor.setMobile(jObj.getString("mobile_number"));
                                    visitor.setUserId(jObj.getString("user_id"));
                                    al.add(visitor);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (selectedItem.equalsIgnoreCase("Host")) {
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
                                if (role.equalsIgnoreCase("host")){
                                    Visitor2 visitor = new Visitor2();
                                    visitor.setId(jObj.getInt("visitor_id"));
                                    visitor.setFullname(jObj.getString("full_name"));
                                    visitor.setEmail(jObj.getString("email_address"));
                                    visitor.setTransportmode(jObj.getString("mode_of_transport"));
                                    visitor.setSign_in(jObj.getString("signed_in"));
                                    visitor.setMobile(jObj.getString("mobile_number"));
                                    visitor.setUserId(jObj.getString("user_id"));
                                    al.add(visitor);
                                }
                            }




                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
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
                                Visitor2 visitor = new Visitor2();
                                visitor.setId(jObj.getInt("visitor_id"));
                                visitor.setFullname(jObj.getString("full_name"));
                                visitor.setEmail(jObj.getString("email_address"));
                                visitor.setTransportmode(jObj.getString("mode_of_transport"));
                                visitor.setSign_in(jObj.getString("signed_in"));
                                visitor.setMobile(jObj.getString("mobile_number"));
                                visitor.setUserId(jObj.getString("user_id"));
                                al.add(visitor);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    aa.notifyDataSetChanged();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Visitor2 person = (Visitor2)parent.getItemAtPosition(position);
                    intent = new Intent(getApplicationContext(), EditVisitor.class);
                    intent.putExtra("visitor", Integer.toString(person.getId()));
                    startActivity(intent);
                }
            });





        }
    }

    @Override
    public void onResume() {
        super.onResume();

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

            aa = new VisitorAdapter(this, R.layout.rowvisitor, al);
            listView.setAdapter(aa);

            try {
                al.clear();
                String jsonString = request.getResponse();

                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    Visitor2 visitor = new Visitor2();
                    visitor.setId(jObj.getInt("visitor_id"));
                    visitor.setFullname(jObj.getString("full_name"));
                    visitor.setEmail(jObj.getString("email_address"));
                    visitor.setTransportmode(jObj.getString("mode_of_transport"));
                    visitor.setSign_in(jObj.getString("signed_in"));
                    visitor.setMobile(jObj.getString("mobile_number"));
                    visitor.setUserId(jObj.getString("user_id"));
                    al.add(visitor);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(DisplayVisitorInfo.this);
            SharedPreferences.Editor edit = pref.edit();
            edit.putInt("isLoggedIn", -1).commit();
            Intent i = new Intent(DisplayVisitorInfo.this, MainActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    }

