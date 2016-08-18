package com.servitization.pv.hadoop;

import com.servitization.commons.user.remote.helper.UserHelper;
import com.servitization.pv.entity.HadoopHeaderDemand;
import com.servitization.pv.entity.HadoopHeaderInfo;

import javax.servlet.http.HttpServletRequest;

public interface HadoopDemand {
    HadoopHeaderInfo getHadoopInfoByDemand(HadoopHeaderDemand hadoopHeaderDemand, UserHelper userLogic);

    boolean isFirstActivating(HttpServletRequest request);
}
