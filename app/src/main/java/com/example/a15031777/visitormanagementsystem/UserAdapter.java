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

    Context context;
    int layoutResourceId;
    ArrayList<User> userList = null;


    public UserAdapter(Context context, int resource, ArrayList<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.userList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PersonHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PersonHolder();
            holder.fullName = (TextView)row.findViewById(R.id.textViewFullName);
            holder.username = (TextView)row.findViewById(R.id.textViewUsername);

            row.setTag(holder);
        }
        else
        {
            holder = (PersonHolder)row.getTag();
        }

        User person = userList.get(position);
        holder.fullName.setText(person.getFullname());
        holder.username.setText(person.getUsername());
        return row;
    }

    static class PersonHolder
    {
        TextView fullName;
        TextView username;
    }




}