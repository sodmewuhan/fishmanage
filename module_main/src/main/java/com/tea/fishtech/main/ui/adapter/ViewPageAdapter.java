package com.tea.fishtech.main.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.tea.fishtech.common.model.BoxAndWaterStatusDTO;
import com.tea.fishtech.common.model.BoxInfo;
import com.tea.fishtech.common.model.FishPondDto;
import com.tea.fishtech.common.utils.log.LatteLogger;
import com.tea.fishtech.main.R;
import com.tea.fishtech.main.ui.chart.ChartDelegate;
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
//                if (dto.getBoxAndWaterStatusDTOList() != null && dto.getBoxAndWaterStatusDTOList().size() > 0) {
//                    waterStatusDTOList.addAll(dto.getBoxAndWaterStatusDTOList());
//                }
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

        // 设置创建时间
        TextView createTimeTv = mView.findViewById(R.id.createTime);
        String createTime = SDF.format(fishPondDtoList.get(position).getFishPond().getCreateDate());
        createTimeTv.setText(createTime);

        // 设置水质明细跳转
        itemHeader = mView.findViewById(R.id.ih_history);
        itemHeader.setOnClickListener(v -> {
            // 跳转到图表页面
            DELEGATE.getSupportDelegate().start(new ChartDelegate());
        });

        this.position = position;
        // 设置控制器
        listView = mView.findViewById(R.id.collect_listView);
        mErrorLayout = mView.findViewById(R.id.collect_error_layout);

        DeviceAdapter deviceAdapter = new DeviceAdapter(container.getContext(),
                fishPondDtoList.get(position).getBoxInfoList());

        List<BoxInfo> boxInfos = fishPondDtoList.get(position).getBoxInfoList();
        if (boxInfos != null && boxInfos.size() > 0) {
            listView.setAdapter(deviceAdapter);
            deviceAdapter.notifyDataSetChanged();
            listView.setVisibility(View.VISIBLE);
            mErrorLayout.setVisibility(View.GONE);
        } else {
            // 为空
            listView.setVisibility(View.GONE);
            mErrorLayout.setVisibility(View.VISIBLE);
        }
        return mView;
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
