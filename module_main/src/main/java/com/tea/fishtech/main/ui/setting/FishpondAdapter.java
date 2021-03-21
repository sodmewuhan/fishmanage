package com.tea.fishtech.main.ui.setting;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.params.DialogParams;
import com.tea.fishtech.common.app.ConfigKeys;
import com.tea.fishtech.common.app.Latte;
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

    private Typeface typeface;

    public FishpondAdapter(List<FishPondDto> fishPondDtos, LatteDelegate delegate) {
        this.fishPondDtos = fishPondDtos;
        DELEGATE = delegate;

        typeface = Typeface.createFromAsset(DELEGATE.getProxyActivity().getAssets(),"iconfont.ttf");
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
    }

    @Override
    public int getItemCount() {
        return fishPondDtos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Context context;

        // 塘口名称
        public TextView pondNameTV;
        // 删除
        public TextView deleteTv;

        // 养殖品种
        private TextView categoryTv;

        // 设置
        private TextView settingTv;

        public ViewHolder(View itemView, Context context) {
            super(itemView);

            this.context = context;

            pondNameTV = itemView.findViewById(R.id.tv_pondname_text);

            deleteTv = itemView.findViewById(R.id.tv_delete);
            deleteTv.setTypeface(typeface);
            deleteTv.setText(R.string.remove);

            categoryTv = itemView.findViewById(R.id.tv_category_text);

            settingTv = itemView.findViewById(R.id.tv_setting);
            settingTv.setTypeface(typeface);
            settingTv.setText(R.string.setting);

             //注册点击事件
            itemView.setOnClickListener(this);
            deleteTv.setOnClickListener(this);
            settingTv.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Long pondId = fishPondDtos.get(position).getFishPond().getId();
            if (view.getId()== R.id.tv_delete) {
                LatteLogger.d(TAG,"删除塘口信息,"+pondId);
                deletePond();
            } else if (view.getId() == R.id.tv_setting) {
                LatteLogger.d(TAG,"绑定设备"+pondId);
                BindDevDelegate bindDevDelegate = new BindDevDelegate();
                // 传递参数
                Bundle bundle = new Bundle();
                bundle.putLong("pondId",pondId);
                bindDevDelegate.setArguments(bundle);
                DELEGATE.getParentDelegate().getSupportDelegate().start(bindDevDelegate);
            }
        }

        private void deletePond() {
            new CircleDialog.Builder()
                    .setMaxHeight(0.8f)
                    .setCanceledOnTouchOutside(false)
                    .setCancelable(false)
                    .setTitle("确定")
                    .setText("您确定要删除该塘口信息吗？")
                    .setNegative("取消", null)
                    .setPositive("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LatteLogger.d("确定删除");
                            // TODO 删除
                        }
                    })
                    .show(DELEGATE.getFragmentManager())
                    ;
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
