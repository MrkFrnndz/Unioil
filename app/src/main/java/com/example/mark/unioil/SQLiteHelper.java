package com.example.mark.unioil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jen on 6/5/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "unioil.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_DR = "TABLE_DR";
    public static final String DR_NUMBER = "FIELD_NUMBER";
    public static final String DR_USERNAME = "FIELD_USERNAME";
    public static final String DR_CUSTOMERNAME = "FIELD_CUSTOMERNAME";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_DR + " ("
                + DR_NUMBER + " TEXT PRIMARY KEY, "
                + DR_USERNAME + " TEXT NOT NULL UNIQUE, "
                + DR_CUSTOMERNAME + " TEXT NOT NULL UNIQUE );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_DR);
        onCreate(sqLiteDatabase);
    }
}
