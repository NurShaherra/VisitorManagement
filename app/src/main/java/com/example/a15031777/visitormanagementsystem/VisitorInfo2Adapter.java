package com.example.a15031777.visitormanagementsystem;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by 15017199 on 8/8/2017.
 */

public class VisitorInfo2Adapter extends ArrayAdapter<Report> {

    public VisitorInfo2Adapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }
}
