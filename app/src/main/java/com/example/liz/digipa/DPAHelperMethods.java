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

    public static String convertFromSQLDateFormat(String date) {
        //YYYYMMDD --> MM/DD/YY
        String year = date.substring(0,4);
        String month = date.substring(4, 6);
        String day = date.substring(6);
        return month+"/"+day+"/"+year;
    }
    public static String convertDateFormat(String date) {
        // MM/DD/YYYY --> YYYYMMDD
        String newFormat;
        String[] dateExploded = date.split("/");
        String month, day;
        month = addLeadingZero(Integer.parseInt(dateExploded[0]));
        day = addLeadingZero(Integer.parseInt(dateExploded[1]));
        newFormat = dateExploded[2] + month + day;
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

    public static void initializeToDate(Button button, String date) {
        button.setText(convertFromSQLDateFormat(date));
    }
    public static String[] twelveHourFormat(int hour) {
        // [hour, APM]
        String[] hourAPM = new String[2];
        String APM;

        if(hour >= 12) {
            APM = "PM";
        } else {
            APM = "AM";
        }

        if(hour > 12) {
            hour = (hour%13)+1;
            APM = "PM";
        }

        if(hour == 0) {
            hour = 12;
        }

        hourAPM[0] = Integer.toString(hour);
        hourAPM[1] = APM;
        return hourAPM;
    }

    public static void initializeTime(Button btn, boolean offset) {

        Calendar c = Calendar.getInstance();
        String APM;

        int curHour = c.get(Calendar.HOUR_OF_DAY);

        String[] hourAPM = twelveHourFormat(curHour);
        curHour = Integer.parseInt(hourAPM[0]);
        APM = hourAPM[1];

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
