package com.example.liz.digipa;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Charlene on 11/14/2014.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    static final int START_DATE = 1;
    static final int END_DATE = 2;
    static final int DUE_DATE = 3;

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

        String dateFromButton="";

        switch (mChosenDate) {
            case START_DATE:
                cur = START_DATE;
                dateFromButton = "" + ((Button)getActivity().findViewById(R.id.event_details_start_date)).getText();
                break;
            case END_DATE:
                cur = END_DATE;
                dateFromButton = "" + ((Button)getActivity().findViewById(R.id.event_details_end_date)).getText();
                break;
            case DUE_DATE:
                cur = DUE_DATE;
                dateFromButton = "" + ((Button)getActivity().findViewById(R.id.task_details_due_date)).getText();
                break;
        }
        String[] dateExploded = dateFromButton.split("/");

        return new DatePickerDialog(getActivity(), this, Integer.parseInt(dateExploded[2]), Integer.parseInt(dateExploded[0])-1, Integer.parseInt(dateExploded[1]));
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        Button btn;
        if(cur == START_DATE) {
            btn = (Button)getActivity().findViewById(R.id.event_details_start_date);
        } else if (cur == END_DATE) {
            btn = (Button)getActivity().findViewById(R.id.event_details_end_date);
        } else {
            btn = (Button)getActivity().findViewById(R.id.task_details_due_date);
        }
        btn.setText(DPAHelperMethods.addLeadingZero(month+1) +"/"+ DPAHelperMethods.addLeadingZero(day) +"/"+ year);
    }
}