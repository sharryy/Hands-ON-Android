package com.anonymous.simpleservice;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends IntentService {
    public static final String TAG = "Running Service";
    public static final String KEY_NUM = "key_num";
    public static final String SUM = "sum";
    public static final String RESULT = "result";
    private int result = Activity.RESULT_CANCELED;
    public static final String NOTIFICATION = "com.anonymous.simpleservice.MyService";

    public MyService() {
        super("My Intent Service");
    }


    private int sum(int a) {
        return a + a;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        assert intent != null;
        int a = intent.getIntExtra(KEY_NUM, 1);
        int sum = sum(a);
        Log.i(TAG, "onHandleIntent: " + sum);

        result = Activity.RESULT_OK;
        publishSum(sum, result);

    }

    private void publishSum(int sum, int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(SUM, sum);
        intent.putExtra(RESULT, result);

        sendBroadcast(intent);
    }
}
