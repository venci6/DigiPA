package com.example.liz.digipa;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Charlene on 11/5/2014.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_HIGH_PRI = "high_priority";
    public static final String COLUMN_DAY_ID = "day_id";

    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_END_TIME = "end_time";
    public static final String COLUMN_LOCATION = "location";

    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_DUE_DATE = "due_date";
    public static final String COLUMN_IS_COMPLETE = "is_complete";

    private static final String DATABASE_NAME = "digipa.db";
    private static final int DATABASE_VERSION = 1;

    // events (id, title, description, start date, end date, star ttime, end time, location, category, high_pri)
    private static final String CREATE_EVENTS_TABLE = "create table " + TABLE_EVENTS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_DESCRIPTION + " text, "
            + COLUMN_START_DATE + " text, "
            + COLUMN_START_TIME + " text, "
            + COLUMN_END_DATE + " text, "
            + COLUMN_END_TIME + " text, "
            + COLUMN_LOCATION + " text, "
            + COLUMN_CATEGORY + " text, "
            + COLUMN_HIGH_PRI + " integer);";

    // tasks (id title descrip, due date, category, high pri, is_complete
    private static final String CREATE_TASKS_TABLE= "create table " + TABLE_TASKS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_DESCRIPTION + " text, "
            + COLUMN_DUE_DATE+ " text, "
            + COLUMN_CATEGORY + " text, "
            + COLUMN_HIGH_PRI + " integer, "
            + COLUMN_IS_COMPLETE + "integer);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_EVENTS_TABLE);
        database.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVers, int newVers ) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading db from version "+ oldVers + " to "
                + newVers + ", which will destroy all the old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }
}
