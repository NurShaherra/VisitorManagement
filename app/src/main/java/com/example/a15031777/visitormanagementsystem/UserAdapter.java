package com.example.a15031777.visitormanagementsystem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 15031777 on 7/8/2017.
 */

public class UserAdapter extends ArrayAdapter<User> {

    private ArrayList<User> user;
    private Context context;
    private TextView fullName;
    private TextView username;

    public UserAdapter(Context context, int resource, ArrayList<User> objects){
        super(context, resource, objects);
        // Store the food that is passed to this adapter
        user = objects;
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
        View rowView = inflater.inflate(R.layout.rowuser, parent, false);

        // Get the TextView object
        fullName = (TextView) rowView.findViewById(R.id.textViewFullName);
        username = (TextView) rowView.findViewById(R.id.textViewUsername);

        // The parameter "position" is the index of the
        //  row ListView is requesting.
        //  We get back the food at the same index.
        User currentFood = user.get(position);
        // Set the TextView to show the food

        fullName.setText(currentFood.getFullname());
        username.setText(currentFood.getUsername());

        // Return the nicely done up View to the ListView
        return rowView;
    }
}
