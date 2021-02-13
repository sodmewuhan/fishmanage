package com.main.fishmanage;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.tea.fishtech.common.app.Latte;
import com.tea.fishtech.common.constants.ServerURL;
import com.tea.fishtech.common.net.interceptors.LoggingInterceptor;
import com.tea.fishtech.ui.icon.FontEcModule;
import com.zhouyou.http.EasyHttp;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;

import okhttp3.Interceptor;

public class FishApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);

        ArrayList<Interceptor> interceptorList = new ArrayList();
        interceptorList.add(new LoggingInterceptor());
//        interceptorList.add(new BearerTokenInterceptor());
        
        Latte.init(this)
                .withApiHost(ServerURL.BASE_URL)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withInterceptors(interceptorList)
                .configure();

        // 日期控件joda初始化
        JodaTimeAndroid.init(this);
        // 网络库初始化
        EasyHttp.init(this);
    }
}
