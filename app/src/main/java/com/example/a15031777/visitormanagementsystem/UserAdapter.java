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
 * Created by 15031777 on 18/7/2017.
 */

public class UserAdapter extends ArrayAdapter<User> {
    Context context;
    int layoutResourceId;
    ArrayList<User> personList = null;


    public UserAdapter(Context context, int resource, ArrayList<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.personList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // The usual way to get the LayoutInflater object to
        //  "inflate" the XML file into a View object
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // "Inflate" the row.xml as the layout for the View object
        View rowView = inflater.inflate(R.layout.rowuser, parent, false);

        // Get the TextView object
        TextView tvUserName = (TextView) rowView.findViewById(R.id.tvUsername);
        TextView tvRole = (TextView) rowView.findViewById(R.id.tvRole);

        // The parameter "position" is the index of the
        //  row ListView is requesting.
        //  We get back the food at the same index.
        User currentUser = personList.get(position);
        // Set the TextView to show the food

        tvUserName.setText(currentUser.getUsername());
        tvRole.setText(currentUser.getUserRole());

        // Return the nicely done up View to the ListView
        return rowView;
    }
}
