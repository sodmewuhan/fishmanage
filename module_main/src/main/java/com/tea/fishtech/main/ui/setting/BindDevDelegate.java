package com.tea.fishtech.main.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.collect.Lists;
import com.tea.fishtech.common.constants.ServerURL;
import com.tea.fishtech.common.constants.TestConstants;
import com.tea.fishtech.common.model.BoxInfo;
import com.tea.fishtech.common.model.Result;
import com.tea.fishtech.common.net.RestCreator;
import com.tea.fishtech.common.utils.log.LatteLogger;
import com.tea.fishtech.main.R;
import com.tea.fishtech.main.R2;
import com.tea.fishtech.ui.delegates.LatteDelegate;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 塘口绑定设备
 */
public class BindDevDelegate extends LatteDelegate {

    private static final String TAG = BindDevDelegate.class.getName();

    @BindView(R2.id.register_titleBar_iv_back)
    ImageView mTitleBarIvBack;

    @BindView(R2.id.bind_flaot_btn)
    FloatingActionButton fab;

    @BindView(R2.id.rv_bind_device_list)
    RecyclerView mRecyclerView = null;

    // 未绑定的设备
    private List<BoxInfo> unBindDev = Lists.newArrayList();

    // 已经绑定设备
    private List<BoxInfo> bindDev = Lists.newArrayList();

    private Long ponId = null;

    private BindDevAdapter bindDevAdapter;

    private LinearLayoutManager mLayoutManager;

    @Override
    public Object setLayout() {
        return R.layout.delegate_bind_dev_pond;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        ponId = getArguments().getLong("pondId");
        initData();
        getUnBindDev();
        LatteLogger.d("ponid is " + ponId);
    }

    @OnClick({R2.id.register_titleBar_iv_back})
    public void clickEventPerform(View view) {
        switch (view.getId()) {
//            case R.id.register_titleBar_iv_back:
//                getActivity().onBackPressed();
//                break;
        }
    }



    /**
     * 添加用户需要绑定的盒子
     * @param view
     */
    @OnClick(R2.id.bind_flaot_btn)
    public void fabClick(View view) {
        // 绑定设备
        addDevList();
    }

    private void addDevList() {
        // 采用Android-PickerView 下拉实现
        List<String> unBindDevId = Lists.newArrayList();
        for(BoxInfo boxInfo: unBindDev) {
            unBindDevId.add(boxInfo.getBoxNumber());
        }

//        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                BoxInfo bindPondParameter = new BoxInfo();
//                String devId = unBindDevId.get(options1);
//
//                bindPondParameter.setBoxNumber(devId);
//                bindPondParameter.setFishPondId(ponId);
//                // 绑定
//                bindDevAndPond(bindPondParameter);
//                // 刷新数据
//
//            }
//        })
//        .setCancelText("取消")//取消按钮文字
//        .setSubmitText("确定")//确认按钮文字
//        .build();


//        pvOptions.setPicker(unBindDevId);
//        pvOptions.show();

    }

    // 得到当前已经绑定的数据
    private void initData() {
        try {
            BoxInfo boxInfo = new BoxInfo();
            boxInfo.setFishPondId(ponId);

            String raw = JSON.toJSONString(boxInfo);
            final Observable<String> observable = RestCreator.getRxRestService()
                    .postRaw(ServerURL.apiGetBindDevByFishPond, RequestBody.create(
                            MediaType.parse("application/json;charset=UTF-8"),
                            raw
                    ));
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String response) {
                            LatteLogger.d("the bind device list is " + response);
                            Result<BoxInfo> result = JSON.parseObject(response,
                                    new TypeReference<Result<BoxInfo>>() {
                                    });
                            if ("SUCCESS".equals(result.getMessage())) {
                                List<BoxInfo> boxInfos = result.getData();
                                setRecyclerView(boxInfos);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } catch (Exception ex) {

        }
    }

    // 设备与塘口绑定
    private void bindDevAndPond(BoxInfo boxInfo) {
        try {
            String raw = JSON.toJSONString(boxInfo);
            LatteLogger.d(TAG,raw);

            final Observable<String> observable = RestCreator.getRxRestService()
                    .postRaw(ServerURL.apiBindDevAndPond, RequestBody.create(
                            MediaType.parse("application/json;charset=UTF-8"),
                            raw
                    ));
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String response) {
                            LatteLogger.d("the response " + response);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } catch (Exception ex) {

        }
    }

    private void getUnBindDev() {
        String customerName = TestConstants.DEFAULT_USER;
        if (StringUtils.isEmpty(customerName)) {
            return;
        }

        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("userName",customerName);
            String raw = jsonParam.toString();
            LatteLogger.d(TAG,raw);

            final Observable<String> observable = RestCreator.getRxRestService()
                    .postRaw(ServerURL.apiGetUnBindDevByUser, RequestBody.create(
                            MediaType.parse("application/json;charset=UTF-8"),
                            raw
                    ));

            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String response) {
                            LatteLogger.d("the unbind device list is " + response);
                            Result<BoxInfo> result = JSON.parseObject(response,
                                    new TypeReference<Result<BoxInfo>>() {
                                    });
                            if ("SUCCESS".equals(result.getMessage())) {
                                if (result.getData() != null && result.getData().size() > 0) {
                                    unBindDev = result.getData();

                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {

        }
    }

    @Override
    public void post(Runnable runnable) {

    }


    private void setRecyclerView(List<BoxInfo> boxInfos) {
        bindDevAdapter = new BindDevAdapter(boxInfos,this);
        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(bindDevAdapter);

    }
}
