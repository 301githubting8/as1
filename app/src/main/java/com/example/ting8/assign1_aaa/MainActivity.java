package com.example.ting8.assign1_aaa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity {

    public static final String FILENAME = "file.sav";
    private NewAdapter newAdapter;
    private AllHabits allHabits;
    private ArrayList<Habit> todayHabits;
    public static ArrayList<Habit> habits = new ArrayList<Habit>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFromFile();

    }

    @Override
    protected void onStart() {
        super.onStart();

        allHabits = new AllHabits(habits);
        todayHabits = allHabits.getTodayHabits();
        newAdapter = new NewAdapter(this,todayHabits);

        ListView todayHabitsList = (ListView) findViewById(R.id.listView3);
        todayHabitsList.setAdapter(newAdapter);

        //reach here when user click an item of the todayHabitsList
        todayHabitsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Habit currentHabit = allHabits.getTodayHabits().get(position);
                Date date = new Date();
                //create a new history with current date
                currentHabit.updateCount(date);
                //tell the adapter that the data has changed
                newAdapter.notifyDataSetChanged();
                saveInFile();
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        allHabits = new AllHabits(habits);
        //clear the todayHabits and get it again
        todayHabits.clear();
        todayHabits = allHabits.getTodayHabits();
        newAdapter.clear();
        newAdapter.addAll(todayHabits);
        //tell he adapter that the data has changed
        newAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    // Called when the user clicks the AddHabit button
    public void addHabit(View view) {
        Intent intent = new Intent(this, AddNewHabit.class);
        startActivity(intent);
    }

    // Called when the user clicks the AllHabits button
    public void allHabits(View view) {
        Intent intent = new Intent(this, ShowAllHabit.class);
        startActivity(intent);
    }

    private void loadFromFile() {
        try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			Gson gson = new Gson();
			//Code taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt Sept.22,2016
			Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();
			habits = gson.fromJson(in, listType);

            if (habits == null){
                habits = new ArrayList<Habit>();
            }
            //AllHabits allHabits = new AllHabits(AddNewHabit.habits);
            //for (Habit habit :habits) {
              //  habits.add(habit);
            //}
		} catch (IOException e) {
			//throw new RuntimeException();
		}

    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(MainActivity.FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(habits, writer);
            writer.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
