package org.o6u.studentapp.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.o6u.studentapp.R;
import org.o6u.studentapp.registrationActivities.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
