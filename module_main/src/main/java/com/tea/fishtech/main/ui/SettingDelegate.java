package com.tea.fishtech.main.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.collect.Lists;
import com.mylhyl.circledialog.BaseCircleDialog;
import com.mylhyl.circledialog.CircleDialog;
import com.tea.fishtech.common.constants.Constants;
import com.tea.fishtech.common.constants.ServerURL;
import com.tea.fishtech.common.constants.TestConstants;
import com.tea.fishtech.common.model.FishPondDto;
import com.tea.fishtech.common.model.Result;
import com.tea.fishtech.common.net.RestCreator;
import com.tea.fishtech.common.utils.log.LatteLogger;
import com.tea.fishtech.main.R;
import com.tea.fishtech.main.R2;
import com.tea.fishtech.main.ui.setting.FishpondAdapter;
import com.tea.fishtech.ui.bottom.BottomItemDelegate;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.request.PostRequest;
import com.zhouyou.http.subsciber.IProgressDialog;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SettingDelegate extends BottomItemDelegate  {

    private static final String TAG = SettingDelegate.class.getName();

    /**
     * 增加按钮
     */
    @BindView(R2.id.flaot_btn)
    FloatingActionButton fab;

    // 列表信息
    @BindView(R2.id.rv_pond_list)
    RecyclerView mRecyclerView = null;

    // 数据
    private List<FishPondDto> pondLists = Lists.newArrayList();

    private BaseCircleDialog dialogFragment;

    private LinearLayoutManager mLayoutManager;

    private FishpondAdapter fishpondAdapter;

    @Override
    public Object setLayout() {
        return R.layout.delegate_setting;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        try {
            // 初始化数据
            getFishPond();
        } catch (Exception e) {
            Toasty.error(getContext(), "数据加载失败", Toast.LENGTH_SHORT, true).show();
        }

    }

    /**
     * 添加塘口信息
     * @param view
     */
    @OnClick(R2.id.flaot_btn)
    public void fabClick(View view) {

        //getParentDelegate().getSupportDelegate().start(new AddPondDelegate());
        addPond();
    }

    @Override
    public void post(Runnable runnable) {

    }

    // 初始化塘口信息
    private void getFishPond() throws Exception {
        String customerName = TestConstants.DEFAULT_USER;
        if (StringUtils.isEmpty(customerName)) {
            return;
        }

        JSONObject jsonParam = new JSONObject();
        jsonParam.put(Constants.USER_NAME,customerName);
        String postRaw = jsonParam.toString();

        final Observable<String> observable = RestCreator.getRxRestService()
                .postRaw(ServerURL.apiGetAllPondByUser, RequestBody.create(
                        MediaType.parse("application/json;charset=UTF-8"),
                        postRaw
                ));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String response) {
                        Result<FishPondDto> result = JSON.parseObject(response,
                                new TypeReference<Result<FishPondDto>>() {
                                });
                        if (result.getData() != null && result.getData().size() > 0) {
                            pondLists = result.getData();
                            setRecyclerView();
                        }

                        Toasty.info(getContext(), "数据加载成功", Toast.LENGTH_SHORT, true).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void addPond() {
        dialogFragment = new CircleDialog.Builder()
                .setCanceledOnTouchOutside(false)
                .setCancelable(true)
                .setTitle("输入框")
                .setInputHint("塘口名字")
                .setInputHeight(150)
                .setInputShowKeyboard(true)
                .setInputEmoji(true)
                .setInputCounter(18)
                .configInput(params -> {
                    params.styleText = Typeface.BOLD;
                })
                .setNegative("取消", null)
                .setPositiveInput("确定", (text, v) -> {
                    if (TextUtils.isEmpty(text)) {
                        Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();
                        return false;
                    } else {
                        // 添加塘口调用后台信息
                        addPontSubmit(text);
                        return true;
                    }
                })
                .show(_mActivity.getSupportFragmentManager());
    }

    private void addPontSubmit(String pondName) {

        JSONObject jsonParam = new JSONObject();
        jsonParam.put("userName", TestConstants.DEFAULT_USER);
        jsonParam.put("pondName", pondName);
        String raw = jsonParam.toString();
        LatteLogger.d(TAG,raw);

        IProgressDialog mProgressDialog = new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("请稍候...");
                return dialog;
            }
        };

        PostRequest post = EasyHttp.post(ServerURL.apiAddPond);

        post.upJson(raw);
        post.execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
            @Override
            public void onError(ApiException e) {
                super.onError(e);//super.onError(e)必须写不能删掉或者忘记了
                LatteLogger.e(e.getMessage(), e.fillInStackTrace().getMessage());
            }

            @Override
            public void onSuccess(String response) {
                LatteLogger.d(response);
                Result result = JSON.parseObject(response, new TypeReference<Result>() {});

                if ("SUCCESS".equals(result.getMessage())) {
                    Toasty.info(getContext(), "数据已经成功添加", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }

    private void setRecyclerView() {
        fishpondAdapter = new FishpondAdapter(this.pondLists,this);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(fishpondAdapter);
    }
}
