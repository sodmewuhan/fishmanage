package com.tea.fishtech.ui.bottom;

import android.widget.Toast;

import com.tea.fishtech.common.app.Latte;
import com.tea.fishtech.ui.R;
import com.tea.fishtech.ui.delegates.LatteDelegate;

public abstract class BottomItemDelegate extends LatteDelegate {
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "双击退出" + Latte.getApplicationContext().getString(
                    R.string.app_name), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
