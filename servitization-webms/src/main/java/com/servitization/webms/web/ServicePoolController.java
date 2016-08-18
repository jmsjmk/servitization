package com.servitization.webms.web;

import com.alibaba.fastjson.JSON;
import com.servitization.commons.util.DateUtil;
import com.servitization.webms.entity.MetadataServicePool;
import com.servitization.webms.service.IMetadataProxyService;
import com.servitization.webms.service.IMetadataServicePoolService;
import com.servitization.webms.util.adapter.BaseObserver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "webms/servicePool")
public class ServicePoolController extends BaseObserver {

    @Resource
    private IMetadataServicePoolService metadataServicePoolService;
    @Resource
    private IMetadataProxyService metadataProxyService;

    @RequestMapping(value = "selectPools", method = RequestMethod.GET)
    @ResponseBody
    public String selectPools(HttpServletRequest request, HttpServletResponse response) {
        String metadataId = request.getParameter("metadataId");
        return JSON.toJSONString(metadataServicePoolService.selectPools(Long.parseLong(metadataId)));
    }

    @RequestMapping(value = "insertPool", method = RequestMethod.POST)
    @ResponseBody
    public String insertPool(HttpServletRequest request, HttpServletResponse response) {
        MetadataServicePool pool = new MetadataServicePool();
        long metadataId = Long.parseLong(request.getParameter("metadataId"));
        pool.setMetadataId(metadataId);
        pool.setServicePoolName(request.getParameter("servicePoolName"));
        pool.setUrl(request.getParameter("url"));
        String coefficient = request.getParameter("coefficient");
        pool.setCoefficient(StringUtils.isBlank(coefficient) ? 0d : Double.parseDouble(coefficient));
        String connectTimeout = request.getParameter("connectTimeout");
        pool.setConnectTimeout(StringUtils.isBlank(connectTimeout) ? 0 : Integer.parseInt(connectTimeout));
        String forceCloseChannel = request.getParameter("forceCloseChannel");
        pool.setForceCloseChannel(StringUtils.isBlank(forceCloseChannel) ? 0 : Integer.parseInt(forceCloseChannel));
        String forceCloseTimeMillis = request.getParameter("forceCloseTimeMillis");
        pool.setForceCloseTimeMillis(
                StringUtils.isBlank(forceCloseTimeMillis) ? 0l : Long.parseLong(forceCloseTimeMillis));
        String serviceType = request.getParameter("serviceType");
        pool.setServiceType(StringUtils.isBlank(serviceType) ? 0 : Integer.parseInt(serviceType));
        List<MetadataServicePool> l = metadataServicePoolService.selectPools(metadataId);
        if (StringUtils.isBlank(pool.getServicePoolName()) || StringUtils.isBlank(pool.getUrl())) {
            return "pool name and url can not empty";
        }
        for (MetadataServicePool mPool : l) {
            if (mPool.getServicePoolName().equals(pool.getServicePoolName())) {
                return "pool name already exists, please input again";
            }
        }
        pool.setCreateTime(DateUtil.formatDate(new Date(), DateUtil.FORMAT_ALL));
        if (metadataServicePoolService.insertPool(pool) > 0) {
            return "insert succeeded";
        }
        return "insert failed";
    }

    @Transactional(rollbackFor = {Exception.class})
    @RequestMapping(value = "updatePoolByName", method = RequestMethod.POST)
    @ResponseBody
    public String updatePoolByName(HttpServletRequest request, HttpServletResponse response) {
        MetadataServicePool pool = new MetadataServicePool();
        long metadataId = Long.parseLong(request.getParameter("metadataId"));
        pool.setMetadataId(metadataId);
        String servicePoolName = request.getParameter("servicePoolName");
        pool.setServicePoolName(servicePoolName);
        String oldServicePoolName = request.getParameter("oldServicePoolName");
        pool.setOldServicePoolName(oldServicePoolName);
        pool.setUrl(request.getParameter("url"));
        String coefficient = request.getParameter("coefficient");
        pool.setCoefficient(StringUtils.isBlank(coefficient) ? 0d : Double.parseDouble(coefficient));
        String connectTimeout = request.getParameter("connectTimeout");
        pool.setConnectTimeout(StringUtils.isBlank(connectTimeout) ? 0 : Integer.parseInt(connectTimeout));
        String forceCloseChannel = request.getParameter("forceCloseChannel");
        pool.setForceCloseChannel(StringUtils.isBlank(forceCloseChannel) ? 0 : Integer.parseInt(forceCloseChannel));
        String forceCloseTimeMillis = request.getParameter("forceCloseTimeMillis");
        pool.setForceCloseTimeMillis(
                StringUtils.isBlank(forceCloseTimeMillis) ? 0l : Long.parseLong(forceCloseTimeMillis));
        String serviceType = request.getParameter("serviceType");
        pool.setServiceType(StringUtils.isBlank(serviceType) ? 0 : Integer.parseInt(serviceType));
        if (StringUtils.isBlank(pool.getServicePoolName()) || StringUtils.isBlank(pool.getUrl())) {
            return "pool name and url can not empty";
        }
        if (metadataServicePoolService.updatePoolByName(pool) > 0) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("metadataId", metadataId);
            param.put("servicePoolName", servicePoolName);
            param.put("oldServicePoolName", oldServicePoolName);
            metadataProxyService.updateProxyPoolName(param);
            return "update succeeded";
        }
        return "update failed";
    }

    @RequestMapping(value = "deletePoolByName", method = RequestMethod.POST)
    @ResponseBody
    public String deletePoolByName(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("servicePoolName", request.getParameter("servicePoolName"));
        param.put("metadataId", Long.parseLong(request.getParameter("metadataId")));
        if (metadataServicePoolService.deletePoolByName(param) > 0) {
            return "delete succeeded";
        }
        return "delete failed. can not find this entry";
    }

    @Override
    public void deleteMetadata(List<String> metadataId) {
        metadataServicePoolService.deleteServicePoolByMetadataid(metadataId);
    }

    @Override
    public String getHandlerName() {

        return "servicePoolHandler";
    }
}
