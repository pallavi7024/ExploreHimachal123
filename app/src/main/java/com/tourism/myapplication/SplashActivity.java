package com.tourism.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            Intent iHome = new Intent(SplashActivity.this,
                    (FirebaseAuth.getInstance().getCurrentUser() == null) ? LoginActivity.class : MainActivity.class);
            startActivities(new Intent[]{iHome});
            finish();
        }, 500);

    }
}