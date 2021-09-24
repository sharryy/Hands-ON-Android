package com.anonymous.crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.anonymous.crud.Constants.DatabaseColumns;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "student.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TAG = DatabaseHandler.class.getSimpleName();

    public Context mContext;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_STUDENT_TABLE = "CREATE TABLE " +
                DatabaseColumns.TABLE_NAME + " (" +
                DatabaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseColumns.COLUMN_UNIVERSITY + " TEXT NOT NULL, " +
                DatabaseColumns.COLUMN_STUDENT_ID + " INTEGER NOT NULL UNIQUE, " +
                DatabaseColumns.COLUMN_FIRST_NAME + " TEXT NOT NULL UNIQUE, " +
                DatabaseColumns.COLUMN_LAST_NAME + " TEXT NOT NULL UNIQUE, " +
                DatabaseColumns.COLUMN_DEPARTMENT + " TEXT NOT NULL, " +
                DatabaseColumns.COLUMN_FEE + " INTEGER NOT NULL, " +
                DatabaseColumns.COLUMN_IS_SUBMITTED + " INTEGER NOT NULL DEFAULT 0, " +
                DatabaseColumns.COLUMN_IS_PASSED + " INTEGER NOT NULL DEFAULT 0, " +
                DatabaseColumns.COLUMN_REMARKS + " TEXT NOT NULL" + ");";
        db.execSQL(CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseColumns.TABLE_NAME);
        onCreate(db);
    }
}
