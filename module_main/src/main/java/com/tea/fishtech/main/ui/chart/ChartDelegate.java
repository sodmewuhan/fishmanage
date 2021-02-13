package com.tea.fishtech.main.ui.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.tea.fishtech.common.constants.Constants;
import com.tea.fishtech.common.constants.ServerURL;
import com.tea.fishtech.common.constants.TestConstants;
import com.tea.fishtech.common.model.AxisData;
import com.tea.fishtech.common.model.Result;
import com.tea.fishtech.common.net.RestCreator;
import com.tea.fishtech.common.utils.log.LatteLogger;
import com.tea.fishtech.main.R;
import com.tea.fishtech.main.R2;
import com.tea.fishtech.ui.delegates.LatteDelegate;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ChartDelegate extends LatteDelegate implements View.OnClickListener, OnChartValueSelectedListener {

    private static final String TAG = ChartDelegate.class.getName();

    private static DateTimeFormatter SDF = DateTimeFormat.forPattern("MM月dd日");

    private static DateTimeFormatter SDF_PARAMETER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    private static final Integer LABEL_COUNT = 8;

    // 溶氧量的ID
    private static final String OXYGEN_ID = "1";

    // 接口调用时间参数
    private Integer selectBeginDateTime;

    private Integer selectEndDateTime;

    // 选择的日期
    private DateTime selectDate;

    @BindView(R2.id.title_back)
    ImageView mTitleBarIvBack;

    // 今天
    @BindView(R2.id.topic_item_today)
    RadioButton todayBtn;

    // 昨天
    @BindView(R2.id.topic_item_yesterday)
    RadioButton yesterdayBtn;

    // 前天
    @BindView(R2.id.topic_item_before_yesterday)
    RadioButton beforeBtn;

    // 时间
    @BindView(R2.id.clock0)
    RadioButton clock0Btn;

    @BindView(R2.id.clock4)
    RadioButton clock4Btn;

    @BindView(R2.id.clock8)
    RadioButton clock8Btn;

    @BindView(R2.id.clock12)
    RadioButton clock12Btn;

    @BindView(R2.id.clock16)
    RadioButton clock16Btn;

    @BindView(R2.id.clock20)
    RadioButton clock20Btn;

    @BindView(R2.id.lineChart)
    LineChart lineChart;

    @Override
    public Object setLayout() {
        return R.layout.delegate_chart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        LatteLogger.d("enter the ChartDelegate");
        mTitleBarIvBack.setOnClickListener(this);

        initCalendarBtn();
    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.title_back) {
            finish();
        }
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    /**
     * 下拉框的事件监听
     * @param view
     * @param ischanged
     */
    @OnCheckedChanged({R2.id.topic_item_today,R2.id.topic_item_yesterday,R2.id.topic_item_before_yesterday})
    public void OnCheckedChangeListener(CompoundButton view, boolean ischanged ){

        if (view.getId() == R.id.topic_item_today) {
            if (ischanged) {
                selectDate = DateTime.now();
            }
        }

        if (view.getId() == R.id.topic_item_yesterday) {
            if (ischanged) {
                selectDate = DateTime.now().plusDays(-1);
            }
        }

        if (view.getId() == R.id.topic_item_before_yesterday) {
            if (ischanged) {
                selectDate = DateTime.now().plusDays(-2);
            }
        }

        try {
            getChartData(TestConstants.DETAULT_XOYGEN_ID);
        } catch (Exception e) {
            LatteLogger.e(TAG,e.getMessage());
        }
    }

    @OnCheckedChanged({R2.id.clock0,R2.id.clock4,R2.id.clock8,R2.id.clock12,R2.id.clock16,R2.id.clock20})
    public void OnTimeCheckedChangeListener(CompoundButton view, boolean ischanged ){

        if (view.getId() == R.id.clock0) {
            if (ischanged) {
                selectBeginDateTime = 0;
                selectEndDateTime = 4;
            }
        }

        if (view.getId() == R.id.clock4) {
            if (ischanged) {
                selectBeginDateTime = 4;
                selectEndDateTime = 8;
            }
        }

        if (view.getId() == R.id.clock8) {
            if (ischanged) {
                selectBeginDateTime = 8;
                selectEndDateTime = 12;
            }
        }

        if (view.getId() == R.id.clock12) {
            if (ischanged) {
                selectBeginDateTime = 12;
                selectEndDateTime = 16;
            }
        }

        if (view.getId() == R.id.clock16) {
            if (ischanged) {
                selectBeginDateTime = 16;
                selectEndDateTime = 20;
            }
        }

        if (view.getId() == R.id.clock20) {
            if (ischanged) {
                selectBeginDateTime = 16;
                selectEndDateTime = 23;
            }
        }

        try {
            getChartData(TestConstants.DETAULT_XOYGEN_ID);
        } catch (Exception e) {
            LatteLogger.e(TAG,e.getMessage());
        }
    }

    /**
     * 初始化按钮
     */
    private void initCalendarBtn() {
        // 初始化日期
        selectDate = DateTime.now();
        todayBtn.setText(selectDate.toString(SDF));
        todayBtn.setChecked(true);

        yesterdayBtn.setText(DateTime.now().plusDays(-1).toString(SDF));

        beforeBtn.setText(DateTime.now().plusDays(-2).toString(SDF));

        // 初始化时间
        Integer rightNow = DateTime.now().get(DateTimeFieldType.hourOfDay());
        selectTimeBtn(rightNow);
    }

    private void selectTimeBtn(int rightNow) {
        if (rightNow >= 0 && rightNow < 4 ) {
            clock0Btn.setChecked(true);
            selectBeginDateTime = 0;
            selectEndDateTime = 4;
        }
        if (rightNow >= 4 && rightNow < 8 ) {
            clock4Btn.setChecked(true);
            selectBeginDateTime = 4;
            selectEndDateTime = 8;
        }
        if (rightNow >= 8 && rightNow < 12 ) {
            clock8Btn.setChecked(true);
            selectBeginDateTime = 8;
            selectEndDateTime = 12;
        }
        if (rightNow >= 12 && rightNow < 16 ) {
            clock12Btn.setChecked(true);
            selectBeginDateTime = 12;
            selectEndDateTime = 16;
        }
        if (rightNow >= 16 && rightNow < 20 ) {
            clock16Btn.setChecked(true);
            selectBeginDateTime = 16;
            selectEndDateTime = 20;
        }
        if (rightNow >= 20) {
            clock20Btn.setChecked(true);
            selectBeginDateTime = 20;
            selectEndDateTime = 24;
        }
    }

    // 初始化设置图表
    private void initChart() {
        // background color
        lineChart.setBackgroundColor(Color.WHITE);
        // disable description text
        lineChart.getDescription().setEnabled(false);
        // enable touch gestures
        lineChart.setTouchEnabled(true);
        // set listeners
        lineChart.setOnChartValueSelectedListener(this);
        lineChart.setDrawGridBackground(false);

        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        // force pinch zoom along both axis
        lineChart.setPinchZoom(true);

        // 设置Y轴
        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = lineChart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            lineChart.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMinimum(0);
        }

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = lineChart.getXAxis();
            xAxis.setLabelCount(LABEL_COUNT);
            // vertical grid lines
//            xAxis.enableGridDashedLine(10f, 10f, 0f);
            xAxis.setDrawAxisLine(false);
            xAxis.setDrawGridLines(false);
            xAxis.setLabelRotationAngle(-20);//设置x轴字体显示角度

        }
