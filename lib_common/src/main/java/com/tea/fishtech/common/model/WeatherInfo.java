package com.tea.fishtech.common.model;

public class WeatherInfo {

    private String showapi_res_error;

    private String showapi_res_id;

    private String showapi_res_code;

    private WeatherResBody showapi_res_body;


    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public String getShowapi_res_id() {
        return showapi_res_id;
    }

    public void setShowapi_res_id(String showapi_res_id) {
        this.showapi_res_id = showapi_res_id;
    }

    public String getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(String showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public WeatherResBody getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(WeatherResBody showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }
}
