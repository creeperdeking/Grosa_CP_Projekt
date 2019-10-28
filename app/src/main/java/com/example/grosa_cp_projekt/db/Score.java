package com.example.grosa_cp_projekt.db;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.io.Serializable;

@Entity(primaryKeys = {"user_id", "game_id"}/*,
        foreignKeys = {@ForeignKey(entity = Game.class,
                parentColumns = "id",
                childColumns = "game_id",
                onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE)}*/)
public class Score implements Serializable {

    @ColumnInfo(name = "user_id")
    @NonNull
    private Integer userId;

    @ColumnInfo(name = "game_id")
    @NonNull
    private Integer gameId;

    @ColumnInfo(name = "score")
    private Integer score;

    /*
     * Getters and Setters
     * */

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}