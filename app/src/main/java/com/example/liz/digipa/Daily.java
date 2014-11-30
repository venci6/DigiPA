package com.example.liz.digipa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Daily extends Activity implements View.OnClickListener {
    private final String TAG = month.class.getSimpleName();

    TextView dateHeading, eventsHeader, tasksHeader, noEventTxt, noTaskTxt;
    RelativeLayout rl;

    String dateChosen;
    DPADataHandler handler;
    ArrayList<Tasks> taskArr;
    ArrayList<Events> eventArr;

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

        initializeViews();

        rl = (RelativeLayout) findViewById(R.id.root);

        refresh();
    }

    void showTasks() {
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
                    bundle.putInt("EVENT_OR_TASK", 2);

                    EventTaskOptionsFragment fragment = new EventTaskOptionsFragment();
                    fragment.setArguments(bundle);

                    fragment.show(getFragmentManager(),"eventTaskOptions");



                    return true;
                }

                return false;
            }
        });
    }

    public void refresh() {
        if(instantiateEvents(dateChosen)){
            showTasks();
            showEvents();
        }
    }
    void showEvents() {
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
            Log.v("daily event id", ""+ event.getId() + " category " + event.getCategory());
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
                    bundle.putInt("EVENT_OR_TASK", 1);


                    EventTaskOptionsFragment fragment = new EventTaskOptionsFragment();
                    fragment.setArguments(bundle);

                    fragment.show(getFragmentManager(),"eventTaskOptions");

                    return true;
                }

                return false;
            }
        });
    }
    void initializeViews() {
        dateHeading = (TextView)findViewById(R.id.date);
        dateHeading.setText(DPAHelperMethods.niceDateFormat(dateChosen));

        noEventTxt = (TextView)findViewById(R.id.no_event);
        noTaskTxt = (TextView)findViewById(R.id.no_task);

        tasksScroll = (ExpandableListView)findViewById(R.id.task_scroll);
        eventsScroll = (ExpandableListView)findViewById(R.id.event_scroll);

        eventsHeader = (TextView)findViewById(R.id.events);
        tasksHeader = (TextView)findViewById(R.id.tasks);

        eventsHeader.setOnClickListener(this);
        tasksHeader.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        switch(v.getId()) {
            case R.id.events:
                if(eventsScroll.getVisibility()==View.VISIBLE) {
                    noEventTxt.setVisibility(View.GONE);
                    eventsScroll.setVisibility(View.GONE);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tasksHeader.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, R.id.events);
                    tasksHeader.setLayoutParams(params);

                    Resources r = getResources();
                    int height = rl.getHeight()- 2*tasksHeader.getHeight();


                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(rl.getWidth(), height);
                    params2.addRule(RelativeLayout.BELOW, R.id.tasks);
                    tasksScroll.setLayoutParams(params2);
                } else {
                    noEventTxt.setVisibility(View.VISIBLE);
                    eventsScroll.setVisibility(View.VISIBLE);

                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(rl.getWidth(), RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.CENTER_VERTICAL);
                    tasksHeader.setLayoutParams(params);
                }
                break;
            case R.id.tasks:
                if(tasksScroll.getVisibility()==View.VISIBLE) {
                    noTaskTxt.setVisibility(View.GONE);
                    tasksScroll.setVisibility(View.GONE);

                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(rl.getWidth(), RelativeLayout.LayoutParams.WRAP_CONTENT);
                    if(eventsScroll.getVisibility()==View.VISIBLE) {
                        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    } else {
                        params.addRule(RelativeLayout.BELOW, R.id.events);
                    }
                    tasksHeader.setLayoutParams(params);

                    Resources r = getResources();
                    int height = rl.getHeight()- 2*tasksHeader.getHeight();
                    Log.v(TAG, "height " + height);
                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(rl.getWidth(), height);
                    params2.addRule(RelativeLayout.BELOW, R.id.events);
                    eventsScroll.setLayoutParams(params2);
                } else {
                    noTaskTxt.setVisibility(View.VISIBLE);
                    tasksScroll.setVisibility(View.VISIBLE);

                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(rl.getWidth(), RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.BELOW, R.id.event_scroll);
                    tasksHeader.setLayoutParams(params);

                    Resources r = getResources();
                    int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, r.getDisplayMetrics());
                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(rl.getWidth(), px);
                    params2.addRule(RelativeLayout.BELOW, R.id.events);
                    eventsScroll.setLayoutParams(params2);
                }
                break;
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
        taskArr = new ArrayList<Tasks>();
        eventArr = new ArrayList<Events>();
        handler = new DPADataHandler(getApplicationContext());
        handler.open();
        Cursor taskCursor = handler.returnTasks(date);
        Cursor eventCursor = handler.returnEvents(date);
        int numberOfTasks = taskCursor.getCount();
        int taskTitleIndex = taskCursor.getColumnIndex(DigiPAContract.COLUMN_NAME_TITLE);

        if(numberOfTasks < 1){

            noTaskTxt.setText("No tasks for today");
            noTaskTxt.setVisibility(View.VISIBLE);
        }else {
            noTaskTxt.setVisibility(View.GONE);
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


            }while(taskCursor.moveToNext());
        }


        int numberOfEvents = eventCursor.getCount();
        int eventTitleIndex = eventCursor.getColumnIndex(DigiPAContract.COLUMN_NAME_TITLE);

        if (numberOfEvents < 1){

            noEventTxt.setText("No events for today");
            noEventTxt.setVisibility(View.VISIBLE);
        } else {
            noEventTxt.setVisibility(View.GONE);
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


           //     setEventScroll(startTime, title);
                eventArr.add(event);


            }while(eventCursor.moveToNext());
        }
        handler.close();
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("DAY_TO_VIEW", dateChosen);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //refresh();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.daily, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_addEvent:
                Intent addNewEvent = new Intent(Daily.this, CreateEvent.class);
                addNewEvent.putExtra("DAY_TO_ADD_TO", dateChosen);
                startActivity(addNewEvent);
                break;
            case R.id.action_addTask:
                Intent addNewTask = new Intent(Daily.this, CreateTask.class);
                addNewTask.putExtra("DAY_TO_ADD_TO", dateChosen);
                startActivity(addNewTask);
                break;
            case R.id.action_settings:
                Intent goSettings = new Intent(Daily.this, Settings.class);
                startActivity(goSettings);
                break;
            case R.id.action_sign_out:
                SharedPreferences sharedPreferences = getSharedPreferences(Login.MYPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent logOut = new Intent(Daily.this, Login.class);
                startActivity(logOut);
                Daily.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
