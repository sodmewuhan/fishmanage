package com.tea.fishtech.main.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tea.fishtech.common.model.BoxInfo;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class WaterSettingAdapter extends RecyclerView.Adapter<WaterSettingAdapter.ViewHolder> {

    private List<BoxInfo> boxInfos;

    public WaterSettingAdapter(List<BoxInfo> boxInfos) {
        this.boxInfos = boxInfos;
    }

    @NonNull
    @Override
    public WaterSettingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull WaterSettingAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return CollectionUtils.isEmpty(boxInfos) ? 0: boxInfos.size();
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
