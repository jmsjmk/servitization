package com.servitization.pv.hadoop.event;

import com.servitization.pv.entity.HadoopEventObj;
import com.servitization.pv.hadoop.HadoopLogInterceptor;
import com.google.common.eventbus.Subscribe;

public class HadoopEvent {
    private HadoopLogInterceptor logInterceptor;

    @Subscribe
    public void eventTrigger(HadoopEventObj event) {
        logInterceptor.writeHadoopLog(event.getRequest(), event.getController(), event.getMethodName(),
                event.getRealClientIp(), event.getBusinessLine());
    }

    public void setLogInterceptor(HadoopLogInterceptor logInterceptor) {
        this.logInterceptor = logInterceptor;
    }
}
