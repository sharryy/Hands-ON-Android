package com.anonymous.simpleservice;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        =
        @Override
        @SuppressLint("SetTextI18n")
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                int sum = bundle.getInt(MyService.SUM);
                int resultCode = bundle.getInt(MyService.RESULT);

                if (resultCode == RESULT_OK) {
                    textView.setText(MessageFormat.format("Process Completed with Sum = {0}", sum));
                } else {
                    textView.setText("Process Failed");
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(MyService.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start Service
                Intent intent = new Intent(MainActivity.this, MyService.class);
                intent.putExtra(MyService.KEY_NUM, 90);
                startService(intent);
            }
        });
    }
}
