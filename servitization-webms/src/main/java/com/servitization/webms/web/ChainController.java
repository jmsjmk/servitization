package com.servitization.webms.web;

import com.servitization.webms.entity.Metadata;
import com.servitization.webms.entity.MetadataGroup;
import com.servitization.webms.entity.MetadataModule;
import com.servitization.webms.service.IMetadataGroupService;
import com.servitization.webms.service.IMetadataModuleService;
import com.servitization.webms.service.IMetadataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "webms/chain")
public class ChainController {

    @Resource
    private IMetadataService metadataService;

    @Resource
    private IMetadataGroupService metadataGroupService;

    @Resource
    private IMetadataModuleService metadataModuleService;

    /**
     * 获取node执行的列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getChainPage", method = RequestMethod.GET)
    public ModelAndView getChainPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
        mav.setViewName("chain");
        String req = request.getParameter("metadataId");
        Metadata metadata = metadataService.getMetadataById(Long.parseLong(req));
        modelMap.put("metadata", metadata);
        List<MetadataGroup> groups = metadataGroupService.getGroupsByMetadataId(Long.parseLong(req));
        modelMap.put("groups", groups);
        List<MetadataModule> modules = metadataModuleService.getAllModuleList();
        modelMap.put("modules", modules);
        mav.addAllObjects(modelMap);
        return mav;
    }
}
