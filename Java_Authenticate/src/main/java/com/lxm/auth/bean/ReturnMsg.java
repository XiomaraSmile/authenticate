package com.lxm.auth.bean;

import com.alibaba.fastjson.JSON;

/**
 * returnCode enmu
 *
 * @author lixiaomiao
 * @since 2022-09027
 */
public enum ReturnMsg {

    /**
     * system info returnCode
     */
    SUCCESS("00000", "success"),
    PARAMETER_ERROR("00001", "parameter error"),

    /**
     * business info returnCode
     */
    DUPLICATE_DATA("10001", "the data already exist in our system"),
    NO_DATA_ERROR("10002", "the data do not exist in our system"),
    AUTH_FAIL_ERROR("00003", "auth fail"),
    ENCRYPT_FAIL_ERROR("00004", "encrypt fail"),
    SYSTEM_ERROR("99999", "SYSTEM ERROR");

    /**
     * returnCode ,to identfy the result
     */
    private String returnCode;

    /**
     * returnInfo, todescribe the result
     */
    private String returnInfo;

    ReturnMsg(String returnCode, String returnInfo) {
        this.returnCode = returnCode;
        this.returnInfo = returnInfo;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(String returnInfo) {
        this.returnInfo = returnInfo;
    }

    @Override
    public String toString() {
        return "{\"returnCode\":\"" + returnCode + "\",\"returnInfo\":\"" + returnInfo +"\"}";
    }
}
