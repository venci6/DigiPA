package com.example.liz.digipa;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static java.security.AccessController.getContext;


public class Daily extends Activity {
    private final String TAG = month.class.getSimpleName();

    TextView dateHeading;
    String dateChosen;
    DPADataHandler handler;
    ArrayList<Tasks> taskArr = new ArrayList<Tasks>();
    ArrayList<Events> eventArr = new ArrayList<Events>();
    ExpandableListView tasksScroll;
    ExpandableListView eventsScroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        Bundle extras;

        // Passed string of Date to view
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if(extras == null) {
                dateChosen= null;
            } else {
                dateChosen= extras.getString("DAY_TO_VIEW");
            }
        } else {
            dateChosen= (String) savedInstanceState.getSerializable("DAY_TO_VIEW");
        }


        dateHeading = (TextView)findViewById(R.id.date);
        dateHeading.setText(DPAHelperMethods.niceDateFormat(dateChosen));

        if(instantiateEvents(dateChosen)){
            tasksScroll=(ExpandableListView)findViewById(R.id.task_scroll);
            eventsScroll=(ExpandableListView)findViewById(R.id.event_scroll);
            myELVAdapter taskAdapter = new myELVAdapter(this);

            for(int i = 0; i < taskArr.size(); i++){
                Tasks task = taskArr.get(i);

                taskAdapter.parentArr[i] = task.getTitle();

                taskAdapter.childrenArr[i][0] = task.getDescription();
                taskAdapter.childrenArr[i][1] = task.getDueDate();
                taskAdapter.childrenArr[i][2] = task.getCategory();
            }

            myELVAdapter eventAdapter = new myELVAdapter(this);

            Log.v("Daily", "eventArr lenght " + eventArr.size());

            for(int i = 0; i < eventArr.size(); i++){
                Events event = eventArr.get(i);
                eventAdapter.parentArr[i] = event.getTitle();
                eventAdapter.childrenArr[i][0] = event.getDescription();
                eventAdapter.childrenArr[i][1] = event.getStartDate();
                eventAdapter.childrenArr[i][2] = event.getStartTime();
                eventAdapter.childrenArr[i][3] = event.getEndDate();
                eventAdapter.childrenArr[i][4] = event.getEndTime();
                eventAdapter.childrenArr[i][5] = event.getLocation();
                eventAdapter.childrenArr[i][6] = event.getCategory();
                eventAdapter.childrenArr[i][7] = "" + event.getPriority();

            }
            tasksScroll.setAdapter(taskAdapter);
            eventsScroll.setAdapter(eventAdapter);

        }


    }

 /*   public void setTaskScroll(String title){
        LinearLayout LL = (LinearLayout)findViewById(R.id.task_scroll);
        TextView newTV = new TextView(this);
        newTV.setText(title);
        newTV.setTextSize(30);

        LL.addView(newTV);

        newTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewTaskDetails = new Intent(Daily.this, xxx.class);
                startActivity(viewTaskDetails);
            }
        });
    }

    public void setEventScroll(String startTime, String title){
        LinearLayout LL = (LinearLayout)findViewById(R.id.event_scroll);
        TextView timeTxt = new TextView(this);
        timeTxt.setText(startTime);
        timeTxt.setTextSize(30);
        timeTxt.setWidth(100);

        TextView titleTxt = new TextView(this);
        titleTxt.setText(title);
        titleTxt.isClickable();
        titleTxt.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        titleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewEventDetails = new Intent(Daily.this, xxx.class);
                startActivity(viewEventDetails);
            }
        });
        LL.addView(timeTxt);
        LL.addView(titleTxt);
    }
*/
    public boolean instantiateEvents(String date){
        handler = new DPADataHandler(getApplicationContext());
        handler.open();
        Cursor taskCursor = handler.returnTasks(date);
        Cursor eventCursor = handler.returnEvents(date);
        int numberOfTasks = taskCursor.getCount();
        int taskTitleIndex = taskCursor.getColumnIndex(DigiPAContract.COLUMN_NAME_TITLE);
        int index = 0;


        if(numberOfTasks < 1){
            TextView noTaskTxt = (TextView)findViewById(R.id.no_task);
            noTaskTxt.setText("No tasks for today");
        }else {
            taskCursor.moveToFirst();
            do{
                String title = taskCursor.getString(taskTitleIndex);
                String desc = taskCursor.getString(taskTitleIndex + 1);
                String dueDate = taskCursor.getString(taskTitleIndex + 2);
                String category = taskCursor.getString(taskTitleIndex + 3);
                int priority = taskCursor.getInt(taskTitleIndex + 4);
                int complete = taskCursor.getInt(taskTitleIndex + 5);

                Tasks task = new Tasks(title,desc,dueDate,category,priority,complete);
                taskArr.add(task);

              //  setTaskScroll(title);

                index++;
            }while(taskCursor.moveToNext());
        }

        index = 0;
        int numberOfEvents = eventCursor.getCount();
        Log.v(TAG, "Number of events: " + numberOfEvents);
        int eventTitleIndex = eventCursor.getColumnIndex(DigiPAContract.COLUMN_NAME_TITLE);
        if (numberOfEvents < 1){
            TextView noEventTxt = (TextView)findViewById(R.id.no_event);
                noEventTxt.setText("No events for today");
        } else {
            eventCursor.moveToFirst();
            do{
                String title = eventCursor.getString(eventTitleIndex);
                String desc = eventCursor.getString(eventTitleIndex + 1);
                String startDate = eventCursor.getString(eventTitleIndex + 2);
                String startTime = eventCursor.getString(eventTitleIndex + 3);
                String endDate = eventCursor.getString(eventTitleIndex + 4);
                String endTime = eventCursor.getString(eventTitleIndex + 5);
                String location = eventCursor.getString(eventTitleIndex + 6);
                String category = eventCursor.getString(eventTitleIndex + 7);
                int priority = eventCursor.getInt(eventTitleIndex + 8);

                Log.v(TAG, "Grabbed event: " + title + " " + desc + " " + startDate + " " + startTime + " " + endDate + " " + endTime + " " + location + " " + category + " " + priority);
                Events event = new Events(title, desc, startDate, startTime, endDate, endTime, location, category, priority);

           //     setEventScroll(startTime, title);

                //Events getEvent = eventArr[index];
                eventArr.add(event);

                index++;
            }while(eventCursor.moveToNext());
        }
        handler.close();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
}
