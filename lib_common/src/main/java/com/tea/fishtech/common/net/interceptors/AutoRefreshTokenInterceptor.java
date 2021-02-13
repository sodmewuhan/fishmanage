//package com.tea.fishtech.common.net.interceptors;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.collect.Lists;
//import com.tea.fishtech.core.utils.log.LatteLogger;
//import com.tea.fishtech.core.utils.storage.LattePreference;
//import com.tea.fishtech.utils.Constants;
//import com.tea.fishtech.utils.ServerURL;
//
//import org.apache.commons.lang3.StringUtils;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import okhttp3.Call;
//import okhttp3.FormBody;
//import okhttp3.Interceptor;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
///**
// * 自动刷新过期的token
// */
//public class AutoRefreshTokenInterceptor implements Interceptor {
//
//    private static final String TAG = "AutoRefreshTokenInterceptor.class";
//
//    private static List<OnRefreshListener> mRefreshListenerList = Lists.newArrayList();
//    private static volatile boolean isRefreshing = false;
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request request = chain.request();
//        Response response = chain.proceed(request);
//
////        if (response.code() == Constants.AUTHORIZATION_CODE) {
////            // token失效
////            if (!isRefreshing) {
////                isRefreshing = true;
////                // 同步请求
////                String newToken = getNewToken();
////                if (StringUtils.isNotEmpty(newToken)) {
////                    LattePreference.addCustomAppProfile(Constants.TOKEN,newToken);
////                    Request newRequest = request.newBuilder()
////                            .header(Constants.TOKEN,newToken)
////                            .build();
////                    isRefreshing = false;
////                    triggerRefreshListener(true, newToken);
////                    return chain.proceed(newRequest);
////                } else {
////                    triggerRefreshListener(false, null);
////                }
////
////            } else {
////                CountDownLatch countDownLatch = new CountDownLatch(1);
////                //加入到请求队列中(回调运行在其他线程中，非当前线程)
////                mRefreshListenerList.add(new OnRefreshListener() {
////                    @Override
////                    public void onRefresh(boolean isSuccess, String accessToken) {
////                        mRefreshListenerList.remove(this);//从队列中删除
////                        if (isSuccess) {
////                            countDownLatch.countDown();//成功后继续请求
////                        } else {//可能由于网络原因造成RefreshToken失败
////                            //这儿就不再处理了， 直到线程终止
////                        }
////                    }
////                });
////
////                try {
////                    countDownLatch.await();
////                } catch (InterruptedException e) {
////                    LatteLogger.d("countDownLatch InterruptedException:" + e.toString());
////                }
////            }
////        }
//
//        return response;
//    }
//
//    /**
//     * 获取刷新的token
//     * @return
//     */
//    private String getNewToken() {
//        String token = StringUtils.EMPTY;
//
//        // 获取电话 和密码
//        String userName = LattePreference.getCustomAppProfile(Constants.LOGIN_USERNAME);
//        String encrytedPassword = LattePreference.getCustomAppProfile(Constants.USER_ENCRYTED_PASSWORD);
//
//        if (StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(encrytedPassword)) {
//            Map<String, String> param = new HashMap<String, String>();
//            param.put("username", userName);
//            param.put("password",encrytedPassword);
//
//            String raw = JSON.toJSONString(param);
//            try {
//                // 同步请求方法
//                OkHttpClient okHttpClient = new OkHttpClient();
//                RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"),
//                        raw);
//                Request request = new Request.Builder().url(ServerURL.apiUrlLogin).post(requestBody).build();
//                Call call = okHttpClient.newCall(request);
//                Response response = call.execute();
//                if (response.isSuccessful()) {
//                    // 得到token
//                    String responseStr = response.body().string();
//                    final String message = JSON.parseObject(responseStr).getString("message");
//                    final String data = JSON.parseObject(responseStr).getString("data");
//                    if (Constants.SUCCESS.equals(message) && StringUtils.isNotEmpty(data) &&
//                            !"false".equals(data)) {
//                        JSONObject jsonObject = JSON.parseObject(data);
//                        token = jsonObject.getString("token");
//                    }
//                    response.close();
//                }
//            } catch (Exception e) {
//                LatteLogger.e(TAG,e.getMessage());
//            }
//        }
//        return token;
//    }
//
//    /**
//     * 将RefreshToken结果通知到所有队列中等待的线程
//     *
//     */
//    private void triggerRefreshListener(boolean isSuccess, String accessToken) {
//        for (OnRefreshListener onRefreshListener : mRefreshListenerList) {
//            onRefreshListener.onRefresh(isSuccess, accessToken);
//        }
//    }
//
//    public interface OnRefreshListener {
//        void onRefresh(boolean isSuccess, String accessToken);
//    }
//}
