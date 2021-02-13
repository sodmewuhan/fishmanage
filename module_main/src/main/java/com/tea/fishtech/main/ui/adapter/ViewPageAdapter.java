package com.tea.fishtech.main.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.tea.fishtech.main.R;
import com.tea.fishtech.main.ui.chart.ChartDelegate;
import com.tea.fishtech.ui.delegates.LatteDelegate;
import com.tea.fishtech.ui.widget.ItemHeader;

import java.util.List;

public class ViewPageAdapter extends PagerAdapter {

    private ItemHeader itemHeader;

    private List<String> mDataList;

    private final LatteDelegate DELEGATE;

    public ViewPageAdapter(List<String> mDataList, LatteDelegate delegate) {
        this.mDataList = mDataList;
        DELEGATE = delegate;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
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
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View mView = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter_index, null);
        container.addView(mView);

        itemHeader = mView.findViewById(R.id.ih_history);
        itemHeader.setOnClickListener(v -> {
            // 跳转到图表页面
            DELEGATE.getSupportDelegate().start(new ChartDelegate());
        });

        return mView;
    }

    @Override
    public int getItemPosition(Object object) {
        TextView textView = (TextView) object;
        String text = textView.getText().toString();
        int index = mDataList.indexOf(text);
        if (index >= 0) {
            return index;
        }
        return POSITION_NONE;
    }


}
