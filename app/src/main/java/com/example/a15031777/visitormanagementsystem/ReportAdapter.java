package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 15017199 on 7/8/2017.
 */

public class ReportAdapter extends ArrayAdapter<Report> {
    private ArrayList<Report> al;
    private Context context;
    private TextView tvId, tvName, tvNumSignedIn, tvCreatedDate;


    public ReportAdapter(Context context, int resource, ArrayList<Report> objects) {
        super(context, resource, objects);

        al = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // "Inflate" the row.xml as the layout for the View object
        View rowView = inflater.inflate(R.layout.row, parent, false);

        // Get the View object
        tvId = (TextView) rowView.findViewById(R.id.textViewId);
        tvName = (TextView) rowView.findViewById(R.id.textViewNum);
        tvNumSignedIn = (TextView) rowView.findViewById(R.id.textViewNumSignedIn);
        tvCreatedDate = (TextView) rowView.findViewById(R.id.textViewCreatedDate);

        // Return the nicely done up View to the ListView

        Report currentItem = al.get(position);
        // Set the TextView to show the food

        tvId.setText(currentItem.getReportId());
        tvName.setText(currentItem.getManagerName());
        tvNumSignedIn.setText(currentItem.getNumPeopleSignedIn());
        tvCreatedDate.setText(currentItem.getCreatedDate());

        return rowView;
    }
}

