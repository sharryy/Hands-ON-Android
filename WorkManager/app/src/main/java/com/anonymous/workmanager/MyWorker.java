package com.anonymous.workmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    public static final String TAG = "Work";
    public static final String DATA_KEY = "data_key";
    public static final String ANONYMOUS = "anonymous";
    public static final String TASK_NOTIFICATION = "task_notification";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i(TAG, "doWork: ");

        //Passing Data to activity
        Data data = new Data.Builder()
                .putString(DATA_KEY, "Hello From Done_Work")
                .build();

        //Receiving Data From Activity
        String result = getInputData().getString(MyWorker.DATA_KEY);
        Log.i(TAG, "doWork: " + result);

        displayNotification(result, data.getString(MyWorker.DATA_KEY));

        return Result.success(data);
    }
    private void displayNotification(String title, String messge){

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(ANONYMOUS, TASK_NOTIFICATION, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        Intent notificationIntent = new Intent(getApplicationContext(), ShowDetails.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
        
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), ANONYMOUS)
                .setContentTitle(title)
                .setContentText(messge)
                .setSmallIcon(android.R.drawable.star_on)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent))
                .setAutoCancel(true);

        notificationManager.notify(101, notification.build());

    }
}
