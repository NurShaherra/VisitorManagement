package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import java.io.FileOutputStream;

/* DONE BY 15017484 */
public class AddVisitor extends AppCompatActivity {
    EditText etName, etEmail, etMobile;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visitor);
        etName = (EditText) findViewById(R.id.editTextfullname);
        etEmail = (EditText) findViewById(R.id.editTextEmail);
        etMobile = (EditText) findViewById(R.id.editTextMobile);
        btnSave = (Button) findViewById(R.id.buttonSave);
        Intent i = getIntent();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        final int id = pref.getInt("isLoggedIn", -1);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //incomplete as send email and generate qr code is in the works.
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
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
                            String visitor_id = jsonObj.getString("just_created_id");
                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                            try {
                                BitMatrix bitMatrix = multiFormatWriter.encode(visitor_id, BarcodeFormat.QR_CODE, 200, 200);
                                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                shareBitmap(bitmap, "qrcode", email);
                            } catch (WriterException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(getBaseContext(), "Successfully saved visitor!", Toast.LENGTH_SHORT).show();
                            finish();
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
}
