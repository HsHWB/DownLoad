package com.example.root.download;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.root.Service.DownloadService;
import com.example.root.entities.FileInfo;


public class MainActivity extends Activity implements View.OnClickListener {

    private TextView mTextView = null;
    private Button begin = null;
    private Button end = null;
    private ProgressBar mProgressBar = null;
    private FileInfo fileInfo;
//    private String downLoadUrl = "http://p.gdown.baidu.com/cbcb9d7c67df6459dc4f11e689568e148b3baec09" +
//            "7308703b71bebc14c0baba46c03fc48f21717b98dcf3aae13e4a5d8c2ad1346c76a3f9a6d388e1156eaff3c" +
//            "0228996cc39e219b4dd4e14dc8d793104dfc3f3a1f268c46e38163c6791474d4af1e88e13242db8e8e1f50e1f" +
//            "379b8002ff0c460a349fd7ba2c99aa58be10161578bbb72a0acd9ce99a9924d2a34a6d8";
    private String downLoadUrl = "http://download.kugou.com/download/kugou_android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        begin = (Button)this.findViewById(R.id.begin);
        end = (Button)this.findViewById(R.id.end);
        mProgressBar = (ProgressBar)this.findViewById(R.id.progressBar);

        begin.setOnClickListener(this);
        end.setOnClickListener(this);

        fileInfo = new FileInfo("kugou.apk",
                "0", downLoadUrl, 0, 0);


    }

    @Override
    public void onClick(View v) {
        System.out.println("v.getId() == "+v.getId());
        switch (v.getId()){
            case R.id.begin:
                Intent intentBegin = new Intent(MainActivity.this, DownloadService.class);
                intentBegin.setAction(DownloadService.START);
                intentBegin.putExtra("fileInfo", fileInfo);
                startService(intentBegin);
                break;
            case R.id.end:
                Intent intentEnd = new Intent(MainActivity.this, DownloadService.class);
                intentEnd.setAction(DownloadService.STOP);
                intentEnd.putExtra("fileInfo", fileInfo);
                startService(intentEnd);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
