package com.android.weatherapp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.weatherapp.R;
import com.android.weatherapp.helper.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * desc   : 历史
 */
public final class WeatherCityFragment extends Fragment {
    RecyclerView rvHistoryCity;
    SwipeRefreshLayout swipeRefreshLayout;
    private BaseQuickAdapter<String, BaseViewHolder> adapter;

    public static WeatherCityFragment newInstance() {
        return new WeatherCityFragment();
    }

    private View inflate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_history_city, null, false);
        initView();
        return inflate;
    }

    protected void initView() {
        rvHistoryCity = inflate.findViewById(R.id.rv_history_city);
        swipeRefreshLayout = inflate.findViewById(R.id.swipe_refresh);
        rvHistoryCity.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_city) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_name, item);
            }

        };
        rvHistoryCity.setAdapter(adapter);
        adapter.setNewData(getCityData());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.setNewData(getCityData());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(getContext());
                normalDialog.setTitle("删除");
                normalDialog.setMessage("是否要删除该城市?");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                List<String> city = SPUtils.getDataList(getContext(), "city");
                                city.remove(position);
                                SPUtils.setDataList(getContext(), "city", city);
                                adapter.remove(position);

                            }
                        });
                normalDialog.setNegativeButton("关闭",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                // 显示
                normalDialog.show();
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }


    private List<String> getCityData() {
        List<String> city = SPUtils.getDataList(getContext(), "city");
        return city;
    }
}