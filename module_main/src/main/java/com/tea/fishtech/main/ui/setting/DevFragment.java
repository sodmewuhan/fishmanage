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
import com.tea.fishtech.ui.delegates.LatteDelegate;

import java.util.List;

public class DevFragment extends Fragment {

    private List<BoxInfo> boxInfoList = Lists.newArrayList();

    private DeviceSettingAdapter deviceSettingAdapter;

    private LinearLayoutManager mLayoutManager;

    private LatteDelegate DELEGATE;

    // 列表信息
    RecyclerView devListView = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dev, container,false);
        devListView = view.findViewById(R.id.rv_dev_list);
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
        deviceSettingAdapter = new DeviceSettingAdapter(this.boxInfoList,DELEGATE);
        mLayoutManager = new LinearLayoutManager(getContext());
        devListView.setLayoutManager(mLayoutManager);
        devListView.setAdapter(deviceSettingAdapter);
    }

    public LatteDelegate getDELEGATE() {
        return DELEGATE;
    }

    public void setDELEGATE(LatteDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }
}
