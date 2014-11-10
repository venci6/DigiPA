package com.example.liz.digipa;

import android.app.Activity;
import android.os.Bundle;
import android.util.MonthDisplayHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.text.format.DateFormat;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class month extends Activity implements View.OnClickListener {
    private final String TAG = month.class.getSimpleName();

    private Calendar cal;
    private int month, year;
    private Button prevMonth, currMonth, nextMonth;
    private GridView calendarView;
    private ArrayList<Integer> days;
    private MonthDisplayHelper displayHelper;
//    private MonthDayAdapter adapter;
    private ArrayAdapter adapter;
    private final DateFormat dateFormatter = new DateFormat();
    private static final String dateTemplate = "MMMM yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);

        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);

        displayHelper = new MonthDisplayHelper(year, month);

        prevMonth = (Button) this.findViewById(R.id.previousMonth);
        prevMonth.setOnClickListener(this);

        currMonth = (Button) this.findViewById(R.id.currentMonth);
//        currMonth.setText(cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + " " + year);
        currMonth.setOnClickListener(this);

        nextMonth = (Button) this.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(this);

        calendarView = (GridView) findViewById(R.id.monthView);
        setDays();

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

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,days);
        calendarView.setAdapter(adapter);

        currMonth.setText((displayHelper.getMonth()+1)+ "/" + displayHelper.getYear());
    }

    public void onClick (View v) {

        if(v == prevMonth) {
            displayHelper.previousMonth();
        } else if(v == nextMonth) {
            displayHelper.nextMonth();
        }

        setDays();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.month, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
