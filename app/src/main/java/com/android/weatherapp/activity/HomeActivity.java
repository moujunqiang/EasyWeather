package com.android.weatherapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.weatherapp.R;
import com.android.weatherapp.adapter.BaseFragmentAdapter;
import com.android.weatherapp.fragment.SettingFragment;
import com.android.weatherapp.fragment.WeatherCityFragment;
import com.android.weatherapp.fragment.WeatherFragment;
import com.android.weatherapp.helper.DoubleClickHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;



/**
 * desc   : 主页界面
 */
public final class HomeActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    ViewPager mViewPager;
    BottomNavigationView mBottomNavigationView;

    /**
     * ViewPager 适配器
     */
    private BaseFragmentAdapter<Fragment> mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mViewPager=findViewById(R.id.vp_home_pager);
        mBottomNavigationView=findViewById(R.id.bv_home_navigation);

        initView();
    }


    protected void initView() {
        mViewPager.addOnPageChangeListener(this);

        // 不使用图标默认变色
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        initData();

    }

    protected void initData() {
        mPagerAdapter = new BaseFragmentAdapter<>(this);
        mPagerAdapter.addFragment(WeatherFragment.newInstance());
        mPagerAdapter.addFragment(WeatherCityFragment.newInstance());
        mPagerAdapter.addFragment(SettingFragment.newInstance());

        mViewPager.setAdapter(mPagerAdapter);

        // 限制页面数量
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());
    }

    /**
     * {@link ViewPager.OnPageChangeListener}
     */

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {

            case 0:
                mBottomNavigationView.setSelectedItemId(R.id.home_found);
                break;
            case 1:
                mBottomNavigationView.setSelectedItemId(R.id.home_message);
                break;
            case 2:
                mBottomNavigationView.setSelectedItemId(R.id.home_me);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * {@link BottomNavigationView.OnNavigationItemSelectedListener}
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_found:
                mViewPager.setCurrentItem(0);
                return true;
            case R.id.home_message:
                mViewPager.setCurrentItem(1);
                return true;
            case R.id.home_me:
                mViewPager.setCurrentItem(2);
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (DoubleClickHelper.isOnDoubleClick()) {
            //移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    finish();
                }
            }, 300);
        } else {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        mViewPager.removeOnPageChangeListener(this);
        mViewPager.setAdapter(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);
        super.onDestroy();
    }
}