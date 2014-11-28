package com.example.liz.digipa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;


public class CreateTask extends Activity implements View.OnClickListener {
    private final String TAG = CreateTask.class.getSimpleName();

    public static Button cancelBttn, createTaskBttn, dueDate;
    Spinner selectCategorySpr;
    EditText titleField, descrField;
    String title, description, category, chosenDate;
    CheckBox highPri;

    int priority = 0, isComplete = 0;
    DPADataHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        titleField = (EditText)findViewById(R.id.CT_title);
        highPri = (CheckBox)findViewById(R.id.checkbox_highpri);
        descrField = (EditText)findViewById(R.id.CT_description);
        selectCategorySpr = (Spinner)findViewById(R.id.categorySpinner);
        dueDate = (Button)findViewById(R.id.due_date);

        DPAHelperMethods.initializeDate(dueDate);

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


        cancelBttn = (Button)findViewById(R.id.CT_cancel);
        createTaskBttn = (Button)findViewById(R.id.CT_create);

        cancelBttn.setOnClickListener(this);
        createTaskBttn.setOnClickListener(this);
    }

    public void onClick (View v) {
        switch(v.getId()) {
            case R.id.CT_cancel:
                finish();
                break;
            case R.id.CT_create:
                if(titleField.getText().toString().length() > 0) {
                    if(createTask() != -1) {
                        Toast.makeText(this, "Task successfully created!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this,"Error creating event", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this,"Title must be populated", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void onCheckboxClicked(View v) {
        if(((CheckBox) v).isChecked()) {
            priority = 1;
        } else {
            priority = 0;
        }
    }


    public long createTask(){
        handler = new DPADataHandler(this);
        handler.open();

        String taskTitle = "" + titleField.getText();
        String taskDescription = "" + descrField.getText();

        String taskDDate = "" + dueDate.getText();
        taskDDate = DPAHelperMethods.convertDateFormat(taskDDate);

        String taskCategory = "" + selectCategorySpr.getSelectedItem().toString();

        Tasks task = new Tasks(taskTitle, taskDescription, taskDDate, taskCategory, priority, 0);


        Log.v(TAG, "Before task insertion");
        long result = handler.insertTask(task);
        Log.v(TAG, "After insertion. Result = " + result);
        handler.close();
        return result;
    }
/*
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            //return new DatePickerDialog(getActivity(), this, year, month, day);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
                dueDate.setText((month+1) +"/"+ day +"/"+ year);
        }
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_task, menu);
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
