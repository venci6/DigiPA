package com.example.liz.digipa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.app.FragmentManager;
import android.widget.CheckBox;

import java.util.Calendar;

/**
 * Created by Charlene on 11/10/2014.
 */
public class CreateEvent extends Activity implements View.OnClickListener  {
    private final String TAG = month.class.getSimpleName();
    EditText title, description, location;
   public static Button sDate, sTime, eDate, eTime, cancel, create;
    Spinner category;
    boolean high_pri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        initializeViews();
        initializeDateTimes();

        // Category Spinner Fragment
        if(findViewById(R.id.categories_container)!=null) {
            if(savedInstanceState != null) {
                return;
            }

            CategoriesFragment categories = new CategoriesFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.categories_container, categories);
            fragmentTransaction.commit();
        }



    }

    @Override
    public void onClick (View v) {
        if(v.getId()==R.id.CE_start_date  || v.getId()==R.id.CE_end_date) {
            Bundle bundle = new Bundle();
            
            if(v.getId()==R.id.CE_start_date) {
                bundle.putInt("DATE",1);
            } else {
                bundle.putInt("DATE",2);
            }

            DialogFragment newFragment = new DatePickerFragment();
            newFragment.setArguments(bundle);
            newFragment.show(getFragmentManager(), "datePicker");

        } else if(v.getId()==R.id.CE_start_time || v.getId()==R.id.CE_end_time) {
            Bundle bundle = new Bundle();

            if(v.getId()==R.id.CE_start_time) {
                bundle.putInt("TIME",1);
            } else {
                bundle.putInt("TIME",2);
            }

            DialogFragment newFragment = new TimePickerFragment();
            newFragment.setArguments(bundle);
            newFragment.show(getFragmentManager(), "timePicker");
        } else {

            switch(v.getId()) {
                case R.id.CE_cancel:
                    break;
                case R.id.CE_create:
                    break;
                default:
                    break;
            }
        }

    }
    private void initializeDateTimes() {
        Calendar c = Calendar.getInstance();
        int curHour = c.get(Calendar.HOUR_OF_DAY);
        int curMinute = c.get(Calendar.MINUTE);
        int curYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH);
        int curDay = c.get(Calendar.DAY_OF_MONTH);

        sDate.setText((curMonth+1) + "/" + curDay + "/" + curYear);
        eDate.setText((curMonth+1) + "/" + curDay + "/" + curYear);

        sTime.setText(curHour + ":" + curMinute);
        sTime.setText(curHour + ":" + curMinute);
    }

    private void initializeViews() {
       // category = (Spinner) findViewById(R.id.categoryDropdown);

        title = (EditText) findViewById(R.id.CE_title);
        description = (EditText) findViewById(R.id.CE_descrip);
        location = (EditText) findViewById(R.id.CE_location);

        sDate = (Button) findViewById(R.id.CE_start_date);
        eDate = (Button) findViewById(R.id.CE_end_date);

        sTime = (Button) findViewById(R.id.CE_start_time);
        eTime = (Button) findViewById(R.id.CE_end_time);

        cancel = (Button) findViewById(R.id.CE_cancel);
        create = (Button) findViewById(R.id.CE_create);

        cancel.setOnClickListener(this);
        create.setOnClickListener(this);
        sDate.setOnClickListener(this);
        eDate.setOnClickListener(this);
        sTime.setOnClickListener(this);
        eTime.setOnClickListener(this);

    }


    public void onCheckboxClicked(View v) {
        boolean checked = ((CheckBox) v).isChecked();
        high_pri = checked;
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        static final int START_DATE = 1;
        static final int END_DATE = 2;

        private int mChosenDate;

        int cur = 0;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            //return new DatePickerDialog(getActivity(), this, year, month, day);

            Bundle bundle = this.getArguments();
            if(bundle != null){
                mChosenDate = bundle.getInt("DATE",1);
            }

            switch (mChosenDate) {
                case START_DATE:
                    cur = START_DATE;
                    break;
                case END_DATE:
                    cur = END_DATE;
                    break;
            }
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            if(cur == START_DATE) {
                sDate.setText((month+1) +"/"+ day +"/"+ year);
            } else {
                eDate.setText((month+1) +"/"+ day +"/"+ year);
            }
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        static final int START_TIME = 1;
        static final int END_TIME = 2;

        private int mChosenTime;

        int cur = 0;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            Bundle bundle = this.getArguments();
            if(bundle != null){
                mChosenTime= bundle.getInt("TIME",1);
            }

            switch (mChosenTime) {
                case START_TIME:
                    cur = START_TIME;
                    break;
                case END_TIME:
                    cur = END_TIME;
                    break;
            }
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(cur == START_TIME) {
                sTime.setText(hourOfDay + ":" + minute);
            } else {
                eTime.setText(hourOfDay + ":" + minute);
            }
        }
    }
}
