package com.tea.fishtech.main.ui.setting;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.tea.fishtech.common.constants.ServerURL;
import com.tea.fishtech.common.model.FishPond;
import com.tea.fishtech.common.model.FishPondDto;
import com.tea.fishtech.common.model.Result;
import com.tea.fishtech.common.utils.log.LatteLogger;
import com.tea.fishtech.main.R;
import com.tea.fishtech.ui.delegates.LatteDelegate;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;

import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class FishpondAdapter extends RecyclerView.Adapter<FishpondAdapter.ViewHolder> {

    private static final String TAG = FishpondAdapter.class.getName();

    private List<FishPondDto> fishPondDtos = Lists.newArrayList();

    private LatteDelegate DELEGATE;

    public FishpondAdapter(List<FishPondDto> fishPondDtos, LatteDelegate delegate) {
        this.fishPondDtos = fishPondDtos;
        DELEGATE = delegate;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pond_list, parent, false);
        return new FishpondAdapter.ViewHolder(view,parent.getContext());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.pondNameTV.setText(fishPondDtos.get(position).getFishPond().getPondName());

        holder.categoryTv.setText(fishPondDtos.get(position).getFishPond().getCategory());

        holder.manageModeTv.setText(fishPondDtos.get(position).getFishPond().getManageModeDesc());
    }

    @Override
    public int getItemCount() {
        return fishPondDtos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Context context;

        // 塘口名称
        public TextView pondNameTV;

        // 养殖品种
        private TextView categoryTv;

        // 管理模式
        TextView manageModeTv;

        public Button modifyBtn;

        public Button bindDevBtn;

        public ViewHolder(View itemView, Context context) {
            super(itemView);

            this.context = context;

            pondNameTV = itemView.findViewById(R.id.tv_pondname_text);

            categoryTv = itemView.findViewById(R.id.tv_category_text);

            manageModeTv = itemView.findViewById(R.id.tv_manageMode_tv);

            modifyBtn = itemView.findViewById(R.id.modify_btn);

            bindDevBtn = itemView.findViewById(R.id.bind_dev_btn);

            // 注册点击事件
            itemView.setOnClickListener(this);
            modifyBtn.setOnClickListener(this);
            bindDevBtn.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Long pondId = fishPondDtos.get(position).getFishPond().getId();
            if (view.getId()== R.id.modify_btn) {
                LatteLogger.d(TAG,"修改管理模式,"+pondId);
                showModfiyMode(position,this.context);
            } else if (view.getId() == R.id.bind_dev_btn) {
                LatteLogger.d(TAG,"绑定设备"+pondId);
                BindDevDelegate bindDevDelegate = new BindDevDelegate();
                // 传递参数
                Bundle bundle = new Bundle();
                bundle.putLong("pondId",pondId);
                bindDevDelegate.setArguments(bundle);
                DELEGATE.getParentDelegate().getSupportDelegate().start(bindDevDelegate);
            }
        }

        // 修改管理模式
        private void showModfiyMode(int position, Context context) {

            FishPond fishPond = fishPondDtos.get(position).getFishPond();
            //1：手动  2 ：自动  3  定时
            List<String> categoryMode = Lists.newArrayList();
            categoryMode.add("手动");
            categoryMode.add("自动");
            categoryMode.add("定时");

//            OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
//                @Override
//                public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                    LatteLogger.d("确定位置" + options1);
//                    // 修改管理方式
//                    fishPond.setManageMode(String.valueOf(options1+1));
//                    updatePondRest(fishPond);
//
//                    // 刷新当前item View
//                    notifyItemChanged(position);
//                }
//            })
//            .setCancelText("取消")//取消按钮文字
//            .setSubmitText("确定")//确认按钮文字
//            .build();
//
//            pvOptions.setPicker(categoryMode);
//
//            pvOptions.show();
        }

        // RPC调用管理模式改变
        private void updatePondRest(FishPond fishPond) {

            try {
                String raw = JSON.toJSONString(fishPond);
                LatteLogger.d(TAG,raw);

                IProgressDialog mProgressDialog = new IProgressDialog() {
                    @Override
                    public Dialog getDialog() {
                        ProgressDialog dialog = new ProgressDialog(DELEGATE.getContext());
                        dialog.setMessage("请稍候...");
                        return dialog;
                    }
                };

                EasyHttp.post(ServerURL.apiUpdatePond)
                        .upJson(raw)
                        .execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);//super.onError(e)必须写不能删掉或者忘记了
                            }

                            @Override
                            public void onSuccess(String response) {
                                Result result = JSON.parseObject(response, new TypeReference<Result>() {});
                                if ("SUCCESS".equals(result.getMessage())) {
                                    LatteLogger.d("data update success");
                                    Toasty.success(DELEGATE.getContext(), "模式修改成功", Toast.LENGTH_SHORT, true)
                                            .show();
                                }

                            }
                        });
            } catch (Exception e) {
                LatteLogger.e(TAG, Arrays.toString(e.getStackTrace()));
            }
        }
    }
}
