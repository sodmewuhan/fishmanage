package com.tea.fishtech.main.ui.setting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tea.fishtech.common.utils.log.LatteLogger;

import java.util.ArrayList;
import java.util.List;

public class DeviceFragmentAdapter extends FragmentPagerAdapter {

    private List<String> mDataList = new ArrayList<>();

    public DeviceFragmentAdapter(@NonNull FragmentManager fm, int behavior,List<String> mDataList) {
        super(fm, behavior);

        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                WaterFragment waterFragment = new WaterFragment();
                fragment = waterFragment;
                break;
            case 1:
                DevFragment devFragment = new DevFragment();
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
