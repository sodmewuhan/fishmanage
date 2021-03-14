package com.tea.fishtech.main.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tea.fishtech.common.model.BoxInfo;
import com.tea.fishtech.main.R;

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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_water_setting, parent, false);
        return new WaterSettingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterSettingAdapter.ViewHolder holder, int position) {
        holder.waterIdTv.setText(boxInfos.get(position).getBoxNumber());
    }

    @Override
    public int getItemCount() {
        return CollectionUtils.isEmpty(boxInfos) ? 0: boxInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // 塘口名称
        public TextView waterIdTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            waterIdTv = itemView.findViewById(R.id.water_id);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
