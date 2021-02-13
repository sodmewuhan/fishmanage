package com.tea.fishtech.main.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.tea.fishtech.common.app.Latte;
import com.tea.fishtech.ui.activties.ProxyActivity;
import com.tea.fishtech.ui.delegates.LatteDelegate;

public class MainActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new BottomDelegate();
    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);


//        String alias = LattePreference.getCustomAppProfile(Constants.LOGIN_USERNAME);
//        if (StringUtils.isNotEmpty(alias)) {
//            String registrationid = JPushInterface.getRegistrationID(this);
//            LatteLogger.d("registrationid is " + registrationid);
//            JPushInterface.setAlias(this,TagAliasOperatorHelper.sequence++,alias);
//        }
    }

}
