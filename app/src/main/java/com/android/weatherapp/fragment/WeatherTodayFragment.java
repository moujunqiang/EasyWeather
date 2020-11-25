package com.android.weatherapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.weatherapp.R;
import com.android.weatherapp.db.WeatherDao;
import com.android.weatherapp.gson.AddressBean;
import com.android.weatherapp.gson.WeatherBean;
import com.android.weatherapp.helper.HttpUtil;
import com.android.weatherapp.helper.SPUtils;
import com.android.weatherapp.weatherview.WeatherModel;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * desc   : 今日天气
 */
public final class WeatherTodayFragment extends Fragment {
    DrawerLayout drawerLayout;
    Button navButton;
    SwipeRefreshLayout swipeRefresh;
    String mWeatherId = "杭州";
    ScrollView weatherLayout;
    TextView titleCity;
    TextView titleUpdateTime;
    TextView degreeText;
    TextView weatherInfoText;
    LinearLayout forecastLayout;
    private WeatherModel weatherModel;

    public static WeatherTodayFragment newInstance() {

        return new WeatherTodayFragment();
    }

    private View inflate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_weather_today, null, false);
        initView();
        EventBus.getDefault().register(this);
        return inflate;
    }

    protected void initView() {
        drawerLayout = inflate.findViewById(R.id.drawer_layout);
        navButton = inflate.findViewById(R.id.nav_button);
        swipeRefresh = inflate.findViewById(R.id.swipe_refresh);
        weatherLayout = inflate.findViewById(R.id.weather_layout);
        titleCity = inflate.findViewById(R.id.title_city);
        titleUpdateTime = inflate.findViewById(R.id.title_update_time);
        degreeText = inflate.findViewById(R.id.degree_text);
        weatherInfoText = inflate.findViewById(R.id.weather_info_text);
        forecastLayout = inflate.findViewById(R.id.forecast_layout);
        weatherModel = new WeatherModel();
        /*获取城市名字*/
        mWeatherId = (String) SPUtils.get(getContext(), "weatherId", "");

        requestWeather(mWeatherId);
        requestNowWeather(mWeatherId);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWeatherId = (String) SPUtils.get(getContext(), "weatherId", "");
                requestWeather(mWeatherId);
                requestNowWeather(mWeatherId);
            }
        });
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    /**
     * 接收选中城市消息
     * 选中城市后 重新刷新刷新
     *
     * @param addressBean
     */
    @Subscribe
    public void onEvent(AddressBean addressBean) {
        Log.e("address", addressBean.getName());
        mWeatherId = addressBean.getName();
        swipeRefresh.setRefreshing(true);
        requestWeather(mWeatherId);
        requestNowWeather(mWeatherId);
        drawerLayout.closeDrawers();
        List<String> city = SPUtils.getDataList(getContext(), "city");
        if (!city.contains(addressBean.getName())) {
            city.add(addressBean.getName());
        }
        SPUtils.setDataList(getContext(), "city", city);
    }


    /**
     * 获取当前城市当前天气
     */
    public void requestNowWeather(final String cityName) {
        String weatherUrl = "https://free-api.heweather.net/s6/weather/now?location=" + cityName + "&key=f23ef7e1cd4e42adaaf5f6ca99a6fbe2";
        Log.d("TAG", "开始发出请求查询天气，url=" + weatherUrl);
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "请求失败");
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TAG", "查询成功");
                final String responseText = response.body().string();
                Log.d("TAG", " 返回结果为 " + responseText);
                Gson gson = new Gson();
                final WeatherBean weatherBean = gson.fromJson(responseText, WeatherBean.class);
                final WeatherBean.HeWeather6Bean heWeather6Bean = weatherBean.getHeWeather6().get(0);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (heWeather6Bean != null && "ok".equals(heWeather6Bean.getStatus())) {

                            showNowWeather(heWeather6Bean);
                        } else {
                            Toast.makeText(getContext(), "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    /**
     * 根据天气id请求城市天气信息
     */
    public void requestWeather(final String cityName) {

        String weatherUrl = "https://free-api.heweather.net/s6/weather/forecast?location=" + cityName + "&key=f23ef7e1cd4e42adaaf5f6ca99a6fbe2";
        Log.d("TAG", "开始发出请求查询天气，url=" + weatherUrl);
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "请求失败");
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TAG", "查询成功");
                final String responseText = response.body().string();
                Log.d("TAG", " 返回结果为 " + responseText);
                Gson gson = new Gson();
                final WeatherBean weatherBean = gson.fromJson(responseText, WeatherBean.class);
                final WeatherBean.HeWeather6Bean heWeather6Bean = weatherBean.getHeWeather6().get(0);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (heWeather6Bean != null && "ok".equals(heWeather6Bean.getStatus())) {

                            showWeatherInfo(heWeather6Bean);
                        } else {
                            Toast.makeText(getContext(), "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void showNowWeather(WeatherBean.HeWeather6Bean weather) {
        WeatherBean.HeWeather6Bean.BasicBean basic = weather.getBasic();
        WeatherBean.HeWeather6Bean.UpdateBean update = weather.getUpdate();
        WeatherBean.HeWeather6Bean.NowBean nowBean = weather.getNow();

        String cityName = basic.getLocation();
        String updateTime = update.getUtc();

        String degree = nowBean.getTmp() + "℃";
        String weatherInfo = nowBean.getCond_txt();
        titleUpdateTime.setText(updateTime);
        titleCity.setText(cityName);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);


    }


    /**
     * 处理并展示Weather实体类中的数据
     */
    private void showWeatherInfo(WeatherBean.HeWeather6Bean weather) {
        forecastLayout.removeAllViews();
        for (int i = 0; i < weather.getDaily_forecast().size(); i++) {
            WeatherBean.HeWeather6Bean.DailyForecastBean forecast = weather.getDaily_forecast().get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.getDate());
            infoText.setText(forecast.getCond_txt_d());
            maxText.setText(forecast.getTmp_max());
            minText.setText(forecast.getTmp_min());
            forecastLayout.addView(view);
            if (i == 0) {
                weatherModel.setDate(forecast.getDate());
                weatherModel.setDayTemp(Integer.parseInt(forecast.getTmp_max()));
                weatherModel.setNightTemp(Integer.parseInt(forecast.getTmp_min()));
                weatherModel.setNightWeather(forecast.getCond_txt_n());
                weatherModel.setDayWeather(forecast.getCond_txt_d());
                weatherModel.setWindOrientation(forecast.getWind_dir());
                weatherModel.setWindLevel(forecast.getWind_spd());
                weatherModel.setCityName(mWeatherId);

                new WeatherDao(getContext()).add(weatherModel);
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}