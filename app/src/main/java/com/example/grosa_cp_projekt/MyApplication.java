package com.example.grosa_cp_projekt;

import android.app.Application;

import com.example.grosa_cp_projekt.db.Game;
import com.example.grosa_cp_projekt.db.User;

public class MyApplication extends Application {
    private User user = null;
    private boolean isAdmin = false;
    private Game currentGame = null;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }
}
