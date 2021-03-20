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

import cn.addapp.pickers.picker.TimePicker;
import es.dmoral.toasty.Toasty;

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

        // 设置开启
        public TextView tvOpenSetting;

        // 设置关闭
        public TextView tvCloseSetting;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            devIdTv = itemView.findViewById(R.id.dev_id);
            tvOpenSetting = itemView.findViewById(R.id.tv_open_setting);
            tvCloseSetting = itemView.findViewById(R.id.tv_close_setting);
            tvOpenSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LatteLogger.d("设置打开阈值");
                    onTimePicker(itemView);
                }
            });

            tvCloseSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LatteLogger.d("设置关闭阈值");
                    onTimePicker(itemView);
                }
            });
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

    public void onTimePicker(View view) {
        TimePicker picker = new TimePicker(DELEGATE.getActivity(), TimePicker.HOUR_24);
        picker.setRangeStart(9, 0);//09:00
        picker.setRangeEnd(18, 0);//18:30
        picker.setTopLineVisible(false);
        picker.setLineVisible(false);
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                Toasty.normal(DELEGATE.getContext(),hour + ":" + minute).show();
            }
        });
        picker.show();
    }
}
