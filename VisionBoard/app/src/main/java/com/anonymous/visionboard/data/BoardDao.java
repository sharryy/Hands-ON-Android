package com.anonymous.visionboard.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.anonymous.visionboard.model.Board;

import java.util.List;

@Dao
public interface BoardDao {

    @Insert
    long insert(Board board);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateBoard(Board board);

    @Query("DELETE FROM board_tbl where id = :id")
    int deleteBoard(int id);

    @Query("SELECT * FROM board_tbl")
    LiveData<List<Board>> loadAllBoards();

    @Query("SELECT * FROM board_tbl WHERE id = :id")
    LiveData<Board> loadBoardById(int id);

    @Query("DELETE FROM board_tbl")
    void deleteAllBoards();
}
