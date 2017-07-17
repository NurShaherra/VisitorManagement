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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayUserInfo extends AppCompatActivity {
    Intent intent;
    ArrayList<User> personList = new ArrayList<User>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onResume() {
        super.onResume();
        personList.clear();
        // Check if there is network access
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            //helper class - parse in the http url
            HttpRequest request = new HttpRequest("pyramidal-drift.000webhostapp.com/getAllUsers.php");
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
                    System.out.println(jObj.getString("firstname") + " " + jObj.getString("lastname"));
                    User person = new User();
                    person.setId(jObj.getInt("user_id"));
                    person.setUsername(jObj.getString("user_name"));
                    person.setEmail(jObj.getString("email_address"));
                    person.setUserRole(jObj.getString("user_role"));
                    person.setFullname(jObj.getString("full_name"));
                    person.setUnitAddress(jObj.getString("unit_address"));
                    person.setBlocknum(jObj.getString("block_number"));
                    personList.add(person);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            UserAdapter arrayAdapter = new UserAdapter(this, R.layout.rowuser, personList);
            listView = (ListView) findViewById(R.id.lvUser);
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View arg1, int arg2, long arg3) {

                    User person = (User) parent.getItemAtPosition(arg2);

                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("com.example.MAIN_MESSAGE", Integer.toString(person.getId()));
                    startActivity(intent);
                }
            });
        }
    }
}