package com.example.liz.digipa;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class TaskDetailsFragment extends Fragment {
    private final String TAG = TaskDetailsFragment.class.getSimpleName();

    private static Button dueDate;
    
    Spinner category;
    public static EditText title;
    private static EditText descrip;
    public static String categorySelected;
    private CheckBox priority;

    private static int high_pri= 0, isComplete = 0;
    DPADataHandler handler;
    public int currTaskId = -1;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.task_details_fragment, container, false);

        // CATEGORY SPINNER
        category = (Spinner) v.findViewById(R.id.task_details_categories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long i) {
                categorySelected = parent.getItemAtPosition(pos).toString();
                Log.v(TAG, " category selected" + categorySelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
                categorySelected = "Default";
            }
        });

        title = (EditText) v.findViewById(R.id.task_details_title);
        descrip = (EditText) v.findViewById(R.id.task_details_descrip);

        priority = (CheckBox) v.findViewById(R.id.task_details_priority);
        priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()) {
                    high_pri = 1;
                } else {
                    high_pri = 0;
                }
            }
        });
        
        dueDate = (Button) v.findViewById(R.id.task_details_due_date);
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("DATE", 3);
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        Bundle bundle = this.getArguments();
        if(bundle!= null) {
            currTaskId = bundle.getInt("ID", -1);
        }

        if(currTaskId == -1) {
            // creating a task
            // initialize stuffs to default values (current day)
            DPAHelperMethods.initializeDate(dueDate);

        } else {
            // editing a task
            // query from DB and populate the fields with current data
            handler = new DPADataHandler(getActivity());
            handler.open();
            Cursor taskCursor = handler.returnTaskFromId(currTaskId);

            if(taskCursor.getCount() != 1) {
                Log.v(TAG, "Unexpected number of tasks found with id="+currTaskId);
                getActivity().finish();
            } else {
                // get data
                int taskTitleIndex = taskCursor.getColumnIndex(DigiPAContract.COLUMN_NAME_TITLE);
                taskCursor.moveToFirst();

                // populate the fields
                title.setText(taskCursor.getString(taskTitleIndex));
                descrip.setText(taskCursor.getString(taskTitleIndex + 1));
                dueDate.setText(DPAHelperMethods.convertFromSQLDateFormat(taskCursor.getString(taskTitleIndex + 2)));

                category.setSelection(adapter.getPosition(taskCursor.getString(taskTitleIndex + 3)));

                if(taskCursor.getInt(taskTitleIndex + 4)==1) {
                    high_pri = 1;
                    priority.setChecked(true);
                } else {
                    high_pri = 0;
                    priority.setChecked(false);
                }
            }
        }
        return v;
    }




    public static Tasks createTask(){

        String taskTitle = "" + title.getText();
        String taskDescription = "" + descrip.getText();

        String taskDDate = "" + dueDate.getText();
        taskDDate = DPAHelperMethods.convertDateFormat(taskDDate);

        Tasks task = new Tasks(taskTitle, taskDescription, taskDDate, categorySelected, high_pri, 0);



        return task;
    }

}
