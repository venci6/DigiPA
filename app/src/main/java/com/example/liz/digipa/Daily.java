package com.example.liz.digipa;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

import static java.security.AccessController.getContext;


public class Daily extends Activity {

    TextView dateHeading;
    String dateChosen;
    DPADataHandler handler;
    Tasks[] taskArr = new Tasks[15];
    Events[] eventArr = new Events[15];
    ExpandableListView tasksScroll;
    ExpandableListView eventsScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        Bundle extras;


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
        dateHeading.setText(dateChosen);

        if(instantiateEvents(dateChosen)){
            tasksScroll=(ExpandableListView)findViewById(R.id.task_scroll);
            eventsScroll=(ExpandableListView)findViewById(R.id.event_scroll);
            myELVAdapter taskAdapter = new myELVAdapter(this);
            for(int i = 0; i < taskArr.length; i++){
                int attrIndex = 0;
                taskAdapter.parentArr[i] = taskArr[i].getTitle();
                while (attrIndex < 0){
                    taskAdapter.childrenArr[i][attrIndex] = taskArr[i].getDescription();
                    taskAdapter.childrenArr[i][attrIndex] = taskArr[i].getDueDate();
                    taskAdapter.childrenArr[i][attrIndex] = taskArr[i].getCategory();
                }
            }

            myELVAdapter eventAdapter = new myELVAdapter(this);
            for(int i = 0; i < eventArr.length; i++){
                int attrIndex = 0;
                taskAdapter.parentArr[i] = taskArr[i].getTitle();
                while (attrIndex < 0) {
                    taskAdapter.childrenArr[i][attrIndex] = eventArr[i].getDescription();
                    taskAdapter.childrenArr[i][attrIndex] = eventArr[i].getStartDate();
                    taskAdapter.childrenArr[i][attrIndex] = eventArr[i].getStartTime();
                    taskAdapter.childrenArr[i][attrIndex] = eventArr[i].getEndDate();
                    taskAdapter.childrenArr[i][attrIndex] = eventArr[i].getEndTime();
                    taskAdapter.childrenArr[i][attrIndex] = eventArr[i].getLocation();
                    taskAdapter.childrenArr[i][attrIndex] = eventArr[i].getCategory();
                }
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
              //  setTaskScroll(title);
                Tasks getTask = taskArr[index];
                getTask.setTitle(title);
                getTask.setDescription(taskCursor.getString(taskTitleIndex + 1));
                getTask.setDueDate(taskCursor.getString(taskTitleIndex + 2));
                getTask.setCategory(taskCursor.getString(taskTitleIndex + 3));
                getTask.setPriority(taskCursor.getInt(taskTitleIndex + 4));
                getTask.setComplete(taskCursor.getInt(taskTitleIndex + 5));

                index++;
            }while(taskCursor.moveToNext());
        }

        index = 0;
        int numberOfEvents = eventCursor.getCount();
        int eventTitleIndex = eventCursor.getColumnIndex(DigiPAContract.COLUMN_NAME_TITLE);
        if (numberOfEvents < 1){
            TextView noEventTxt = (TextView)findViewById(R.id.no_event);
                noEventTxt.setText("No events for today");
        } else {
            eventCursor.moveToFirst();
            do{
                String title = eventCursor.getString(eventTitleIndex);
                String startTime = eventCursor.getString(eventTitleIndex + 3);
           //     setEventScroll(startTime, title);

                Events getEvent = eventArr[index];
                getEvent.setTitle(title);
                getEvent.setDescription(eventCursor.getString(eventTitleIndex + 1));
                getEvent.setStartDate(eventCursor.getString(eventTitleIndex + 2));
                getEvent.setStartTime(startTime);
                getEvent.setEndDate(eventCursor.getString(eventTitleIndex + 4));
                getEvent.setEndTime(eventCursor.getString(eventTitleIndex + 5));
                getEvent.setLocation(eventCursor.getString(eventTitleIndex + 6));
                getEvent.setCategory(eventCursor.getString(eventTitleIndex + 7));
                getEvent.setPriority(eventCursor.getInt(eventTitleIndex + 8));

                index++;
            }while(eventCursor.moveToNext());
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.daily, menu);
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
