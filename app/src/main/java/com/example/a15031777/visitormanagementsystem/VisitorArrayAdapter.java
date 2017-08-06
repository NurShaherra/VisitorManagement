package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class VisitorArrayAdapter extends ArrayAdapter<Visitor> {
    Context context;
    ArrayList<Visitor> notes;
    int resource;
    ImageView iv1, iv2, iv3, iv4, iv5;
    TextView tv;
    TextView tvId;

    public VisitorArrayAdapter(Context context, int resource, ArrayList<Visitor> notes) {
        super(context, resource, notes);
        this.context = context;
        this.notes = notes;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(resource, parent, false);

        //Match the UI components with Java variables
        tvId = (TextView) rowView.findViewById(R.id.textViewId);
        tv = (TextView) rowView.findViewById(R.id.textViewName);
        Visitor note = notes.get(position);
        tv.setText("Visitor's full name: " + note.getFullname());
        tvId.setText("Visitor ID: " + note.getId());

        return rowView;
    }


}
