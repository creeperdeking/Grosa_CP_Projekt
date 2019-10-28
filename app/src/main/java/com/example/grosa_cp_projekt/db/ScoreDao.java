package com.example.grosa_cp_projekt.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ScoreDao {

    @Query("SELECT * FROM score")
    List<Score> getAll();

    @Query("SELECT * FROM score WHERE :userId = user_id")
    List<Score> getAllUserScore(Integer userId);

    @Insert
    void insert(Score score);

    @Insert
    long[] insertAll(Score... scores);

    @Delete
    void delete(Score score);

    @Update
    void update(Score score);

}