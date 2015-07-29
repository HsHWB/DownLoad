package com.example.root.db;

import com.example.root.entities.threadInfo;

import java.util.List;

/**
 * Created by root on 15-7-29.
 */
public interface ThreadDAO {

    public void insertThread(threadInfo threadInfo);
    public void delectThread(String url, int thread_id);
    public void updateThread(String url, int thread_id, int finished);
    public List<threadInfo> getThreads(String url);
    public boolean isExit(String url, int thread_id);

}
