package com.android.weatherapp.activity;

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
import com.android.weatherapp.helper.SPUtils;


/**
 * desc   : 重置密码
 */
public final class PasswordResetActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mPasswordView1;
    EditText mPasswordView2;
    EditText mPasswordViewOld;
    Button mCommitView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        mPasswordView2 = findViewById(R.id.et_password_reset_password2);
        mPasswordView1 = findViewById(R.id.et_password_reset_password1);
        mPasswordViewOld = findViewById(R.id.et_password_old);
        mCommitView = findViewById(R.id.btn_password_reset_commit);
        mCommitView.setOnClickListener(this);
    }




    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_password_reset_commit:
                UserDao userDao = new UserDao(this);
                String name = (String) SPUtils.get(this, "name", "");
                int i = userDao.updatePw(name, mPasswordViewOld.getText().toString(), mPasswordView1.getText().toString());
                if (i==1){
                    Toast.makeText(this, "密码重置成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(this, "修改失败请重试", Toast.LENGTH_SHORT).show();

                }


                break;

        }
    }
}