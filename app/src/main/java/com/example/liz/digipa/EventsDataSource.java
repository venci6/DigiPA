package com.example.liz.digipa;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by Charlene on 11/5/2014.
 */
public class EventsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private DigiPAOpenHelper dbHelper;
//    private String[] allColumns = {
//            DigiPAOpenHelper.COLUMN_ID,
//            DigiPAOpenHelper.COLUMN_TITLE,
//            DigiPAOpenHelper.COLUMN_DESCRIPTION,
//            DigiPAOpenHelper.COLUMN_START_DATE,
//            DigiPAOpenHelper.COLUMN_START_TIME,
//            DigiPAOpenHelper.COLUMN_END_DATE,
//            DigiPAOpenHelper.COLUMN_END_TIME,
//            DigiPAOpenHelper.COLUMN_CATEGORY,
//            DigiPAOpenHelper.COLUMN_HIGH_PRI
//    };

    public EventsDataSource(Context context) {
        dbHelper = new DigiPAOpenHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

//    public Events createComment(String comment) {
//        ContentValues values = new ContentValues();
//        values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
//        long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
//                values);
//        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
//                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
//                null, null, null);
//        cursor.moveToFirst();
//        Comment newComment = cursorToComment(cursor);
//        cursor.close();
//        return newComment;
//    }
//
//    public void deleteComment(Comment comment) {
//        long id = comment.getId();
//        System.out.println("Comment deleted with id: " + id);
//        database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
//                + " = " + id, null);
//    }
//
//    public List<Comment> getAllComments() {
//        List<Comment> comments = new ArrayList<Comment>();
//
//        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
//                allColumns, null, null, null, null, null);
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            Comment comment = cursorToComment(cursor);
//            comments.add(comment);
//            cursor.moveToNext();
//        }
//        // make sure to close the cursor
//        cursor.close();
//        return comments;
//    }
//
//    private Comment cursorToComment(Cursor cursor) {
//        Comment comment = new Comment();
//        comment.setId(cursor.getLong(0));
//        comment.setComment(cursor.getString(1));
//        return comment;
//    }
}
