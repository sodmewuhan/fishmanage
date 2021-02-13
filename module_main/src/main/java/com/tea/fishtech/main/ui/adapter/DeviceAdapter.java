package com.tea.fishtech.main.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tea.fishtech.common.constants.ServerURL;
import com.tea.fishtech.common.model.BoxAndWaterStatusDTO;
import com.tea.fishtech.common.model.BoxStatusDTO;
import com.tea.fishtech.common.model.Result;
import com.tea.fishtech.common.net.RestCreator;
import com.tea.fishtech.common.ui.Adapter.CommonAdapter;
import com.tea.fishtech.common.ui.Adapter.ViewHolder;
import com.tea.fishtech.common.utils.log.LatteLogger;
import com.tea.fishtech.main.R;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DeviceAdapter  extends CommonAdapter<BoxAndWaterStatusDTO> implements View.OnClickListener {

    public static final String TAG = DeviceAdapter.class.getSimpleName();

    private static final String ONLINE = "在线";

    private static final String OFFLINE = "离线";

    private final Context context = this.getContext();

    private CheckBox device1;

    private CheckBox device2;

    private CheckBox device3;

    private CheckBox device4;

    public DeviceAdapter(Context context, List<BoxAndWaterStatusDTO> dataList) {
        super(context, dataList);
    }

    /**
     * 设置界面信息
     * @param position
     * @param convertView
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = ViewHolder.get(getContext(), convertView, R.layout.item_device_list, viewGroup);
        // 设置准备编号
        TextView deviceId = holder.getView(R.id.deviceinfo);
        StringBuilder deviceIdInfo = new StringBuilder("设备编号:");
        BoxAndWaterStatusDTO boxAndWaterStatusDTO = getDataList().get(position);
        deviceIdInfo.append(boxAndWaterStatusDTO.getBoxInfoDTO().getBoxNumber());


        if (boxAndWaterStatusDTO.getBoxOnline() != null) {
            // 设备在线离线判断
            String online = boxAndWaterStatusDTO.getBoxOnline().getOnlineStatus();
            online = StringUtils.isNotEmpty(online) ? online : StringUtils.EMPTY;
//            deviceIdInfo.append("：");
            online = online.equals("Y") ? ONLINE : OFFLINE;

//            deviceIdInfo.append(online);
        }

        deviceId.setText(deviceIdInfo.toString());

        // 设置开关的监听
        String status = StringUtils.EMPTY;
        device1 = holder.getView(R.id.deviceId1);
        device2 = holder.getView(R.id.deviceId2);
        device3 = holder.getView(R.id.deviceId3);
        device4 = holder.getView(R.id.deviceId4);

        List<BoxStatusDTO> boxStatusDTOS = getDataList().get(position).getBoxStatusDTOS();

        if (boxStatusDTOS != null && boxStatusDTOS.size() > 0) {
            BoxStatusDTO boxStatusDTO1 = getBoxStatus(boxStatusDTOS,"1");
            if (boxStatusDTO1 != null) {
                device1.setChecked("0".equals(boxStatusDTO1.getAction()) ? false : true);
            }
            BoxStatusDTO boxStatusDTO2 = getBoxStatus(boxStatusDTOS,"2");
            if (boxStatusDTO2 != null) {
                device2.setChecked("0".equals(boxStatusDTO2.getAction()) ? false : true);
            }
            BoxStatusDTO boxStatusDTO3 = getBoxStatus(boxStatusDTOS,"3");
            if (boxStatusDTO3 != null) {
                device3.setChecked("0".equals(boxStatusDTO3.getAction()) ? false : true);
            }
            BoxStatusDTO boxStatusDTO4 = getBoxStatus(boxStatusDTOS,"4");
            if (boxStatusDTO4 != null) {
                device4.setChecked("0".equals(boxStatusDTO4.getAction()) ? false : true);
            }
        };
        device1.setTag(position);
        device2.setTag(position);
        device3.setTag(position);
        device4.setTag(position);

        device1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;

                boolean checked = checkBox.isChecked();
                int position = (int)checkBox.getTag();
                String boxNumber = getDataList().get(position).getBoxInfoDTO().getBoxNumber();
//                LatteLogger.d("device1 check status is " + checked + " and tag is " + position);
                String action  = checked ? "1" : "0";
                Log.d(TAG,"click deviceId2 and deviceId is " + boxNumber + " and action is " + action);
                controllDevice(String.valueOf(boxNumber),"1",action);
            }
        });

        device2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;

                boolean checked = checkBox.isChecked();
                int position = (int)checkBox.getTag();
                String boxNumber = getDataList().get(position).getBoxInfoDTO().getBoxNumber();
                LatteLogger.d("device2 check status is " + checked + " and tag is " + position);
                String action  = checked ? "1" : "0";
                Log.d(TAG,"click deviceId2 and deviceId is " + boxNumber + " and action is " + action);
                controllDevice(String.valueOf(boxNumber),"2",action);
            }
        });

        device3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;

                boolean checked = checkBox.isChecked();
                int position = (int)checkBox.getTag();
                String boxNumber = getDataList().get(position).getBoxInfoDTO().getBoxNumber();
                LatteLogger.d("device3 check status is " + checked + " and tag is " + position);
                String action  = checked ? "1" : "0";
                Log.d(TAG,"click deviceId3 and deviceId is " + boxNumber + " and action is " + action);
                controllDevice(String.valueOf(boxNumber),"3",action);
            }
        });

        device4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;

                boolean checked = checkBox.isChecked();
                int position = (int)checkBox.getTag();
                String boxNumber = getDataList().get(position).getBoxInfoDTO().getBoxNumber();
                LatteLogger.d("device4 check status is " + checked + " and tag is " + position);
                String action  = checked ? "1" : "0";
                Log.d(TAG,"click deviceId4 and deviceId is " + boxNumber + " and action is " + action);
                controllDevice(String.valueOf(boxNumber),"4",action);
            }
        });
        return holder.getConvertView();
    }

    private BoxStatusDTO getBoxStatus(List<BoxStatusDTO> boxStatusDTOS,String index) {
        for(BoxStatusDTO boxStatusDTO : boxStatusDTOS) {
            if (index.equals(boxStatusDTO.getPort())) {
                return boxStatusDTO;
            }
        }
        return null;
    }

    // 点击事件
    @Override
    public void onClick(View view) {

    }

    @Override
    public int getCount() {
        return getDataList().size();
    }

    @Override
    public Object getItem(int i) {
        return getDataList().get(i);
    }

    /**
     * 打开或者关闭设备
     * @param deviceId
     * @param action
     */
    private void controllDevice(final String deviceId , final String devNo, final String action) {

        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("topic", deviceId);
            jsonParam.put("port",devNo);
            jsonParam.put("action",action);
            String raw = jsonParam.toString();
            Log.d(TAG,"设备控制参数" + raw);

            final Observable<String> observable = RestCreator.getRxRestService()
                    .postRaw(ServerURL.apiDispatchInstructStatus, RequestBody.create(
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
                            LatteLogger.i("控制返回值",response);
                            Result result = JSON.parseObject(response, new TypeReference<Result>(){});
                            if (result!=null && "SUCCESS".equals(result.getMessage())) {
                                Toast.makeText(context, "操作成功", Toast.LENGTH_SHORT).show();
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
            Log.e(TAG,"控制设备报错",e);
        }
    }
}