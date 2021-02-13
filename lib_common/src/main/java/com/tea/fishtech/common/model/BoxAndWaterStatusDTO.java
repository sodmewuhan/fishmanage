package com.tea.fishtech.common.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.List;

public class BoxAndWaterStatusDTO {

    private BoxInfoDTO boxInfoDTO;

    /**
     * 设备在线状态
     */
    private BoxOnline boxOnline;

    /**
     * 管理的设备状态
     */
    private List<BoxStatusDTO> boxStatusDTOS;

    // 水质的溶氧量
    private BigDecimal waterOxygen;

    // 上传时间
    private DateTime oxygenTime;

    // 水质的PH值
    private BigDecimal waterPh;

    // PH值上传时间
//    private DateTime phTime;

    // 水质温度
    private BigDecimal tempterature;

    // 上传时间
//    private DateTime tempteratureTime;

    public BoxInfoDTO getBoxInfoDTO() {
        return boxInfoDTO;
    }

    public void setBoxInfoDTO(BoxInfoDTO boxInfoDTO) {
        this.boxInfoDTO = boxInfoDTO;
    }

    public List<BoxStatusDTO> getBoxStatusDTOS() {
        return boxStatusDTOS;
    }

    public void setBoxStatusDTOS(List<BoxStatusDTO> boxStatusDTOS) {
        this.boxStatusDTOS = boxStatusDTOS;
    }

    public BoxOnline getBoxOnline() {
        return boxOnline;
    }

    public void setBoxOnline(BoxOnline boxOnline) {
        this.boxOnline = boxOnline;
    }

    public BigDecimal getWaterOxygen() {
        return waterOxygen;
    }

    public void setWaterOxygen(BigDecimal waterOxygen) {
        this.waterOxygen = waterOxygen;
    }

    public DateTime getOxygenTime() {
        return oxygenTime;
    }

    public void setOxygenTime(DateTime oxygenTime) {
        this.oxygenTime = oxygenTime;
    }

    public BigDecimal getWaterPh() {
        return waterPh;
    }

    public void setWaterPh(BigDecimal waterPh) {
        this.waterPh = waterPh;
    }

//    public DateTime getPhTime() {
//        return phTime;
//    }
//
//    public void setPhTime(DateTime phTime) {
//        this.phTime = phTime;
//    }

    public BigDecimal getTempterature() {
        return tempterature;
    }

    public void setTempterature(BigDecimal tempterature) {
        this.tempterature = tempterature;
    }

//    public DateTime getTempteratureTime() {
//        return tempteratureTime;
//    }
//
//    public void setTempteratureTime(DateTime tempteratureTime) {
//        this.tempteratureTime = tempteratureTime;
//    }
}
