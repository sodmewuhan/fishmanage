package com.tea.fishtech.main.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.collect.Lists;
import com.tea.fishtech.common.model.BoxInfo;
import com.tea.fishtech.common.utils.log.LatteLogger;
import com.tea.fishtech.main.R;
import com.tea.fishtech.ui.delegates.LatteDelegate;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class DeviceSettingAdapter extends RecyclerView.Adapter<DeviceSettingAdapter.ViewHolder> {

    private List<BoxInfo> boxInfos = Lists.newArrayList();

    private LatteDelegate DELEGATE;

    public DeviceSettingAdapter(List<BoxInfo> boxInfos,LatteDelegate delegate) {
        this.boxInfos = boxInfos;
        this.DELEGATE = delegate;
    }

    @NonNull
    @Override
    public DeviceSettingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dev_settting, parent, false);
        return new DeviceSettingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceSettingAdapter.ViewHolder holder, int position) {
        holder.devIdTv.setText(boxInfos.get(position).getBoxNumber());
    }

    @Override
    public int getItemCount() {
        LatteLogger.d(boxInfos.size());
        return CollectionUtils.isEmpty(boxInfos) ? 0 : boxInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // 塘口名称
        public TextView devIdTv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            devIdTv = itemView.findViewById(R.id.dev_id);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public LatteDelegate getDELEGATE() {
        return DELEGATE;
    }

    public void setDELEGATE(LatteDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    public List<BoxInfo> getBoxInfos() {
        return boxInfos;
    }

    public void setBoxInfos(List<BoxInfo> boxInfos) {
        this.boxInfos = boxInfos;
    }
}
