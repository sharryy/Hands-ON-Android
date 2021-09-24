package com.anonymous.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anonymous.jobscheduler.service.MyService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Bundle bundle = intent.getExtras();
//            if (bundle != null) {
//                int val  = bundle.getInt(MyService.VAL);
//                textView.setText(String.valueOf(val));
//                Log.i("JOB", "onReceive: " + val);
//            }
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(Objects.requireNonNull(bundle.getString(MyService.VAL)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    assert jsonObject != null;
                    textView.setText(jsonObject.getString("title"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, new IntentFilter(MyService.ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button job = findViewById(R.id.button);
        Button cancelButton = findViewById(R.id.cancel_job);
        textView = findViewById(R.id.textView);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                assert jobScheduler != null;
                jobScheduler.cancel(101);
                Log.i("JOB", "onClick: Job Cancelled");
            }
        });

        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

                JobInfo jobInfo = new JobInfo.Builder(101, new ComponentName(getApplicationContext(),
                        MyService.class))
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                        .setPersisted(true)
                        .setPeriodic(15*60*1000)
                        .build();

                assert jobScheduler != null;
                jobScheduler.schedule(jobInfo);
            }
        });
    }
}
