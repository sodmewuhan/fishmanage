package com.tea.fishtech.common.constants;

import retrofit2.http.PUT;

/**
 * 全局静态变量
 */
public final class Constants {

    public final static String SUCCESS = "SUCCESS";

    // 当前登录的用户名
    public final static String LOGIN_USERNAME = "USERNAME";

    // 当前登录用户密码（加密）
    public final static String USER_ENCRYTED_PASSWORD = "PASSWORD";

    public final static String TOKEN = "Authorization";

    public final static String AUTHORIZATION = "Authorization";

    public final static String BEARER = "Bearer ";

    // 首页广告切换时间
    public final static Integer FLASH_BAR = 5000;

    // 智能控制器
    public final static String BOX_TYPE_CTRL = "1001";

    // 水质检测器
    public final static String BOX_TYPE_WATER = "1002";

    public final static String USER_NAME = "userName";

    /**
     * AES加密用的key
     */
    public static final String AES_PASSWORD = "123@password";

    /**
     * TOKEN 验证不通过CODE
     */
    public static final Integer AUTHORIZATION_CODE = 401;


    /**
     * TOKEN 过期
     */
    public static final Integer AUTHORIZATION_TOKEN_EXPIRED = 403;

    public static final Integer DEV_TYPE_NAME_CODE = 1001;

    public static final String DEV_TYPE_NAME_WATER = "水质探头";

    public static final String DEV_TYPE_NAME_CONTROL = "控制器";
}