//        //设置图表
        lineChart.getAxisRight().setEnabled(false); // 右边坐标不在显示

    }

    private void getChartData(String boxId) throws Exception {
        // 得到日期
        DateTime beginTime = selectDate;
        DateTime endTime = selectDate;

        beginTime = beginTime.withField(DateTimeFieldType.hourOfDay(),selectBeginDateTime)
                .withField(DateTimeFieldType.minuteOfHour(),0)
                .withField(DateTimeFieldType.secondOfMinute(),0);

        endTime = endTime.withField(DateTimeFieldType.hourOfDay(),selectEndDateTime)
                .withField(DateTimeFieldType.minuteOfHour(),0)
                .withField(DateTimeFieldType.secondOfMinute(),0);
        if (selectEndDateTime == 23) {
            endTime = endTime.withField(DateTimeFieldType.hourOfDay(),selectEndDateTime)
                    .withField(DateTimeFieldType.minuteOfHour(),59)
                    .withField(DateTimeFieldType.secondOfMinute(),59);
        }

        // 时间统一减去8小时，适应influxdb的时间
        beginTime = beginTime.plusHours(-8);
        endTime = endTime.plusHours(-8);

        // 格式化时间
        String beginTimeStr = beginTime.toString(SDF_PARAMETER);
        String endTimeStr   = endTime.toString(SDF_PARAMETER);

        // 得到用户名
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("boxId",boxId);
        jsonParam.put("deviceId",OXYGEN_ID);
        jsonParam.put("beginTime",beginTimeStr);
        jsonParam.put("endTime",endTimeStr);

        String raw = jsonParam.toString();

        final Observable<String> observable = RestCreator.getRxRestService()
                .postRaw(ServerURL.apiCharDay, RequestBody.create(
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
                        LatteLogger.d(response);
                        // UI 线程
                        Result<AxisData> result = JSON.parseObject(response,
                                new TypeReference<Result<AxisData>>() {
                                });
                        if (Constants.SUCCESS.equals(result.getMessage())) {
                            // 绘图
                            if (result.getData() != null && result.getData().size() > 0) {
                                initXYAxis(result.getData());
                                setLineChartData(result.getData());
                            } else {
                                //  清空数据
                                lineChart.clear();
                            }
                        } else {
                            //  清空数据
                            lineChart.clear();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        ;

    }

    private void initXYAxis(List<AxisData> axisDatas) {
        //        //自定义x轴显示
        Float[] fList = new Float[axisDatas.size()];
        for(int i=0; i<axisDatas.size(); i++) {
            AxisData data = axisDatas.get(i);
            fList[i] = Float.valueOf(data.getXaxis());
        }

        XAxisValueFormatter formatter = new XAxisValueFormatter(fList);
        // 格式化X轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(formatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
    }
    /**
     * 设置折线图的数据
     */
    private void setLineChartData(List<AxisData> axisDatas) {

        List<Entry> points = new ArrayList<>();
        if (axisDatas!=null && axisDatas.size()>0) {
            for(AxisData data : axisDatas) {
                points.add(new Entry(Float.valueOf(data.getXaxis()),Float.valueOf(data.getYaxis())));
            }
        }

        LineDataSet setOxygen = null;

        if (lineChart.getData() != null) {
            LineData lineData = lineChart.getData();
            lineData.removeDataSet(lineData.getDataSetCount() - 1);
        }
        // 首次创建
        setOxygen = new LineDataSet(points, "溶氧量");
        setOxygen.setLineWidth(2.5f);
        setOxygen.setDrawValues(true); // 是否在点上绘制Value

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setOxygen);

        LineData lineData = new LineData(dataSets);

        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private void finish() {
        // fragment 销毁自己
        getSupportDelegate().pop();
    }
}
