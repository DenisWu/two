package com.denis.phoneguard.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {

    public final static int VERSION = 1;
    private final static String DB_NAME = "phoneguard.db";

    private DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static DBHelper newInstance(Context context) {
        return new DBHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + BlackNameList.TABLE_NAME + "(" +
                BlackNameList.UUID + " integer primary key autoincrement," + BlackNameList
                .PHONE + " varchar," + BlackNameList.MODE + " varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static final class BlackNameList implements BaseColumns {

        public static final String UUID = "UUID";
        public static final String PHONE = "PHONE";
        public static final String MODE = "MODE";

        public static final String TABLE_NAME = "BlackName";

    }

}
