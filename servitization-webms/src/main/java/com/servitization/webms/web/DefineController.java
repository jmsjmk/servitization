package com.servitization.webms.web;

import com.alibaba.fastjson.JSONArray;
import com.servitization.commons.permission.annotation.Permission;
import com.servitization.metadata.define.defence.impl.DefenceDefineImpl;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.webms.entity.MetadataDefine;
import com.servitization.webms.entity.MetadataModule;
import com.servitization.webms.entity.MetadataProxy;
import com.servitization.webms.service.IMetadataDefineService;
import com.servitization.webms.service.IMetadataProxyService;
import com.servitization.webms.util.IPListUtil;
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
@RequestMapping(value = "webms/define")
public class DefineController extends BaseObserver {

    @Resource
    private IMetadataDefineService metadataDefineService;
    @Resource
    private IMetadataProxyService metadataProxyService;

    /**
     * 获取防攻击列表页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getDefinePage", method = RequestMethod.GET)
    public ModelAndView getMetadataPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
        mav.setViewName("define");

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

        List<MetadataDefine> metadataDefines = metadataDefineService.getMetadataDefineList(params);
        if (metadataDefines == null) {
            metadataDefines = new ArrayList<>();
        }

        int count = metadataDefineService.getMetadataDefineCount(params);
        int pageCount = count == 0 ? 0 : (count % pageSize == 0 ? count / pageSize : count / pageSize + 1);

        modelMap.put("pageIndex", pageIndex);
        modelMap.put("pageSize", pageSize);
        modelMap.put("pageCount", pageCount);
        modelMap.put("metadataDefines", metadataDefines);

        mav.addAllObjects(modelMap);

        return mav;
    }

    /**
     * 删除防攻击列表
     *
     * @param request
     * @param response
     */
    @Permission(name = "update")
    @RequestMapping(value = "deleteMetadataDefines", method = RequestMethod.POST)
    public ResponseEntity<byte[]> deleteMetadataDefines(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        List<String> list = JSONArray.parseArray(ids, String.class);
        int count = metadataDefineService.deleteMetadataDefines(list);
        String msg = count == list.size() ? "删除成功" : "部分记录删除失败：没找到相应的记录";
        return new ResponseEntity<>(msg.getBytes(), HttpStatus.OK);
    }

    /**
     * 获取添加页面
     *
     * @param request
     * @param response
     * @return
     */
    @Permission(name = "update")
    @RequestMapping(value = "getAddDefinePage", method = RequestMethod.GET)
    public ModelAndView getDefineAddPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("define_add");
        Map<String, Object> modelMap = new HashMap<>();

        String proxyId = request.getParameter("proxyId");

        if (StringUtils.isNotBlank(proxyId)) {
            MetadataDefine metadataDefine = metadataDefineService.getDefineById(Long.parseLong(proxyId));
            modelMap.put("metadataDefine", metadataDefine);
        }

        String metaId = request.getParameter("metadataId");
        List<MetadataProxy> list = metadataProxyService.getMetadataProxyList(Long.parseLong(metaId));

