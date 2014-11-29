package com.example.liz.digipa;

import android.app.DialogFragment;
import android.app.Fragment;

import android.database.Cursor;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.CheckBox;


/**
 * Created by Charlene on 11/10/2014.
 */
public class EventDetailsFragment extends Fragment implements View.OnClickListener  {
    private final String TAG = EventDetailsFragment.class.getSimpleName();


    // the only thing we need to check ... if populated
    private CheckBox priority;
    public static EditText title;
    private static EditText description, location;
   private static Button sDate, sTime, eDate, eTime;
    Spinner category;
    static int high_pri = 0;
    DPADataHandler handler;
    public static String categorySelected;

    public int currEventId = -1;
    public String currDayAddingTo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.event_details_fragment, container, false);

        // CATEGORY SPINNER
        category = (Spinner) v.findViewById(R.id.event_details_categories);
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

        title = (EditText) v.findViewById(R.id.event_details_title);
        description = (EditText) v.findViewById(R.id.event_details_descrip);
        location = (EditText) v.findViewById(R.id.event_details_location);

        priority = (CheckBox) v.findViewById(R.id.event_details_priority);
        sDate = (Button) v.findViewById(R.id.event_details_start_date);
        eDate = (Button) v.findViewById(R.id.event_details_end_date);

        sTime = (Button) v.findViewById(R.id.event_details_start_time);
        eTime = (Button) v.findViewById(R.id.event_details_end_time);

        sDate.setOnClickListener(this);
        eDate.setOnClickListener(this);
        sTime.setOnClickListener(this);
        eTime.setOnClickListener(this);

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

        Bundle bundle = this.getArguments();
        if(bundle!= null) {
            currEventId = bundle.getInt("ID", -1);
            currDayAddingTo = bundle.getString("ADDING_TO_DAY");
        }

        if(currEventId == -1) {
            // creating an event
            if(currDayAddingTo==null) {
                // initialize stuffs to default values (current day)
                DPAHelperMethods.initializeDate(sDate);
                DPAHelperMethods.initializeDate(eDate);
                DPAHelperMethods.initializeTime(sTime, false);
                DPAHelperMethods.initializeTime(eTime, true);
            } else {
                DPAHelperMethods.initializeToDate(sDate, currDayAddingTo);
                DPAHelperMethods.initializeToDate(eDate, currDayAddingTo);
                DPAHelperMethods.initializeTime(sTime, false);
                DPAHelperMethods.initializeTime(eTime, true);
            }

        } else {
            // editing an event
            // query from DB and populate the fields with current data
            handler = new DPADataHandler(getActivity());
            handler.open();
            Cursor eventCursor = handler.returnEventFromId(currEventId);

            if(eventCursor.getCount() != 1) {
                Log.v(TAG, "Unexpected number of events found with id="+currEventId);
                getActivity().finish();
            } else {
                // get data
                int eventTitleIndex = eventCursor.getColumnIndex(DigiPAContract.COLUMN_NAME_TITLE);
                eventCursor.moveToFirst();

                // populate the fields
                title.setText(eventCursor.getString(eventTitleIndex));
                description.setText(eventCursor.getString(eventTitleIndex + 1));
                sDate.setText(DPAHelperMethods.convertFromSQLDateFormat(eventCursor.getString(eventTitleIndex + 2)));
                sTime.setText(eventCursor.getString(eventTitleIndex + 3));
                eDate.setText(DPAHelperMethods.convertFromSQLDateFormat(eventCursor.getString(eventTitleIndex + 4)));
                eTime.setText(eventCursor.getString(eventTitleIndex + 5));
                location.setText(eventCursor.getString(eventTitleIndex + 6));

                category.setSelection(adapter.getPosition(eventCursor.getString(eventTitleIndex + 7)));
                if(eventCursor.getInt(eventTitleIndex + 8)==1) {
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

    @Override
    public void onClick (View v) {
        if(v.getId()==R.id.event_details_start_date  || v.getId()==R.id.event_details_end_date) {
            Bundle bundle = new Bundle();
            
            if(v.getId()==R.id.event_details_start_date) {
                bundle.putInt("DATE",1);
            } else {
                bundle.putInt("DATE",2);
            }

            DialogFragment newFragment = new DatePickerFragment();
            newFragment.setArguments(bundle);
            newFragment.show(getFragmentManager(), "datePicker");

        } else if(v.getId()==R.id.event_details_start_time || v.getId()==R.id.event_details_end_time) {
            Bundle bundle = new Bundle();

            if(v.getId()==R.id.event_details_start_time) {
                bundle.putInt("TIME",1);
            } else {
                bundle.putInt("TIME",2);
            }

            DialogFragment newFragment = new TimePickerFragment();
            newFragment.setArguments(bundle);
            newFragment.show(getFragmentManager(), "timePicker");
        }
    }



    public static Events createEvent() {
        String eventTitle = "" + title.getText();
        String eventDescription = "" + description.getText();

        String eventSDate = "" + sDate.getText();
        eventSDate = DPAHelperMethods.convertDateFormat(eventSDate);

        String eventSTime = "" + sTime.getText();

        String eventEDate = "" + eDate.getText();
        eventEDate = DPAHelperMethods.convertDateFormat(eventEDate);

        String eventETime = "" + eTime.getText();
        String eventLocation = "" + location.getText();

        Events event = new Events(eventTitle, eventDescription, eventSDate,
                eventSTime, eventEDate, eventETime, eventLocation, categorySelected, high_pri);

        return event;
    }

}
