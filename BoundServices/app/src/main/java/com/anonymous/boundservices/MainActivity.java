package com.anonymous.boundservices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anonymous.boundservices.Service.MyService;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Service Running";

    private TextView textView;

    private MyService myService;
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.ServiceBinder serviceBinder = (MyService.ServiceBinder) service;
            myService = serviceBinder.getService();
            Log.i(TAG, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (myService != null) {
                myService = null;
            }
            Log.i(TAG, "onServiceDisconnected: ");

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent serviceIntent = new Intent(this, MyService.class);
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "onStart: Service Started");
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
        Log.i(TAG, "onStop: Service Unbound");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textview);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(myService.serveValue());
            }
        });
    }
}