        modelMap.put("metadataProxyList", list);
        mav.addAllObjects(modelMap);
        return mav;
    }

    @RequestMapping(value = "addOrUpdateDefine", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addOrUpdateDefine(HttpServletRequest request, HttpServletResponse response) {
        String defineId = request.getParameter("defineId");
        String metadataId = request.getParameter("metadataId");
        String proxyId = request.getParameter("proxyId");
        String timeUnit = request.getParameter("timeUnit");
        String times = request.getParameter("times");
        MetadataDefine metadataDefine = new MetadataDefine();
        metadataDefine.setMetadataId(Integer.parseInt(metadataId));
        metadataDefine.setProxyId(Integer.parseInt(proxyId));
        metadataDefine.setTimeUnit(Integer.parseInt(timeUnit));
        metadataDefine.setTimes(Integer.parseInt(times));
        metadataDefine.setCreateTime(new Date());

        String msg = "成功";
        if (StringUtils.isNotBlank(defineId)) {
            metadataDefine.setId(Long.parseLong(defineId));
            int count = metadataDefineService.updateMetadataDefine(metadataDefine);
            msg = count > 0 ? "修改成功" : "修改失败";
        } else {
            int count = metadataDefineService.addMetadataDefine(metadataDefine);
            msg = count > 0 ? "添加成功" : "添加失败";
        }
        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }

    @RequestMapping(value = "vertifyDefine", method = RequestMethod.GET)
    @ResponseBody
    public String vertifyDefine(HttpServletRequest request) {
        String metadataId = request.getParameter("metadataId");
        String proxyId = request.getParameter("proxyId");
        Map<String, Object> params = new HashMap<>();
        params.put("metadata_id", Integer.parseInt(metadataId));
        params.put("proxy_id", Integer.parseInt(proxyId));
        return String.valueOf(metadataDefineService.vertifyDefine(params));
    }

    @RequestMapping(value = "getWhitelistPage", method = RequestMethod.GET)
    public ModelAndView getWhitelistPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("define_whitelist");
        Map<String, Object> modelMap = new HashMap<>();

        String metadataId = request.getParameter("metadataId");
        Map<String, Object> params = new HashMap<>();
        params.put("metadata_id", Integer.parseInt(metadataId));

        String ips = metadataDefineService.getDefineWhitelist(params);

        modelMap.put("ips", ips == null ? "" : ips);
        mav.addAllObjects(modelMap);
        return mav;
    }

    @RequestMapping(value = "addOrUpdateWhitelist", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addOrUpdateWhitelist(HttpServletRequest request, HttpServletResponse response) {
        String ips = request.getParameter("ips");
        String metadataId = request.getParameter("metadataId");
        // Format ips
        Set<String> ips_set = null;
        try {
            ips_set = IPListUtil.ipSpliter(ips);
        } catch (Exception e) {
            return new ResponseEntity<byte[]>(e.getMessage().getBytes(), HttpStatus.OK);
        }
        if (ips_set == null) ips = "";
        else ips = IPListUtil.format(ips_set);

        Map<String, Object> params = new HashMap<>();
        params.put("metadata_id", Integer.parseInt(metadataId));
        String has_ips = metadataDefineService.getDefineWhitelist(params);
        String msg = "更新成功";
        if (has_ips == null) {
            //insert
            params = new HashMap<>();
            params.put("metadata_id", metadataId);
            params.put("ips", ips);
            params.put("createTime", new Date());
            int rst = metadataDefineService.addDefineWhitelist(params);
            if (rst <= 0) msg = "添加失败";
        } else {
            //update
            params = new HashMap<>();
            params.put("metadata_id", metadataId);
            params.put("ips", ips);
            int rst = metadataDefineService.updateDefineWhitelist(params);
            if (rst <= 0) msg = "更新失败";
        }
        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }


    /**
     * 作为观察者，当抽象主题角色删除操作的时候会调用该方法
     */
    @Override
    public void deleteMetadata(List<String> args) {
        metadataDefineService.deleteDefineByMetadaId(args);
        metadataDefineService.deleteDefineWhitelist(args);
    }

    @Override
    public String getHandlerName() {
        return "defenceServiceChainHandler";
    }

    @Override
    public void builderXml(long metadataId, MetadataModule module, List<ChainElementDefine> chainList) {
        DefenceDefineImpl defenceDefine = this.handleDefenceDefine(metadataId);
        defenceDefine.setName(module.getHandlerName());
        defenceDefine.setHandlerClazz(module.getHandlerClazz());
        chainList.add(defenceDefine);
    }

    private DefenceDefineImpl handleDefenceDefine(long metadataId) {
        DefenceDefineImpl defenceDefine = new DefenceDefineImpl();
        Map<String, Object> params = new HashMap<>();
        params.put("metadataId", metadataId);
        params.put("pageIndex", 0);
        params.put("pageSize", Integer.MAX_VALUE);
        List<MetadataDefine> list = metadataDefineService.getMetadataDefineList(params);
        Map<String, String> strategyMap = new HashMap<>();
        for (MetadataDefine define : list) {
            strategyMap.put(define.getSourceUrl(), define.getTimeUnit() + "," + define.getTimes());
        }
        defenceDefine.setStrategyMap(strategyMap);
        params = new HashMap<>();
        params.put("metadata_id", metadataId);
        String ips = metadataDefineService.getDefineWhitelist(params);
        if (ips != null && ips.length() > 0) {
            defenceDefine.setIPWhiteList(IPListUtil.unformat(ips));
        }
        return defenceDefine;
    }


    @Override
    public void copyMetadata(long oldMetaId, long newMetaId, long newProxyId, long oldProxyId) {
        // 复制添加该Proxy下对应的防攻击配置
        List<MetadataDefine> defenceList = metadataDefineService
                .getMetadataDefineListByMetaIdAndProxyId(oldMetaId, oldProxyId);
        if (defenceList != null && defenceList.size() > 0) {
            for (MetadataDefine metadataDefine : defenceList) {
                metadataDefine.setMetadataId(newMetaId);
                metadataDefine.setProxyId(newProxyId);
                metadataDefine.setCreateTime(new Date());
                metadataDefineService.addMetadataDefine(metadataDefine);
            }
        }
    }

}
