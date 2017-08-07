package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/* DONE BY 15017484 */
public class SignInManuallyActivity extends AppCompatActivity {
    TextView tv;
    ListView lv;
    ArrayAdapter aa;
    ArrayList<Visitor> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_guard);
        tv = (TextView) findViewById(R.id.textViewWelcome);
        lv = (ListView) findViewById(R.id.lv);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int id = pref.getInt("isLoggedIn", -1);
        String role = pref.getString("role", "");
        tv.setText("Sign In");
        values = new ArrayList<Visitor>();
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/getAllVisitorsByIdOut.php");
            request.addData("id", id + "");
            request.setMethod("POST");
            request.execute();

            /******************************/
            try {
                String jsonString = request.getResponse();
                Log.d("JsonString", "jsonString: " + jsonString);
                JSONArray jsonArray = new JSONArray(jsonString);
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
                    values.add(v);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getBaseContext(), "No network connection available.", Toast.LENGTH_SHORT).show();
        }

        aa = new VisitorArrayAdapter(this, R.layout.dumborow, values);
        lv.setAdapter(aa);
        final SharedPreferences.Editor edit = pref.edit();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Visitor v = values.get(position);
                Intent i = new Intent(SignInManuallyActivity.this, ConfirmActivity.class);
                i.putExtra("id", v.getId() + "");
                i.putExtra("sign", "in");
                startActivity(i);

            }
        });
    }
}
