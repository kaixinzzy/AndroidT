package com.zzy.json.module;

public class PayResult {

    /**
     * payType : 41
     * message : 支付成功，卡内余额：2960.35元
     * status : SUCCESS
     */

    private String payType;
    private String message;
    private String status;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
