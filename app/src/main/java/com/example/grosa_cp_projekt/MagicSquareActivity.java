package com.example.grosa_cp_projekt;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MagicSquareActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, ChooseDifficulty.class);
        intent.putExtra(ChooseDifficulty.EXTRA_PARAMETERS_KEY, "");
        intent.putExtra(ChooseDifficulty.LAUNCH_ACTIVITY_NAME_KEY, MagicSquareGameActivity.class.getName());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}
