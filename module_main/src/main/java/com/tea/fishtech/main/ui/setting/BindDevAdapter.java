package com.tea.fishtech.main.ui.setting;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tea.fishtech.common.constants.Constants;
import com.tea.fishtech.common.model.BoxInfo;
import com.tea.fishtech.common.utils.log.LatteLogger;
import com.tea.fishtech.main.R;
import com.tea.fishtech.ui.delegates.LatteDelegate;

import java.util.List;

public class BindDevAdapter extends RecyclerView.Adapter<BindDevAdapter.ViewHolder> {

    private LatteDelegate DELEGATE;

    private List<BoxInfo> boxInfos = Lists.newArrayList();

    private Typeface typeface;

    public BindDevAdapter(List<BoxInfo> boxInfos, LatteDelegate delegate) {
        LatteLogger.d(JSON.toJSONString(boxInfos));
        this.boxInfos = boxInfos;
        this.DELEGATE = delegate;

        typeface = Typeface.createFromAsset(DELEGATE.getProxyActivity().getAssets(),"iconfont.ttf");

    }

    @Override
    public BindDevAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dev_bind_list, parent, false);
        return new BindDevAdapter.ViewHolder(view,parent.getContext());
    }

    @Override
    public void onBindViewHolder(BindDevAdapter.ViewHolder holder, int position) {
        holder.titleDevType.setTypeface(typeface);
        if (boxInfos.get(position).getBoxTypeId().equals(Constants.BOX_TYPE_WATER)) {
            holder.titleDevType.setText(DELEGATE.getProxyActivity().getResources().getString(R.string.water));
        } else if (boxInfos.get(position).getBoxTypeId().equals(Constants.BOX_TYPE_CTRL)) {
            holder.titleDevType.setText(DELEGATE.getProxyActivity().getResources().getString(R.string.device));
        }

//        holder.devNumber.setText(boxInfos.get(position).getBoxNumber());
    }

    @Override
    public int getItemCount() {
        return boxInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Context context;

        private TextView titleDevType;

        private TextView devNumber;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
//            devNumber = itemView.findViewById(R.id.tv_devNumber);
            titleDevType = itemView.findViewById(R.id.title_dev_type);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
