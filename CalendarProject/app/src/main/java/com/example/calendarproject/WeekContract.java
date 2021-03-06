package com.example.calendarproject;

import android.provider.BaseColumns;

public class WeekContract {
    public static final String DB_NAME="week.db";
    public static final int DATABASE_VERSION = 2;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private WeekContract() {
    }

    /* Inner class that defines the table contents */
    public static class Weeks implements BaseColumns {
        public static final String TABLE_NAME="Weeks";
        public static final String KEY_TITLE = "title";
        public static final String KEY_START = "start";
        public static final String KEY_END = "end";
        public static final String KEY_ADDRESS = "address";
        public static final String KEY_MEMO = "memo";
        public static final String KEY_CAL = "cal";
        public static final String KEY_HOUR = "hour";



        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +KEY_TITLE + TEXT_TYPE + COMMA_SEP +
                KEY_START + TEXT_TYPE + COMMA_SEP + KEY_END + TEXT_TYPE + COMMA_SEP +
                KEY_ADDRESS + TEXT_TYPE + COMMA_SEP + KEY_MEMO + TEXT_TYPE + COMMA_SEP +
                KEY_CAL + TEXT_TYPE + COMMA_SEP + KEY_HOUR + TEXT_TYPE + " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
