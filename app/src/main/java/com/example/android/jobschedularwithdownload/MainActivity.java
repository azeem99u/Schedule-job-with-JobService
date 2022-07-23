package com.example.android.jobschedularwithdownload;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int JOB_ID = 1001;
    private static final String TAG = "myTag";
    private Button button;
    private Button button2;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button.setOnClickListener(view -> {

            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

            @SuppressLint("JobSchedulerService")
            ComponentName componentName = new ComponentName(this,MyDownloadJob.class);

            JobInfo jobInfo = new JobInfo.Builder(JOB_ID,componentName)
                    //.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .setMinimumLatency(2000)
                    .setRequiresCharging(true)
                    .setPeriodic(15*60*1000)
                    .setPersisted(true) //device reBoot
                    .build();

            int result = jobScheduler.schedule(jobInfo);

            if(result == JobScheduler.RESULT_SUCCESS){
                Log.d(TAG, "onCreate: Job Scheduled");
            }else{
                Log.d(TAG, "onCreate: Job not scheduled");
            }
        });

        button2.setOnClickListener(view -> {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            jobScheduler.cancel(JOB_ID);
        });
    }

}