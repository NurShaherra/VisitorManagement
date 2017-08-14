package com.example.a15031777.visitormanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

/* DONE BY 15017484 */
public class ChooseAddActivity extends AppCompatActivity {
    Button btnNext;
    Spinner spn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_visitor);
        btnNext = (Button) findViewById(R.id.buttonNext);
        ImageButton imgB = (ImageButton) findViewById(R.id.imageButton);

        imgB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });
        spn = (Spinner) findViewById(R.id.spinnerAdd);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spn.setAdapter(adapter);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spn.getSelectedItem().toString().equalsIgnoreCase("1")){
                    Intent i = new Intent(ChooseAddActivity.this,AddVisitor.class);
                    startActivity(i);
                } else if (spn.getSelectedItem().toString().equalsIgnoreCase("2")){
                    Intent i = new Intent(ChooseAddActivity.this,Add2Visitor.class);
                    startActivity(i);
                } else if (spn.getSelectedItem().toString().equalsIgnoreCase("3")) {
                    Intent i = new Intent(ChooseAddActivity.this, Add3Visitor.class);
                    startActivity(i);
                } else if (spn.getSelectedItem().toString().equalsIgnoreCase("4")) {
                    Intent i = new Intent(ChooseAddActivity.this, Add4Visitor.class);
                    startActivity(i);
                } else if (spn.getSelectedItem().toString().equalsIgnoreCase("5")) {
                    Intent i = new Intent(ChooseAddActivity.this, Add5Visitor.class);
                    startActivity(i);
                }

            }
        });
    }

}

