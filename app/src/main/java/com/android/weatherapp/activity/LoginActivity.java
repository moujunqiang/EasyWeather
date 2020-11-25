package com.android.weatherapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.android.weatherapp.R;
import com.android.weatherapp.db.UserDao;
import com.android.weatherapp.helper.InputTextHelper;
import com.android.weatherapp.helper.SPUtils;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;


/**
 * desc   : 登录界面
 */
public final class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mLogoView;

    LinearLayout mBodyLayout;
    EditText mPhoneView;
    EditText mPasswordView;
    Button mCommitView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }


    protected void initView() {
        mLogoView = findViewById(R.id.iv_login_logo);
        mBodyLayout = findViewById(R.id.ll_login_body);
        mPhoneView = findViewById(R.id.et_login_phone);
        mPasswordView = findViewById(R.id.et_login_password);
        mCommitView = findViewById(R.id.btn_login_commit);
        mCommitView.setOnClickListener(this);
        InputTextHelper.with(this)
                .addView(mPhoneView)
                .addView(mPasswordView)
                .setMain(mCommitView)
                .build();
        TitleBar titleBar = findViewById(R.id.title);
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_commit:
                login();
                break;
            default:
                break;
        }
    }

    public void login() {

        UserDao userdao = new UserDao(LoginActivity.this);
        Cursor cursor = userdao.query(mPhoneView.getText().toString(), mPasswordView.getText().toString());
        if (cursor.moveToNext()) {
            SPUtils.put(LoginActivity.this, "isLogin", true);
            SPUtils.put(LoginActivity.this, "name", mPhoneView.getText().toString());
            // 处理登录
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            cursor.close();
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "密码验证失败，请重新验证登录", Toast.LENGTH_SHORT).show();
        }

    }


}