package com.android.weatherapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.weatherapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;



public final class WeatherFragment extends Fragment {
    TabLayout tableLayout;
    ViewPager viewPager;
    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    private View inflate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_weather, null, false);
        initView();
        return inflate;
    }


    protected void initView() {
         tableLayout =inflate.findViewById(R.id.tl_tabs);
        viewPager =inflate.findViewById(R.id.vp_content);
        fragments.add(WeatherTodayFragment.newInstance());
        fragments.add(WeatherRecomFragment.newInstance());
        titles.add("今日");
        titles.add("推荐");
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {

                return titles.get(position);
            }
        });

        tableLayout.setupWithViewPager(viewPager);
    }


}