package com.tea.fishtech.main.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.google.common.collect.Lists;
import com.tea.fishtech.common.constants.Constants;
import com.tea.fishtech.common.model.BoxAndWaterStatusDTO;
import com.tea.fishtech.common.model.BoxInfo;
import com.tea.fishtech.common.model.FishPondDto;
import com.tea.fishtech.common.utils.log.LatteLogger;
import com.tea.fishtech.main.R;
import com.tea.fishtech.ui.delegates.LatteDelegate;
import com.tea.fishtech.ui.widget.ItemHeader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends PagerAdapter {

    private static final SimpleDateFormat SDF =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );

    private ItemHeader itemHeader;

    private ListView listView ;

    private LinearLayout mErrorLayout;

    private List<String> mDataList = new ArrayList<>();

    private List<FishPondDto> fishPondDtoList;

    private final LatteDelegate DELEGATE;

    private int position ;

    public ViewPageAdapter(List<FishPondDto> fishPondDtoList, LatteDelegate delegate) {
        LatteLogger.d("ViewPageAdapter fun");
        this.fishPondDtoList = fishPondDtoList;
        if (fishPondDtoList != null && fishPondDtoList.size() > 0) {
            for (FishPondDto dto : fishPondDtoList) {
                mDataList.add(dto.getFishPond().getPondName());
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

        // 控制器和水质探测器分组
        List<BoxInfo> boxInfos = fishPondDtoList.get(position).getBoxInfoList();
        List<BoxInfo> controlBox = Lists.newArrayList();
        List<BoxInfo> waterBox = Lists.newArrayList();
        List<BoxAndWaterStatusDTO> boxAndWaterStatusDTOList =
                fishPondDtoList.get(position).getBoxAndWaterStatusDTOList();

        for(BoxInfo boxInfo : boxInfos) {
            if (boxInfo.getBoxTypeId().equals(Constants.BOX_TYPE_CTRL)) {
                controlBox.add(boxInfo);
            }else if (boxInfo.getBoxTypeId().equals(Constants.BOX_TYPE_WATER)) {
                waterBox.add(boxInfo);
            }
        }

        this.position = position;
        // 设置
        setWater(container, mView, waterBox,boxAndWaterStatusDTOList);
        // 设置控制器
        setControl(container, mView, controlBox);

        return mView;
    }

    private void setWater(ViewGroup container, View mView, List<BoxInfo> waterBox,
                          List<BoxAndWaterStatusDTO> boxAndWaterStatusDTOList) {

        listView = mView.findViewById(R.id.collect_waterlistView);
        mErrorLayout = mView.findViewById(R.id.collect_water_error_layout);

        WaterAdapter waterAdapter = new WaterAdapter(container.getContext(),waterBox,boxAndWaterStatusDTOList);
        if (waterBox != null && waterBox.size() > 0) {
            listView.setAdapter(waterAdapter);
            waterAdapter.notifyDataSetChanged();
            listView.setVisibility(View.VISIBLE);
            mErrorLayout.setVisibility(View.GONE);
        } else {
            // 为空
            listView.setVisibility(View.GONE);
            mErrorLayout.setVisibility(View.VISIBLE);
        }
    }
    private void setControl(ViewGroup container, View mView, List<BoxInfo> controlBox) {
        listView = mView.findViewById(R.id.collect_controllistView);
        mErrorLayout = mView.findViewById(R.id.collect_error_layout);

        DeviceAdapter deviceAdapter = new DeviceAdapter(container.getContext(),
                controlBox);
        if (controlBox != null && controlBox.size() > 0) {
            listView.setAdapter(deviceAdapter);
            deviceAdapter.notifyDataSetChanged();
            listView.setVisibility(View.VISIBLE);
            mErrorLayout.setVisibility(View.GONE);
        } else {
            // 为空
            listView.setVisibility(View.GONE);
            mErrorLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
