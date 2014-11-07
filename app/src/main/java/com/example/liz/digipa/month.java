package com.example.liz.digipa;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.GridView;

import java.util.Calendar;

public class month extends Activity implements View.OnClickListener {

    private Calendar cal;
    private int month, year;
    private Button prevMonth, currMonth, nextMonth;
    private GridView calendarView;
    private MonthDayAdapter adapter;
    private final DateFormat dateFormatter = new DateFormat();
    private static final String dateTemplate = "MMMM yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);

        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);

        prevMonth = (Button) this.findViewById(R.id.previousMonth);
        prevMonth.setOnClickListener(this);

        currMonth = (Button) this.findViewById(R.id.currentMonth);
        currMonth.setText(month + " " + year);
        currMonth.setOnClickListener(this);

        nextMonth = (Button) this.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(this);

        calendarView = (GridView) findViewById(R.id.monthView);
        //adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month, year);
        adapter = new MonthDayAdapter(this,cal);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);

//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                Toast.makeText(HelloGridView.this, "" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    public void onClick (View v) {

        if(v == prevMonth) {
            if (month <= 1) {
                month = 11;
                year--;
            } else {
                month--;
            }
        } else if(v == nextMonth) {
            if (month >= 11) {
                month = 0;
                year++;
            } else {
                month++;
            }
        }

        adapter = new MonthDayAdapter(getApplicationContext(), R.id.calendar_day, month, year);
        cal.set(year, month, cal.get(Calendar.DAY_OF_MONTH));
        currMonth.setText(dateFormatter.format(dateTemplate, cal.getTime()));

        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
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
