package com.tea.fishtech.main.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.collect.Lists;
import com.tea.fishtech.common.model.BoxInfo;
import com.tea.fishtech.main.R;
import com.tea.fishtech.ui.delegates.LatteDelegate;

import java.util.List;

public class DeviceSettingAdapter extends RecyclerView.Adapter<DeviceSettingAdapter.ViewHolder> {

    private List<BoxInfo> boxInfos = Lists.newArrayList();

    private LatteDelegate DELEGATE;

    public DeviceSettingAdapter(List<BoxInfo> boxInfos) {
        this.boxInfos = boxInfos;
    }

    @NonNull
    @Override
    public DeviceSettingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pond_list, parent, false);
        return new DeviceSettingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceSettingAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
