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
    ArrayList<Visitor> al;
    ArrayAdapter<Visitor> aa;
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
        al = new ArrayList<Visitor>();

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
                                    Visitor visitor = new Visitor();
                                    visitor.setId(jObj.getInt("visitor_id"));
                                    visitor.setFullname(jObj.getString("full_name"));
                                    visitor.setEmail(jObj.getString("email_address"));
                                    visitor.setTransportmode(jObj.getString("mode_of_transport"));
                                    visitor.setSign_in(jObj.getInt("signed_in"));
                                    visitor.setMobile(jObj.getInt("mobile_number"));
                                    visitor.setUserId(jObj.getInt("user_id"));
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
                                    Visitor visitor = new Visitor();
                                    visitor.setId(jObj.getInt("visitor_id"));
                                    visitor.setFullname(jObj.getString("full_name"));
                                    visitor.setEmail(jObj.getString("email_address"));
                                    visitor.setTransportmode(jObj.getString("mode_of_transport"));
                                    visitor.setSign_in(jObj.getInt("signed_in"));
                                    visitor.setMobile(jObj.getInt("mobile_number"));
                                    visitor.setUserId(jObj.getInt("user_id"));
                                    al.add(visitor);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (selectedItem.equalsIgnoreCase("Manager")){
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
                                if (role.equalsIgnoreCase("manager")){
                                    Visitor visitor = new Visitor();
                                    visitor.setId(jObj.getInt("visitor_id"));
                                    visitor.setFullname(jObj.getString("full_name"));
                                    visitor.setEmail(jObj.getString("email_address"));
                                    visitor.setTransportmode(jObj.getString("mode_of_transport"));
                                    visitor.setSign_in(jObj.getInt("signed_in"));
                                    visitor.setMobile(jObj.getInt("mobile_number"));
                                    visitor.setUserId(jObj.getInt("user_id"));
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
                                    Visitor visitor = new Visitor();
                                    visitor.setId(jObj.getInt("visitor_id"));
                                    visitor.setFullname(jObj.getString("full_name"));
                                    visitor.setEmail(jObj.getString("email_address"));
                                    visitor.setTransportmode(jObj.getString("mode_of_transport"));
                                    visitor.setSign_in(jObj.getInt("signed_in"));
                                    visitor.setMobile(jObj.getInt("mobile_number"));
                                    visitor.setUserId(jObj.getInt("user_id"));
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

                                String visitorId = jObj.getString("visitor_id");
                                String name = jObj.getString("full_name");
                                String email = jObj.getString("email_address");
                                String mode = jObj.getString("mode_of_transport");
                                String sign_in = jObj.getString("signed_in");
                                String mobile = jObj.getString("mobile_number");
                                String userId = jObj.getString("user_id");
                                Visitor v = new Visitor(Integer.parseInt(visitorId), name, email, Integer.parseInt(mobile), mode, Integer.parseInt(sign_in), Integer.parseInt(userId));
                                al.add(v);
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
                    Visitor person = (Visitor)parent.getItemAtPosition(position);
                    intent = new Intent(getApplicationContext(), EditVisitor.class);
                    intent.putExtra("visitor", Integer.toString(person.getId()));
                    startActivity(intent);
                }
            });





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

