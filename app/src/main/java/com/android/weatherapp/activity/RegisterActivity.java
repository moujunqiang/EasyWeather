package com.android.weatherapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.android.weatherapp.R;
import com.android.weatherapp.db.UserDao;
import com.android.weatherapp.helper.InputTextHelper;


/**
 * desc   : 注册界面
 */
public final class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    EditText mCodeView;
    EditText mPasswordView1;
    EditText mPasswordView2;
    Button mCommitView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }


    protected void initView() {
        mCodeView = findViewById(R.id.et_register_code);
        mPasswordView1 = findViewById(R.id.et_register_password1);
        mPasswordView2 = findViewById(R.id.et_register_password2);
        mCommitView = findViewById(R.id.btn_register_commit);

        InputTextHelper.with(this)
                .addView(mCodeView)
                .addView(mPasswordView1)
                .addView(mPasswordView2)
                .setMain(mCommitView)
                .setListener(new InputTextHelper.OnInputTextListener() {

                    @Override
                    public boolean onInputChange(InputTextHelper helper) {

                        return mPasswordView1.getText().toString().equals(mPasswordView2.getText().toString());
                    }
                })
                .build();
        findViewById(R.id.btn_login_commit).setOnClickListener(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register_commit:
                regist();
                break;

        }
    }

    public void regist() {

        UserDao userdao = new UserDao(this);

        Cursor cursor = userdao.query(mCodeView.getText().toString(), mPasswordView1.getText().toString());
        if (cursor.moveToNext()) {
            Toast.makeText(getApplicationContext(), "该用户已被注册，请重新输入", Toast.LENGTH_LONG).show();
        } else {
            userdao.insertUser(mCodeView.getText().toString(), mPasswordView1.getText().toString());
            cursor.close();
            Toast.makeText(getApplicationContext(), "用户注册成功，请前往登录", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.putExtra("phone", mCodeView.getText().toString());
            intent.putExtra("password", mPasswordView1.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}