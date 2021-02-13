package com.tea.fishtech.common.model;

import java.util.List;

public class FishPondDto {

    private FishPond fishPond;

    /**
     * 在该塘口下的设备
     */
    private List<BoxInfo> boxInfoList;

    /**
     * 该塘口下的水质情况
     */
    private List<BoxAndWaterStatusDTO> boxAndWaterStatusDTOList;


    public FishPond getFishPond() {
        return fishPond;
    }

    public void setFishPond(FishPond fishPond) {
        this.fishPond = fishPond;
    }

    public List<BoxInfo> getBoxInfoList() {
        return boxInfoList;
    }

    public void setBoxInfoList(List<BoxInfo> boxInfoList) {
        this.boxInfoList = boxInfoList;
    }

    public List<BoxAndWaterStatusDTO> getBoxAndWaterStatusDTOList() {
        return boxAndWaterStatusDTOList;
    }

    public void setBoxAndWaterStatusDTOList(List<BoxAndWaterStatusDTO> boxAndWaterStatusDTOList) {
        this.boxAndWaterStatusDTOList = boxAndWaterStatusDTOList;
    }
}
