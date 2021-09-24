package com.anonymous.loginsignup;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "database";
    public static final int DATABASE_VERsION = 1;

    String createTableUser = "CREATE TABLE IF NOT EXISTS 'users' ( 'id' INTEGER PRIMARY KEY AUTOINCREMENT, 'username' TEXT," +
            " 'password' TEXT, 'email' TEXT, 'country' TEXT, 'dob' TEXT, 'gender' TEXT )";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERsION);
        getWritableDatabase().execSQL(createTableUser);
    }

    public void insertUser(ContentValues contentValues) {
        getWritableDatabase().insert("user", "", contentValues);
    }

    public boolean isLoginValid(String username, String password) {
        String sql = "Select count(*) from user where username='" + username + "' and password='" + password + "'";
        SQLiteStatement sqLiteStatement = getReadableDatabase().compileStatement(sql);
        long l = sqLiteStatement.simpleQueryForLong();
        sqLiteStatement.close();
        return l == 1;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUser = "CREATE TABLE IF NOT EXISTS 'users' ( 'id' INTEGER PRIMARY KEY AUTOINCREMENT, 'username' TEXT," +
                " 'password' TEXT, 'email' TEXT, 'country' TEXT, 'dob' TEXT, 'gender' TEXT )";
        db.execSQL(createTableUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS user";
        db.execSQL(sql);

    }
}
