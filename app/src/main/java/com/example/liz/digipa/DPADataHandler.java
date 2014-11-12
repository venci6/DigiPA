package com.example.liz.digipa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Liz on 11/11/2014.
 */
public class DPADataHandler {
    private static final String DATABASE_NAME = "digipa.db";
    private static final int DATABASE_VERSION = 1;

    // events (id, title, description, start date, end date, star ttime, end time, location, category, high_pri)
    private static final String CREATE_EVENTS_TABLE = "create table " + DigiPAContract.DPAEvent.TABLE_NAME + "("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DigiPAContract.COLUMN_NAME_TITLE + " text not null, "
            + DigiPAContract.COLUMN_NAME_DESCRIPTION + " text, "
            + DigiPAContract.DPAEvent.COLUMN_NAME_START_DATE + " text, "
            + DigiPAContract.DPAEvent.COLUMN_NAME_START_TIME + " text, "
            + DigiPAContract.DPAEvent.COLUMN_NAME_END_DATE + " text, "
            + DigiPAContract.DPAEvent.COLUMN_NAME_END_TIME + " text, "
            + DigiPAContract.DPAEvent.COLUMN_NAME_LOCATION + " text, "
            + DigiPAContract.COLUMN_NAME_CATEGORY + " text, "
            + DigiPAContract.COLUMN_NAME_HIGH_PRI + " integer);";

    // tasks (id title descrip, due date, category, high pri, is_complete
    private static final String CREATE_TASKS_TABLE= "create table " + DigiPAContract.DPATask.TABLE_NAME + "("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DigiPAContract.COLUMN_NAME_TITLE + " text not null, "
            + DigiPAContract.COLUMN_NAME_DESCRIPTION + " text, "
            + DigiPAContract.DPATask.COLUMN_NAME_DUE_DATE+ " text, "
            + DigiPAContract.COLUMN_NAME_CATEGORY + " text, "
            + DigiPAContract.COLUMN_NAME_HIGH_PRI + " integer, "
            + DigiPAContract.DPATask.COLUMN_NAME_IS_COMPLETE + "integer);";

    SQLiteDatabase DPAdb;
    DPAOpenHelper dbhelper;
    Context ctx;
    public DPADataHandler(Context ctx){
        this.ctx = ctx;
        dbhelper = new DPAOpenHelper(ctx);
    }

    private static class DPAOpenHelper extends SQLiteOpenHelper{

        public DPAOpenHelper(Context ctx){
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(CREATE_EVENTS_TABLE);
            database.execSQL(CREATE_TASKS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVers, int newVers ) {
            Log.w(DigiPAOpenHelper.class.getName(), "Upgrading db from version " + oldVers + " to "
                    + newVers + ", which will destroy all the old data");
            db.execSQL("DROP TABLE IF EXISTS " + DigiPAContract.DPAEvent.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DigiPAContract.DPATask.TABLE_NAME);
            onCreate(db);
        }
    }

    public DPADataHandler open(){
        DPAdb = dbhelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbhelper.close();
    }

    public long insertTask(String title, String description, String dueDate, String category, int hi_pri, boolean is_complete){
        ContentValues content = new ContentValues();
        content.put(DigiPAContract.COLUMN_NAME_TITLE, title);
        content.put(DigiPAContract.COLUMN_NAME_DESCRIPTION, description);
        content.put(DigiPAContract.DPATask.COLUMN_NAME_DUE_DATE, dueDate);
        content.put(DigiPAContract.COLUMN_NAME_CATEGORY, category);
        content.put(DigiPAContract.COLUMN_NAME_HIGH_PRI, hi_pri);
        content.put(DigiPAContract.DPATask.COLUMN_NAME_IS_COMPLETE, is_complete);

        return DPAdb.insertOrThrow(DigiPAContract.DPATask.TABLE_NAME, null, content);
    }

    public Cursor returnTasks(String date){
        String[] cols = {DigiPAContract.COLUMN_NAME_TITLE, DigiPAContract.COLUMN_NAME_DESCRIPTION, DigiPAContract.DPATask.COLUMN_NAME_DUE_DATE, DigiPAContract.COLUMN_NAME_CATEGORY, DigiPAContract.COLUMN_NAME_HIGH_PRI, DigiPAContract.DPATask.COLUMN_NAME_IS_COMPLETE};
        return DPAdb.query(DigiPAContract.DPATask.TABLE_NAME, cols, "due_date=?", new String[]{date}, "is_complete", null, null);
    }

    public Cursor returnEvents(String date){
        String[] cols = {DigiPAContract.COLUMN_NAME_TITLE, DigiPAContract.COLUMN_NAME_DESCRIPTION, DigiPAContract.DPAEvent.COLUMN_NAME_START_DATE, DigiPAContract.DPAEvent.COLUMN_NAME_START_TIME, DigiPAContract.DPAEvent.COLUMN_NAME_END_DATE, DigiPAContract.DPAEvent.COLUMN_NAME_END_TIME, DigiPAContract.DPAEvent.COLUMN_NAME_START_DATE, DigiPAContract.DPAEvent.COLUMN_NAME_LOCATION, DigiPAContract.COLUMN_NAME_CATEGORY, DigiPAContract.COLUMN_NAME_HIGH_PRI};
        return DPAdb.query(DigiPAContract.DPAEvent.TABLE_NAME, cols, "start_date=?", new String[]{date}, null, null, "start_time");
    }

    public long insertEvent(String title, String description, String startDate, String startTime, String endDate, String endTime, String location, String category, int hi_pri){
        ContentValues content = new ContentValues();
        content.put(DigiPAContract.COLUMN_NAME_TITLE, title);
        content.put(DigiPAContract.COLUMN_NAME_DESCRIPTION, description);
        content.put(DigiPAContract.DPAEvent.COLUMN_NAME_START_DATE, startDate);
        content.put(DigiPAContract.DPAEvent.COLUMN_NAME_START_TIME, startTime);
        content.put(DigiPAContract.DPAEvent.COLUMN_NAME_END_DATE, endDate);
        content.put(DigiPAContract.DPAEvent.COLUMN_NAME_END_TIME, endTime);
        content.put(DigiPAContract.DPAEvent.COLUMN_NAME_LOCATION, location);
        content.put(DigiPAContract.COLUMN_NAME_CATEGORY, category);
        content.put(DigiPAContract.COLUMN_NAME_HIGH_PRI, hi_pri);

        return DPAdb.insertOrThrow(DigiPAContract.DPAEvent.TABLE_NAME, null, content);
    }
}
