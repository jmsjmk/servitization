package com.servitization.webms.web;

import com.alibaba.fastjson.JSONArray;
import com.servitization.commons.permission.annotation.Permission;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.metadata.define.session.StrategyEntry;
import com.servitization.metadata.define.session.impl.SessionDefineImpl;
import com.servitization.webms.entity.MetadataModule;
import com.servitization.webms.entity.MetadataProxy;
import com.servitization.webms.entity.MetadataSession;
import com.servitization.webms.mapper.MetadataSessionMapper;
import com.servitization.webms.service.IMetadataProxyService;
import com.servitization.webms.service.IMetadataSessionService;
import com.servitization.webms.service.impl.MetadataServiceImpl;
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
import java.util.*;

@Controller
@RequestMapping(value = "webms/session")
public class SessionController extends BaseObserver {

    private static Map<String, String> strategies = new HashMap<>();
    static {
        strategies.put("ForceCheckStrategy", "com.servitization.session.strategy.impl.ForceCheckStrategy");
        strategies.put("ForceUnCheckStrategy", "com.servitization.session.strategy.impl.ForceUnCheckStrategy");
        strategies.put("ForceCheckWithVersionStrategy", "com.servitization.session.strategy.impl.ForceCheckWithVersionStrategy");
        strategies.put("ForceCheckWriteCardNoStrategy", "com.servitization.session.strategy.impl.ForceCheckWriteCardNoStrategy");
        strategies.put("HttpCheckStrategy", "com.servitization.session.strategy.impl.HttpCheckStrategy");
    }

    @Resource
    private IMetadataSessionService metadataSessionService;
    @Resource
    private IMetadataProxyService metadataProxyService;
    @Resource
    private MetadataServiceImpl metadataServiceImpl;
    @Resource
    private MetadataSessionMapper metadataSessionMapper;

    /**
     * 获取session列表页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getSessionPage", method = RequestMethod.GET)
    public ModelAndView getMetadataPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
        mav.setViewName("session");
        String metadataId = request.getParameter("metadataId");
        if (StringUtils.isBlank(metadataId)) {
            return mav;
        }
        String sourceUrl = request.getParameter("sourceUrl");
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
        params.put("pageIndex", pageIndex * pageSize);
        params.put("pageSize", pageSize);
        List<MetadataSession> metadataSessions = metadataSessionService.getMetadataSessionList(params);
        if (metadataSessions == null) {
            metadataSessions = new ArrayList<>();
        }
        int count = metadataSessionService.getMetadataSessionCount(params);
        int pageCount = count == 0 ? 0 : (count % pageSize == 0 ? count / pageSize : count / pageSize + 1);
        modelMap.put("pageIndex", pageIndex);
        modelMap.put("pageSize", pageSize);
        modelMap.put("pageCount", pageCount);
        modelMap.put("metadataSessions", metadataSessions);
        mav.addAllObjects(modelMap);
        return mav;
    }

    /**
     * 删除session列表
     *
     * @param request
     */
    @Permission(name = "update")
    @RequestMapping(value = "deleteMetadataSessions", method = RequestMethod.POST)
    public ResponseEntity<byte[]> deleteMetadataSessions(HttpServletRequest request) {
        String ids = request.getParameter("ids");
        List<String> list = JSONArray.parseArray(ids, String.class);
        int count = metadataSessionService.deleteMetadataSessions(list);
        String msg = count == list.size() ? "删除成功" : "部分记录删除失败：没找到相应的记录";
        return new ResponseEntity<>(msg.getBytes(), HttpStatus.OK);
    }

