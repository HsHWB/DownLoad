package com.example.root.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.root.entities.threadInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 15-7-29.
 */
public class ThreadDAOImpl implements ThreadDAO{

    private DBHelper mHelper = null;

    public ThreadDAOImpl(Context context) {
        mHelper = new DBHelper(context);
    }

    @Override
    public void insertThread(threadInfo threadInfo) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("insert into thread_info(thread_id,url,start,end,finished)values(?,?,?,?,?)",
        new Object[]{threadInfo.getThreadId(),threadInfo.getThreadUrl(),threadInfo.getStart(),threadInfo.getEnd(),
        threadInfo.getFinished()});
        db.close();
    }

    @Override
    public void delectThread(String url, int thread_id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("delete from thread_info where url = ? and thread_id = ?",
                new Object[]{url,thread_id});
        db.close();
    }

    @Override
    public void updateThread(String url, int thread_id, int finished) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("update thread_info set finished = ? where url = ? and thread_id = ?",
                new Object[]{finished, url,thread_id});
        db.close();
    }

    @Override
    public List<threadInfo> getThreads(String url) {
        List<threadInfo> list = new ArrayList<>();
        threadInfo threadInfo = null;
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ?", new String[]{url});
        while (cursor.moveToNext()){
            threadInfo = new threadInfo();
            threadInfo.setThreadId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfo.setThreadUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            list.add(threadInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean isExit(String url, int thread_id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        threadInfo threadInfo = null;
        Cursor cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?", new String[]{url,
                thread_id+""});
        boolean exists = cursor.moveToNext();
        cursor.close();
        db.close();
        return exists;
    }
}
