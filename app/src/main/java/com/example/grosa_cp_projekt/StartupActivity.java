package com.example.grosa_cp_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grosa_cp_projekt.db.AdminPassword;
import com.example.grosa_cp_projekt.db.DatabaseClient;

import java.util.List;

public class StartupActivity extends AppCompatActivity {

    private DatabaseClient mDb;

    private Handler handler = new Handler();

    private int startup_time =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        mDb = DatabaseClient.getInstance(this);

        handler.postDelayed(new Runnable() {
            public void run() {
                /**
                 * Création d'une classe asynchrone pour récupérer le mot de passe
                 */
                class CheckAdminPassword extends AsyncTask<Void, Void, AdminPassword> {

                    @Override
                    protected AdminPassword doInBackground(Void... voids) {
                        List<AdminPassword> adminPasswords = mDb.getAppDatabase().adminPasswordDao().getAll();
                        if (adminPasswords.size() > 0) {
                            return adminPasswords.get(0);
                        } else {
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(AdminPassword adminPassword) {
                        super.onPostExecute(adminPassword);
                        if (adminPassword == null) {
                            setContentView(R.layout.create_password);
                        } else {
                            startConnectActivity();
                        }
                    }
                }

                CheckAdminPassword cap = new CheckAdminPassword();
                cap.execute();

            }
        }, startup_time);
    }

    private void startConnectActivity() {
        Intent intent = new Intent(this, ConnectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        this.finish();
    }

    public void onCreatePasswordButtonPushed(View view) {
        EditText passwordEdit = findViewById(R.id.password_edit);
        String password = passwordEdit.getText().toString();
        if (password.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.password_empty, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            AdminPassword adminPassword = new AdminPassword();
            adminPassword.setPassword(password);

            class SetAdminPassword extends AsyncTask<AdminPassword, Void, Void> {

                @Override
                protected Void doInBackground(AdminPassword... adminPasswords) {
                    mDb.getAppDatabase().adminPasswordDao().insert(adminPasswords[0]);
                    return null;
                }
            }

            SetAdminPassword setAdminPassword = new SetAdminPassword();
            setAdminPassword.execute(adminPassword);

            startConnectActivity();
        }

    }
}
