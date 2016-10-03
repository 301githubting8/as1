package com.example.ting8.assign1_aaa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ting8 on 9/30/16.
 */
public class AllHabits {
    private ArrayList<Habit> habits = new ArrayList<Habit>();

    //An array list holding all the habits of today
    private ArrayList<Habit> todayHabits = new ArrayList<Habit>();

    //constructor whit a parameter habits
    public AllHabits(ArrayList<Habit> habits) {this.habits = habits;}

    public ArrayList<Habit> getHabits() {return habits;}

    public ArrayList<Habit> getTodayHabits() {
        Date today = new Date();
        todayHabits.clear();

        //get what day is it today
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        //if habit's plan has today, add it to the todayHabits
        for (Habit habit : habits){
            if (habit.getPlan().contains(dayOfWeek)){
                todayHabits.add(habit);
            }
        }
        return todayHabits;
    }

    public void deleteHabit(Habit habit){habits.remove(habit);}

    public void addHabit(Habit habit){habits.add(habit);}
}
