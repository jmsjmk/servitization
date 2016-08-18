package com.servitization.commons.user.remote.result;

public class MobileChannelResponse extends BaseResult {

    //渠道号是否有效
    private boolean isValid;

    public boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }
}
