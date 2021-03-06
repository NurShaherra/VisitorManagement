package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 15031777 on 8/8/2017.
 */

public class VisitorAdapter extends ArrayAdapter<Visitor2> {

    private ArrayList<Visitor2> visitor;
    private Context context;
    private TextView fullName;
    private TextView email;
    private TextView mobile;


    public VisitorAdapter(Context context, int resource, ArrayList<Visitor2> objects){
        super(context, resource, objects);
        // Store the food that is passed to this adapter
        visitor = objects;
        // Store Context object as we would need to use it later
        this.context = context;
    }

    // getView() is the method ListView will call to get the
    //  View object every time ListView needs a row
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // The usual way to get the LayoutInflater object to
        //  "inflate" the XML file into a View object
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // "Inflate" the row.xml as the layout for the View object
        View rowView = inflater.inflate(R.layout.rowvisitor, parent, false);

        // Get the TextView object
        fullName = (TextView) rowView.findViewById(R.id.textViewFullName);
        email = (TextView) rowView.findViewById(R.id.textViewEmail);
        mobile = (TextView) rowView.findViewById(R.id.textViewMobile);


        // The parameter "position" is the index of the
        //  row ListView is requesting.
        //  We get back the food at the same index.
        Visitor2 currentFood = visitor.get(position);
        // Set the TextView to show the food

        fullName.setText(currentFood.getFullname());
        email.setText(currentFood.getEmail());
        mobile.setText(currentFood.getMobile());


        // Return the nicely done up View to the ListView
        return rowView;
    }
}


