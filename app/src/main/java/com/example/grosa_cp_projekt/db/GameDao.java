package com.example.grosa_cp_projekt.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GameDao {

    @Query("SELECT * FROM game")
    List<Game> getAll();

    @Insert
    void insert(Game game);

    @Insert
    long[] insertAll(Game... games);

    @Delete
    void delete(Game game);

    @Update
    void update(Game game);

}