package com.example.liz.digipa;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * Created by Charlene on 11/14/2014.
 */
public  class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    static final int START_TIME = 1;
    static final int END_TIME = 2;

    private int mChosenTime;

    int cur = 0;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if(bundle != null){
            mChosenTime = bundle.getInt("TIME",1);
        }
        int hour, minute;
        String time;
        String[] timeExploded;


        switch (mChosenTime) {
            case START_TIME:
                cur = START_TIME;
                time = ""+ ((Button)getActivity().findViewById(R.id.CE_start_time)).getText();
                break;
            case END_TIME:
                time = "" + ((Button)getActivity().findViewById(R.id.CE_end_time)).getText();
                cur = END_TIME;
                break;
            default:
                time="00 : 00";
                break;
        }
        timeExploded = time.split(" ");
        hour = Integer.parseInt(timeExploded[0]);
        minute = Integer.parseInt(timeExploded[2]);
        Log.v("time dialog", "create hour befu = " + hour);
        if(timeExploded[3].equals("PM") && hour != 12) {
            hour = hour + 12;
        }

        Log.v("time dialog", "create hour after = " + hour);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String min = DPAHelperMethods.addLeadingZero(minute);
        String APM;
        if(hourOfDay >= 12) {
            APM = "PM";
        } else {
            APM = "AM";
        }
        if(hourOfDay > 12) {
            hourOfDay = (hourOfDay % 13) + 1;
        }
        if(hourOfDay == 0) {
            hourOfDay = 12;
        }

        Button btn;
        if(cur == START_TIME) {
            btn = (Button) getActivity().findViewById(R.id.CE_start_time);
        } else {
            btn = (Button) getActivity().findViewById(R.id.CE_end_time);
        }
        btn.setText(hourOfDay + " : " + min + " " + APM);
    }
}