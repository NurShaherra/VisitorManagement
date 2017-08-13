package com.example.a15031777.visitormanagementsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* DONE BY 15017484 */
public class Add3Visitor extends AppCompatActivity {
    EditText etName, etEmail, etMobile, etName2, etEmail2, etMobile2,etName3, etEmail3, etMobile3;
    Button btnSave;
    String sendEmail,visitor_id, url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visitor_3);
        etName = (EditText) findViewById(R.id.editTextUserName);
        etEmail = (EditText) findViewById(R.id.editTextEmail);
        etMobile = (EditText) findViewById(R.id.editTextUnit);
        etName2 = (EditText) findViewById(R.id.editTextUserName2);
        etEmail2 = (EditText) findViewById(R.id.editTextEmail2);
        etMobile2 = (EditText) findViewById(R.id.editTextUnit2);
        etName3 = (EditText)findViewById(R.id.editTextUserName3);
        etEmail3 = (EditText) findViewById(R.id.editTextEmail3);
        etMobile3 = (EditText) findViewById(R.id.editTextUnit3);
        btnSave = (Button) findViewById(R.id.buttonSave);
        Intent i = getIntent();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        final int id = pref.getInt("isLoggedIn", -1);
        String user = pref.getString("user","guard");
        if(user.equalsIgnoreCase("host")){
            url = "https://pyramidal-drift.000webhostapp.com/addVisitor.php";
        } else {
            url = "https://pyramidal-drift.000webhostapp.com/addSignInVisitor.php";
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //incomplete as send email and generate qr code is in the works.
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                int mobile = Integer.parseInt(etMobile.getText().toString());
                String name2 = etName2.getText().toString();
                String email2 = etEmail2.getText().toString();
                String mobile2 = etMobile2.getText().toString();
                String name3 = etName3.getText().toString();
                String email3 = etEmail3.getText().toString();
                String mobile3 = etMobile3.getText().toString();
                if (name.equalsIgnoreCase("") || email.equalsIgnoreCase("") || etMobile.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getBaseContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    //Idk what to put so just fuck it all and dash because what the fuck is this even.
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        HttpRequest request = new HttpRequest(url);
                        request.addData("fullname", name);
                        request.addData("email", email);
                        request.addData("mode", "");
                        request.addData("mobile", mobile + "");
                        request.addData("userId", id + "");
                        request.setMethod("POST");
                        request.execute();

                        /******************************/
                        try {
                            String jsonString = request.getResponse();
                            Log.d("JsonString", "jsonString: " + jsonString);

                            JSONObject jsonObj = (JSONObject) new JSONTokener(jsonString).nextValue();
                            visitor_id = jsonObj.getString("just_created_id");
                            sendEmail = email;
                            new SendMail().execute();
                            request = new HttpRequest(url);
                            request.addData("fullname", name2);
                            request.addData("email", email2);
                            request.addData("mode", "");
                            request.addData("mobile", mobile2 + "");
                            request.addData("userId", id + "");
                            request.setMethod("POST");
                            request.execute();

                            /******************************/
                            try {
                                jsonString = request.getResponse();
                                Log.d("JsonString", "jsonString: " + jsonString);

                                jsonObj = (JSONObject) new JSONTokener(jsonString).nextValue();
                                String visitor_id2 = jsonObj.getString("just_created_id");
                                sendEmail = email2;
                                visitor_id = visitor_id2;
                                new SendMail().execute();
                                request = new HttpRequest(url);
                                request.addData("fullname", name3);
                                request.addData("email", email3);
                                request.addData("mode", "");
                                request.addData("mobile", mobile3 + "");
                                request.addData("userId", id + "");
                                request.setMethod("POST");
                                request.execute();

                                /******************************/
                                try {
                                    jsonString = request.getResponse();
                                    Log.d("JsonString", "jsonString: " + jsonString);

                                    jsonObj = (JSONObject) new JSONTokener(jsonString).nextValue();
                                    String visitor_id3 = jsonObj.getString("just_created_id");
                                    sendEmail = email3;
                                    visitor_id = visitor_id3;
                                    new SendMail().execute();
                                    Toast.makeText(getBaseContext(), "Successfully saved visitors!", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                                    startActivity(i);
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "No network connection available.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private class SendMail extends AsyncTask<String, Void, Integer> {
        ProgressDialog pd = null;
        String error = null;
        Integer result;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(getBaseContext());
            pd.setTitle("Sending Mail");
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            // TODO Auto-generated method stub
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = null;
            try {
                bitMatrix = multiFormatWriter.encode(visitor_id, BarcodeFormat.QR_CODE, 200, 200);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            File file = new File(getApplicationContext().getExternalCacheDir(), "qrcode.png");
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.setReadable(true, false);
            GMailSender sender = new GMailSender("vms.fyp@gmail.com", "fyp12345");

            sender.setTo(new String[]{sendEmail});
            sender.setFrom("vms.fyp@gmail.com");
            sender.setSubject("QR Code");
            sender.setBody("Please show the QR Code attached to the Security Guard to scan when signing in and out. Thank you!");
            try {
                sender.addAttachment(file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (sender.send()) {
                    System.out.println("Message sent");
                    return 1;
                } else {
                    return 2;
                }
            } catch (Exception e) {
                error = e.getMessage();
                Log.e("SendMail", e.getMessage(), e);
            }

            return 3;
        }

        protected void onPostExecute(Integer result) {
            pd.dismiss();
            if (error != null) {
                Log.d("error", error);
            }
            if (result == 1) {
                Toast.makeText(getBaseContext(),
                        "Email was sent successfully.", Toast.LENGTH_LONG)
                        .show();
            } else if (result == 2) {
                Toast.makeText(getBaseContext(),
                        "Email was not sent.", Toast.LENGTH_LONG).show();
            } else if (result == 3) {
                Toast.makeText(getBaseContext(),
                        "There was a problem sending the email.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
