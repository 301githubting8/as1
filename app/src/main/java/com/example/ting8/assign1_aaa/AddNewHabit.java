package com.example.ting8.assign1_aaa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddNewHabit extends AppCompatActivity {

    private CheckBox MondayBox;
    private CheckBox TuesdayBox;
    private CheckBox WednesdayBox;
    private CheckBox ThursdayBox;
    private CheckBox FridayBox;
    private CheckBox SaturdayBox;
    private CheckBox SundayBox;
    private EditText userName;
    private EditText userDate;
    private Button finishButton;
    //public static ArrayList<Habit> habits = new ArrayList<Habit>();
    private Date habitDate = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_habit);
    }

    @Override
    protected void onStart() {
        super.onStart();

        userName = (EditText) findViewById(R.id.editText);
        finishButton = (Button) findViewById(R.id.button3);
        MondayBox = (CheckBox) findViewById(R.id.checkBox);
        TuesdayBox = (CheckBox) findViewById(R.id.checkBox2);
        WednesdayBox = (CheckBox) findViewById(R.id.checkBox3);
        ThursdayBox = (CheckBox) findViewById(R.id.checkBox4);
        FridayBox = (CheckBox) findViewById(R.id.checkBox5);
        SaturdayBox = (CheckBox) findViewById(R.id.checkBox6);
        SundayBox = (CheckBox) findViewById(R.id.checkBox7);
        userDate = (EditText) findViewById(R.id.editText2);

        //make current date as default, and use it to give the user an input format hint
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(habitDate);
        userDate.setHint(dateString);


        //reach here when user click the finish button
        finishButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String name = userName.getText().toString();
                ArrayList<Integer> plan = new ArrayList<Integer>();
                //add the day(s) of week of checked checkbox(es) to the plan of habit
                if (MondayBox.isChecked()) {
                    plan.add(2);
                }
                if (TuesdayBox.isChecked()) {
                    plan.add(3);
                }
                if (WednesdayBox.isChecked()) {
                    plan.add(4);
                }
                if (ThursdayBox.isChecked()) {
                    plan.add(5);
                }
                if (FridayBox.isChecked()) {
                    plan.add(6);
                }
                if (SaturdayBox.isChecked()) {
                    plan.add(7);
                }
                if (SundayBox.isChecked()) {
                    plan.add(1);
                }
                //make a new habit with date, name and plan, then add to the allHabits
                Habit habit = new Habit(new Date(), name, plan);
                AllHabits allHabits = new AllHabits(MainActivity.habits);
                allHabits.addHabit(habit);

                //save the changes ino the file
                saveInFile();

                //destroy this page, return to last page
                finish();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_habit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(MainActivity.FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(MainActivity.habits, writer);
            writer.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
