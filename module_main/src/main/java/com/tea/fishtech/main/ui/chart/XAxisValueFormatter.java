package com.tea.fishtech.main.ui.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class XAxisValueFormatter  extends ValueFormatter {

    private Float[] mValues;

    public XAxisValueFormatter(Float[] values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(Float.valueOf(String.valueOf(value)).longValue());
        return sdf.format(date);
    }


}
