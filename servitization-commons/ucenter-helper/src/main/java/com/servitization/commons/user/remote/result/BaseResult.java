package com.servitization.commons.user.remote.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.servitization.commons.user.remote.common.ErrorCodeEnum;
import com.servitization.commons.util.husky.FieldDes;
import org.apache.commons.lang3.StringUtils;

public abstract class BaseResult {
    @FieldDes("true:出错，false:正常结果")
    @JSONField(name = "IsError")
    private boolean isError;
    @FieldDes("错误信息")
    @JSONField(name = "ErrorMessage")
    private String errorMessage = StringUtils.EMPTY;
    @FieldDes("错误代码")
    @JSONField(name = "ErrorCode")
    private String errorCode = StringUtils.EMPTY;

    private String debugMessage;

    /**
     * @return the isError
     */
    public final Boolean getIsError() {
        return isError;
    }

    /**
     * @param isError the isError to set
     */
    public final void setIsError(Boolean isError) {
        this.isError = isError;
    }

    /**
     * @return the errorMessage
     */
    public final String getErrorMessage() {
        return errorMessage == null ? "" : errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public final void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorCode
     */
    public final String getErrorCode() {
        return errorCode == null ? "" : errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public final void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorCodeEnum(ErrorCodeEnum errorEnum) {
        this.setIsError(true);
        this.setErrorCode(errorEnum.getErrorCode());
        this.setErrorMessage(errorEnum.getErrorMessage());
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }
}
