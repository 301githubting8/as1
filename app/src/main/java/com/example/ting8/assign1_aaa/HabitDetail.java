package com.example.ting8.assign1_aaa;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class HabitDetail extends AppCompatActivity {
    int HabitId;
    Habit currentHabit = null;
    AllHabits allHabits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);

        Button deleteHabitButton = (Button) findViewById(R.id.button4);
        Button deleteHistoryButton = (Button) findViewById(R.id.button5);
        TextView totalCount = (TextView) findViewById(R.id.textView13);
        TextView currentHabitName = (TextView) findViewById(R.id.textView14);

        //get HabitId from ShowAllHabit activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            HabitId = bundle.getInt("HabitId");
        }
        allHabits = new AllHabits(MainActivity.habits);
        //currentHabit means the habit that user clicked
        currentHabit = MainActivity.habits.get(HabitId);

        //print the total count of the currentHabit up to now
        totalCount.setText(currentHabit.getTotalCount().toString() + "   ");
        //print the currentHabit's name
        currentHabitName.setText(currentHabit.getName().toString());


        // Called when the user clicks the deleteThewholeHabit button
        deleteHabitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                allHabits.deleteHabit(currentHabit);
                saveInFile();
                finish();

            }
        });

        // Called when the user clicks the deleteAllHistory button
        deleteHistoryButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                currentHabit.deletePastResult();
                saveInFile();
                finish();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_habit_detail, menu);
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
