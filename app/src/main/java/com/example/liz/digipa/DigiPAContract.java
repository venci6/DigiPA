package com.example.liz.digipa;

import android.provider.BaseColumns;

/**
 * Created by Charlene on 11/10/2014.
 */
public final class DigiPAContract {
    // prevent someone from instantiating contract class
    public DigiPAContract(){}

    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_DESCRIPTION = "description";
    public static final String COLUMN_NAME_CATEGORY = "category";
    public static final String COLUMN_NAME_HIGH_PRI = "high_priority";

    /*Inner class that defines table contents*/
    public static abstract class DPAEvent implements BaseColumns {
        public static final String TABLE_NAME = "event";
        public static final String COLUMN_NAME_START_DATE = "start_date";
        public static final String COLUMN_NAME_START_TIME = "start_time";
        public static final String COLUMN_NAME_END_DATE = "end_date";
        public static final String COLUMN_NAME_END_TIME = "end_time";
        public static final String COLUMN_NAME_LOCATION = "location";

    }

    public static abstract class DPATask implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_DUE_DATE = "due_date";
        public static final String COLUMN_NAME_IS_COMPLETE = "is_complete";
    }


}
