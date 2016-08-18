package com.servitization.web.callback;

import com.servitization.web.service.ResourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.util.List;

public class ServitizationConfigReloadCallback implements IResourceChangeCallback {
    private static final Logger Logger = LoggerFactory.getLogger(ServitizationConfigReloadCallback.class);

    public void configCallback(ApplicationContext applicationContext, List<File> filePath) {
        reloadCallBack();
    }

    public void reloadCallBack() {
        if (ResourceContext.getResourceChangeCallback() != null) {
            ResourceContext.getResourceChangeCallback().reloadCallBack();
        } else {
            Logger.error("ResourceChangeCallback() is null !");
        }
    }
}
