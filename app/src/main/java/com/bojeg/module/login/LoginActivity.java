package com.bojeg.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.bojeg.Database;
import com.bojeg.PreferencesManager;
import com.bojeg.R;
import com.bojeg.model.User;
import com.bojeg.module.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_user_name)
    EditText etUserName;

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_login)
    public void login() {
        String userName = etUserName.getText().toString();
        if (TextUtils.isEmpty(userName.trim())) {
            Toast.makeText(this, "Username can not empty", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(userName);
        String userId = Database.saveUser(user);
        PreferencesManager.with(this).setUserId(userId);

        startActivity(new Intent(this, HomeActivity.class));
    }
}
