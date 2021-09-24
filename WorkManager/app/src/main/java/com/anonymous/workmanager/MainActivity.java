package com.anonymous.workmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.textView);

        Button runWork = findViewById(R.id.button);
        runWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constraints constraints = new Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build();

                //Pass Data to Worker
                Data data = new Data.Builder().putString(MyWorker.DATA_KEY, "DATA FROM ACTIVITY").build();

                /*
                Periodic  Work Request
                 */

                PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 10, TimeUnit.HOURS).build();

                final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class).setConstraints(constraints).setInputData(data).build();

                //Chaining Works
                WorkManager.getInstance(getApplicationContext()).enqueueUniquePeriodicWork("Unique", ExistingPeriodicWorkPolicy.KEEP
                , periodicWorkRequest);
//                       / .beginWith(workRequest).then(workRequest).then(workRequest).enqueue();

                WorkManager.getInstance(getApplicationContext()).enqueue(workRequest);
                WorkManager.getInstance(getApplicationContext())
                        .getWorkInfoByIdLiveData(workRequest.getId())
                        .observe(MainActivity.this, new Observer<WorkInfo>() {
                            @Override
                            public void onChanged(WorkInfo workInfo) {
                                if (workInfo.getState() ==  WorkInfo.State.SUCCEEDED){
                                    String result = workInfo.getOutputData().getString(MyWorker.DATA_KEY);
                                    textView.setText(result);

                                }
                            }
                        });
            }
        });
    }
}
