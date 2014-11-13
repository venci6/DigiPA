package com.example.liz.digipa;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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

import static java.security.AccessController.getContext;


public class Daily extends Activity {

    TextView dateHeading;
    String dateChosen;
    DPADataHandler handler;

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

        instantiateEvents(dateChosen);
    }

    public boolean instantiateEvents(String date){
        handler = new DPADataHandler(getBaseContext());
        Cursor taskCursor = handler.returnTasks(date);
        Cursor eventCursor = handler.returnEvents(date);

        int numberOfTasks = taskCursor.getCount();
        int taskTitleIndex = taskCursor.getColumnIndex(DigiPAContract.COLUMN_NAME_TITLE);
        ScrollView taskScroll = (ScrollView)findViewById(R.id.task_scroll);
        if(numberOfTasks < 1){
            TextView noTaskTxt = (TextView)findViewById(R.id.task_text);
            noTaskTxt.setText("No tasks for today");
        }else {
            taskCursor.moveToFirst();
            do{
                String title = taskCursor.getString(taskTitleIndex);
            }while(taskCursor.moveToNext());
        }

        int numberOfEvents = eventCursor.getCount();
        int eventTitleIndex = eventCursor.getColumnIndex(DigiPAContract.COLUMN_NAME_TITLE);
        if (numberOfEvents < 1){
            TextView noTaskTxt = (TextView)findViewById(R.id.task_text);
                noTaskTxt.setText("No events for today");
        } else {

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
    /*
    //Fragment class used to expand daily tasks/events
    public class DrawerFragment extends Fragment implements ExpandableListView.OnGroupExpandListener, ExpandableListView.OnGroupCollapseListener {
        ScrollView scrollTaskView;
        ScrollView scrollEventView;
        ExpandableListView expEventView;
        ExpandableListView expTaskView;


        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_layout, container, false);
            scrollView = (ScrollView) rootView.findViewById(R.id.scrollViewDrawer);
            expListView = (ExpandableListView) rootView.findViewById(R.id.expandableListCategory);

        }
        @Override
        public void onGroupExpand(int groupPosition) {
            LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) expListView.getLayoutParams();
            param.height = (childCount * expListView.getHeight());
            expListView.setLayoutParams(param);
            expListView.refreshDrawableState();
            scrollView.refreshDrawableState();
        }

        @Override
        public void onGroupCollapse(int groupPosition) {
            LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) expListView.getLayoutParams();
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            expListView.setLayoutParams(param);
            expListView.refreshDrawableState();
            scrollView.refreshDrawableState();
        }
        */
}
