package com.servitization.pv.enums;

import org.apache.commons.lang3.StringUtils;

public enum OrderFromTypeEnum {
    HOTEL(0, "hotel"),
    FLIGHT(1, "flight"),
    Groupon(2, "ctrip_tuan"),
    GLOABLHOTEL(3, "globalhotel");

    OrderFromTypeEnum(int typeId, String typeDesc) {
        this.typeId = typeId;
        this.typeDesc = typeDesc;
    }

    public static OrderFromTypeEnum OrderFromTypeOfEnum(String desc) {
        for (OrderFromTypeEnum ce : values()) {
            if (StringUtils.equals(ce.getTypeDesc(), desc)) {
                return ce;
            }
        }
        return null;
    }

    public static int GetTypeIdByEnum(OrderFromTypeEnum orderFromTypeEnum) {

        for (OrderFromTypeEnum ce : values()) {
            if (ce == orderFromTypeEnum) {
                return ce.getTypeId();
            }
        }
        return 0;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    private int typeId; // 类型ID
    private String typeDesc; // 类型描述
}
