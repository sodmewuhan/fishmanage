package com.tea.fishtech.common.model;

public class WeatherResBody {

    private DayWeatherInfo f1; // 今天天气

    private DayWeatherInfo f2; // 明天天气

    private DayWeatherInfo f3; // 后天天气

    private CityInfo cityInfo; // 城市信息

    public DayWeatherInfo getF1() {
        return f1;
    }

    public void setF1(DayWeatherInfo f1) {
        this.f1 = f1;
    }

    public DayWeatherInfo getF2() {
        return f2;
    }

    public void setF2(DayWeatherInfo f2) {
        this.f2 = f2;
    }

    public DayWeatherInfo getF3() {
        return f3;
    }

    public void setF3(DayWeatherInfo f3) {
        this.f3 = f3;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }
}
