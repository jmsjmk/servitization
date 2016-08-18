package com.servitization.web.confighotload;

import com.alibaba.fastjson.JSON;
import com.servitization.web.callback.IResourceChangeCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

public class ServitizationConfigReloadImpl {
    @Resource
    private IResourceChangeCallback reloadCallbackImpl;
    private static final Logger LOGGER = LoggerFactory.getLogger(ServitizationConfigReloadImpl.class);

    public void configCallback(ApplicationContext applicationContext, List<File> filePath) {
        LOGGER.info("configCallback" + applicationContext.hashCode() + ":" + JSON.toJSONString(filePath));
        if (reloadCallbackImpl != null)
            reloadCallbackImpl.reloadCallBack();
    }
}
