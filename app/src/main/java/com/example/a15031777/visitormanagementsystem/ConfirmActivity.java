package com.example.a15031777.visitormanagementsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileOutputStream;

/* DONE BY 15017484 */
public class ConfirmActivity extends AppCompatActivity {
    TextView tvTitle, tvName, tvEmail, tvNum, tvArrived, tvLicense;
    Button btnConfirm;
    String email, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        Intent i = getIntent();
        final String id = i.getStringExtra("id");
        final String license = i.getStringExtra("license");
        final String arrivedBy = i.getStringExtra("by");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ConfirmActivity.this);
        String sign = pref.getString("sign", "");
        tvTitle = (TextView) findViewById(R.id.textViewName);
        tvName = (TextView) findViewById(R.id.textViewFullName);
        tvEmail = (TextView) findViewById(R.id.textViewEmail);
        tvNum = (TextView) findViewById(R.id.textViewNum);
        tvArrived = (TextView) findViewById(R.id.textViewMode);
        tvLicense = (TextView) findViewById(R.id.textViewLicense);
        btnConfirm = (Button) findViewById(R.id.buttonConfirm);
        String by = "";
        tvTitle.setText(sign);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/getVisitorById.php");
            request.addData("visitorId", id);
            request.setMethod("POST");
            request.execute();

            /******************************/
            try {
                String jsonString = request.getResponse();
                Log.d("JsonString", "jsonString: " + jsonString);
                JSONObject jsonObj = (JSONObject) new JSONTokener(jsonString).nextValue();
                tvName.setText(jsonObj.getString("full_name"));
                tvEmail.setText(jsonObj.getString("email_address"));
                tvNum.setText(jsonObj.getString("mobile_number"));
                JSONObject jObj = jsonObj.getJSONObject("user_email");
                email = jObj.getString("email_address");
                name = jsonObj.getString("full_name");
                Log.d("check", email);
                by = jsonObj.getString("mode_of_transport");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(ConfirmActivity.this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (sign.equalsIgnoreCase("Sign In")) {
            tvArrived.setText(arrivedBy);
            tvLicense.setText(license);
            btnConfirm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/signInVisitor.php");
                        request.addData("id", id);
                        request.addData("mode", arrivedBy);
                        request.setMethod("POST");
                        request.execute();

                        /******************************/
                        try {
                            String jsonString = request.getResponse();
                            Log.d("JsonString", "jsonString: " + jsonString);
                            JSONObject jsonObj = (JSONObject) new JSONTokener(jsonString).nextValue();
                            String message = jsonObj.getString("message");
                            new SendMail().execute();
                            Toast.makeText(ConfirmActivity.this, message, Toast.LENGTH_SHORT).show();
                            Intent back = new Intent(ConfirmActivity.this, MainActivity.class);
                            startActivity(back);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ConfirmActivity.this, "No network connection available.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else if (sign.equalsIgnoreCase("Sign Out")) {
            tvArrived.setText(by);
            tvLicense.setText("-");
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/signOutVisitor.php");
                        request.addData("id", id);
                        request.setMethod("POST");
                        request.execute();

                        /******************************/
                        try {
                            String jsonString = request.getResponse();
                            Log.d("JsonString", "jsonString: " + jsonString);
                            JSONObject jsonObj = (JSONObject) new JSONTokener(jsonString).nextValue();
                            String message = jsonObj.getString("message");
                            Toast.makeText(ConfirmActivity.this, message, Toast.LENGTH_SHORT).show();
                            Intent back = new Intent(ConfirmActivity.this, MainActivity.class);
                            startActivity(back);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ConfirmActivity.this, "No network connection available.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else if (sign.equalsIgnoreCase("Display Visitors")){
            tvArrived.setText(by);
            tvLicense.setText("-");
            btnConfirm.setText("Resend QRCode");
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    BitMatrix bitMatrix = null;
                    try {
                        bitMatrix = multiFormatWriter.encode(id, BarcodeFormat.QR_CODE, 200, 200);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    shareBitmap(bitmap, "qrcode", email);
                    finish();

                }
            });
        }


    }

    private void shareBitmap(Bitmap bitmap, String fileName, String email) {
        try {
            File file = new File(getApplicationContext().getExternalCacheDir(), fileName + ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            String aEmailList[] = {email};
            intent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "QR Code");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, "Please show the QR Code attached to the Security Guard to scan when signing in and out. Thank you!");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class SendMail extends AsyncTask<String, Void, Integer> {
        ProgressDialog pd = null;
        String error = null;
        Integer result;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(ConfirmActivity.this);
            pd.setTitle("Sending Mail");
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            // TODO Auto-generated method stub

            GMailSender sender = new GMailSender("vms.fyp@gmail.com", "fyp12345");

            sender.setTo(new String[]{email});
            sender.setFrom("vms.fyp@gmail.com");
            sender.setSubject("A visitor signed in.");
            sender.setBody("Your visitor, " + name + ", has just signed in!");
            sender.setBody("Your visitor, " + name + ", has just signed in!");
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
                Toast.makeText(ConfirmActivity.this,
                        "Email was sent successfully.", Toast.LENGTH_LONG)
                        .show();
            } else if (result == 2) {
                Toast.makeText(ConfirmActivity.this,
                        "Email was not sent.", Toast.LENGTH_LONG).show();
            } else if (result == 3) {
                Toast.makeText(ConfirmActivity.this,
                        "There was a problem sending the email.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
