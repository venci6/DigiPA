package com.example.liz.digipa;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Charlene on 12/1/2014.
 */
public class CalendarViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<CalendarDay> mItems;

    public CalendarViewAdapter(Context context, List<CalendarDay> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.day_cell, parent, false);
        }

        TextView view = (TextView) convertView.findViewById(R.id.calendar_day);

        int day = mItems.get(position).day;

        // make days that are not in the current month gray
        if((position < 7 && day > 7) || (position >28 && day <15)){
            view.setTextColor(parent.getResources().getColor(R.color.gray));
        }

        view.setText(String.valueOf(day));

        // YYYYMMDD
        String date = mItems.get(position).year + DPAHelperMethods.addLeadingZero(mItems.get(position).month+1) + DPAHelperMethods.addLeadingZero(day);

        // to handle querying the database for a high priority task/event for every day
        AsyncCalendarLoader handler = new AsyncCalendarLoader(mContext, view);
        handler.execute(date);

        return convertView;
    }
}
