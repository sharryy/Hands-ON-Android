package com.anonymous.jobscheduler.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class MyService extends JobService {
    public static final String TAG = "JOB";
    public static final String ACTION = "com.anonymous.MY_APP";
    public static final String VAL = "val";
    public static final String STRING_URL = "https://jsonplaceholder.typicode.com/todos/1";

    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: " + params.getJobId());
        downloadJSON(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob: " + params.getJobId());
        jobCancelled = true;
        return true;
    }

    private void downloadJSON(final JobParameters parameters) {

        Thread thread = new Thread(){
            @Override
            public void run() {
                Intent jobIntent = new Intent(ACTION);
                try {
                    URL url = new URL(STRING_URL);
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                    httpsURLConnection.setRequestMethod("GET");
                    httpsURLConnection.connect();

                    int responseCode = httpsURLConnection.getResponseCode();
                    if (responseCode != 200) {
                        throw new RuntimeException("Https Response Code: " + responseCode);
                    } else {
                        Scanner scanner = new Scanner(url.openStream());
                        StringBuilder builder = new StringBuilder();
                        while (scanner.hasNext()) {
                            if (jobCancelled)
                                return;
                            builder.append(scanner.nextLine());
                        }

                        Log.i(TAG, "Downloaded Text: " + builder.toString());

                        //Make it A JSON Object
                        JSONObject jsonObject = new JSONObject(String.valueOf(builder));
                        Log.i(TAG, "JSON: " + jsonObject.getString("title"));

                        scanner.close();

                        jobIntent.putExtra(VAL, jsonObject.toString());
                        sendBroadcast(jobIntent);

                        jobFinished(parameters, false);

                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
