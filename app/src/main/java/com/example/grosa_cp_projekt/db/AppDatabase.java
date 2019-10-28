package com.example.grosa_cp_projekt.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Game.class, Score.class, User.class, AdminPassword.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract GameDao gameDao();

    public abstract UserDao userDao();

    public abstract ScoreDao scoreDao();

    public abstract AdminPasswordDao adminPasswordDao();
}