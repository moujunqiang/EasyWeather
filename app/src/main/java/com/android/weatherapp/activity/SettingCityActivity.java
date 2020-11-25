package com.android.weatherapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.android.weatherapp.R;
import com.android.weatherapp.helper.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 选择默认的城市
 */
public class SettingCityActivity extends AppCompatActivity {
    RecyclerView rvCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_city);
        rvCity = findViewById(R.id.rv_city);

        String mWeatherId = (String) SPUtils.get(this, "weatherId", "杭州");

        final BaseQuickAdapter adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_city_select) {

            @Override
            protected void convert(@NonNull BaseViewHolder helper, String item) {
                int position = helper.getPosition();
                helper.setText(R.id.tv_name, item);
                if (mWeatherId.equals(item)) {
                    helper.getView(R.id.iv_select).setVisibility(VISIBLE);
                } else {
                    helper.getView(R.id.iv_select).setVisibility(GONE);

                }

            }
        };
        List<String> city = SPUtils.getDataList(SettingCityActivity.this, "city");
        adapter.setNewData(city);
        rvCity.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SPUtils.put(SettingCityActivity.this, "weatherId", city.get(position));
                finish();
            }
        });

        rvCity.setAdapter(adapter);
    }
}