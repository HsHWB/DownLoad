package com.example.root.Service;

import android.content.Context;
import android.content.Intent;

import com.example.root.db.ThreadDAO;
import com.example.root.db.ThreadDAOImpl;
import com.example.root.entities.FileInfo;
import com.example.root.entities.threadInfo;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

/**
 * Created by root on 15-7-29.
 */
public class DownLoadTask {

    private Context mContext = null;
    private FileInfo mFileInfo = null;
    private ThreadDAO mDao = null;
    private int mFinished = 0;
    public boolean isPause = false;

    public DownLoadTask(Context mContext, FileInfo mFileInfo) {
        this.mContext = mContext;
        this.mFileInfo = mFileInfo;
        this.mDao = new ThreadDAOImpl(mContext);
    }

    public void DownLoad(){
        List<threadInfo> threadInfos = mDao.getThreads(mFileInfo.getFileUrl());
        threadInfo threadInfo = null;
        if (threadInfos.size() == 0){
            threadInfo = new threadInfo(0,mFileInfo.getFileUrl(),0,mFileInfo.getLength(),0);
        }else{
            threadInfo = threadInfos.get(0);
        }
        new DownLoadThread(threadInfo).start();
    }

    class DownLoadThread extends Thread{
        private threadInfo mThreadInfo = null;

        public DownLoadThread(threadInfo threadInfo) {
            this.mThreadInfo = threadInfo;
        }

        public void run(){
            HttpURLConnection conn = null;
            InputStream inputStream = null;
            RandomAccessFile raf = null;
            if (mDao.isExit(mThreadInfo.getThreadUrl(), Integer.valueOf(mThreadInfo.getThreadId()))){
                mDao.insertThread(mThreadInfo);
            }
            try {
                URL url = new URL(mThreadInfo.getThreadUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                int start = mThreadInfo.getStart()+mThreadInfo.getFinished();
                conn.setRequestProperty("Range", "bytes=" + start + "-" + mThreadInfo.getEnd());
                File file = new File(DownloadService.DOWN_PATH, mFileInfo.getFileName());
                raf = new RandomAccessFile(file,"rwd");
                raf.seek(start);

                Intent intent = new Intent();
                mFinished += mThreadInfo.getFinished();
                if (conn.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT){
                    inputStream = conn.getInputStream();
                    byte[] buffer = new byte[1024*4];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while((len = inputStream.read(buffer)) != -1){
                        raf.write(buffer, 0, len);
                        mFinished += len;
                        if (System.currentTimeMillis() - time > 500) {
                            intent.putExtra("finished", mFinished * 100 / mFileInfo.getLength());
                            mContext.sendBroadcast(intent);
                        }
                        if (isPause){
                            mDao.updateThread(mThreadInfo.getThreadUrl(), mThreadInfo.getThreadId(),
                                    mFinished);
                            return;
                        }
                    }
                    mDao.delectThread(mThreadInfo.getThreadUrl(), mThreadInfo.getThreadId());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                conn.disconnect();
                try {
                    inputStream.close();
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
