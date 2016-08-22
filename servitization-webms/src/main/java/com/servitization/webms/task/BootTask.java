package com.servitization.webms.task;

import com.servitization.webms.service.IMetadataBootService;
import org.apache.log4j.Logger;

public class BootTask implements Runnable {

    private IMetadataBootService metadataBootService;
    private static final Logger LOGGER = Logger.getLogger(BootTask.class);

    public BootTask(IMetadataBootService metadataBootService) {
        this.metadataBootService = metadataBootService;
    }

    @Override
    public void run() {
        while (true) {
            // 初始化现成感觉没啥用一期感觉用处不到
            int result = metadataBootService.bootScan();
            LOGGER.info("bootScan " + result);
            try {
                Thread.currentThread().sleep(500000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
