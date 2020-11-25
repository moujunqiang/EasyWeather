package com.android.weatherapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.android.weatherapp.R;
import com.android.weatherapp.db.WeatherDao;
import com.android.weatherapp.helper.SPUtils;
import com.android.weatherapp.weatherview.WeatherItemView;
import com.android.weatherapp.weatherview.WeatherModel;
import com.android.weatherapp.weatherview.WeatherView;

import java.util.ArrayList;
import java.util.List;


/**
 * desc   : 历史
 */
public final class WeatherHistoryFragment extends Fragment {
    WeatherView weatherView;
    SwipeRefreshLayout swipeRefreshLayout;

    public static WeatherHistoryFragment newInstance() {
        return new WeatherHistoryFragment();
    }

    private View inflate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_history, null, false);
        initView();
        return inflate;
    }

    protected void initView() {
        weatherView = inflate.findViewById(R.id.weather_view);
        swipeRefreshLayout = inflate.findViewById(R.id.swipe_refresh);

        //填充天气数据
        weatherView.setList(generateData());

        //画折线
        //weatherView.setLineType(ZzWeatherView.LINE_TYPE_DISCOUNT);
        //画曲线(已修复不圆滑问题)
        weatherView.setLineType(WeatherView.LINE_TYPE_CURVE);

        //设置线宽
        weatherView.setLineWidth(2f);

        //设置一屏幕显示几列(最少3列)
        try {
            weatherView.setColumnNumber(6);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置白天和晚上线条的颜色
        weatherView.setDayAndNightLineColor(Color.parseColor("#E4AE47"), Color.parseColor("#58ABFF"));

        //点击某一列
        weatherView.setOnWeatherItemClickListener(new WeatherView.OnWeatherItemClickListener() {
            @Override
            public void onItemClick(WeatherItemView itemView, int position, WeatherModel weatherModel) {
                //Toast.makeText(MainActivity.this, position+"", Toast.LENGTH_SHORT).show();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                weatherView.setList(generateData());
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }


    private List<WeatherModel> generateData() {
        List<WeatherModel> list = new ArrayList<>();
        String mWeatherId;
        mWeatherId = (String) SPUtils.get(getContext(), "weatherId", "");
        List<WeatherModel> weatherModels = new WeatherDao(getContext()).queryAlllxr(mWeatherId);
        //获取设置里面显示的天数
        String count = (String) SPUtils.get(getContext(), "count", "7");
        int i = Integer.parseInt(count);
        if (i == 0) {
        } else {
            if (weatherModels == null || weatherModels.size() == 0) {
                return list;
            }
            for (int j = 0; j < weatherModels.size(); j++) {
                if (j == i) {
                    break;
                }
                WeatherModel weatherModel = weatherModels.get(j);
                list.add(weatherModel);
            }
        }
        swipeRefreshLayout.setRefreshing(false);

        return list;
    }
}