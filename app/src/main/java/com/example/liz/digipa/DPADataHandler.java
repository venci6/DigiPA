package com.example.liz.digipa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Liz on 11/11/2014.
 */
public class DPADataHandler {
    private static final String DATABASE_NAME = "digipa.db";
    private static final int DATABASE_VERSION = 1;

    // events (id, title, description, start date, end date, star ttime, end time, location, category, high_pri)
    private static final String CREATE_EVENTS_TABLE = "create table " + DigiPAContract.DPAEvent.TABLE_NAME + "("
            + DigiPAContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DigiPAContract.COLUMN_NAME_TITLE + " text not null, "
            + DigiPAContract.COLUMN_NAME_DESCRIPTION + " text, "
            + DigiPAContract.DPAEvent.COLUMN_NAME_START_DATE + " text, "
            + DigiPAContract.DPAEvent.COLUMN_NAME_START_TIME + " text, "
            + DigiPAContract.DPAEvent.COLUMN_NAME_END_DATE + " text, "
            + DigiPAContract.DPAEvent.COLUMN_NAME_END_TIME + " text, "
            + DigiPAContract.DPAEvent.COLUMN_NAME_LOCATION + " text, "
            + DigiPAContract.COLUMN_NAME_CATEGORY + " text not null, "
            + DigiPAContract.COLUMN_NAME_HIGH_PRI + " integer);";

    // tasks (id title descrip, due date, category, high pri, is_complete
    private static final String CREATE_TASKS_TABLE= "create table " + DigiPAContract.DPATask.TABLE_NAME + "("
            + DigiPAContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DigiPAContract.COLUMN_NAME_TITLE + " text not null, "
            + DigiPAContract.COLUMN_NAME_DESCRIPTION + " text, "
            + DigiPAContract.DPATask.COLUMN_NAME_DUE_DATE+ " text, "
            + DigiPAContract.COLUMN_NAME_CATEGORY + " text not null, "
            + DigiPAContract.COLUMN_NAME_HIGH_PRI + " integer, "
            + DigiPAContract.DPATask.COLUMN_NAME_IS_COMPLETE + " integer);";

    // category (id name color)
    private static final String CREATE_CATEGORY_TABLE= ("create table " + DigiPAContract.DPACategory.TABLE_NAME + "("
            + DigiPAContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DigiPAContract.DPACategory.COLUMN_NAME_CATEGORY_TITLE + " text not null, "
            + DigiPAContract.DPACategory.COLUMN_NAME_COLOR + " text);");

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

        List<String> defaultCategories = Arrays.asList("Default", "Birthday", "Anniversary", "Meeting", "Classwork", "HW/Quiz", "Presentation/Exam");
        List<String> defaultColors = Arrays.asList("#FFFFFF","#FF9933","#FFFF66","#99FF33","#3399FF","#B266FF","#FF66B2");
        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(CREATE_EVENTS_TABLE);
            database.execSQL(CREATE_TASKS_TABLE);
            database.execSQL(CREATE_CATEGORY_TABLE);

            String insertCategory;

