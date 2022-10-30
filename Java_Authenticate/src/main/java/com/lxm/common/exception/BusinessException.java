package com.lxm.common.exception;

import com.lxm.auth.bean.ReturnMsg;

/**
 * BusinessException, used to support exception case
 *
 * @author lixiaomiao
 * @since 2022-09-26
 */
public class BusinessException  extends Exception{
    private static final long serialVersionUID = 2593837893304264632L;

    /**
     * returnCode 000000 if success, others otherwise
     */
    private String returnCode;

    /**
     * returnInfo the description about the result
     */
    private String returnInfo;

    public BusinessException(ReturnMsg returnMsg) {
        super(returnMsg.getReturnCode() + " " + returnMsg.getReturnInfo());
        this.returnCode = returnMsg.getReturnCode();
        this.returnInfo = returnMsg.getReturnInfo();
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
}
