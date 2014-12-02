package com.example.liz.digipa;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by Charlene on 12/1/2014.
 */
public class AsyncCalendarLoader extends AsyncTask<String, Void, Boolean> {
    private final WeakReference<TextView> textViewReference;
    private Context mContext;

    public AsyncCalendarLoader(Context context,TextView view) {
        this.textViewReference = new WeakReference<TextView>(view);
        this.mContext = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        // this method executes the task in a background thread
        String day = params[0]; // the day to query database

        DPADataHandler db = new DPADataHandler(mContext);
        if(db.hasHighPriority(day)>0) {
            return true;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean highPri) {
        // set the background color of a day with a high priority day/event to red
        final TextView textView = textViewReference.get();
        if(highPri) {
            textView.setBackgroundColor(mContext.getResources().getColor(R.color.red));
        }
    }
}
