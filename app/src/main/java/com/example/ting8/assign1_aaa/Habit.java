package com.example.ting8.assign1_aaa;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ting8 on 9/30/16.
 */
public class Habit {
    private Date date;
    private String name;

    //the index of day in a weak that user wants to do the habit
    private ArrayList<Integer> plan;
    private ArrayList<History> histories = new ArrayList<History>();

    public Date getDate() {return date;}

    public Habit(Date date, String name, ArrayList<Integer> plan) {
        this.date = date;
        this.name = name;
        this.plan = plan;
    }

    public ArrayList<History> getHistories(){return histories;}

    public String getName() {return name;}

    public Integer getTotalCount(){return histories.size();}

    //create a new history with current date
    public void updateCount(Date date){
        History history = new History(date);
        histories.add(history);
    }

    public ArrayList<Integer> getPlan() {return plan;}

    //clear all past history
    public void deletePastResult(){this.histories = new ArrayList<History>();}

    @Override
    public String toString(){return this.name;}

}
