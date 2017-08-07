package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 15017199 on 7/8/2017.
 */

public class ReportAdapter extends ArrayAdapter<Report> {
    private ArrayList<Report> reportList;
    private Context context;
    private TextView tvEReport;
    private ImageButton ibEReport;

    public ReportAdapter(Context context, int resource, ArrayList<Report> objects) {
        super(context, resource, objects);
        reportList = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // The usual way to get the LayoutInflater object to
        //  "inflate" the XML file into a View object
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // "Inflate" the row.xml as the layout for the View object
        View rowView = inflater.inflate(R.layout.row, parent, false);

        // Get the TextView object
      //  tvEReport = (TextView) rowView.findViewById(R.id.textViewEReport);
        // Get the ImageView object
        ibEReport = (ImageButton) rowView.findViewById(R.id.imageButtonEReport);

        //Report currentReport = reportList.get(position);

        // Return the nicely done up View to the ListView

        return rowView;
    }
}

