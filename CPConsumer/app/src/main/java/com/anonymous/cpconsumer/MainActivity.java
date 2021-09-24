package com.anonymous.cpconsumer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShowAllAsync(getApplicationContext()).execute();

            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class ShowAllAsync extends AsyncTask<Void, Void, String> {
        private final WeakReference<Context> weakReference;

        public ShowAllAsync(Context applicationContext) {
            this.weakReference = new WeakReference<>(getApplicationContext());
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
        }

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder builder;
            try {
                @SuppressLint("Recycle") Cursor cursor = weakReference.get().getContentResolver()
                        .query(Uri.parse("content://com.anonymous.dreamprovider/dream_tbl"), null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                builder = new StringBuilder();

                while (!cursor.isAfterLast()) {
                    builder.append("\n")
                            .append(cursor.getString(cursor.getColumnIndex("id")))
                            .append(". ")
                            .append(cursor.getString(cursor.getColumnIndex("name")));

                    cursor.moveToNext();
                }
                return builder.toString();
            } catch (Exception e) {
                Log.d("TAG", "doInBackground: ");
            }
            return null;
        }
    }
}
