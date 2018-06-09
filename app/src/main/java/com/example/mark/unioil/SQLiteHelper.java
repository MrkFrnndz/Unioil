package com.example.mark.unioil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jen on 6/5/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    static final String TABLE_DR = "TABLE_DR";
    static final String DR_NUMBER = "FIELD_NUMBER";
    static final String DR_USERNAME = "FIELD_USERNAME";
    static final String DR_CUSTOMERNAME = "FIELD_CUSTOMERNAME";
    private static final String DATABASE_NAME = "unioil.db";
    private static final int DATABASE_VERSION = 1;


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
