package com.example.liz.digipa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.MonthDisplayHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class month extends Activity implements View.OnClickListener {
    private final String TAG = month.class.getSimpleName();
    private TextView welcome;
    private Calendar cal;
    private int year;
    private int month;
    private Button prevMonth, currMonth, nextMonth, addEvent, addTask;
    private GridView calendarView;
    private ArrayList<Integer> days;
    private MonthDisplayHelper displayHelper;
    private ArrayAdapter adapter;
    public String user;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);

        welcome = (TextView)findViewById(R.id.welcome);
        sharedPreferences = getSharedPreferences(Login.MYPREFERENCES, Context.MODE_PRIVATE);
        user = sharedPreferences.getString("nameKey", "");
        if(!user.equalsIgnoreCase("")) {
            welcome.setText("Welcome, " + user);
        }


        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        initializeButtons();

        displayHelper = new MonthDisplayHelper(year, month);
        calendarView = (GridView) findViewById(R.id.monthView);
        setDays();
        calendarView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // MM/YYYY
                String currMonthText = "" + currMonth.getText();
                String[] dateExploded = currMonthText.split(" ");

                String monthText = dateExploded[0];
                int tempMonth = DPAHelperMethods.numericMonth(monthText);
                int tempYear = Integer.parseInt(dateExploded[1]);

                String dayTemp = "" + parent.getItemAtPosition(position);
                int day = Integer.parseInt(dayTemp);

                if (position < 7 && day > 7) {
                    tempMonth--;
                    if(tempMonth == 0) {
                        tempMonth = 12;
                        tempYear--;
                    }
                } else if (position > 28 && day < 15) {
                    tempMonth++;
                    if(tempMonth == 13) {
                        tempMonth = 1;
                        tempYear++;
                    }
                }

                // YYYYMMDD
                String dateClicked = tempYear + DPAHelperMethods.addLeadingZero(tempMonth) + DPAHelperMethods.addLeadingZero(day);

                Log.v(TAG, "prompt: " + dateClicked + " at pos " + position);

                Intent dailyView = new Intent(month.this, Daily.class);
                dailyView.putExtra("DAY_TO_VIEW", dateClicked);
                startActivity(dailyView);
            }
        });
    }




    private void initializeButtons() {
        prevMonth = (Button) this.findViewById(R.id.previousMonth);
        currMonth = (Button) this.findViewById(R.id.currentMonth);
        nextMonth = (Button) this.findViewById(R.id.nextMonth);
        addEvent = (Button) this.findViewById(R.id.addEvent);
        addTask = (Button) this.findViewById(R.id.addTask);



        prevMonth.setOnClickListener(this);
        currMonth.setOnClickListener(this);
        nextMonth.setOnClickListener(this);
        addEvent.setOnClickListener(this);
        addTask.setOnClickListener(this);
    }
    private void setDays() {
        days = new ArrayList<Integer>();

        int startMonthColumn = displayHelper.getColumnOf(1);
        int totalDays = displayHelper.getNumberOfDaysInMonth();

        // need to get last week or so of the previous month
        // 7 - startMonthColumn

        displayHelper.previousMonth();
        int prevTotalDays = displayHelper.getNumberOfDaysInMonth();
        int prevStartDate;
        if(startMonthColumn == 0) {
            prevStartDate = prevTotalDays - 6;
        } else {
            prevStartDate = prevTotalDays - (startMonthColumn-1);
        }

        // add
        while(prevStartDate <= prevTotalDays) {
            days.add(prevStartDate);
            prevStartDate++;
        }

        // add all the days of the month
        for(int i = 1; i <= totalDays; i++) {
            days.add(i);
        }

        displayHelper.nextMonth();

        // add first couple days of the next month...
        int lastColumn = 1 + displayHelper.getColumnOf(totalDays);
        int lastRow = 1 + displayHelper.getRowOf(totalDays);

        if(startMonthColumn == 0) {
            lastRow++;
        }

        int currCellsOccupied;
        if(lastColumn != 7) {
            currCellsOccupied = lastColumn + (7 * (lastRow-1));
        } else {
            currCellsOccupied = lastColumn * lastRow;
        }
        int numCellsEmpty = (6*7) - currCellsOccupied;

        for(int j = 1; j<=numCellsEmpty; j++) {
            days.add(j);
        }

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, days);
        calendarView.setAdapter(adapter);

        currMonth.setText((DPAHelperMethods.months[displayHelper.getMonth()]) + " " + displayHelper.getYear());

    }


    @Override
    public void onClick (View v) {

        switch(v.getId()) {
            case R.id.previousMonth:
                displayHelper.previousMonth();
                setDays();
                Log.v(TAG, "DECREMENT. dH is now " + displayHelper.getYear() + displayHelper.getMonth());
                break;
            case R.id.nextMonth:
                displayHelper.nextMonth();
                setDays();
                Log.v(TAG, "INCREMENT. dH is now " + displayHelper.getYear() + displayHelper.getMonth());
                break;
            case R.id.currentMonth:
                Log.v(TAG, "Clicked on Current MONTH! Will want to let the user easily choose a different month, methinks?");
                break;
            case R.id.addEvent:
                Log.v(TAG, "Clicked on Add Event");
                Intent createEvent = new Intent(month.this, CreateEvent.class);
                startActivity(createEvent);
                break;
            case R.id.addTask:
                Intent createTask = new Intent(month.this, CreateTask.class);
                startActivity(createTask);
                break;

            default:
                break;
        }
        if(v == prevMonth) {
        } else if(v == nextMonth) {
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_settings:

                Intent goSettings = new Intent(month.this, Settings.class);
                startActivity(goSettings);
                break;
            case R.id.action_sign_out:
                SharedPreferences sharedPreferences = getSharedPreferences(Login.MYPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent logOut = new Intent(month.this, Login.class);
                startActivity(logOut);
                month.this.finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
