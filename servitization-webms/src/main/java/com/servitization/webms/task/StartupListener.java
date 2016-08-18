package com.servitization.webms.task;

import com.servitization.webms.service.IMetadataBootService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Resource
    private IMetadataBootService metadataBootService;

    private static boolean isStart = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        if (!isStart) {
            scan();
            isStart = true;
        } else {
            // two.
            logger.info("System has been started!!!");
        }
    }

    private void scan() {
        BootTask bootTask = new BootTask(metadataBootService);
        Thread t = new Thread(bootTask);
        t.start();
    }

}
