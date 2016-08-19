package com.servitization.webms.web;

import com.servitization.commons.permission.annotation.Permission;
import com.servitization.webms.entity.MetadataModule;
import com.servitization.webms.service.IMetadataModuleService;
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
@RequestMapping(value = "webms/module")
public class ModuleController {

    @Resource
    private IMetadataModuleService moduleService;


    @RequestMapping(value = "updateModule", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView updateModulePage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        String moduleId = request.getParameter("id");
        String moduleName = request.getParameter("moduleName");
        String handlerName = request.getParameter("handlerName");
        String handlerClazz = request.getParameter("handlerClazz");
        String chain = request.getParameter("chain");

        MetadataModule metadataModule = new MetadataModule();

        metadataModule.setChain(Integer.parseInt(chain));
        metadataModule.setId(Long.parseLong(moduleId));
        metadataModule.setHandlerClazz(handlerClazz);
        metadataModule.setHandlerName(handlerName);
        metadataModule.setName(moduleName);

        int result = moduleService.updateModule(metadataModule);

        Map<String, Object> modelMap = new HashMap<>();

        if (result == 1) {
            modelMap.put("msg", "update success !!!");
        } else {
            modelMap.put("msg", "update failure !!!");
        }

        int pageIndex = 0;
        int pageSize = 10;
        Map<String, Integer> params = new HashMap<>();
        params.put("pageIndex", pageIndex * pageSize);
        params.put("pageSize", pageSize);

        List<MetadataModule> modules = moduleService.getModuleList(params);
        modelMap.put("modules", modules);
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("module");
        return modelAndView;
    }


    @RequestMapping(value = "preUpdateModule", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView preUpdateModule(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        String moduleId = request.getParameter("id");
        List<String> moduleIds = new ArrayList<>();
        moduleIds.add(moduleId);
        MetadataModule metadataModule = new MetadataModule();
        List<MetadataModule> metadataModules = moduleService.getModulesByIds(moduleIds);
        if (null != metadataModules && metadataModules.size() == 1) {
            metadataModule = metadataModules.get(0);
        }
        Map<String, Object> modelMap = new HashMap<>();

        modelMap.put("metadataModule", metadataModule);
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("module_update");
        return modelAndView;
    }

    @RequestMapping(value = "getModulePage", method = RequestMethod.GET)
    public ModelAndView getModulePage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
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
        Map<String, Integer> params = new HashMap<>();
        params.put("pageIndex", pageIndex * pageSize);
        params.put("pageSize", pageSize);

        List<MetadataModule> modules = moduleService.getModuleList(params);
        modelMap.put("modules", modules);
        mav.addAllObjects(modelMap);
        mav.setViewName("module");
        return mav;
    }

    @Permission(name = "update")
    @RequestMapping(value = "getModuleAddPage", method = RequestMethod.GET)
    public ModelAndView getModuleAddPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
        mav.addAllObjects(modelMap);
        mav.setViewName("moduleadd");
        return mav;
    }

    /**
     * 添加模块
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "addModule", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addModule(HttpServletRequest request, HttpServletResponse response) {
        String msg = "添加成功";

        String moduleName = request.getParameter("moduleName");
        String handlerName = request.getParameter("handlerName");
        String handlerClazz = request.getParameter("handlerClazz");
        String chainString = request.getParameter("chain");

        if (StringUtils.isBlank(moduleName) || StringUtils.isBlank(handlerName) ||
                StringUtils.isBlank(handlerClazz) || StringUtils.isBlank(chainString)) {
            msg = "参数不能为空!";
            return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
        }

        MetadataModule metadataModule = new MetadataModule();
        metadataModule.setName(moduleName);
        metadataModule.setHandlerName(handlerName);
        metadataModule.setHandlerClazz(handlerClazz);
        metadataModule.setChain(Integer.parseInt(chainString));
        metadataModule.setCreateTime(new Date());

        int flag = moduleService.addModule(metadataModule);
        if (flag == 0) {
            msg = "添加模块失败, 请稍后重试!";
        }
        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }

    /**
     * 删除模块
     *
     * @param request
     * @param response
     */
    @Permission(name = "update")
    @RequestMapping(value = "delModule", method = RequestMethod.POST)
    public ResponseEntity<byte[]> delModule(HttpServletRequest request, HttpServletResponse response) {
        String msg = "删除成功";
        String idString = request.getParameter("id");

        if (StringUtils.isBlank(idString)) {
            msg = "id不能为空!";
            return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
        }
        int flag = moduleService.deleteModule(Long.parseLong(idString));
        if (flag == 0) {
            msg = "删除失败, 请稍后重试!";
        }
        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }

    @RequestMapping(value = "vertifyModule", method = RequestMethod.GET)
    @ResponseBody
    public String vertifyModule(HttpServletRequest request) {
        String handlerName = request.getParameter("handlerName");
        String handlerClazz = request.getParameter("handlerClazz");
        Map<String, Object> map = new HashMap<>();
        map.put("handlerName", handlerName);
        map.put("handlerClazz", handlerClazz);
        int result = moduleService.vertifyModule(map);
        return String.valueOf(result);
    }
}
