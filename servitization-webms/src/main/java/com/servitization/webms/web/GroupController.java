package com.servitization.webms.web;

import com.servitization.commons.permission.annotation.Permission;
import com.servitization.webms.entity.MetadataGroup;
import com.servitization.webms.entity.MetadataModule;
import com.servitization.webms.mapper.MetadataGroupMapper;
import com.servitization.webms.service.IMetadataGroupService;
import com.servitization.webms.service.IMetadataModuleService;
import com.servitization.webms.util.adapter.BaseObserver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "webms/group")
public class GroupController extends BaseObserver {
    @Resource
    private IMetadataGroupService metadataGroupService;
    @Resource
    private IMetadataModuleService metadataModuleService;
    @Resource
    private MetadataGroupMapper metadataGroupMapper;

    @RequestMapping(value = "getGroupPage", method = RequestMethod.GET)
    public ModelAndView getGroupPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
        mav.setViewName("group");

        String req = request.getParameter("metadataId");

        List<MetadataGroup> groups = metadataGroupService.getGroupsByMetadataId(Long.parseLong(req));
        modelMap.put("metadataGroups", groups);

        List<MetadataModule> modules = metadataModuleService.getAllModuleList();

        modelMap.put("modules", modules);

        mav.addAllObjects(modelMap);

        return mav;
    }

    /**
     * 添加或修改group页面
     *
     * @param request
     * @param response
     * @return
     * @author Chao.Mu
     */
    @Permission(name = "update")
    @RequestMapping(value = "getAddGroupPage", method = RequestMethod.GET)
    public ModelAndView getAddGroupPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
        mav.setViewName("group_add");
        String groupIdString = request.getParameter("groupId");
        if (StringUtils.isNotBlank(groupIdString)) {
            MetadataGroup group = metadataGroupService.getGroupById(Long.parseLong(groupIdString));
            modelMap.put("metadataGroup", group);
        }
        List<MetadataModule> modules = metadataModuleService.getAllModuleList();
        modelMap.put("modules", modules);
        mav.addAllObjects(modelMap);
        return mav;
    }

    @RequestMapping(value = "addOrUpdateGroup", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addOrUpdateGroup(HttpServletRequest request, HttpServletResponse response) {
        String metadataId = request.getParameter("metadataId");
        String groupId = request.getParameter("groupId");
        String upModules = request.getParameter("upModules");
        String downModules = request.getParameter("downModules");
        String name = request.getParameter("name");
        String processTimeOut = request.getParameter("processTimeOut");
        String size = request.getParameter("size");
        String policy = request.getParameter("policy");
        MetadataGroup group = new MetadataGroup();
        group.setMetadataId(Long.parseLong(metadataId));
        group.setName(name);
        group.setProcessTimeOut(Integer.parseInt(processTimeOut));
        group.setSize(Integer.parseInt(size));
        group.setPolicy(policy);
        group.setCreateTime(new Date());
        String msg = "";
        // 判断分组
        if (StringUtils.isBlank(upModules) && StringUtils.isBlank(downModules)) {
            msg = "未选择模块";
            return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
        } else if (StringUtils.isNotBlank(upModules) && StringUtils.isNotBlank(downModules)) {
            msg = "所选模块不能同时含有上下行模块";
            return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
        } else if (StringUtils.isNotBlank(upModules) && StringUtils.isBlank(downModules)) {
            group.setChain(0);
            group.setModuleIds(upModules);
        } else if (StringUtils.isBlank(upModules) && StringUtils.isNotBlank(downModules)) {
            group.setChain(1);
            group.setModuleIds(downModules);
        }
        if (StringUtils.isNotBlank(groupId)) {
            group.setId(Long.parseLong(groupId));
            // 修改
            int count = metadataGroupService.updateGroup(group);
            msg = count > 0 ? "修改成功" : "修改失败";
        } else {
            // 添加
            int count = metadataGroupService.addGroup(group);
            msg = count > 0 ? "添加成功" : "添加失败";
        }
        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }

    @Permission(name = "update")
    @RequestMapping(value = "delGroup", method = RequestMethod.POST)
    public ResponseEntity<byte[]> delGroup(HttpServletRequest request, HttpServletResponse response) {
        String msg = "删除成功";
        String idString = request.getParameter("groupId");

        if (StringUtils.isBlank(idString)) {
            msg = "id不能为空!";
            return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
        }
        int flag = metadataGroupService.deleteGroup(Long.parseLong(idString));
        if (flag == 0) {
            msg = "删除失败, 请稍后重试!";
        }
        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }

    @Override
    public void deleteMetadata(List<String> args) {
        metadataGroupService.deleteGroupByMetadataid(args);
    }

    @Override
    public String getHandlerName() {
        return "groupHandler";
    }
}
