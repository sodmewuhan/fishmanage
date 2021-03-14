package com.tea.fishtech.main.ui.setting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.common.collect.Lists;
import com.tea.fishtech.common.constants.Constants;
import com.tea.fishtech.common.model.BoxInfo;
import com.tea.fishtech.common.utils.log.LatteLogger;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class DeviceFragmentAdapter extends FragmentPagerAdapter {

    private List<String> mDataList = new ArrayList<>();

    private List<BoxInfo> devList = Lists.newArrayList();

    private List<BoxInfo> waterList = Lists.newArrayList();

    public DeviceFragmentAdapter(@NonNull FragmentManager fm, int behavior,
                                 List<String> mDataList,
                                 List<BoxInfo> boxInfos) {
        super(fm, behavior);
        this.mDataList = mDataList;

        if (CollectionUtils.isNotEmpty(boxInfos)) {
            for(BoxInfo obj : boxInfos) {
                if (Constants.BOX_TYPE_CTRL.equals(obj.getBoxTypeId())) {
                    devList.add(obj);
                } else if (Constants.BOX_TYPE_WATER.equals(obj.getBoxTypeId())) {
                    waterList.add(obj);
                }
            }
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                WaterFragment waterFragment = new WaterFragment();
                waterFragment.setBoxInfoList(waterList);
                fragment = waterFragment;
                break;
            case 1:
                DevFragment devFragment = new DevFragment();
                devFragment.setBoxInfoList(devList);
                fragment = devFragment;
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        LatteLogger.d("getPageTitle" + mDataList.get(position));
        return mDataList.get(position);
    }
}
