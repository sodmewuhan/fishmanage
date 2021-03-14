package com.tea.fishtech.main.ui.setting;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.hjq.bar.TitleBar;
import com.tea.fishtech.common.constants.Constants;
import com.tea.fishtech.common.constants.ServerURL;
import com.tea.fishtech.common.constants.TestConstants;
import com.tea.fishtech.common.model.BoxInfo;
import com.tea.fishtech.common.model.Result;
import com.tea.fishtech.common.net.RestCreator;
import com.tea.fishtech.common.utils.log.LatteLogger;
import com.tea.fishtech.main.R;
import com.tea.fishtech.main.R2;
import com.tea.fishtech.ui.delegates.LatteDelegate;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import butterknife.BindView;
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

    private List<String> titleList = Lists.newArrayList();

    @BindView(R2.id.dev_titlebar)
    TitleBar titleBar;

    @BindView(R2.id.magic_indicator1)
    MagicIndicator magicIndicator;

    @BindView(R2.id.view_pager)
    ViewPager viewPager;


    private CommonNavigator mCommonNavigator;


    private DeviceFragmentAdapter deviceFragmentAdapter;

     //未绑定的设备
    private List<BoxInfo> boxInfos = Lists.newArrayList();
//
//    // 已经绑定设备
//    private List<BoxInfo> bindDev = Lists.newArrayList();

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


        LatteLogger.d("ponid is " + ponId);
    }

    /**
     * 初始化控件
     */
    private void initControl() {
        // 绑定adapter
        deviceFragmentAdapter = new DeviceFragmentAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,titleList,boxInfos);
        viewPager.setAdapter(deviceFragmentAdapter);
        // 设置指示器
        magicIndicator.setBackgroundColor(Color.WHITE);
        mCommonNavigator = new CommonNavigator(getContext());
        mCommonNavigator.setSkimOver(true);
        mCommonNavigator.setAdjustMode(true);
        mCommonNavigator.setScrollPivotX(0.35f);

        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {

                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(titleList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ffff621d"));
                simplePagerTitleView.setTextSize(16f);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LatteLogger.d("名字:" + titleList.get(index)+ "index is " + index);
                        viewPager.setCurrentItem(index);
//                        deviceFragmentAdapter.setPosition(index);
                        deviceFragmentAdapter.notifyDataSetChanged();
                    }


                });
                return simplePagerTitleView;
            }


            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });

        magicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }


//    private void  initListener() {
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                LatteLogger.d("the position is" + position);
//                magicIndicator.onPageSelected(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                magicIndicator.onPageScrollStateChanged(state);
//            }
//        });
//
//    }


//    private void addDevList() {
//        // 采用Android-PickerView 下拉实现
//        List<String> unBindDevId = Lists.newArrayList();
//        for(BoxInfo boxInfo: unBindDev) {
//            unBindDevId.add(boxInfo.getBoxNumber());
//        }
//
//    }

    // 得到当前已经绑定的数据
    private void initData() {
        try {

            titleList.add(Constants.DEV_TYPE_NAME_WATER);
            titleList.add(Constants.DEV_TYPE_NAME_CONTROL);

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
                                boxInfos = result.getData();
                                // 有数据后绑定
                                initControl();
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

//    private void getUnBindDev() {
//        String customerName = TestConstants.DEFAULT_USER;
//        if (StringUtils.isEmpty(customerName)) {
//            return;
//        }
//
//        try {
//            JSONObject jsonParam = new JSONObject();
//            jsonParam.put("userName",customerName);
//            String raw = jsonParam.toString();
//            LatteLogger.d(TAG,raw);
//
//            final Observable<String> observable = RestCreator.getRxRestService()
//                    .postRaw(ServerURL.apiGetUnBindDevByUser, RequestBody.create(
//                            MediaType.parse("application/json;charset=UTF-8"),
//                            raw
//                    ));
//
//            observable.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<String>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(String response) {
//                            LatteLogger.d("the unbind device list is " + response);
//                            Result<BoxInfo> result = JSON.parseObject(response,
//                                    new TypeReference<Result<BoxInfo>>() {
//                                    });
//                            if ("SUCCESS".equals(result.getMessage())) {
//                                if (result.getData() != null && result.getData().size() > 0) {
//                                    unBindDev = result.getData();
//
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//        } catch (Exception e) {
//
//        }
//    }

    @Override
    public void post(Runnable runnable) {

    }


//    private void setRecyclerView(List<BoxInfo> boxInfos) {
//        bindDevAdapter = new BindDevAdapter(boxInfos,this);
//        mLayoutManager = new LinearLayoutManager(getContext());
//
////        mRecyclerView.setLayoutManager(mLayoutManager);
////        mRecyclerView.setAdapter(bindDevAdapter);
//
//    }
}
