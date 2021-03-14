package com.tea.fishtech.main.ui.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tea.fishtech.common.model.BoxInfo;
import com.tea.fishtech.common.utils.log.LatteLogger;
import com.tea.fishtech.main.R;
import com.tea.fishtech.ui.delegates.LatteDelegate;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import cn.addapp.pickers.picker.NumberPicker;
import es.dmoral.toasty.Toasty;

public class WaterSettingAdapter extends RecyclerView.Adapter<WaterSettingAdapter.ViewHolder> {

    private List<BoxInfo> boxInfos;

    private LatteDelegate DELEGATE;

    public WaterSettingAdapter(List<BoxInfo> boxInfos,LatteDelegate delegate) {
        this.boxInfos = boxInfos;
        this.DELEGATE = delegate;
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

        // 盒子ID号
        public TextView waterIdTv;

        // 设置开启
        public TextView tvOpenSetting;

        // 设置关闭
        public TextView tvCloseSetting;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            waterIdTv = itemView.findViewById(R.id.water_id);
            tvOpenSetting = itemView.findViewById(R.id.tv_open_setting);

            tvCloseSetting = itemView.findViewById(R.id.tv_close_setting);

            tvOpenSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LatteLogger.d("设置打开阈值");
                    onNumberPicker(itemView);
                }
            });

            tvCloseSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LatteLogger.d("设置关闭阈值");
                    onNumberPicker(itemView);
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }

    public void onNumberPicker(View view) {
        NumberPicker picker = new NumberPicker(DELEGATE.getProxyActivity());
        picker.setWidth(picker.getScreenWidthPixels() / 2);
        picker.setCanLoop(false);
        picker.setCancelText("取消");
        picker.setSubmitText("确定");
        picker.setLineVisible(false);
        picker.setOffset(2);//偏移量
        picker.setRange(10, 100, 1);//数字范围
        picker.setSelectedItem(50);
        picker.setLabel("溶解氧");
        picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
            @Override
            public void onNumberPicked(int index, Number item) {
                Toasty.normal(DELEGATE.getContext(),
                        "index=" + index + ", item=" + item.intValue())
                .show();

                // TODO 发送设置值 对整个塘口控制器的开启
            }
        });
        picker.show();
    }
}
