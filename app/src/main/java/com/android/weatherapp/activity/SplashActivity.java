package com.android.weatherapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.weatherapp.R;
import com.android.weatherapp.helper.SPUtils;


/**
 * desc   : 闪屏界面
 */
public final class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if ((Boolean) SPUtils.get(SplashActivity.this, "isLogin", false)) {//判断是否登录
                    if ((Boolean) SPUtils.get(SplashActivity.this, "isAutoLogin", false)) {//是否设置了自动登录
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));

                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));


                }
                finish();
            }
        }, 3000);
    }


}