package com.example.android.jobschedularwithdownload;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

public class MyDownloadJob extends JobService {
    private static final String TAG = "myTag";
    private boolean isJobCancelled = false;
    private boolean mSuccess = false;

    public MyDownloadJob() {
    }

    //job scheduled
    //run on main thread
    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (isJobCancelled){
                    return;
                }
                int i = 0;
                while (i<10){
                    Log.d(TAG, "run: "+ i++);
                    SystemClock.sleep(1000);
                }
                Log.d(TAG, "run : DownloadCompleted");
                mSuccess = true;
                jobFinished(jobParameters,mSuccess);
            }
        }).start();

        //check job criteria like wifi, battery,wifi required, priority
        //define the service, release the awake lock
        // return true means work completed successfully, and service rescheduled

        //if we use background thread then return true;
        return true;
    }


    // this method execute when job not finished due to its condition, like wifi, fail.
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        //if any reason job was failed then this isJobC remove the thread;
        isJobCancelled = true;

        //if job was failed return true means job restart
        //return false means job not restart at that time;
        return true;
    }

}