package com.example.grosa_cp_projekt.db;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(primaryKeys = {"password"})
public class AdminPassword implements Serializable {

    @ColumnInfo(name = "password")
    @NonNull
    private String password;

    /*
     * Getters and Setters
     * */

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}