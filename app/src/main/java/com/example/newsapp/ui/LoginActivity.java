package com.example.newsapp.ui;

import static com.example.newsapp.utils.constants.STATIC_PASSWORD;
import static com.example.newsapp.utils.constants.STATIC_USERNAME;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.newsapp.R;
import com.example.newsapp.utils.SharedPreferencesHelper;

public class LoginActivity extends AppCompatActivity {
    SharedPreferencesHelper sharedPreferencesHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPreferencesHelper = new SharedPreferencesHelper(this);
        // Check if the user is already logged in
        if (sharedPreferencesHelper.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            finish();
        }

        Button loginBtn = findViewById(R.id.btn_login);
        EditText usernameInput = findViewById(R.id.username);
        EditText passwordInput = findViewById(R.id.password);

        loginBtn.setOnClickListener(v -> {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();

            if (username.equals(STATIC_USERNAME) && password.equals(STATIC_PASSWORD)) {
                sharedPreferencesHelper.setLoggedIn(true);
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                Toast.makeText(this, "Logged in successfully !", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Invalid username or password!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}