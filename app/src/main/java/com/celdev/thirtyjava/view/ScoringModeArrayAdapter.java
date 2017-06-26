package com.celdev.thirtyjava.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.celdev.thirtyjava.model.scoring.ScoringMode;

public class ScoringModeArrayAdapter extends ArrayAdapter<ScoringMode> {


    @NonNull
    private final Context context;

    private ScoringMode[] values;

    public ScoringModeArrayAdapter(@NonNull Context context, @LayoutRes int resource, ScoringMode[] scoringModes) {
        super(context, resource);
        this.context = context;
        this.values = scoringModes;
    }


    public int getCount(){
        return values.length;
    }

    public ScoringMode getItem(int position){
        return values[position];
    }

    public long getItemId(int position){
        return position;
    }



    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        label.setText(values[position].getSpinnerNameStringId());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        label.setText(values[position].getSpinnerNameStringId());
        label.setPadding(5,2,0,2);
        return label;
    }



}
