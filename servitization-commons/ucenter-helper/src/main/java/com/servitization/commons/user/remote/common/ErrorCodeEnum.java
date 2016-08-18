package com.servitization.commons.user.remote.common;

public enum ErrorCodeEnum {
    ServerException("10000", "网络繁忙，稍后重试！"),
    ParametersErr("10001", "请求错误，请重试！"),
    MobileErr("10002", "请输入正确的手机号"),
    EmailErr("10003", "电子邮箱格式不正确，请按照以下格式输入***@***.*"),
    PasswordErr("10004", "两次输入的密码不一致"),
    PasswordEmpty("10005", "密码不能为空"),
    Regiested("10006", "此手机号或邮箱已经注册，请到[忘记密码]处获取密码。"),
    RegiestErr("10007", "注册失败。"),
    UserNameOrPasswordEmpty("10008", "用户名或密码不能为空"),
    UserNameOrPasswordErr("10009", "用户名或密码错误"),
    BlackList("10010", "你的帐号不能登录"),
    LoginFailed("10011", "登录失败"),
    GetAccessTokenErr("10012", "登录验证失败"),
    AccessTokenTimeOutErr("10013", "登录过期"),
    RefreshTokenErr("10014", "刷新登录失败"),
    RefreshTokenTimeOutErr("10015", "刷新登录时间过期"),
    PassWordModifyErr("10016", "两次输入的密码不一致"),
    PassWordModifyLength("10017", "密码长度必须为6-30位"),
    AuthorIdOrAuthorPwdEmpty("10018", "授权帐号或授权密码不能为空"),
    AuthorIdOrAuthorPwdErr("10019", "授权帐号或授权密码错误"),
    AuthorBlackList("10020", "授权帐号不能登录"),
    AuthorLoginFailed("10021", "授权验证失败"),
    REQUEST_VERIFICATION_ERR("10022", "验证请求失败"),
    SSO_NONE_ERR("10023", "不支持的绑定类型"),
    SSO_BIND_ERR("10024", "绑定失败"),
    SSO_ALREADY_BIND_ERR("10025", "已绑定过会员"),
    VERIFY_CODE_ERROR("10026", "短信验证码输入错误，请重新输入"),
    VERIFY_NEED_CODE("10027", "请输入验证码"),
    Defence_NEED_CODE("turtle_1000", "请输入验证码"),
    Defence_Info_blanck("turtle_1001", "验证信息不完整"),
    DynamicCodeUseTooMuch("10031", "您已达到今天获取动态密码的最高次数，请使用普通登录方式"),
    DynamicGraphCheckCodeError("10032", "图形验证码输入错误或失效，请刷新后重新输入"),
    CardNoHasCreated("10033", "您输入的手机号已注册，请直接登录"),
    UserNotRegist("10034", "您的手机号尚未注册"),
    MobileBindTooMuchCards("10035", "您的手机号绑定多个艺龙账号，暂不支持手机动态密码登录，请使用普通登录"),
    DynamicCodeInvalid("10036", "动态密码已失效，请重新获取"),
    DynamicCodeError("10037", "动态密码输入错误"),
    DynamicCodeMobileUseTooMuch("10038", "您已达到今天获取短信验证码的最高次数，请明天再试"),
    DynamicCodeRegisterInvalid("10039", "短信验证码已失效，请重新获取"),
    SendSmsCodeError("10040", "短信验证码发送错误"),
    NeedGCode("10041", "需要图形验证码"),
    GCodeError("10042", "图形验证码错误"),
    CardNoEmpty("10043", "请输入卡号"),
    RequireOldMobileValid("10044", "对不起，请先进行旧手机号码验证"),
    CheckCodeMaxNumberExceeded("10045", "您今日的获取次数已用完，请明日再获取"),
    CheckCodeWrongNumberExceeded("10046", "输入错误超过3次，请重新获取验证码"),
    SendCheckCodeError("10047", "验证码发送失败，请稍后再试"),
    WrongCheckcode("10048", "验证码输入错误，请确认后重新输入"),
    CheckCodeWrongTooManyTimes("10049", "输入错误超过3次，请重新获取验证码"),
    CheckCodeExpired("10050", "验证码失效，请重新获取验证码"),
    MobileAlreadyBindingCard("10051", "您输入的手机号已绑定艺龙账号。"),
    PassWordSameErr("10052", "新旧密码不能相同"),
    OldPasswordErr("10053", "旧密码有误"),
    LoginByWebSessionInvalid("10054", "二维码可能已失效，请刷新后重新扫描。"),
    BoTaoRegisterError("10055", "铂涛注册失败。"),
    BoTaoParameterError("10056", "您填写的信息不完整"),
    BoTaoTimeError("10057", "离店时间不能小于入店时间"),
    DynamicCodeBegRepate("10058", "动态密码正在发送到您的手机，请稍后查阅"),
    ImageDataIllegal("10059", "图片无效!"),
    RechargeGiftSetFail("10060", "领取失败，请稍后再试~"),
    OtherFail("10062", "网络不给力，请稍后再试~");

    ErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static ErrorCodeEnum errorCodeOfEnum(String errorCode) {
        for (ErrorCodeEnum ce : values()) {
            if (ce.getErrorCode() == errorCode) {
                return ce;
            }
        }
        return null;
    }

    private String errorCode;            //错误码
    private String errorMessage;        //错误描述

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
