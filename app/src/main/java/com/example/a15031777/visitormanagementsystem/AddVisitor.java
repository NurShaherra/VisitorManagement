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
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
public class AddVisitor extends AppCompatActivity {
    EditText etName, etEmail, etMobile;
    Button btnSave;
    String visitor_id, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visitor);
        ImageButton imgB = (ImageButton) findViewById(R.id.imageButton);

        imgB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });
        etName = (EditText) findViewById(R.id.editTextusername);
        etEmail = (EditText) findViewById(R.id.editTextEmail);
        etMobile = (EditText) findViewById(R.id.editTextUnit);
        btnSave = (Button) findViewById(R.id.buttonSave);
        Intent i = getIntent();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        final int id = pref.getInt("isLoggedIn", -1);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //incomplete as send email and generate qr code is in the works.
                String name = etName.getText().toString();
                email = etEmail.getText().toString();
                int mobile = Integer.parseInt(etMobile.getText().toString());
                if (name.equalsIgnoreCase("") || email.equalsIgnoreCase("") || etMobile.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getBaseContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    //Idk what to put so just fuck it all and dash because what the fuck is this even.
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        HttpRequest request = new HttpRequest("https://pyramidal-drift.000webhostapp.com/addVisitor.php");
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
                            new SendMail().execute();
                            Toast.makeText(getBaseContext(), "Successfully saved visitor!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getBaseContext(),MainActivity.class);
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(AddVisitor.this, "No network connection available.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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

            sender.setTo(new String[]{email});
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
