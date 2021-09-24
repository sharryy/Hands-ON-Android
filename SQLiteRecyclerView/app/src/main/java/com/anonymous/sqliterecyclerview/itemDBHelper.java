package com.anonymous.sqliterecyclerview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class itemDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "itemlist.db";
    public static final int DATABASE_VERSION = 1;

    private Context context;

    public itemDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_GROCERYLIST_TABLE = "CREATE TABLE " +
                ItemContract.ItemEntry.TABLE_NAME + " (" +
                ItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemContract.ItemEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ItemContract.ItemEntry.COLUMN_AMOUNT + " INTEGER NOT NULL, " +
                ItemContract.ItemEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ");";
        db.execSQL(SQL_CREATE_GROCERYLIST_TABLE);

        Toast.makeText(context, "TAble Created Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ItemContract.ItemEntry.TABLE_NAME);
        onCreate(db);
    }
}
