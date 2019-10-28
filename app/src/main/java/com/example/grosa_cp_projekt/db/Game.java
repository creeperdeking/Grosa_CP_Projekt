package com.example.grosa_cp_projekt.db;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Game implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "game_name_id")
    @NonNull
    private Integer gameNameId;

    @ColumnInfo(name = "image_id")
    @NonNull
    private Integer imageId;

    @ColumnInfo(name = "explanation_id")
    @NonNull
    private Integer explanationId;

    @ColumnInfo(name = "explanation_image_id")
    private Integer explanationImageId;

    @ColumnInfo(name = "activity_classname")
    @NonNull
    private String activityClassname;

    public Game(int id, Integer gameNameId, Integer imageId, Integer explanationId, Integer explanationImageId, String activityClassname) {
        this.id = id;
        this.gameNameId = gameNameId;
        this.imageId = imageId;
        this.explanationId = explanationId;
        this.explanationImageId = explanationImageId;
        this.activityClassname = activityClassname;
    }

    /*
     * Getters and Setters
     * */

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public Integer getGameNameId() {
        return gameNameId;
    }

    public void setGameNameId(@NonNull Integer gameNameId) {
        this.gameNameId = gameNameId;
    }

    public Integer getExplanationId() {
        return explanationId;
    }

    public void setExplanationId(Integer explanationId) {
        this.explanationId = explanationId;
    }

    public Integer getExplanationImageId() {
        return explanationImageId;
    }

    public void setExplanationImageId(Integer explanationImageId) {
        this.explanationImageId = explanationImageId;
    }

    public String getActivityClassname() {
        return activityClassname;
    }

    public void setActivityClassname(String activityClassname) {
        this.activityClassname = activityClassname;
    }
}