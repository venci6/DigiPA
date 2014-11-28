package com.example.liz.digipa;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.security.AccessController.getContext;


public class Daily extends Activity {
    private final String TAG = month.class.getSimpleName();

    TextView dateHeading;
    String dateChosen;
    DPADataHandler handler;
    ArrayList<Tasks> taskArr = new ArrayList<Tasks>();
    ArrayList<Events> eventArr = new ArrayList<Events>();

    ExpandableListView tasksScroll;
    List<String> taskTitles = new ArrayList<String>();
    HashMap<String, List<String>> taskDetails= new HashMap<String, List<String>>();
    ExpandableListView eventsScroll;
    List<String> eventTitles = new ArrayList<String>();
    HashMap<String, List<String>> eventDetails= new HashMap<String, List<String>>();


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
            /*  =========================================================================
                                    TASKS
                ========================================================================= */
            tasksScroll=(ExpandableListView)findViewById(R.id.task_scroll);

            Log.v(TAG, "NUMBER OF TASKS" + taskArr.size());

            for(int i = 0; i < taskArr.size(); i++){
                List<String> taskData = new ArrayList<String>();
                Tasks task = taskArr.get(i);

                taskData.add(task.getTitle());
                taskData.add(task.getDescription());
                taskData.add(task.getDueDate());
                taskData.add(task.getCategory());
                taskData.add("" + task.getPriority());
                taskTitles.add("" + task.getId());

                taskDetails.put("" + task.getId(), taskData);
            }
            myELVAdapter taskAdapter = new myELVAdapter(this, taskTitles, taskDetails);
            tasksScroll.setAdapter(taskAdapter);
            tasksScroll.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                        int taskId = Integer.parseInt(taskTitles.get(position));
                        Log.v(TAG, "" + taskId);

                        Bundle bundle = new Bundle();
                        bundle.putInt("ID", taskId);

                        EventTaskOptionsFragment fragment = new EventTaskOptionsFragment();
                        fragment.setArguments(bundle);

                        fragment.show(getFragmentManager(),"eventTaskOptions");
                        //Toast toast = Toast.makeText(c, "Loong", Toast.LENGTH_LONG);
                        //toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                        //toast.show();

                        return true;
                    }

                    return false;
                }
            });
            /*  =========================================================================
                                    EVENTS
                ========================================================================= */
            Log.v("Daily", "eventArr lenght " + eventArr.size());

            eventsScroll=(ExpandableListView)findViewById(R.id.event_scroll);



            for(int i = 0; i < eventArr.size(); i++){
                List<String> eventData = new ArrayList<String>();
                Events event = eventArr.get(i);

                eventData.add(event.getTitle());
                eventData.add(event.getDescription());
                eventData.add(event.getStartDate());
                eventData.add(event.getStartTime());
                eventData.add(event.getEndDate());
                eventData.add(event.getEndTime());
                eventData.add(event.getLocation());
                eventData.add(event.getCategory());
                eventData.add("" + event.getPriority());
                eventTitles.add("" + event.getId());

                eventDetails.put("" + event.getId(), eventData);
                Log.v("daily event id", ""+ event.getId());

            }
            myELVAdapter eventAdapter = new myELVAdapter(this, eventTitles, eventDetails);


            eventsScroll.setAdapter(eventAdapter);
            eventsScroll.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                        int eventId = Integer.parseInt(eventTitles.get(position));
                        Log.v(TAG, "" + eventId);

                        Bundle bundle = new Bundle();
                        bundle.putInt("ID", eventId);

                        EventTaskOptionsFragment fragment = new EventTaskOptionsFragment();
                        fragment.setArguments(bundle);

                        fragment.show(getFragmentManager(),"eventTaskOptions");
                        //Toast toast = Toast.makeText(c, "Loong", Toast.LENGTH_LONG);
                        //toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                        //toast.show();

                        return true;
                    }

                    return false;
                }
            });


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
        Log.v(TAG, "numbver of tasks" + numberOfTasks);
        int taskTitleIndex = taskCursor.getColumnIndex(DigiPAContract.COLUMN_NAME_TITLE);
        int index = 0;


        if(numberOfTasks < 1){
            TextView noTaskTxt = (TextView)findViewById(R.id.no_task);
            noTaskTxt.setText("No tasks for today");
        }else {
            taskCursor.moveToFirst();

            do{
                String title = taskCursor.getString(taskTitleIndex);
                int id = taskCursor.getInt(taskTitleIndex-1);
                String desc = taskCursor.getString(taskTitleIndex + 1);
                String dueDate = taskCursor.getString(taskTitleIndex + 2);
                String category = taskCursor.getString(taskTitleIndex + 3);
                int priority = taskCursor.getInt(taskTitleIndex + 4);
                int complete = taskCursor.getInt(taskTitleIndex + 5);

                Tasks task = new Tasks(title,desc,dueDate,category,priority,complete);
                task.setId(id);

                taskArr.add(task);

              //  setTaskScroll(title);

                index++;
            }while(taskCursor.moveToNext());
        }

        index = 0;
        int numberOfEvents = eventCursor.getCount();
        String[] arr = eventCursor.getColumnNames();
        Log.v(TAG, "Number of events: " + numberOfEvents + " columns" +
                eventCursor.getColumnCount() + " arr "+ Arrays.toString(arr));
        int eventTitleIndex = eventCursor.getColumnIndex(DigiPAContract.COLUMN_NAME_TITLE);
        //int eventIdIndex = eventCursor.getColumnIndex(BaseColumns._ID);
        if (numberOfEvents < 1){
            TextView noEventTxt = (TextView)findViewById(R.id.no_event);
            noEventTxt.setText("No events for today");
        } else {
            eventCursor.moveToFirst();

            do{
                String title = eventCursor.getString(eventTitleIndex);
                int id = eventCursor.getInt(eventTitleIndex-1);
                String desc = eventCursor.getString(eventTitleIndex + 1);
                String startDate = eventCursor.getString(eventTitleIndex + 2);
                String startTime = eventCursor.getString(eventTitleIndex + 3);
                String endDate = eventCursor.getString(eventTitleIndex + 4);
                String endTime = eventCursor.getString(eventTitleIndex + 5);
                String location = eventCursor.getString(eventTitleIndex + 6);
                String category = eventCursor.getString(eventTitleIndex + 7);
                int priority = eventCursor.getInt(eventTitleIndex + 8);

                Log.v(TAG, "Grabbed event: " + id + " " + title + " " + desc + " " +
                        startDate + " " + startTime + " " + endDate + " " +
                        endTime + " " + location + " " + category + " " + priority);
                Events event = new Events(title, desc, startDate, startTime, endDate, endTime, location, category, priority);
                event.setId(id);
                //Log.v(TAG, "event id: " + event.getId());

//                event.setId(Integer.parseInt(BaseColumns._ID));

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
