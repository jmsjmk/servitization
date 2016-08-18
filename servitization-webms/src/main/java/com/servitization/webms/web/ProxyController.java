package com.servitization.webms.web;

import com.alibaba.fastjson.JSONArray;
import com.servitization.commons.permission.annotation.Permission;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.metadata.define.proxy.*;
import com.servitization.metadata.define.proxy.impl.ProxyDefineImpl;
import com.servitization.webms.entity.MetadataModule;
import com.servitization.webms.entity.MetadataProxy;
import com.servitization.webms.entity.MetadataServicePool;
import com.servitization.webms.service.IMetadataProxyService;
import com.servitization.webms.service.IMetadataServicePoolService;
import com.servitization.webms.util.ConcreteSubject;
import com.servitization.webms.util.adapter.BaseObserver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "webms/proxy")
public class ProxyController extends BaseObserver {

    @Resource
    private IMetadataProxyService proxyService;
    @Resource
    private IMetadataServicePoolService metadataServicePoolService;

    @RequestMapping(value = "getProxyPage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getModulePage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
        mav.setViewName("proxy");
        String metadataId = request.getParameter("metadataId");
        if (StringUtils.isBlank(metadataId)) {
            return mav;
        }
        String sourceUrl = request.getParameter("sourceUrl");
        String targetUrl = request.getParameter("targetUrl");
        String pageIndexStr = request.getParameter("pageIndex");
        String pageSizeStr = request.getParameter("pageSize");
        int pageIndex = 0;
        int pageSize = 10;
        if (StringUtils.isNotBlank(pageIndexStr)) {
            pageIndex = Integer.parseInt(pageIndexStr);
        }
        if (StringUtils.isNotBlank(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("metadataId", metadataId);
        params.put("sourceUrl", sourceUrl);
        params.put("targetUrl", targetUrl);
        params.put("pageIndex", pageIndex * pageSize);
        params.put("pageSize", pageSize);
        List<MetadataProxy> metadataProxys = proxyService.getMetadataProxyListByParams(params);
        if (metadataProxys == null) {
            metadataProxys = new ArrayList<>();
        }
        int count = proxyService.getMetadataProxyCount(params);
        int pageCount = count == 0 ? 0 : (count % pageSize == 0 ? count / pageSize : count
                / pageSize + 1);
        modelMap.put("pageIndex", pageIndex);
        modelMap.put("pageSize", pageSize);
        modelMap.put("pageCount", pageCount);
        modelMap.put("metadataProxys", metadataProxys);
        mav.addAllObjects(modelMap);

        return mav;
    }

    /**
     * 删除转发列表
     *
     * @param request
     * @param response
     */
    @Permission(name = "update")
    @RequestMapping(value = "deleteMetadataProxys", method = RequestMethod.POST)
    public ResponseEntity<byte[]> deleteMetadataProxys(HttpServletRequest request,
                                                       HttpServletResponse response) {
        String ids = request.getParameter("ids");
        System.out.println("deleteMetadataProxys ids " + ids);
        List<String> list = JSONArray.parseArray(ids, String.class);
        System.out.println("list " + list);
        List<String> idList = getIdList(list);
        System.out.println("idList:" + idList);
        // fuck 传递32__192.168.100.172:8090_HTTP 尽然给我删除了。。。
        int count = proxyService.deleteMetadataProxys(list);
        // int  count = proxyService.deleteMetadataProxys(idList);
        System.out.println("deleteMetadataProxys count:" + count);
        if (count > 0) {
            ConcreteSubject concreteSubject = ConcreteSubject.instances();
            concreteSubject.deleteProxy(list);
        }
        String msg = count == list.size() ? "删除成功" : "部分记录删除失败：没找到相应的记录";
        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }


    public List<String> getIdList(List<String> list) {
        List<String> idList = new ArrayList<String>();
        for (String s : list) {
            String[] array = s.split("_");
            idList.add(array[0]);
        }

        return idList;
    }


    /**
     * 获取添加页面
     *
     * @param request
     * @param response
     * @return
     */
    @Permission(name = "update")
    @RequestMapping(value = "getAddProxyPage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getProxyAddPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("proxy_add");
        Map<String, Object> modelMap = new HashMap<>();
        String proxyId = request.getParameter("proxyId");
        if (StringUtils.isNotBlank(proxyId)) {
            MetadataProxy metadataProxy = proxyService.getProxyById(Long.parseLong(proxyId));
            modelMap.put("metadataProxy", metadataProxy);
        }
        mav.addAllObjects(modelMap);
        return mav;
    }

    @RequestMapping(value = "addOrUpdateProxy", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addOrUpdateProxy(HttpServletRequest request,
                                                   HttpServletResponse response) {
        String proxyId = request.getParameter("proxyId");
        String metadataId = request.getParameter("metadataId");
        String sourceUrl = request.getParameter("sourceUrl");
        String sourceMethod = request.getParameter("sourceMethod");
        String targetUrl = request.getParameter("targetUrl");
        String targetMethod = request.getParameter("targetMethod");
        String connectTimeout = request.getParameter("connectTimeout");
        String socketTimeout = request.getParameter("socketTimeout");
        String threshold = request.getParameter("threshold");
        String serviceName = request.getParameter("serviceName");
        String serviceVersion = request.getParameter("serviceVersion");
        String servicePoolName = request.getParameter("servicePoolName");
        String serviceType = request.getParameter("serviceType");
        MetadataProxy metadataProxy = new MetadataProxy();
        String convert = request.getParameter("isConvert");
        if (convert != null && convert.equals("yes")) {
            metadataProxy.setConvert(convert);
        }
        metadataProxy.setMetadataId(Integer.parseInt(metadataId));
        metadataProxy.setSourceUrl(convertNull2Empty(sourceUrl));
        metadataProxy.setSourceMethod(convertNull2Empty(sourceMethod));
        metadataProxy.setTargetUrl(convertNull2Empty(targetUrl));
        metadataProxy.setTargetMethod(convertNull2Empty(targetMethod));
        metadataProxy.setConnectTimeout(convertNull2Int(connectTimeout));
        metadataProxy.setSocketTimeout(convertNull2Int(socketTimeout));
        metadataProxy.setThreshold(convertNull2Int(threshold));
        metadataProxy.setSeviceType(convertNull2Int(serviceType));
        metadataProxy.setCreateTime(new Date());
        metadataProxy.setServiceName(convertNull2Empty(serviceName));
        metadataProxy.setServiceVersion(convertNull2Empty(serviceVersion));
        metadataProxy.setServicePoolName(convertNull2Empty(servicePoolName));
        String msg = "成功";
        if (StringUtils.isNotBlank(proxyId)) {
            metadataProxy.setId(Long.parseLong(proxyId));
            int count = proxyService.updateMetadataProxy(metadataProxy);
            msg = count > 0 ? "修改成功" : "修改失败";
        } else {
            int count = proxyService.addMetadataProxy(metadataProxy);
            msg = count > 0 ? "添加成功" : "添加失败";
        }
        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }

    @RequestMapping(value = "selectServiceNameByServicePoolName", method = RequestMethod.GET)
    public ResponseEntity<byte[]> selectServiceNameByServicePoolName(HttpServletRequest request,
                                                                     HttpServletResponse response) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("servicePoolName", request.getParameter("servicePoolName"));
        param.put("metadataId", Long.parseLong(request.getParameter("metadataId")));
        List<String> result = proxyService.selectServiceNameByServicePoolName(param);
        String msg = "success";
        if (result != null && result.size() > 0) {
            msg = "There are some proxys using this pool: " + result.toString()
                    + " please delete them first";
        }
        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }

    /**
     * 验证添加转发配置的sourcesUrl是否已经存在
     *
     * @return
     */
    @RequestMapping(value = "isAgain", method = RequestMethod.GET)
    @ResponseBody
    public String isAgain(HttpServletRequest request) {
        String metadataId = request.getParameter("metadataId");
        String sourceUrl = request.getParameter("sourceUrl");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("metadataId", metadataId);
        params.put("sourceUrl", sourceUrl);
        return String.valueOf(proxyService.sourcesUrlIsAgain(params));
    }

    @Override
    public void deleteMetadata(List<String> args) {
        proxyService.deleteProxyByMetadataid(args);
    }

    private String convertNull2Empty(String param) {
        return StringUtils.isBlank(param) ? "" : param;
    }

    private int convertNull2Int(String param) {
        if (StringUtils.isBlank(param)) {
            return 0;
        }
        try {
            return Integer.parseInt(param);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public String getHandlerName() {
        return "servicePorxyHandler";
    }

    @Override
    public void builderXml(long metadataId, MetadataModule module,
                           List<ChainElementDefine> chainList) {
        ProxyDefineImpl proxyDefine = this.handleProxyDefine(metadataId);
        proxyDefine.setName(module.getHandlerName());
        proxyDefine.setHandlerClazz(module.getHandlerClazz());
        chainList.add(proxyDefine);
    }

    /**
     * 处理转发模块<br/>
     * <p>
     * 1. metadataId 查询所有的(metadata_proxy where id = metadataId) <br/>
     * 注意:代理服务实现类可以支持一个或多个代理服务支持 <br/>
     * metadata_proxy表的record还原如下的数据结构<br/>
     * <serviceModule>
     * <sourceService path="xxx" httpMethod="GET,POST"/>
     * <targetService servicePoolName="127.0.0.1:8080_HTTP" method="GET"
     * serviceName="/test.html" socketTimeout="30000"
     * thresholdType="BYPERCENTAGE" threshold="0"/>
     * </serviceModule>
     * <p>
     * 2. 构建List<ServiceModule> serviceModuleList 对象 <br/>
     * 注意：数据依赖数据库表metadata_proxy,主要构建xml里面的ServiceModule服务模块 <br/>
     * <p>
     * 3. 构建请求连接池设置 <br/>
     * 注意:一个proxy服务同样也有多个池的配置<br/>
     *
     * @param metadataId
     * @return
     */
    public ProxyDefineImpl handleProxyDefine(long metadataId) {
        ProxyDefineImpl proxyDefine = new ProxyDefineImpl();
        // 1.
        List<MetadataProxy> list = proxyService.getMetadataProxyList(metadataId);
        List<ServiceModule> serviceModuleList = new ArrayList<>();
        ServiceModule serviceModule = null;
        SourceService source = null;
        TargetService target = null;

        // 2.
        for (MetadataProxy proxy : list) {
            serviceModule = new ServiceModule();
            source = new SourceService();
            source.setPath(proxy.getSourceUrl());
            source.setHttpMethod(proxy.getSourceMethod());


            target = new TargetService();
            target.setSocketTimeout(proxy.getSocketTimeout());
            target.setThreshold(proxy.getThreshold());
            target.setThresholdType(proxy.getThresholdType() == 0 ? ThresholdType.BYPERCENTAGE
                    : ThresholdType.BYUSER);
            target.setServiceName(proxy.getServiceName());
            target.setServicePoolName(proxy.getServicePoolName());
            // 这个地方http代理必须是0数据字段0
            if (proxy.getSeviceType() == 0) {// 普通模式
                target.setMethod(proxy.getTargetMethod());
            } else if (proxy.getSeviceType() == 1) {// emcf模式
                target.setServiceVersion(proxy.getServiceVersion());
            }
            serviceModule.setSourceService(source);
            serviceModule.setTargetService(target);
            serviceModule.setConvert(proxy.getConvert());
            serviceModuleList.add(serviceModule);

        }
        proxyDefine.setServiceModuleList(serviceModuleList);

        // 3.
        List<MetadataServicePool> pools = metadataServicePoolService.selectPools(metadataId);
        if (pools != null && pools.size() > 0) {
            List<ServicePool> sPools = new ArrayList<ServicePool>(pools.size());
            for (int i = 0; i < pools.size(); i++) {
                ServicePool sPool = new ServicePool();
                MetadataServicePool mPool = pools.get(i);
                sPool.setServicePoolName(mPool.getServicePoolName());
                sPool.setUrl(mPool.getUrl());
                sPool.setServiceType(mPool.getServiceType());
                if (sPool.getServiceType() == 0) {// http
                    sPool.setConnectTimeout(mPool.getConnectTimeout());
                } else if (sPool.getServiceType() == 1) {// emcf
                    sPool.setCoefficient(mPool.getCoefficient());
                    sPool.setForceCloseChannel(mPool.getForceCloseChannel() == 1 ? true : false);
                    sPool.setForceCloseTimeMillis(mPool.getForceCloseTimeMillis());
                }
                sPools.add(sPool);
            }
            proxyDefine.setServicePoolList(sPools);
        }
        return proxyDefine;
    }
}
