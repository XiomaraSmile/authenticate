package com.lxm.auth.bean;

import com.alibaba.fastjson.JSON;

/**
 * data to show to the front
 *
 * @author lixiaomiao
 * @since 2022-09-27
 */
public class FrontBaseResult {

    /**
     * returnCode
     */
    private ReturnMsg returnMsg;

    /**
     * real object to show to front
     */
    private Object frontData;

    public ReturnMsg getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(ReturnMsg returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Object getFrontData() {
        return frontData;
    }

    public void setFrontData(Object frontData) {
        this.frontData = frontData;
    }

    @Override
    public String toString() {
        return "{\"returnMsg\":\"" + returnMsg + "\",\"frontData\":\"" + JSON.toJSONString(frontData) +"\"}";
    }
}
