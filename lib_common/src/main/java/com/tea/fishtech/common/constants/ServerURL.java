package com.tea.fishtech.common.constants;

/**
 * 提交后台服务器的URL地址
 */
public final class ServerURL {

    // 测试环境_V2 版本
    public static String BASE_URL = "http://101.37.146.78:8016/api/";

    //public static  String BASE_URL = "http://192.168.56.1:8016/api/";

    // 用户管理接口
    public static String apiUrlLogin = BASE_URL + "authentication/login";      // 登录
    public static String apiFindUser = BASE_URL + "/user/findUser";   // 查询用户信息
    public static String apiUpdateUser = BASE_URL + "/user/updateUserInfo"; // 更新用户信息
    public static String apiRegisterUser = BASE_URL + "authentication/registerUser"; // 注册新用户

    // 设备接口信息（用户激活设备）
    public static String apiUpdateBoxInfo = BASE_URL + "box/addBoxByCustomer"; // 用户添加设备
    public static String apiGetBoxInfoByUser = BASE_URL + "box/getBoxInfoByUser"; // 根据用户，得到设备信息
    public static String apiGetDeviceInfoAndWaterUserName = BASE_URL + "box/getBoxStatusByUser";   // 得到设备状态信息和上报信息（首页使用）
    // 基础信息接口
    public static String apiFindProv = BASE_URL + "area/getProv";  // 得到省的信息
    public static String apiFindCity = BASE_URL + "area/getCity";  //得到市县信息
    // 得到水质曲线接口
    public static String apiCharDay = BASE_URL + "chart/day";
    // 得到电流曲线接口
    public static String apiCurrentChartDay = BASE_URL + "chart/currentday";
    // 得到当前的水质情况
    public static String apiGetCurrentWaterInfo = BASE_URL + "box/getCurrentWaterInfo";

    // 打开或者关闭设备（下发到设备 并且更新数据库）
    public static String apiDispatchInstructStatus = BASE_URL + "dispatch/instruction";

    // 获取当天的天气预报
    public static String apiGetDayWeather = BASE_URL + "area/getWeather";

    // 获取验证码短信
    public static String apiGetRegisterCustomerVerifyCode = BASE_URL + "sms/registerCustomerVerifyCode";

    // 塘口信息
    public static String apiGetAllPondByUser = BASE_URL + "fishPond/getAllPondByUser";

    // 修改塘口管理模式
    public static String apiUpdatePond = BASE_URL + "fishPond/updatePond";
    // 修改塘口管理模式
    public static String apiGetUnBindDevByUser = BASE_URL + "fishPond/getUnBindDevByUser";
    // 设备与塘口绑定
    public static String apiBindDevAndPond = BASE_URL + "box/bindDevAndPond";
    // 得到已经绑定的设备
    public static String apiGetBindDevByFishPond = BASE_URL + "box/getBindDevByFishPond";

    // 创建一个问题
    public static String apiCreateQuestion = BASE_URL + "answerAndQuestion/createQuestion";

    /************************************版本管理相关******************************************/
    // APK 相关的URL
    public static String APK_URL = "http://101.37.146.78:8012/appversion/";

    // 得到服务器最新APK 的版本信息和下载地址
    public static String apiGetLastAPKVersionInfo = APK_URL + "/version/getLastVersion";

    /***********************************测试相关******************************************/
    public static String apiGetNewsTitle = "http://v.juhe.cn/toutiao/index?type=top&key=a1a755458cc22f129942b34904feb820";
}
