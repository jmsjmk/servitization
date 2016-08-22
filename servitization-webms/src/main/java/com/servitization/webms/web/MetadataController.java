package com.servitization.webms.web;

import com.alibaba.fastjson.JSONArray;
import com.servitization.commons.permission.annotation.Permission;
import com.servitization.webms.entity.Chain;
import com.servitization.webms.entity.Metadata;
import com.servitization.webms.service.IMetadataService;
import com.servitization.webms.util.ConcreteSubject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "webms/metadata")
public class MetadataController {
    @Resource
    private IMetadataService metadataService;

    /**
     * 获取元数据列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getMetadataPage", method = RequestMethod.GET)
    public ModelAndView getMetadataPage(HttpServletRequest request) {
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
        List<Metadata> metadatas = metadataService.getMetadataList(params);
        if (metadatas == null) {
            metadatas = new ArrayList<>();
        }
        int count = metadataService.getMetadataCount();
        int pageCount = count == 0 ? 0 : (count % pageSize == 0 ? count / pageSize : count / pageSize + 1);
        modelMap.put("pageIndex", pageIndex);
        modelMap.put("pageSize", pageSize);
        modelMap.put("pageCount", pageCount);
        modelMap.put("metadatas", metadatas);
        mav.addAllObjects(modelMap);
        mav.setViewName("metadata");
        return mav;
    }

    /**
     * 删除元数据标识
     *
     * @param request
     */
    @Permission(name = "update")
    @RequestMapping(value = "deleteMetadatas", method = RequestMethod.POST)
    public ResponseEntity<byte[]> deleteMetadatas(HttpServletRequest request) {
        String ids = request.getParameter("ids");
        List<String> list = JSONArray.parseArray(ids, String.class);
        int count = metadataService.deleteMetadatas(list);
        /**
         * 可能会出现只是删除了部分的数据，这时候需要在写sql的时候注
         */
        if (count > 0) {
            /**
             * 得到单例对象
             */
            ConcreteSubject concreteSubject = ConcreteSubject.instances();
            concreteSubject.deleteMetadata(list);
        }
        String msg = count == list.size() ? "删除成功" : "部分记录删除失败：没找到相应的记录";
        return new ResponseEntity<>(msg.getBytes(), HttpStatus.OK);
    }

    /**
     * 添加元数据标识
     *
     * @param request
     */
    @Permission(name = "update")
    @RequestMapping(value = "addMetadata", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addMetadata(HttpServletRequest request) {
        String msg = "添加成功";
        String metaKey = request.getParameter("metaKey");
        String description = request.getParameter("description");
        if (StringUtils.isBlank(metaKey) || StringUtils.isBlank(description)) {
            msg = "参数不能为空!";
            return new ResponseEntity<>(msg.getBytes(), HttpStatus.OK);
        }
        Metadata metadata = new Metadata();
        metadata.setMetaKey(metaKey);
        metadata.setDescription(description);
        metadata.setCreateTime(new Date());
        int flag = metadataService.isExistMetadata(metadata);
        if (flag > 0) {
            msg = "元数据id或则元数据名称已经存在";
        } else {
            flag = metadataService.addMetadata(metadata);
            if (flag == 0) {
                msg = "添加元数据标识失败, 请稍后重试!";
            }
        }
        return new ResponseEntity<>(msg.getBytes(), HttpStatus.OK);
    }

    /**
     * 添加元数据标识
     *
     * @param request
     */
    @Permission(name = "update")
    @RequestMapping(value = "addMetadataByCopy", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addMetadataByCopy(HttpServletRequest request) {
        String msg = "复制添加成功";
        String metaKey = request.getParameter("metaKey");
        String description = request.getParameter("description");
        String beCopiedMetaId = request.getParameter("copyFrom");
        if (StringUtils.isBlank(metaKey) || StringUtils.isBlank(description) || StringUtils.isBlank(beCopiedMetaId)) {
            msg = "参数不能为空!";
            return new ResponseEntity<>(msg.getBytes(), HttpStatus.OK);
        }
        Metadata metadata = new Metadata();
        metadata.setMetaKey(metaKey);
        metadata.setDescription(description);
        metadata.setCreateTime(new Date());
        int flag = metadataService.isExistMetadata(metadata);
        if (flag > 0) {
            msg = "元数据id或则元数据名称已经存在";
        } else {
            try {
                flag = metadataService.addMetadataByCopy(metadata, beCopiedMetaId);
            } catch (Exception err) {
                msg = err.getMessage() + "\n";
            }
            if (flag == 0) {
                msg = msg + "复制添加元数据标识失败, 请稍后重试!";
            }
        }
        return new ResponseEntity<>(msg.getBytes(), HttpStatus.OK);
    }

    /**
     * 获取数据配置欢迎页面
     *
     * @param request
     * @return
     */

    @RequestMapping(value = "showWelcomePage", method = RequestMethod.GET)
    public ModelAndView getWelcomePage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
        String metadataId = request.getParameter("metadataId");
        Metadata metadata = metadataService.getMetadataById(Long.parseLong(metadataId));
        String upChainStr = metadata.getUpChain();
        String downChainStr = metadata.getDownChain();
        List<Chain> upChain = metadataService.handleChain(upChainStr);
        List<Chain> downChain = metadataService.handleChain(downChainStr);
        modelMap.put("metadata", metadata);
        modelMap.put("upChain", upChain);
        modelMap.put("downChain", downChain);
        mav.addAllObjects(modelMap);
        mav.setViewName("welcome");
        return mav;
    }

    /**
     * 更新上下处理链条
     *
     * @param request
     * @return
     */
    @Permission(name = "update")
    @RequestMapping(value = "updateMetadataChain", method = RequestMethod.POST)
    public ResponseEntity<byte[]> updateMetadataChain(HttpServletRequest request) {
        String metadataId = request.getParameter("metadataId");
        String deployModel = request.getParameter("deployModel");
        String upChain = request.getParameter("upChain");
        String downChain = request.getParameter("downChain");
        boolean upFlag = metadataService.isCurrentChain(upChain);
        boolean downFlag = metadataService.isCurrentChain(downChain);
        if (!upFlag) {
            return new ResponseEntity<>("上行链条有重复模块,请重新选择".getBytes(), HttpStatus.OK);
        }
        if (!downFlag) {
            return new ResponseEntity<>("下行链条有重复模块,请重新选择".getBytes(), HttpStatus.OK);
        }
        Metadata metadata = new Metadata();
        metadata.setId(Long.parseLong(metadataId));
        metadata.setDeployModel(deployModel);
        metadata.setUpChain(upChain);
        metadata.setDownChain(downChain);
        int count = metadataService.updateMetadataChain(metadata);
        String msg = count > 0 ? "更新成功" : "更新失败,请稍后重试!";
        return new ResponseEntity<>(msg.getBytes(), HttpStatus.OK);
    }
}
