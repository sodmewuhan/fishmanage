package com.tea.fishtech.common.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class FishPond {

    private Long id;

    private String pondName;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 乡
     */
    private String county;


    private String address;

    /**
     * 具体地址
     */
    private String area;

    /**
     * 养殖品种
     */
    private String category;

    private Long userId;

    /**
     * 塘口管理模式：1：手动  2 ：自动  3  定时
     */
    private String manageMode;

    /**
     * 管理模式的描述
     */
    private String manageModeDesc;

    /**
     * 定时时间
     */
    private String fixTimeBegin;

    private String fixTimeEnd;

    /**
     * 用户名称
     */
    private String userName;

    private Date createDate;

    private String createBy;

    private Date lastUpdateDate;

    private String lastUpdateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPondName() {
        return pondName;
    }

    public void setPondName(String pondName) {
        this.pondName = pondName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getManageMode() {
        return manageMode;
    }

    public void setManageMode(String manageMode) {
        this.manageMode = manageMode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getManageModeDesc() {
        String manageModeDesc = StringUtils.EMPTY;
        if ("1".equals(manageMode)) {
            manageModeDesc = "手动";
        } else if ("2".equals(manageMode)) {
            manageModeDesc = "自动";
        } else if ("3".equals("定时")) {
            manageModeDesc = "定时";
        }

        return manageModeDesc;
    }

    public String getFixTimeBegin() {
        return fixTimeBegin;
    }

    public void setFixTimeBegin(String fixTimeBegin) {
        this.fixTimeBegin = fixTimeBegin;
    }

    public String getFixTimeEnd() {
        return fixTimeEnd;
    }

    public void setFixTimeEnd(String fixTimeEnd) {
        this.fixTimeEnd = fixTimeEnd;
    }
}
