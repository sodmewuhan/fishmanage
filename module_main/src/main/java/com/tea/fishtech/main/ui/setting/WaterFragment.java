package com.tea.fishtech.main.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.collect.Lists;
import com.tea.fishtech.common.model.BoxInfo;
import com.tea.fishtech.main.R;
import com.tea.fishtech.main.ui.adapter.DeviceSettingAdapter;
import com.tea.fishtech.main.ui.adapter.WaterSettingAdapter;
import com.tea.fishtech.ui.delegates.LatteDelegate;

import java.util.List;

public class WaterFragment extends Fragment {

    private List<BoxInfo> boxInfoList = Lists.newArrayList();

    private RecyclerView waterListView = null;

    private WaterSettingAdapter waterSettingAdapter;

    private LinearLayoutManager mLayoutManager;

    private LatteDelegate DELEGATE;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water, container,false);

        waterListView = view.findViewById(R.id.rv_water_list);
        setRecyclerView();
        return view;
    }

    public List<BoxInfo> getBoxInfoList() {
        return boxInfoList;
    }

    public void setBoxInfoList(List<BoxInfo> boxInfoList) {
        this.boxInfoList = boxInfoList;
    }

    private void setRecyclerView() {
        waterSettingAdapter = new WaterSettingAdapter(this.boxInfoList,DELEGATE);
        mLayoutManager = new LinearLayoutManager(getContext());
        waterListView.setLayoutManager(mLayoutManager);
        waterListView.setAdapter(waterSettingAdapter);
    }

    public LatteDelegate getDELEGATE() {
        return DELEGATE;
    }

    public void setDELEGATE(LatteDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }
}
