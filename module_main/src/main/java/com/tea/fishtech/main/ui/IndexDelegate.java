package com.tea.fishtech.main.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.tea.fishtech.common.constants.ServerURL;
import com.tea.fishtech.common.constants.TestConstants;
import com.tea.fishtech.common.model.FishPondDto;
import com.tea.fishtech.common.model.Result;
import com.tea.fishtech.common.utils.log.LatteLogger;
import com.tea.fishtech.main.R;
import com.tea.fishtech.main.R2;
import com.tea.fishtech.main.ui.adapter.ViewPageAdapter;
import com.tea.fishtech.ui.bottom.BottomItemDelegate;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.request.PostRequest;
import com.zhouyou.http.subsciber.IProgressDialog;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;

import butterknife.BindView;

public class IndexDelegate extends BottomItemDelegate {

    private static final String TAG = IndexDelegate.class.getName();

    @BindView(R2.id.magic_indicator1)
    MagicIndicator magicIndicator;

    @BindView(R2.id.view_pager)
    ViewPager viewPager;

//    private List<String> titles = new ArrayList<>();

    private ViewPageAdapter titleAdapter;

    private CommonNavigator mCommonNavigator;

    private List<FishPondDto> fishPondDtos;

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initData();
    }



    @Override
    public void post(Runnable runnable) {

    }


    public void initData() {

        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("userName", TestConstants.DEFAULT_USER);
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

            PostRequest post = EasyHttp.post(ServerURL.apiGetAllPondByUser);
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
                    Result<FishPondDto> result = JSON.parseObject(response,
                            new TypeReference<Result<FishPondDto>>() {
                            });

                    if ("SUCCESS".equals(result.getMessage())) {
                        LatteLogger.d("from net read rows is " + result.getData().size());
                        if (result.getData() != null && result.getData().size() > 0) {
                            fishPondDtos = result.getData();

                            initTiltle();
                        } else {
                            // TODO 无塘口信息
                        }
                    }

                }
            });

        } catch (Exception ex) {
            LatteLogger.e(ex.getMessage(),ex.getCause().getMessage());
        }
    }

    private void initTiltle() {
        titleAdapter = new ViewPageAdapter(fishPondDtos, getParentDelegate());
        viewPager.setAdapter(titleAdapter);
        magicIndicator.setBackgroundColor(Color.WHITE);
        mCommonNavigator = new CommonNavigator(getContext());
        mCommonNavigator.setSkimOver(true);
        mCommonNavigator.setAdjustMode(true);
        mCommonNavigator.setScrollPivotX(0.35f);
        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return fishPondDtos.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(fishPondDtos.get(index).getFishPond().getPondName());
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#e94220"));
                simplePagerTitleView.setTextSize(16f);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LatteLogger.d("名字:" + fishPondDtos.get(index).getFishPond().getPondName() + "index is " + index);
                        viewPager.setCurrentItem(index);
                        titleAdapter.setPosition(index);
                        titleAdapter.notifyDataSetChanged();
                    }
                });
                return simplePagerTitleView;

            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Color.parseColor("#ebe4e3"));
                return indicator;
            }
        });

        magicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }
}
