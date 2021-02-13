package com.tea.fishtech.main.ui;

import android.graphics.Color;

import com.tea.fishtech.ui.bottom.BaseBottomDelegate;
import com.tea.fishtech.ui.bottom.BottomItemDelegate;
import com.tea.fishtech.ui.bottom.BottomTabBean;
import com.tea.fishtech.ui.bottom.ItemBuilder;

import java.util.LinkedHashMap;

public class BottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"),new IndexDelegate());
//        items.put(new BottomTabBean("{fa-compass}", "功能设置"), null);
//        items.put(new BottomTabBean("{fa-shopping-cart}","购物"),null);
        items.put(new BottomTabBean("{fa-user}", "我的"), new PersonalDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
