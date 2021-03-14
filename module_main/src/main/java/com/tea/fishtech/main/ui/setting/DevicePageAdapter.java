package com.tea.fishtech.main.ui.setting;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.tea.fishtech.common.constants.Constants;
import com.tea.fishtech.common.utils.log.LatteLogger;
import com.tea.fishtech.main.R;
import com.tea.fishtech.ui.delegates.LatteDelegate;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class DevicePageAdapter extends PagerAdapter {

    private LatteDelegate delegate;

    private List<String> mDataList = new ArrayList<>();

    private String title;

    private int position ;

    public DevicePageAdapter(LatteDelegate delegate, List<String> title) {
        this.delegate = delegate;
        this.mDataList = title;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //return super.instantiateItem(container, position);
        LatteLogger.d("instantiateItem fun");
        View mView;
//
        if (mDataList.get(position).equals(Constants.DEV_TYPE_NAME_WATER)) {
            // 水质
            mView = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter_water_page, null);
            container.addView(mView);
        } else {
            // 控制器
            mView = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter_dev_page, null);
            container.addView(mView);
        }
        return mView;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position);
    }
}
