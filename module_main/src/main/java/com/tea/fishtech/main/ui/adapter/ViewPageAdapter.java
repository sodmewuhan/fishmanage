package com.tea.fishtech.main.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.tea.fishtech.common.model.BoxAndWaterStatusDTO;
import com.tea.fishtech.common.model.FishPondDto;
import com.tea.fishtech.common.utils.log.LatteLogger;
import com.tea.fishtech.main.R;
import com.tea.fishtech.main.ui.chart.ChartDelegate;
import com.tea.fishtech.ui.delegates.LatteDelegate;
import com.tea.fishtech.ui.widget.ItemHeader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewPageAdapter extends PagerAdapter {

    private ItemHeader itemHeader;

    private List<String> mDataList = new ArrayList<>();

    private List<FishPondDto> fishPondDtoList;

    private List<BoxAndWaterStatusDTO> waterStatusDTOList = new ArrayList<>();

    private final LatteDelegate DELEGATE;

    private int position ;

    public ViewPageAdapter(List<FishPondDto> fishPondDtoList, LatteDelegate delegate) {
        LatteLogger.d("ViewPageAdapter fun");
        this.fishPondDtoList = fishPondDtoList;
        if (fishPondDtoList != null && fishPondDtoList.size() > 0) {
            for (FishPondDto dto : fishPondDtoList) {
                mDataList.add(dto.getFishPond().getPondName());
                if (dto.getBoxAndWaterStatusDTOList() != null && dto.getBoxAndWaterStatusDTOList().size() > 0) {
                    waterStatusDTOList.addAll(dto.getBoxAndWaterStatusDTOList());
                }
            }
        }
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
        LatteLogger.d("instantiateItem fun");
        View mView = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter_index, null);
        container.addView(mView);
        // 设置水质明细跳转
        itemHeader = mView.findViewById(R.id.ih_history);
        itemHeader.setOnClickListener(v -> {
            // 跳转到图表页面
            DELEGATE.getSupportDelegate().start(new ChartDelegate());
        });

        this.position = position;
        // 设置控制器
        return mView;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
