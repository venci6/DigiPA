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

import java.util.Calendar;


public class CreateTask extends Activity {
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
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("DATE", 1);
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        cancelBttn = (Button)findViewById(R.id.CT_cancel);
        createTaskBttn = (Button)findViewById(R.id.CT_create);
    }

    public void goBack(){
        Intent goToMonth = new Intent(this, month.class);
        startActivity(goToMonth);
    }

    public void createTask(){
        handler.open();

        title = titleField.getText().toString();
        description = descrField.getText().toString();
        category = selectCategorySpr.getSelectedItem().toString();
        chosenDate = dueDate.getText().toString();
        if(highPri.isChecked()){
            priority = 1;
        }

        Log.v(TAG, "Before task insertion");
        long result = handler.insertTask(title, description, chosenDate, category, priority, isComplete);
        Log.v(TAG, "After insertion. Result = " + result);
    }

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
    }


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
