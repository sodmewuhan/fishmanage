//package com.tea.fishtech.common.net.interceptors;
//
//import com.tea.fishtech.core.utils.storage.LattePreference;
//import com.tea.fishtech.utils.Constants;
//
//import org.apache.commons.lang3.StringUtils;
//
//import java.io.IOException;
//
//import okhttp3.Interceptor;
//import okhttp3.Request;
//import okhttp3.Response;
//
///**
// * 增加token
// */
//public class BearerTokenInterceptor implements Interceptor {
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//
//        Request request = chain.request();
//        String url = request.url().encodedPath();
//        if (StringUtils.isNotEmpty(url) && !url.contains("authentication")) {
//            // 加入token信息
//            String token = LattePreference.getCustomAppProfile(Constants.TOKEN);
//            if (StringUtils.isNotEmpty(token)) {
//
//                Request update = request
//                                    .newBuilder()
//                                    .addHeader("Authorization","Bearer " + token)
//                                    .build();
//                return chain.proceed(update);
//            }
//        }
//        return chain.proceed(request);
//    }
//}
