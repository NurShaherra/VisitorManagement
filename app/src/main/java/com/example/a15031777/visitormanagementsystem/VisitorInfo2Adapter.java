package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 15017199 on 8/8/2017.
 */

public class VisitorInfo2Adapter extends ArrayAdapter<Report> {
    private ArrayList<VisitorInfo2> infoList;
    private Context context;
    private TextView tvTitle;
    private ImageButton ib;

    public VisitorInfo2Adapter(Context context, int resource, ArrayList<VisitorInfo2> objects) {
        super(context, resource);
        infoList = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // "Inflate" the row.xml as the layout for the View object
        View rowView = inflater.inflate(R.layout.row_generate_report2, parent, false);

        // Get the ImageView object
        ib = (ImageButton) rowView.findViewById(R.id.ib);

        // Return the nicely done up View to the ListView

        return rowView;
    }
}

