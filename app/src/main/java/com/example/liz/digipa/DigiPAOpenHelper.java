package com.example.liz.digipa;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Charlene on 11/5/2014.
 */
public class DigiPAOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "digipa.db";
    private static final int DATABASE_VERSION = 1;
    SQLiteDatabase DPAdb;
    Context cxt;

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

    public DigiPAOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_EVENTS_TABLE);
        database.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVers, int newVers ) {
        Log.w(DigiPAOpenHelper.class.getName(), "Upgrading db from version "+ oldVers + " to "
                + newVers + ", which will destroy all the old data");
        db.execSQL("DROP TABLE IF EXISTS " + DigiPAContract.DPAEvent.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DigiPAContract.DPATask.TABLE_NAME);
        onCreate(db);
    }

    public DigiPAOpenHelper open(){
        DPAdb =
    }
}
