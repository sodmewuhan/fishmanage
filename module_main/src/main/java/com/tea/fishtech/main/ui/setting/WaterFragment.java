package com.tea.fishtech.main.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.common.collect.Lists;
import com.tea.fishtech.common.model.BoxInfo;
import com.tea.fishtech.main.R;

import java.util.List;

public class WaterFragment extends Fragment {

    private List<BoxInfo> boxInfoList = Lists.newArrayList();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water, container,false);
        return view;
    }

    public List<BoxInfo> getBoxInfoList() {
        return boxInfoList;
    }

    public void setBoxInfoList(List<BoxInfo> boxInfoList) {
        this.boxInfoList = boxInfoList;
    }
}
