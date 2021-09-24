package com.anonymous.dreamprovider;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.anonymous.dreamprovider.model.Dream;

@Dao
public interface DreamDao {

    @Insert
    long insert(Dream dream);

    @Query("SELECT * FROM dream_tbl")
    Cursor findAll();

    @Query("DELETE FROM dream_tbl WHERE id = :id")
    int delete(long id);

    @Query("DELETE FROM dream_tbl")
    int deleteAll();

    @Update
    int update(Dream dream);

}
