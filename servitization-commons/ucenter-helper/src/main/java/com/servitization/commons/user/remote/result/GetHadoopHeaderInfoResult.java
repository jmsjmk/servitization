package com.servitization.commons.user.remote.result;

public class GetHadoopHeaderInfoResult extends BaseResult {
    private String orderForm;
    private String visitType;
    private String appType;

    public String getOrderForm() {
        return orderForm;
    }

    public void setOrderForm(String orderForm) {
        this.orderForm = orderForm;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

}
