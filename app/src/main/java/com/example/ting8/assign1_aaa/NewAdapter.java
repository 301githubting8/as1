package com.example.ting8.assign1_aaa;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ting8 on 9/30/16.
 */

// Code Reference: https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView

public class NewAdapter extends ArrayAdapter<Habit> {
    public NewAdapter(Context context, ArrayList<Habit> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int todayCount = 0;
        Habit habit = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.maintodayhabits, parent, false);
        }
        TextView HabitName = (TextView) convertView.findViewById(R.id.habitName);
        TextView HabitCount = (TextView) convertView.findViewById(R.id.habitCount);
        HabitName.setText(habit.toString());

        // Code Reference: http://stackoverflow.com/questions/2517709/comparing-two-java-util-dates-to-see-if-they-are-in-the-same-day
        Date today = new Date();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(today);
        Calendar calendar2 = Calendar.getInstance();
        for(History history: habit.getHistories()){
            calendar2.setTime(history.getDate());
            if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                    calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)){
                todayCount ++;
            }
        }
        //if he user has finish the habit, change it to green and make it bigger
        if (todayCount!=0){
            HabitName.setTextColor(Color.GREEN);
            HabitName.setTextSize(25);
        }
        //print the totalCount to the screen
        HabitCount.setText(String.valueOf(todayCount));

        return convertView;
    }
}