    /**
     * 获取添加页面
     *
     * @param request
     * @return
     */
    @Permission(name = "update")
    @RequestMapping(value = "getAddSessionPage", method = RequestMethod.GET)
    public ModelAndView getSessionAddPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("session_add");
        Map<String, Object> modelMap = new HashMap<>();
        String proxyId = request.getParameter("proxyId");
        if (StringUtils.isNotBlank(proxyId)) {
            MetadataSession metadataSession = metadataSessionService.getSessionById(Long.parseLong(proxyId));
            modelMap.put("metadataSession", metadataSession);
        }
        String metaId = request.getParameter("metadataId");
        List<MetadataProxy> list = metadataProxyService.getMetadataProxyList(Long.parseLong(metaId));
        modelMap.put("metadataProxyList", list);
        mav.addAllObjects(modelMap);
        return mav;
    }

    @RequestMapping(value = "addOrUpdateSession", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addOrUpdateSession(HttpServletRequest request) {
        String sessionId = request.getParameter("sessionId");
        String metadataId = request.getParameter("metadataId");
        String proxyId = request.getParameter("proxyId");
        String strategy = request.getParameter("strategy");
        String reqType = request.getParameter("reqType");
        String validateUrl = request.getParameter("validateurl");
        String validateMethod = request.getParameter("validateMethod");
        MetadataSession metadataSession = new MetadataSession();
        metadataSession.setMetadataId(Integer.parseInt(metadataId));
        metadataSession.setProxyId(Integer.parseInt(proxyId));
        metadataSession.setStrategy(strategy);
        metadataSession.setCreateTime(new Date());
        metadataSession.setReqType(reqType);
        metadataSession.setValidateurl(validateUrl);
        metadataSession.setValidateMethod(validateMethod);
        String msg;
        if (StringUtils.isNotBlank(sessionId)) {
            metadataSession.setId(Long.parseLong(sessionId));
            int count = metadataSessionService.updateMetadataSession(metadataSession);
            msg = count > 0 ? "修改成功" : "修改失败";
        } else {
            int count = metadataSessionService.addMetadataSession(metadataSession);
            msg = count > 0 ? "添加成功" : "添加失败";
        }
        return new ResponseEntity<>(msg.getBytes(), HttpStatus.OK);
    }

    @RequestMapping(value = "vertifySession", method = RequestMethod.GET)
    @ResponseBody
    public String vertifySession(HttpServletRequest request) {
        String metadataId = request.getParameter("metadataId");
        String proxyId = request.getParameter("proxyId");
        Map<String, Object> params = new HashMap<>();
        params.put("metadataId", metadataId);
        params.put("proxyId", proxyId);
        int result = metadataSessionService.vertifySession(params);
        return String.valueOf(result);
    }

    /**
     * 当删除元数据的时候，得相应的把元数据对应的session值也删除掉
     */
    @Override
    public void deleteMetadata(List<String> args) {
        metadataSessionService.deleteSessionByMetadataid(args);
    }

    @Override
    public String getHandlerName() {
        return "sessionServiceChainHandler";
    }

    @Override
    public void builderXml(long metadataId, MetadataModule module, List<ChainElementDefine> chainList) {
        SessionDefineImpl sessionDefine = this.handleSessionDefine(metadataId);
        sessionDefine.setName(module.getHandlerName());
        sessionDefine.setHandlerClazz(module.getHandlerClazz());
        chainList.add(sessionDefine);
    }

    /**
     * 处理session模块
     *
     * @param metadataId
     * @return
     */
    public SessionDefineImpl handleSessionDefine(long metadataId) {
        SessionDefineImpl sessionDefine = new SessionDefineImpl();
        Map<String, Object> params = new HashMap<>();
        params.put("metadataId", metadataId);
        params.put("pageIndex", 0);
        params.put("pageSize", Integer.MAX_VALUE);
        List<MetadataSession> list = metadataSessionMapper.getMetadataSessionList(params);
        Map<String, String> strategyMap = new HashMap<>();
        Map<String, StrategyEntry> strategyEntryMap = new HashMap<>();
        for (MetadataSession session : list) {
            StrategyEntry strategyEntry = new StrategyEntry();
            String strategyImpClass = strategies.get(session.getStrategy());
            strategyEntry.setValue(strategyImpClass);
            strategyEntry.setKey(session.getSourceUrl());
            strategyEntry.setHttpMethod(session.getValidateMethod());
            strategyEntry.setType(session.getReqType());
            strategyEntry.setUrl(session.getValidateurl());
            strategyMap.put(session.getSourceUrl(), strategyImpClass);
            strategyEntryMap.put(session.getSourceUrl(), strategyEntry);
        }
        sessionDefine.setStrategyMap(strategyMap);
        sessionDefine.setStrategEntryMap(strategyEntryMap);
        return sessionDefine;
    }

    @Override
    public void copyMetadata(long oldMetaId, long newMetaId, long newProxyId, long oldProxyId) {
        // 复制添加该Proxy下对应的Session配置
        List<MetadataSession> sessionList = metadataSessionMapper
                .getMetadataSessionListByMetaIdAndProxyId(oldMetaId, oldProxyId);
        if (sessionList != null && sessionList.size() > 0) {
            for (MetadataSession metadataSession : sessionList) {
                metadataSession.setMetadataId(newMetaId);
                metadataSession.setProxyId(newProxyId);
                metadataSession.setCreateTime(new Date());
                metadataSessionMapper.addMetadataSession(metadataSession);
            }
        }
    }
}