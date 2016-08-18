package com.servitization.pv.hadoop.support;

import com.alibaba.fastjson.JSON;
import com.servitization.commons.user.remote.helper.UserHelper;
import com.servitization.commons.user.remote.request.GetHadoopHeaderInfoReq;
import com.servitization.commons.user.remote.result.GetHadoopHeaderInfoResult;
import com.servitization.pv.entity.HadoopHeaderDemand;
import com.servitization.pv.entity.HadoopHeaderInfo;
import com.servitization.pv.hadoop.HadoopDemand;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class HadoopDemandSupport implements HadoopDemand {

    @Override
    public HadoopHeaderInfo getHadoopInfoByDemand(HadoopHeaderDemand demand, UserHelper userLogic) {
        if (demand != null && StringUtils.isNotBlank(demand.getChannelId())) {
            GetHadoopHeaderInfoReq req = new GetHadoopHeaderInfoReq();
            req.setChannelId(demand.getChannelId());
            req.setOrderFormType(demand.getOrderFormType());
            req.setClientType(demand.getClientType());
            String result = userLogic.getHadoopHeaderInfo(req);
            if (StringUtils.isNotBlank(result)) {
                GetHadoopHeaderInfoResult resultObj = JSON.parseObject(result, GetHadoopHeaderInfoResult.class);
                HadoopHeaderInfo info = new HadoopHeaderInfo();
                info.setOrderForm(resultObj.getOrderForm());
                info.setVisitType(resultObj.getVisitType());
                return info;
            }
        }
        return null;
    }

    @Override
    public boolean isFirstActivating(HttpServletRequest request) {
        return false;
    }

}
