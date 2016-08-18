package com.servitization.webms.web;

import com.alibaba.fastjson.JSONArray;
import com.servitization.commons.permission.annotation.Permission;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.metadata.define.pvUv.impl.PvUvDefineImpl;
import com.servitization.webms.entity.MetadataModule;
import com.servitization.webms.entity.MetadataProxy;
import com.servitization.webms.entity.MetadataPvUv;
import com.servitization.webms.mapper.MetadataPvUvMapper;
import com.servitization.webms.service.IMetadataProxyService;
import com.servitization.webms.service.IMetadataPvUvService;
import com.servitization.webms.service.impl.MetadataServiceImpl;
import com.servitization.webms.util.adapter.BaseObserver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/***
 * 记录pv、uv的controller
 */
@Controller
@RequestMapping(value = "webms/pvUv")
public class PvUvController extends BaseObserver {

    @Resource
    private IMetadataPvUvService metadataPvUvService;
    @Resource
    private IMetadataProxyService metadataProxyService;
    @Resource
    private MetadataServiceImpl metadataServiceImpl;
    @Resource
    private MetadataPvUvMapper metadataPvUvMapper;

    @RequestMapping(value = "getPvUvPage", method = RequestMethod.GET)
    public ModelAndView getPvUvPage(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setViewName("pvUv");
        String metadataId = request.getParameter("metadataId");

        if (StringUtils.isBlank(metadataId)) {
            return view;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("metadataId", metadataId);

        List<MetadataPvUv> result = metadataPvUvService.getMetadataPvUvList(params);
        if (null == result) {
            result = new ArrayList<MetadataPvUv>();
        }
        List<MetadataProxy> proxyList = metadataProxyService.getMetadataProxyList(Long.parseLong(metadataId));
        Map<String, Object> modelMap = new HashMap<>();

        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<MetadataProxy> resultList = null;
        int pvUvListLen = 0;
        if (proxyList == null || (pvUvListLen = result.size()) <= 0) {
            resultList = proxyList;
        } else {
            resultList = new ArrayList<>();
            Set<String> set = new HashSet<String>();
            for (int i = 0; i < pvUvListLen; i++) {
                set.add(result.get(i).getSourceUrl());
            }
            MetadataProxy proxy = null;
            for (int i = 0, index = proxyList.size(); i < index; i++) {
                proxy = proxyList.get(i);
                if (set.contains(proxy.getSourceUrl())) {
                    continue;
                }
                resultList.add(proxy);
            }
        }
        resultMap.put("proxyList", resultList);
        resultMap.put("metadataPvUv", result);
        modelMap.put("data", resultMap);
        view.addAllObjects(modelMap);
        return view;
    }

    @RequestMapping(value = "showProxyList", method = RequestMethod.GET)
    public ModelAndView showProxyList(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        view.setViewName("pvUv_add");

        String metadataId = request.getParameter("metadataId");
        if (StringUtils.isBlank(metadataId)) {
            return view;
        }
        List<MetadataProxy> proxyList = metadataProxyService.getMetadataProxyList(Long.parseLong(metadataId));
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("proxyList", proxyList);
        view.addAllObjects(modelMap);
        return view;
    }

    /**
     * @param request
     * @return 添加操作
     */
    @Permission(name = "update")
    @RequestMapping(value = "addPvUv", method = RequestMethod.POST)
    @ResponseBody
    public String addPvUv(HttpServletRequest request) {
        String metadataId = request.getParameter("metadataId");
        if (StringUtils.isBlank(metadataId)) {
            return "-1";
        }
        String proxyId = request.getParameter("proxyId");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("metadataId", metadataId);
        params.put("proxyId", proxyId);
        /**
         * 是否有相同数据、有就不添加，返回“1”给前端判断来显示不能添加相同的数据
         */
        if (metadataPvUvService.vertifyPvUv(params) >= 1) {
            return "-2";
        }

        params.put("createTime", new Date());
        /**
         * 添加数据
         */
        metadataPvUvService.addPvUv(params);
        return "1";
    }

    /**
     * 删除操作
     */
    @Permission(name = "update")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletRequest request) {

        String metadataId = request.getParameter("metadataId");
        String proxyId = request.getParameter("proxyId");
        if (StringUtils.isBlank(metadataId)) {
            return "0";
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("metadataId", metadataId);
        params.put("proxyId", proxyId);

        return String.valueOf(metadataPvUvService.delete(params));
    }

    /**
     * 批量删除
     */
    @Permission(name = "update")
    @RequestMapping(value = "deleteMany", method = RequestMethod.POST)
    public ModelAndView deleteMany(HttpServletRequest request) {
        String ids = request.getParameter("ids");
        ModelAndView view = new ModelAndView();
        view.setViewName("pvUv");
        if (StringUtils.isBlank(ids)) {
            return view;
        }
        List<String> list = JSONArray.parseArray(ids, String.class);
        metadataPvUvService.deleteMany(list);
        return getPvUvPage(request);
    }

    @Override
    public void deleteMetadata(List<String> args) {
        metadataPvUvService.deletePvUvByMetadataid(args);
    }

    @Override
    public String getHandlerName() {
        return "PvUvChainHandler";
    }

    @Override
    public void builderXml(long metadataId, MetadataModule module, List<ChainElementDefine> chainList) {
        PvUvDefineImpl pvUvDefine = this.handlePvUvDefine(metadataId);
        pvUvDefine.setName(module.getHandlerName());
        pvUvDefine.setHandlerClazz(module.getHandlerClazz());
        chainList.add(pvUvDefine);
    }

    /**
     * 处理pv、uv模块
     *
     * @param metadataId
     * @return
     */
    public PvUvDefineImpl handlePvUvDefine(long metadataId) {
        PvUvDefineImpl pvUvDefine = new PvUvDefineImpl();
        Map<String, Object> params = new HashMap<>();
        params.put("metadataId", metadataId);
        params.put("pageIndex", 0);
        params.put("pageSize", Integer.MAX_VALUE);
        List<MetadataPvUv> pvUvMetadataList = metadataPvUvService.getMetadataPvUvList(params);
        int len = 0;

        if (null == pvUvMetadataList || (len = pvUvMetadataList.size()) <= 0) {
            return pvUvDefine;
        }
        List<String> pvUvList = new ArrayList<String>();

        for (int index = 0; index < len; index++) {
            pvUvList.add(pvUvMetadataList.get(index).getSourceUrl());
        }
        pvUvDefine.setPvUvList(pvUvList);
        return pvUvDefine;
    }


    @Override
    public void copyMetadata(long oldMetaId, long newMetaId, long newProxyId, long oldProxyId) {
        // 复制添加到pv、uv表信息
        List<MetadataPvUv> pvUvList = metadataPvUvMapper.selectPvUvList(oldMetaId, oldProxyId);
        if (pvUvList != null && pvUvList.size() > 0) {
            for (MetadataPvUv pvUv : pvUvList) {
                pvUv.setMetadataId(newMetaId);
                pvUv.setProxyId(newProxyId);
                pvUv.setCreateTime(new Date());
            }
            metadataPvUvMapper.batchInsert(pvUvList);
        }
    }
}