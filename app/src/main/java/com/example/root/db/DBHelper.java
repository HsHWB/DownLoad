package com.example.root.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 15-7-28.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "download.db";
    private static final int VERSION = 1;
    private static final String SQL_CREATE = "create table thread_info(_id interger primary key autoincrement," +
            "thread_id interger,url text,start interger,end interger,finished interger)";
    private static final String SQL_DROP = "drop table if exists thread_info";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }
}
