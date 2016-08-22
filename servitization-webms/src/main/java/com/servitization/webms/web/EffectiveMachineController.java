package com.servitization.webms.web;

import com.servitization.metadata.zk.StatusInfo;
import com.servitization.webms.entity.MetadataVersion;
import com.servitization.webms.service.IEffectiveMachineService;
import com.servitization.webms.service.IMetadataService;
import com.servitization.webms.service.IMetadataVersionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "webms/machine")
public class EffectiveMachineController {

    @Resource
    private IMetadataService metadataService;

    @Resource
    private IMetadataVersionService metadataVersionService;

    @Resource
    private IEffectiveMachineService effectiveMachineService;

    /**
     * 展示生效机器列表页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getEffectiveMachinePage", method = RequestMethod.GET)
    public ModelAndView getMetadataPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
        mav.setViewName("effectivemachine");
        String metadataId = request.getParameter("metadataId");
        if (StringUtils.isBlank(metadataId)) {
            return mav;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("metadataId", metadataId);
        params.put("pageIndex", 0);
        params.put("pageSize", Integer.MAX_VALUE);
        List<MetadataVersion> metadataVersions = metadataVersionService.getMetadataVersionList(params);
        //获取每个version是否有机器存在
        boolean[] existences = effectiveMachineService.getEffectiveMachineExistence(metadataVersions);
        for (int i = 0; i < metadataVersions.size(); i++) {
            metadataVersions.get(i).setIsMachineExist(existences[i]);
        }
        modelMap.put("versions", metadataVersions);
        mav.addAllObjects(modelMap);
        return mav;
    }


    /**
     * 获取生效机器列表
     *
     * @return
     * @RequestMapping(value = "getEffectiveMachineList", method = RequestMethod.GET)
     * public ModelAndView getEffectiveMachineList(HttpServletRequest request, HttpServletResponse response) {
     * ModelAndView mav = new ModelAndView();
     * Map<String, Object> modelMap = new HashMap<>();
     * mav.setViewName("effectivemachinelist");
     * <p>
     * String metadataId = request.getParameter("metadataId");
     * String versionId = request.getParameter("versionId");
     * <p>
     * if (StringUtils.isBlank(metadataId)) {
     * return mav;
     * }
     * <p>
     * Metadata metadata = metadataService.getMetadataById(Long.parseLong(metadataId));
     * <p>
     * <p>
     * List<Machine> machines = effectiveMachineService.getEffectiveMachineList(metadata, versionId);
     * <p>
     * modelMap.put("machines", machines);
     * mav.addAllObjects(modelMap);
     * <p>
     * return mav;
     * }
     */
    @RequestMapping(value = "getEffectiveMachineList", method = RequestMethod.GET)
    public ModelAndView getEffectiveMachineList() {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
        mav.setViewName("effectivemachinelist");
        List<StatusInfo> list = effectiveMachineService.getEffectiveMachineExistInfo();
        modelMap.put("machines", list);
        mav.addAllObjects(modelMap);
        return mav;
    }
}
