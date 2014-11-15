package com.example.liz.digipa;

import android.app.Activity;
import android.widget.Button;

import java.util.Calendar;

/**
 * Created by Charlene on 11/14/2014.
 */
public class DPAHelperMethods extends Activity{


    public final static String[] months = {"January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"};

    // given YYYYMMDD --> Month Day, Year
    public static String niceDateFormat(String date) {
        String year = date.substring(0,4);
        int month = Integer.parseInt(date.substring(4, 6));
        String day = date.substring(6);
        return months[month-1] + " " + day + ", " + year;
    }

    public static String convertDateFormat(String date) {
        // MM/DD/YYYY --> YYYYMMDD
        String newFormat;
        String[] dateExploded = date.split("/");
        newFormat = dateExploded[2] + dateExploded[0] + dateExploded[1];
        return newFormat;
    }

    public static int numericMonth(String month) {
        if(month.equalsIgnoreCase("January")) {
            return 1;
        } else if(month.equalsIgnoreCase("February")) {
            return 2;
        } else if(month.equalsIgnoreCase("March ")) {
            return 3;
        } else if(month.equalsIgnoreCase("April")) {
            return 4;
        } else if(month.equalsIgnoreCase("May")) {
            return 5;
        } else if(month.equalsIgnoreCase("June")) {
            return 6;
        } else if(month.equalsIgnoreCase("July")) {
            return 7;
        } else if(month.equalsIgnoreCase("August")) {
            return 8;
        } else if(month.equalsIgnoreCase("September")) {
            return 9;
        } else if(month.equalsIgnoreCase("October")) {
            return 10;
        } else if(month.equalsIgnoreCase("November")) {
            return 11;
        } else if(month.equalsIgnoreCase("December")) {
            return 12;
        } else return 0;
    }

    public static String addLeadingZero(int i) {
        if(i < 10) {
            return "0" + i;
        } else return "" + i;
    }

    public static void initializeDate(Button button) {
        Calendar c = Calendar.getInstance();

        //Button button = (Button)findViewById(id);

        int curYear = c.get(Calendar.YEAR);
        int curMonth = 1 + c.get(Calendar.MONTH);
        String curDay = DPAHelperMethods.addLeadingZero(c.get(Calendar.DAY_OF_MONTH));

        button.setText(curMonth + "/" + curDay + "/" + curYear);
    }

    public static void initializeTime(Button btn, boolean offset) {

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

        if(offset) {
            if (incMinutes >= 30) {
                curHour++;
                if (curHour == 13) {
                    curHour = 1;
                    if (APM.equals("PM")) {
                        APM = "AM";
                    } else {
                        APM = "PM";
                    }
                }
            }
            curMinute = DPAHelperMethods.addLeadingZero((incMinutes + 30) % 60);
        }

        btn.setText(curHour + " : " + curMinute + " " + APM);
    }
}