            for(int i = 0; i < defaultCategories.size(); i++) {
                insertCategory = "INSERT INTO " + DigiPAContract.DPACategory.TABLE_NAME + "("
                        + DigiPAContract.DPACategory.COLUMN_NAME_CATEGORY_TITLE + ", " + DigiPAContract.DPACategory.COLUMN_NAME_COLOR + ") "
                        + "VALUES ('" + defaultCategories.get(i) + "', '" + defaultColors.get(i) + "');";
                database.execSQL(insertCategory);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVers, int newVers ) {
            Log.w(DigiPAOpenHelper.class.getName(), "Upgrading db from version " + oldVers + " to "
                    + newVers + ", which will destroy all the old data");
            db.execSQL("DROP TABLE IF EXISTS " + DigiPAContract.DPAEvent.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DigiPAContract.DPATask.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DigiPAContract.DPACategory.TABLE_NAME);
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

    /*  =========================================================================
                                TASK CRUD OPERATIONS
        ========================================================================= */

    public long insertTask(Tasks task){
        ContentValues content = new ContentValues();
        content.put(DigiPAContract.COLUMN_NAME_TITLE, task.getTitle());
        content.put(DigiPAContract.COLUMN_NAME_DESCRIPTION, task.getDescription());
        content.put(DigiPAContract.DPATask.COLUMN_NAME_DUE_DATE, task.getDueDate());
        content.put(DigiPAContract.COLUMN_NAME_CATEGORY, task.getCategory());
        content.put(DigiPAContract.COLUMN_NAME_HIGH_PRI, task.getPriority());
        content.put(DigiPAContract.DPATask.COLUMN_NAME_IS_COMPLETE, task.getComplete());

        return DPAdb.insertOrThrow(DigiPAContract.DPATask.TABLE_NAME, null, content);
    }

    public Cursor returnTasks(String date){
        String[] cols = {
                DigiPAContract._ID,
                DigiPAContract.COLUMN_NAME_TITLE,
                DigiPAContract.COLUMN_NAME_DESCRIPTION,
                DigiPAContract.DPATask.COLUMN_NAME_DUE_DATE,
                DigiPAContract.COLUMN_NAME_CATEGORY,
                DigiPAContract.COLUMN_NAME_HIGH_PRI,
                DigiPAContract.DPATask.COLUMN_NAME_IS_COMPLETE
        };

        return DPAdb.query(DigiPAContract.DPATask.TABLE_NAME, cols,
                "due_date=?", new String[]{date},
                null, null, "is_complete asc, high_priority asc");
    }

    public Cursor returnTaskFromId(int id){
        String[] cols = {
                DigiPAContract._ID,
                DigiPAContract.COLUMN_NAME_TITLE,
                DigiPAContract.COLUMN_NAME_DESCRIPTION,
                DigiPAContract.DPATask.COLUMN_NAME_DUE_DATE,
                DigiPAContract.COLUMN_NAME_CATEGORY,
                DigiPAContract.COLUMN_NAME_HIGH_PRI,
                DigiPAContract.DPATask.COLUMN_NAME_IS_COMPLETE
        };

        return DPAdb.query(DigiPAContract.DPATask.TABLE_NAME, cols,
                "_id=?", new String[]{id+""},
                null, null, null);
    }

    public Cursor returnHighPriTasks(String date) {
        String[] cols = {
                DigiPAContract._ID
        };

        return DPAdb.query(DigiPAContract.DPATask.TABLE_NAME,
                cols, "due_date=? and high_priority=?", new String[]{date,"1"},
                null, null, null);
    }

    public int updateTaskFromId(int id, Tasks task) {
        ContentValues content = new ContentValues();
        content.put(DigiPAContract.COLUMN_NAME_TITLE, task.getTitle());
        content.put(DigiPAContract.COLUMN_NAME_DESCRIPTION, task.getDescription());
        content.put(DigiPAContract.DPATask.COLUMN_NAME_DUE_DATE, task.getDueDate());
        content.put(DigiPAContract.COLUMN_NAME_CATEGORY, task.getCategory());
        content.put(DigiPAContract.COLUMN_NAME_HIGH_PRI, task.getPriority());
        content.put(DigiPAContract.DPATask.COLUMN_NAME_IS_COMPLETE, task.getComplete());

        return DPAdb.update(DigiPAContract.DPATask.TABLE_NAME, content,
                DigiPAContract.DPAEvent._ID + " = " + id, null );
    }

    public int deleteTask(int id) {
        return DPAdb.delete(DigiPAContract.DPATask.TABLE_NAME,
                DigiPAContract.DPAEvent._ID + " = " + id, null );

    }

    /*  =========================================================================
                                EVENTS CRUD OPERATIONS
        ========================================================================= */

    public long  insertEvent(Events event){
        ContentValues content = new ContentValues();
        content.put(DigiPAContract.COLUMN_NAME_TITLE, event.getTitle());
        content.put(DigiPAContract.COLUMN_NAME_DESCRIPTION, event.getDescription());
        content.put(DigiPAContract.DPAEvent.COLUMN_NAME_START_DATE, event.getStartDate());
        content.put(DigiPAContract.DPAEvent.COLUMN_NAME_START_TIME, event.getStartTime());
        content.put(DigiPAContract.DPAEvent.COLUMN_NAME_END_DATE, event.getEndDate());
        content.put(DigiPAContract.DPAEvent.COLUMN_NAME_END_TIME, event.getEndTime());
        content.put(DigiPAContract.DPAEvent.COLUMN_NAME_LOCATION, event.getLocation());
        content.put(DigiPAContract.COLUMN_NAME_CATEGORY, event.getCategory());
        content.put(DigiPAContract.COLUMN_NAME_HIGH_PRI, event.getPriority());

        return DPAdb.insertOrThrow(DigiPAContract.DPAEvent.TABLE_NAME, null, content);
    }

    public Cursor returnEvents(String date){
        String[] cols = {
                DigiPAContract._ID,
                DigiPAContract.COLUMN_NAME_TITLE,
                DigiPAContract.COLUMN_NAME_DESCRIPTION,
                DigiPAContract.DPAEvent.COLUMN_NAME_START_DATE,
                DigiPAContract.DPAEvent.COLUMN_NAME_START_TIME,
                DigiPAContract.DPAEvent.COLUMN_NAME_END_DATE,
                DigiPAContract.DPAEvent.COLUMN_NAME_END_TIME,
                DigiPAContract.DPAEvent.COLUMN_NAME_LOCATION,
                DigiPAContract.COLUMN_NAME_CATEGORY,
                DigiPAContract.COLUMN_NAME_HIGH_PRI
        };

        return DPAdb.query(DigiPAContract.DPAEvent.TABLE_NAME,
                cols, "start_date=?", new String[]{date},
                null, null, "start_time");
    }

    public Cursor returnEventFromId(int id) {
        String[] cols = {
                DigiPAContract._ID,
                DigiPAContract.COLUMN_NAME_TITLE,
                DigiPAContract.COLUMN_NAME_DESCRIPTION,
                DigiPAContract.DPAEvent.COLUMN_NAME_START_DATE,
                DigiPAContract.DPAEvent.COLUMN_NAME_START_TIME,
                DigiPAContract.DPAEvent.COLUMN_NAME_END_DATE,
                DigiPAContract.DPAEvent.COLUMN_NAME_END_TIME,
                DigiPAContract.DPAEvent.COLUMN_NAME_LOCATION,
                DigiPAContract.COLUMN_NAME_CATEGORY,
                DigiPAContract.COLUMN_NAME_HIGH_PRI
        };

        return DPAdb.query(DigiPAContract.DPAEvent.TABLE_NAME, cols,
                "_id=?", new String[]{id+""},
                null, null, null);

    }

    public Cursor returnHighPriEvents(String date) {
        String[] cols = {
                DigiPAContract._ID
        };

        return DPAdb.query(DigiPAContract.DPAEvent.TABLE_NAME,
                cols, "start_date=? and high_priority=?", new String[]{date,"1"},
                null, null, null);
    }

    public int updateEventFromId(int id, Events event) {
        ContentValues content = new ContentValues();
        content.put(DigiPAContract.COLUMN_NAME_TITLE, event.getTitle());
        content.put(DigiPAContract.COLUMN_NAME_DESCRIPTION, event.getDescription());
        content.put(DigiPAContract.DPAEvent.COLUMN_NAME_START_DATE, event.getStartDate());
        content.put(DigiPAContract.DPAEvent.COLUMN_NAME_START_TIME, event.getStartTime());
        content.put(DigiPAContract.DPAEvent.COLUMN_NAME_END_DATE, event.getEndDate());
        content.put(DigiPAContract.DPAEvent.COLUMN_NAME_END_TIME, event.getEndTime());
        content.put(DigiPAContract.DPAEvent.COLUMN_NAME_LOCATION, event.getLocation());
        content.put(DigiPAContract.COLUMN_NAME_CATEGORY, event.getCategory());
        content.put(DigiPAContract.COLUMN_NAME_HIGH_PRI, event.getPriority());
        return DPAdb.update(DigiPAContract.DPAEvent.TABLE_NAME, content,
                DigiPAContract.DPAEvent._ID + " = " + id, null );
    }


    public int deleteEvent(int id) {
        return DPAdb.delete(DigiPAContract.DPAEvent.TABLE_NAME,
                DigiPAContract.DPAEvent._ID + " = " + id, null );

    }


    /*  =========================================================================
                                CATEGORY CRUD OPERATIONS
        ========================================================================= */

    public Cursor returnCategories(){
        String[] cols = {
                DigiPAContract._ID,
                DigiPAContract.DPACategory.COLUMN_NAME_CATEGORY_TITLE,
                DigiPAContract.DPACategory.COLUMN_NAME_COLOR
        };
        return DPAdb.query(DigiPAContract.DPACategory.TABLE_NAME, cols, null, null, null, null, null);
    }

    public long insertCategory(String categoryName, int categoryColor) {
        ContentValues content = new ContentValues();
        content.put(DigiPAContract.DPACategory.COLUMN_NAME_CATEGORY_TITLE, categoryName);
        content.put(DigiPAContract.COLUMN_NAME_DESCRIPTION, categoryColor); // 0xRRGGBB

        return DPAdb.insertOrThrow(DigiPAContract.DPACategory.TABLE_NAME, null, content);
    }

    public String returnColor(String category) {

        String selectQuery = "SELECT color FROM category WHERE category_name=?";
        Cursor mCount= DPAdb.rawQuery(selectQuery, new String[]{category});
         String color="";
        int count = mCount.getCount();
        if(count>0) {

            mCount.moveToFirst();

            do {
                color = mCount.getString(0);

            } while (mCount.moveToNext());
        }

        mCount.close();
        return color;

    }

    public int hasHighPriority(String date) {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String eventQuery = "SELECT count(*) from event where start_date=? AND high_priority=?";
        String taskQuery = "SELECT count(*) from task where due_date=? AND high_priority=?";
        Cursor hpCount = db.rawQuery(eventQuery, new String[] {date, "1"});
        int count;
        try {
            hpCount.moveToFirst();
            count = hpCount.getInt(0);
        } finally {
            hpCount.close();
        }

        if(count ==0 ) {
            hpCount = db.rawQuery(taskQuery, new String[]{date, "1"});

            try {
                hpCount.moveToFirst();
                count += hpCount.getInt(0);
            } finally {
                hpCount.close();
            }

        }
        db.close();
        return count;

    }
}

