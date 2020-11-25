package com.android.weatherapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.weatherapp.R;
import com.android.weatherapp.gson.AddressBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    AddressBean item;
    /*
    当前选中的级别
     */
    private int currentLevel;
    private BaseQuickAdapter<AddressBean, BaseViewHolder> adapter;
    TextView titleText;
    Button backButton;
    RecyclerView listView;
    private JSONObject curProvince,curCity;
    private View inflate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.choose_area, null, false);
        initView();
        return inflate;
    }


    public void initView(){
        titleText = inflate.findViewById(R.id.title_text);

        backButton = inflate.findViewById(R.id.back_button);
        listView = inflate.findViewById(R.id.list_view);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BaseQuickAdapter<AddressBean, BaseViewHolder>(R.layout.item_city) {

            @Override
            protected void convert(BaseViewHolder helper, AddressBean item) {
                helper.setText(R.id.tv_name,item.getName());
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                 item = (AddressBean) adapter.getItem(position);

                if (currentLevel==LEVEL_PROVINCE){//选择省份
                    curProvince=item.getNext();
                    queryCities(item.getNext());
                    currentLevel=LEVEL_CITY;
                }else if (currentLevel==LEVEL_CITY){//选择城市
                    queryAreas(item.getNext());
                    curCity=item.getNext();
                    currentLevel=LEVEL_COUNTY;
                }else if (currentLevel == LEVEL_COUNTY) {//选择区域
                    //通过eventbus通知天气页面 刷新数据
                    EventBus.getDefault().post(item);
                }
            }
        });
        listView.setAdapter(adapter);
        queryProvinces();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCities(curProvince);
                    currentLevel=LEVEL_CITY;
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                    currentLevel=LEVEL_PROVINCE;

                }
            }
        });
    }



    public void queryProvinces() {
        List<AddressBean> provinceList = AddressManager.getProvinceList(getContext());
        adapter.setNewData(provinceList);
    }

    public void queryCities(JSONObject jsonObject) {
        List<AddressBean> cityList = AddressManager.getCityList(jsonObject);
        adapter.setNewData(cityList);

    }

    public void queryAreas(JSONObject jsonObject) {
        List<AddressBean> areaList = AddressManager.getAreaList(jsonObject);
        adapter.setNewData(areaList);

    }


    /**
     * 省市区数据管理类
     */
    private static final class AddressManager {

        /**
         * 获取省列表
         */
        private static List<AddressBean> getProvinceList(Context context) {
            try {
                // 省市区Json数据文件来源：https://github.com/getActivity/ProvinceJson
                JSONArray jsonArray = getProvinceJson(context);

                if (jsonArray != null) {

                    int length = jsonArray.length();
                    ArrayList<AddressBean> list = new ArrayList<>(length);
                    for (int i = 0; i < length; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        list.add(new AddressBean(jsonObject.getString("name"), jsonObject));
                    }

                    return list;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 获取城市列表
         *
         * @param jsonObject 城市Json
         */
        private static List<AddressBean> getCityList(JSONObject jsonObject) {
            try {
                JSONArray listCity = jsonObject.getJSONArray("city");
                int length = listCity.length();

                ArrayList<AddressBean> list = new ArrayList<>(length);

                for (int i = 0; i < length; i++) {
                    list.add(new AddressBean(listCity.getJSONObject(i).getString("name"), listCity.getJSONObject(i)));
                }

                return list;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * 获取区域列表
         *
         * @param jsonObject 区域 Json
         */
        private static List<AddressBean> getAreaList(JSONObject jsonObject) {
            try {
                JSONArray listArea = jsonObject.getJSONArray("area");
                int length = listArea.length();

                ArrayList<AddressBean> list = new ArrayList<>(length);

                for (int i = 0; i < length; i++) {
                    String string = listArea.getString(i);
                    list.add(new AddressBean(string, null));
                }
                return list;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * 获取资产目录下面文件的字符串
         */
        private static JSONArray getProvinceJson(Context context) {
            try {
                InputStream inputStream = context.getAssets().open("province.json");
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[512];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, length);
                }
                outStream.close();
                inputStream.close();
                return new JSONArray(outStream.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
