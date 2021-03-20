package com.tea.fishtech.main.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tea.fishtech.common.model.BoxAndWaterStatusDTO;
import com.tea.fishtech.common.model.BoxInfo;
import com.tea.fishtech.common.ui.Adapter.CommonAdapter;
import com.tea.fishtech.common.ui.Adapter.ViewHolder;
import com.tea.fishtech.common.utils.log.LatteLogger;
import com.tea.fishtech.main.R;
import com.tea.fishtech.main.ui.chart.ChartDelegate;
import com.tea.fishtech.ui.delegates.LatteDelegate;

import org.apache.commons.collections4.CollectionUtils;

import java.text.DecimalFormat;
import java.util.List;

public class WaterAdapter extends CommonAdapter<BoxInfo> implements View.OnClickListener {

    private List<BoxAndWaterStatusDTO> waterStatusDTOList;

    private DecimalFormat fnum   =   new   DecimalFormat("##0.00");

    private LatteDelegate DELEGATE;
    // 溶解氧
    private TextView oxygenValueTv;

    // PH值
    private TextView phTv;

    // 温度
    private TextView tempValueTv;

    // 设备编号
    private TextView devNoTv;

    // 图表
    private TextView praphTv;


    public WaterAdapter(Context context, List<BoxInfo> dataList) {
        super(context, dataList);
    }

    public WaterAdapter(Context context, List<BoxInfo> dataList,List<BoxAndWaterStatusDTO> list,
                        LatteDelegate DELEGATE) {
        super(context, dataList);

        waterStatusDTOList = list;

        this.DELEGATE = DELEGATE;
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = ViewHolder.get(getContext(), convertView, R.layout.item_water_list, viewGroup);

        devNoTv = holder.getView(R.id.devNo);
        oxygenValueTv = holder.getView(R.id.oxygenValue);
        phTv = holder.getView(R.id.phValue);
        tempValueTv = holder.getView(R.id.tempValue);
        praphTv = holder.getView(R.id.graph);

        BoxAndWaterStatusDTO boxAndWaterStatusDTO = null;
        if (CollectionUtils.isNotEmpty(waterStatusDTOList)) {
            boxAndWaterStatusDTO = waterStatusDTOList.get(position);
        }

        if (boxAndWaterStatusDTO != null) {
            if (boxAndWaterStatusDTO.getWaterOxygen() != null) {
                Float f = boxAndWaterStatusDTO.getWaterOxygen().floatValue();
                oxygenValueTv.setText(fnum.format(f));
            }
        }

        if (CollectionUtils.isNotEmpty(getDataList())) {
            BoxInfo boxInfo = getDataList().get(position);
            if (boxInfo != null) {
                devNoTv.setText(boxInfo.getBoxNumber());
            }

        }

        praphTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatteLogger.d("图标");
                ChartDelegate chartDelegate = new ChartDelegate();
                DELEGATE.getSupportDelegate().start(chartDelegate);
            }
        });
        return holder.getConvertView();
    }

    public List<BoxAndWaterStatusDTO> getWaterStatusDTOList() {
        return waterStatusDTOList;
    }

    public void setWaterStatusDTOList(List<BoxAndWaterStatusDTO> waterStatusDTOList) {
        this.waterStatusDTOList = waterStatusDTOList;
    }
}
