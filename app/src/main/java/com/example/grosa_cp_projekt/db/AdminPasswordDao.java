package com.example.grosa_cp_projekt.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AdminPasswordDao {

    @Query("SELECT * FROM adminpassword")
    List<AdminPassword> getAll();

    @Insert
    void insert(AdminPassword adminPassword);

    @Insert
    long[] insertAll(AdminPassword... adminPasswords);

    @Delete
    void delete(AdminPassword adminPassword);

    @Update
    void update(AdminPassword adminPassword);

}