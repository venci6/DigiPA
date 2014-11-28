package com.example.liz.digipa;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.app.FragmentManager;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.mobsandgeeks.saripaar.annotation.Required;

import java.util.Calendar;

/**
 * Created by Charlene on 11/10/2014.
 */
public class CreateEvent extends Activity implements View.OnClickListener  {
    private final String TAG = month.class.getSimpleName();

    // the only thing we need to check ... if populated
    private EditText title;

    private EditText description, location;
   public static Button sDate, sTime, eDate, eTime, cancel, create;
    Spinner category;
    int high_pri;
    DPADataHandler handler;
    public String categorySelected;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        initializeViews();

        DPAHelperMethods.initializeDate(sDate);
        DPAHelperMethods.initializeDate(eDate);
        DPAHelperMethods.initializeTime(sTime,false);
        DPAHelperMethods.initializeTime(eTime,true);

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
                    finish();
                    break;
                case R.id.CE_create:
                    // check if title is populated
                    if(title.getText().toString().length() > 0) {

                        if(createEvent() != -1) {
                            Toast.makeText(this,"Event successfully created!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this,"Error creating event", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this,"Title must be populated", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }

    }



    private long createEvent() {
        handler = new DPADataHandler(this);
        handler.open();

        String eventTitle = ""+title.getText();
        String eventDescription = "" + description.getText();

        String eventSDate = "" + sDate.getText();
        eventSDate = DPAHelperMethods.convertDateFormat(eventSDate);

        String eventSTime = "" + sTime.getText();

        String eventEDate = "" + eDate.getText();
        eventEDate = DPAHelperMethods.convertDateFormat(eventEDate);

        String eventETime = "" + eTime.getText();
        String eventLocation = "" + location.getText();
        //TO DO
        //String eventCategory = "Default";
        String eventCategory = categorySelected;


        Log.v(TAG, eventTitle + " " + eventDescription + " " + eventSDate + " "
                +  eventSTime + " " +  eventEDate + " " +  eventETime + " "
                +  eventLocation + " " + eventCategory + " " + high_pri);

        Toast.makeText(this, " date " + eventSDate, Toast.LENGTH_SHORT).show();
        Events event = new Events(eventTitle, eventDescription, eventSDate,
                eventSTime, eventEDate, eventETime, eventLocation, eventCategory, high_pri);

        long result = handler.insertEvent(event);

        handler.close();
        return result;
    }



    private void initializeDateTimes() {
        Calendar c = Calendar.getInstance();
        String APM;

        int curHour = c.get(Calendar.HOUR_OF_DAY);
        if(curHour >= 12) {
            curHour = (curHour%13)+1;
            APM = "PM";
        } else {
            if(curHour == 0) {
                curHour = 12;
            }

            APM = "AM";
        }

        int incMinutes = c.get(Calendar.MINUTE);
        String curMinute = DPAHelperMethods.addLeadingZero(incMinutes);


        int curYear = c.get(Calendar.YEAR);
        int curMonth = 1 + c.get(Calendar.MONTH);
        String curDay = DPAHelperMethods.addLeadingZero(c.get(Calendar.DAY_OF_MONTH));

        sDate.setText(curMonth + "/" + curDay + "/" + curYear);
        eDate.setText(curMonth + "/" + curDay + "/" + curYear);

        sTime.setText(curHour + " : " + curMinute + " " + APM);

        if(incMinutes >= 30) {
            curHour++;
            if(curHour == 13) {
                curHour = 1;
                if(APM.equals("PM")) {
                    APM = "AM";
                } else {
                    APM = "PM";
                }
            }
        }
        curMinute = DPAHelperMethods.addLeadingZero((incMinutes+30)%60);
        eTime.setText(curHour + " : " + curMinute + " " + APM);
    }


    private void initializeViews() {
        category = (Spinner) findViewById(R.id.categoriesSpinner);
//
//        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int pos, long i) {
//                categorySelected = parent.getItemAtPosition(pos).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // Another interface callback
//                categorySelected = "Default";
//            }
//        });

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
        if(((CheckBox) v).isChecked()) {
            high_pri = 1;
        } else {
            high_pri = 0;
        }
    }
}
