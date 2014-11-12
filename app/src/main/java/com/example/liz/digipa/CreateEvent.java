package com.example.liz.digipa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
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
    EditText title, description, location;
    Button sDate, sTime, eDate, eTime, cancel, create;
    Spinner category;
    boolean high_pri;

    static final int START_DATE_DIALOG_ID = 0;
    static final int END_DATE_DIALOG_ID = 0;
    DatePickerDialog.OnDateSetListener from_dateListener,to_dateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        initializeViews();

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

    }
    private void initializeViews() {
       // category = (Spinner) findViewById(R.id.categoryDropdown);

        title = (EditText) findViewById(R.id.CE_title);
        description = (EditText) findViewById(R.id.CE_descrip);
        location = (EditText) findViewById(R.id.CE_location);

        sDate = (Button) findViewById(R.id.CE_start_date);
        sTime = (Button) findViewById(R.id.CE_start_time);
        eDate = (Button) findViewById(R.id.CE_end_date);
        eTime = (Button) findViewById(R.id.CE_end_time);

        cancel = (Button) findViewById(R.id.CE_cancel);
        create = (Button) findViewById(R.id.CE_create);

        cancel.setOnClickListener(this);
        create.setOnClickListener(this);
    }


    public void onCheckboxClicked(View v) {
        boolean checked = ((CheckBox) v).isChecked();
        high_pri = checked;
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
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
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user

        }
    }
}
