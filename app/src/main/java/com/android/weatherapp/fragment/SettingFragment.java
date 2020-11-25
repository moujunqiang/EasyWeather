package com.android.weatherapp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.android.weatherapp.R;
import com.android.weatherapp.activity.LoginActivity;
import com.android.weatherapp.activity.PasswordResetActivity;
import com.android.weatherapp.activity.SettingCityActivity;
import com.android.weatherapp.helper.SPUtils;
import com.android.weatherapp.weatherview.SettingBar;
import com.android.weatherapp.weatherview.SwitchButton;


/**
 * desc   : 設置
 */
public final class SettingFragment extends Fragment implements SwitchButton.OnCheckedChangeListener, View.OnClickListener {
    SwitchButton mAutoSwitchView;
    SettingBar mLocalSwitchView;
    SettingBar setHistory;
    TextView tvName;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    private View inflate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_setting, null, false);
        initView();
        return inflate;
    }

    protected void initView() {
        mAutoSwitchView = inflate.findViewById(R.id.sb_setting_switch);
        mLocalSwitchView = inflate.findViewById(R.id.sb_setting_local);
        setHistory = inflate.findViewById(R.id.sb_setting_cache);
        tvName = inflate.findViewById(R.id.tv_name);

        // 设置切换按钮的监听
        mAutoSwitchView.setChecked((Boolean) SPUtils.get(getContext(), "isAutoLogin", false));
        mAutoSwitchView.setOnCheckedChangeListener(this);
        tvName.setText((String) SPUtils.get(getContext(), "name", "用户名"));
        setHistory.setRightText((String) SPUtils.get(getContext(), "count", "7"));
        String mWeatherId = (String) SPUtils.get(getContext(), "weatherId", "");
        mLocalSwitchView.setRightText(mWeatherId);
        inflate.findViewById(R.id.sb_setting_exit).setOnClickListener(this);
        inflate.findViewById(R.id.sb_setting_cache).setOnClickListener(this);
        inflate.findViewById(R.id.sb_setting_pwd).setOnClickListener(this);
        inflate.findViewById(R.id.sb_setting_local).setOnClickListener(this);
        inflate.findViewById(R.id.sb_setting_auto).setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sb_setting_exit:
                // 退出登录
                SPUtils.put(getContext(), "isLogin", false);
                startActivity(new Intent(getContext(), LoginActivity.class));
                // 进行内存优化，销毁掉所有的界面
                break;
            case R.id.sb_setting_pwd:
                startActivity(new Intent(getContext(), PasswordResetActivity.class));

                break;
            case R.id.sb_setting_cache:
                //显示天数选择弹框
                final String[] items = {"1", "2", "3", "4", "5", "6", "7"};
                AlertDialog.Builder listDialog =
                        new AlertDialog.Builder(getContext());
                listDialog.setTitle("选择历史显示天数");
                listDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        setHistory.setRightText(items[which]);
                        //用sharepreference保存數據
                        SPUtils.put(getContext(), "count", items[which]);
                    }
                });

                listDialog.show();
                break;
            case R.id.sb_setting_local:
                startActivity(new Intent(getContext(), SettingCityActivity.class));
                break;

        }
    }

    @Override
    public void onCheckedChanged(SwitchButton button, boolean isChecked) {
        switch (button.getId()) {
            case R.id.sb_setting_switch:
                SPUtils.put(getContext(), "isAutoLogin", isChecked);
                break;
            case R.id.sb_setting_local:

                break;
        }
    }
}