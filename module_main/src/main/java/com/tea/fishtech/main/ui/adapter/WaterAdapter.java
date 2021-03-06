package com.tea.fishtech.main.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tea.fishtech.common.model.BoxAndWaterStatusDTO;
import com.tea.fishtech.common.model.BoxInfo;
import com.tea.fishtech.common.ui.Adapter.CommonAdapter;
import com.tea.fishtech.common.ui.Adapter.ViewHolder;
import com.tea.fishtech.main.R;

import org.apache.commons.collections4.CollectionUtils;

import java.text.DecimalFormat;
import java.util.List;

public class WaterAdapter extends CommonAdapter<BoxInfo> implements View.OnClickListener {

    private List<BoxAndWaterStatusDTO> waterStatusDTOList;

    private DecimalFormat fnum   =   new   DecimalFormat("##0.00");

    // 溶解氧
    private TextView oxygenValueTv;

    // PH值
    private TextView phTv;

    // 温度
    private TextView tempValueTv;

    // 设备编号
    private TextView devNoTv;

    public WaterAdapter(Context context, List<BoxInfo> dataList) {
        super(context, dataList);
    }

    public WaterAdapter(Context context, List<BoxInfo> dataList,List<BoxAndWaterStatusDTO> list ) {
        super(context, dataList);

        waterStatusDTOList = list;
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = ViewHolder.get(getContext(), convertView, R.layout.item_water_list, viewGroup);

        BoxAndWaterStatusDTO boxAndWaterStatusDTO = null;
        if (CollectionUtils.isNotEmpty(waterStatusDTOList)) {
            boxAndWaterStatusDTO = waterStatusDTOList.get(position);
        }

        if (boxAndWaterStatusDTO != null) {
            oxygenValueTv = holder.getView(R.id.oxygenValue);
            phTv = holder.getView(R.id.phValue);
            tempValueTv = holder.getView(R.id.tempValue);
            devNoTv = holder.getView(R.id.devNo);

            if (boxAndWaterStatusDTO.getWaterOxygen() != null) {
                Float f = boxAndWaterStatusDTO.getWaterOxygen().floatValue();
                oxygenValueTv.setText(fnum.format(f));
            }
        }

        return holder.getConvertView();
    }

    public List<BoxAndWaterStatusDTO> getWaterStatusDTOList() {
        return waterStatusDTOList;
    }

    public void setWaterStatusDTOList(List<BoxAndWaterStatusDTO> waterStatusDTOList) {
        this.waterStatusDTOList = waterStatusDTOList;
    }
}
