package com.servitization.webms.web;

import com.servitization.webms.entity.Metadata;
import com.servitization.webms.entity.MetadataMachine;
import com.servitization.webms.entity.MetadataModule;
import com.servitization.webms.service.IMetadataMachineService;
import com.servitization.webms.service.IMetadataService;
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
@RequestMapping(value = "webms/machine")
public class MachineController {

    @Resource
    private IMetadataMachineService metadatamachineService;

    @Resource
    private IMetadataService metadataService;

    @RequestMapping(value = "getMachineListByMtdtId", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getMachineListByMtdtId(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        String metadataId = request.getParameter("metadataId");
        MetadataModule metadataModule = new MetadataModule();

        List<MetadataMachine> metadataMachines = metadatamachineService.getMachineListByMtdtId(metadataId);
        Metadata metadata = metadataService.getMetadataById(Long.parseLong(metadataId));

        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("metadataMachines", metadataMachines);
        modelMap.put("metadataId", metadataId);
        modelMap.put("metadata", metadata);
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("machine_list");
        return modelAndView;
    }

    @RequestMapping(value = "addMetadataMachine", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<byte[]> addMetadataMachine(HttpServletRequest request, HttpServletResponse response) {

        String metadataId = request.getParameter("metadataId");
        String ip = request.getParameter("ip");
        String status = request.getParameter("status");
        String metadataDescription = request.getParameter("metadataDescription");

        if (StringUtils.isBlank(metadataId)) {
            return new ResponseEntity<byte[]>("metadataId is Blank".getBytes(), HttpStatus.OK);
        }

        if (StringUtils.isBlank(ip)) {
            return new ResponseEntity<byte[]>("ip is Blank".getBytes(), HttpStatus.OK);
        }

        MetadataMachine metadataMachine = new MetadataMachine();

        metadataMachine.setCreateTime(new Date());
        metadataMachine.setIp(ip);
        metadataMachine.setStatus(status);
        metadataMachine.setMetadataId(Long.parseLong(metadataId));

        int result = metadatamachineService.addMetadataMachine(metadataMachine);
        String prefix = "[" + metadataDescription + "-Node]";
        String msg = result == 1 ? prefix + "添加机器列表成功" : prefix + "添加机器列表失败";
        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }

    @RequestMapping(value = "deleteMetadataMachine", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<byte[]> deleteMetadataMachine(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("metadataId");
        String metadataDescription = request.getParameter("metadataDescription");
        if (StringUtils.isBlank(id)) {
            return new ResponseEntity<byte[]>("metadataId is Blank".getBytes(), HttpStatus.OK);
        }
        int result = metadatamachineService.deleteMetadataMachine(Long.parseLong(id));
        String prefix = "[" + metadataDescription + "-Node]";
        String msg = result == 1 ? prefix + "删除机器成功" : prefix + "删除机器失败";
        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }

}
