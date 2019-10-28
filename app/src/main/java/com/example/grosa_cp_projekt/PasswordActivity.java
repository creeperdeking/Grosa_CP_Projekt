package com.example.grosa_cp_projekt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grosa_cp_projekt.db.DatabaseClient;

///
public class PasswordActivity extends AppCompatActivity {

    private DatabaseClient mDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);
        mDb = DatabaseClient.getInstance(this);
    }

    public void onTestPasswordButtonPushed(View view) {
        class TestAdminPassword extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                String adminPassword = mDb.getAppDatabase().adminPasswordDao().getAll().get(0).getPassword();

                return adminPassword;
            }

            @Override
            protected void onPostExecute(String adminPassword) {
                super.onPostExecute(adminPassword);
                String password = ((EditText)findViewById(R.id.password_edit)).getText().toString();
                if (adminPassword.equals(password)) {
                    Intent intent = new Intent(PasswordActivity.this, SettingsActivity.class);
                    ((MyApplication)getApplication()).setAdmin(true);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(PasswordActivity.this, R.string.wrong_password, Toast.LENGTH_LONG);
                    toast.show();
                    ((EditText)findViewById(R.id.password_edit)).setText("");
                }
            }
        }

        TestAdminPassword testAdminPassword = new TestAdminPassword();
        testAdminPassword.execute();
    }
}
