package com.example.root.download;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.root.entities.FileInfo;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultUserTokenHandler;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;


/**
 * Created by root on 15-7-23.
 */
public class DownloadService extends Service{

    public static final String START = "START";
    public static final String STOP = "STOP";
    public static final int MSG_INIT = 1;
    public static final String DOWN_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/downloads/";

    public DownloadService(){
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (START.equals(intent.getAction())){
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            new DownThread(fileInfo).start();
        }else {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    System.out.println("Handle fileInfo == " + fileInfo.toString());
                    break;
            }
        }
    };

    class DownThread extends Thread{
        private FileInfo mFileInfo = null;

        public DownThread(FileInfo mFileInfo){
            this.mFileInfo = mFileInfo;
        }

        public void run(){
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try{
                URL url = new URL(mFileInfo.getFileUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");

                int length = -1;

                if (conn.getResponseCode() == HttpStatus.SC_OK){
                    length = conn.getContentLength();
                }

                if (length <= 0){
                    return;
                }

                File dir = new File(DOWN_PATH);
                if (!dir.exists()){
                    dir.mkdirs();
                }

                File file = new File(dir, mFileInfo.getFileName());
//                File file = new File(DOWN_PATH, mFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");

                raf.setLength(length);
                mFileInfo.setLength(length);
                mHandler.obtainMessage(MSG_INIT, mFileInfo).sendToTarget();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                conn.disconnect();
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * <url, void, return></>
     */
    class DownTask extends AsyncTask<String, Integer, String>{

        private FileInfo mFileInfo = null;

        public DownTask(FileInfo mFileInfo){
            this.mFileInfo = mFileInfo;
        }

        @Override
        protected String doInBackground(String... params) {

            String url = params[0];
            HttpResponse httpResponse = null;
            HttpGet httpGet = new HttpGet(url);
            try {
                httpResponse = new DefaultHttpClient().execute(httpGet);
                if (httpResponse.getStatusLine().getStatusCode() == 200){
                    String result = EntityUtils.toString(httpResponse.getEntity());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
