package com.bojeg.module.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.bojeg.PreferencesManager;
import com.bojeg.module.home.HomeActivity;
import com.bojeg.module.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userId = PreferencesManager.with(this).getUserId();
        if (TextUtils.isEmpty(userId)) {
            openNextActivity(LoginActivity.class);
        }
        else {
            openNextActivity(HomeActivity.class);
        }
    }

    private void openNextActivity(Class<? extends AppCompatActivity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
