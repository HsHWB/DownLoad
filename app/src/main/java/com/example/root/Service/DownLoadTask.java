package com.example.root.Service;

import android.content.Context;

import com.example.root.entities.FileInfo;
import com.example.root.entities.threadInfo;

import java.io.File;

/**
 * Created by root on 15-7-29.
 */
public class DownLoadTask {

    private Context mContext = null;
    private FileInfo mFileInfo = null;

    public DownLoadTask(Context mContext, FileInfo mFileInfo) {
        this.mContext = mContext;
        this.mFileInfo = mFileInfo;
    }

    class DownLoadThread extends Thread{
        private threadInfo threadInfo = null;

        public DownLoadThread(threadInfo threadInfo) {
            this.threadInfo = threadInfo;
        }

        public void run(){
            
        }
    }
}
