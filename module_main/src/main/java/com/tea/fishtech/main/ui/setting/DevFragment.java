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
import com.tea.fishtech.main.R2;
import com.tea.fishtech.main.ui.adapter.DeviceSettingAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DevFragment extends Fragment {

    private List<BoxInfo> boxInfoList = Lists.newArrayList();

    private DeviceSettingAdapter deviceSettingAdapter;

    private LinearLayoutManager mLayoutManager;

    // 列表信息
    @BindView(R2.id.rv_dev_list)
    RecyclerView devListView = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dev, container,false);

        ButterKnife.bind(this.getActivity());
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
        deviceSettingAdapter = new DeviceSettingAdapter(this.boxInfoList);
        mLayoutManager = new LinearLayoutManager(getContext());
        devListView.setLayoutManager(mLayoutManager);
        devListView.setAdapter(deviceSettingAdapter);
    }
}
