package com.example.grosa_cp_projekt.db;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.grosa_cp_projekt.R;

import java.util.ArrayList;
import java.util.List;

public class DatabaseClient {

    // Instance unique permettant de faire le lien avec la base de données
    private static DatabaseClient instance;

    // Objet représentant la base de données de votre application
    private AppDatabase appDatabase;

    // Constructeur
    private DatabaseClient(final Context context) {

        // Créer l'objet représentant la base de données de votre application
        // à l'aide du "Room database builder"
        // MyToDos est le nom de la base de données
        //appDatabase = Room.databaseBuilder(context, AppDatabase.class, "MyToDos").build();

        // Ajout de la méthode addCallback permettant de populate (remplir) la base de données à sa création
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "AppCPProjekt").addCallback(roomDatabaseCallback).build();
    }

    // Méthode statique
    // Retourne l'instance de l'objet DatabaseClient
    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    // Retourne l'objet représentant la base de données de votre application
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    // Objet permettant de populate (remplir) la base de données à sa création
    RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {

        // Called when the database is created for the first time.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);


            // Création des jeux principaux
            db.execSQL("INSERT INTO game (game_name_id, image_id, explanation_id, activity_classname) " +
                    "VALUES(\"" + R.string.operation_game_name + "\", "+ R.drawable.operations +", \"" + R.string.explanation_operation + "\", \"com.example.grosa_cp_projekt.OperationActivity\");");
            db.execSQL("INSERT INTO game (game_name_id, image_id, explanation_id, activity_classname) " +
                    "VALUES(\"" + R.string.questions_game_name + "\", "+ R.drawable.questions +", \"" + R.string.explanation_questions + "\", \"com.example.grosa_cp_projekt.QuestionsActivity\");");
            db.execSQL("INSERT INTO game (game_name_id, image_id, explanation_id, activity_classname) " +
                    "VALUES(\"" + R.string.magic_square_game_name + "\", "+ R.drawable.magicsquare +", \"" + R.string.explanation_magicsquare + "\", \"com.example.grosa_cp_projekt.MagicsquareActivity\");");
            db.execSQL("INSERT INTO game (game_name_id, image_id, explanation_id, activity_classname) " +
                    "VALUES(\"" + R.string.crossed_words_game_name + "\", "+ R.drawable.crossword +", \"" + R.string.explanation_crossedWords + "\", \"com.example.grosa_cp_projekt.CrosswordsActivity\");");

            db.execSQL("INSERT INTO user (name, surname, image_id) VALUES(\"Anne\", \"Eonym\", " + R.drawable.user_1 +");");

            db.execSQL("INSERT INTO score (user_id, game_id, score) VALUES((SELECT id FROM user WHERE name=\"Anne\" LIMIT 1), (SELECT id FROM game WHERE game_name_id=\"" + R.string.operation_game_name + "\"LIMIT 1), 10);");
            db.execSQL("INSERT INTO score (user_id, game_id, score) VALUES((SELECT id FROM user WHERE name=\"Anne\" LIMIT 1), (SELECT id FROM game WHERE game_name_id=\"" + R.string.questions_game_name + "\"LIMIT 1), 10);");
            db.execSQL("INSERT INTO score (user_id, game_id, score) VALUES((SELECT id FROM user WHERE name=\"Anne\" LIMIT 1), (SELECT id FROM game WHERE game_name_id=\"" + R.string.magic_square_game_name + "\"LIMIT 1), 10);");
            db.execSQL("INSERT INTO score (user_id, game_id, score) VALUES((SELECT id FROM user WHERE name=\"Anne\" LIMIT 1), (SELECT id FROM game WHERE game_name_id=\"" + R.string.crossed_words_game_name + "\"LIMIT 1), 10);");

        }
    };
}
