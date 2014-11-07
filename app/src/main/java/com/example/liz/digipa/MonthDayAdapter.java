package com.example.liz.digipa;

import java.util.Calendar;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.GridView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.util.MonthDisplayHelper;

/**
 * Created by Charlene on 10/22/2014.
 */
public class MonthDayAdapter extends BaseAdapter {
    private Context mContext;

    private Calendar monthCalendar;
    public int month, year;

    public MonthDisplayHelper monthHelper;

    public String[] days;
    public MonthDayAdapter(Context c, Calendar cal) {
        super();

        mContext = c;

        //monthCalendar = Calendar.getInstance();


        monthHelper = new MonthDisplayHelper(year,month);

    }

    public int getCount() {
        return monthHelper.getNumberOfDaysInMonth();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }


}