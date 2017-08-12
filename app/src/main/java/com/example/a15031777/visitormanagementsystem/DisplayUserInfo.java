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

public class DisplayUserInfo extends AppCompatActivity {
    Intent intent;
    ArrayList<User> al;
    ArrayAdapter<User> aa;
    ListView listView;
    Spinner spn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_info);

        listView = (ListView) findViewById(R.id.lvUser);
        spn = (Spinner) findViewById(R.id.spinnerUser);
        intent = getIntent();
        al = new ArrayList<User>();

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

            aa = new UserAdapter(this, R.layout.rowuser, al);
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
                                if (role.equalsIgnoreCase("admin")) {
                                    User user = new User();
                                    user.setId(jObj.getInt("user_id"));
                                    user.setFullname(jObj.getString("full_name"));
                                    user.setEmail(jObj.getString("email_address"));
                                    user.setUsername(jObj.getString("user_name"));
                                    user.setAddress(jObj.getString("unit_address"));
                                    user.setBlock(jObj.getString("block_number"));
                                    al.add(user);
                                }
                            }

                            aa.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (selectedItem.equalsIgnoreCase("Security Guard")) {
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
                                if (role.equalsIgnoreCase("security guard")) {
                                    User user = new User();
                                    user.setId(jObj.getInt("user_id"));
                                    user.setFullname(jObj.getString("full_name"));
                                    user.setEmail(jObj.getString("email_address"));
                                    user.setUsername(jObj.getString("user_name"));
                                    user.setAddress(jObj.getString("unit_address"));
                                    user.setBlock(jObj.getString("block_number"));
                                    al.add(user);
                                }
                            }

                            aa.notifyDataSetChanged();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (selectedItem.equalsIgnoreCase("Manager")) {
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
                                if (role.equalsIgnoreCase("manager")) {
                                    User user = new User();
                                    user.setId(jObj.getInt("user_id"));
                                    user.setFullname(jObj.getString("full_name"));
                                    user.setEmail(jObj.getString("email_address"));
                                    user.setUsername(jObj.getString("user_name"));
                                    user.setAddress(jObj.getString("unit_address"));
                                    user.setBlock(jObj.getString("block_number"));
                                    al.add(user);
                                }
                            }

                            aa.notifyDataSetChanged();


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
                                if (role.equalsIgnoreCase("host")) {
                                    User user = new User();
                                    user.setId(jObj.getInt("user_id"));
                                    user.setFullname(jObj.getString("full_name"));
                                    user.setEmail(jObj.getString("email_address"));
                                    user.setUsername(jObj.getString("user_name"));
                                    user.setAddress(jObj.getString("unit_address"));
                                    user.setBlock(jObj.getString("block_number"));
                                    al.add(user);
                                }
                            }

                            aa.notifyDataSetChanged();


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
                                User user = new User();
                                user.setId(jObj.getInt("user_id"));
                                user.setFullname(jObj.getString("full_name"));
                                user.setEmail(jObj.getString("email_address"));
                                user.setUsername(jObj.getString("user_name"));
                                user.setAddress(jObj.getString("unit_address"));
                                user.setBlock(jObj.getString("block_number"));
                                al.add(user);
                            }

                            aa.notifyDataSetChanged();


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
                    User person = (User) parent.getItemAtPosition(position);

                    intent = new Intent(getApplicationContext(), EditUser.class);
                    intent.putExtra("user", Integer.toString(person.getId()));
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
            final HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/getAllUsers.php");
            //specify the method
            request.setMethod("GET");
            //call the webservice
            request.execute();

            aa = new UserAdapter(this, R.layout.rowuser, al);
            listView.setAdapter(aa);

            try {
                al.clear();
                String jsonString = request.getResponse();

                JSONArray jsonArray = new JSONArray(jsonString);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObj = jsonArray.getJSONObject(i);
                        User user = new User();
                        user.setId(jObj.getInt("user_id"));
                        user.setFullname(jObj.getString("full_name"));
                        user.setEmail(jObj.getString("email_address"));
                        user.setUsername(jObj.getString("user_name"));
                        user.setAddress(jObj.getString("unit_address"));
                        user.setBlock(jObj.getString("block_number"));
                        al.add(user);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();


        if (id == R.id.menu_logout) {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(DisplayUserInfo.this);
            SharedPreferences.Editor edit = pref.edit();
            edit.putInt("isLoggedIn", -1).commit();
            Intent i = new Intent(DisplayUserInfo.this, MainActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.add){
            Intent i = new Intent(DisplayUserInfo.this, ChooseAddActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

