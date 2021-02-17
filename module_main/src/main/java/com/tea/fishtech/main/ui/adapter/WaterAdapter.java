package com.tea.fishtech.main.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.tea.fishtech.common.model.BoxInfo;
import com.tea.fishtech.common.ui.Adapter.CommonAdapter;
import com.tea.fishtech.common.ui.Adapter.ViewHolder;
import com.tea.fishtech.main.R;

import java.util.List;

public class WaterAdapter extends CommonAdapter<BoxInfo> implements View.OnClickListener {

    public WaterAdapter(Context context, List<BoxInfo> dataList) {
        super(context, dataList);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = ViewHolder.get(getContext(), convertView, R.layout.item_water_list, viewGroup);


        return holder.getConvertView();
    }
}
