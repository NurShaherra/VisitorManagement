package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by 15017199 on 12/8/2017.
 */

public class GenerateInfoAdapter extends ArrayAdapter<GenerateInfo> {
    private ArrayList<GenerateInfo> al;
    private Context context;
    private TextView tvTitle, tvId, tvFullName, tvEmail, tvMode, tvMobileNum, tvUserId;
    private ImageButton ib;

    public GenerateInfoAdapter(Context context, int resource, ArrayList<GenerateInfo> objects) {
        super(context, resource, objects);

        al = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // "Inflate" the row.xml as the layout for the View object
        View rowView = inflater.inflate(R.layout.row_generate_report2, parent, false);
        // Get the View object
        tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
        tvId = (TextView) rowView.findViewById(R.id.tvId);
        tvFullName = (TextView) rowView.findViewById(R.id.tvFullName);
        tvEmail = (TextView) rowView.findViewById(R.id.tvEmail);
        tvMode = (TextView) rowView.findViewById(R.id.tvMode);
        tvMobileNum = (TextView) rowView.findViewById(R.id.tvMobileNum);
        tvUserId = (TextView) rowView.findViewById(R.id.tvUserID);
        ib = (ImageButton) rowView.findViewById(R.id.imageButton);

        // Return the nicely done up View to the ListView

        GenerateInfo currentItem = al.get(position);
        // Set the TextView to show the food
        tvId.setText(currentItem.getVisitor_id());
        tvFullName.setText(currentItem.getFullName());
        tvEmail.setText(currentItem.getEmail());
        tvMode.setText(currentItem.getMode_of_transport());
        tvMobileNum.setText(currentItem.getMobile_number());
        tvUserId.setText(currentItem.getUser_id());

        return rowView;
    }
}

