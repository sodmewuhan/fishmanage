package com.tea.fishtech.common.model;

import java.util.List;


/**
 * http 请求返回的对象
 */

public class Result<T> {
    private int code;

    private String message;

    private List<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
