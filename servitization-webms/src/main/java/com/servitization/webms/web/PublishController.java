package com.servitization.webms.web;

import com.servitization.commons.permission.annotation.Permission;
import com.servitization.webms.entity.MetadataPublish;
import com.servitization.webms.entity.MetadataPublishIp;
import com.servitization.webms.entity.MetadataVersion;
import com.servitization.webms.service.IMetadataNodeService;
import com.servitization.webms.service.IMetadataPublishService;
import com.servitization.webms.service.IMetadataService;
import com.servitization.webms.service.IMetadataVersionService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "webms/publish")
public class PublishController {

    @Resource
    private IMetadataVersionService metadataVersionService;

    @Resource
    private IMetadataNodeService metadataNodeService;

    @Resource
    private IMetadataService metadataService;

    @Resource
    private IMetadataPublishService metadataPublishService;

    /**
     * 发布选择版本
     *
     * @param request
     * @param response
     */
    @Permission(name = "push")
    @RequestMapping(value = "publishMetadataSelectVersion", method = RequestMethod.GET)
    public ModelAndView publishMetadataSelectVersion(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
        mav.setViewName("version_select");
        String msg = StringUtils.EMPTY;
        String metadataId = request.getParameter("metadataId");
        String aosRelationId = request.getParameter("aosRelationId");
        String aosNodeId = request.getParameter("aosNodeId");
        if (StringUtils.isBlank(metadataId)) {
            msg = "metadataId不能为空";
            modelMap.put("errorMsg", msg);
        }
        if (StringUtils.isBlank(aosRelationId)) {
            msg = "aosRelationId不能为空";
            modelMap.put("errorMsg", msg);
        }
        if (StringUtils.isBlank(aosNodeId)) {
            msg = "aosNodeId不能为空";
            modelMap.put("errorMsg", msg);
        }
        int pageIndex = 0;
        int pageSize = Integer.MAX_VALUE;

        Map<String, Object> params = new HashMap<>();
        params.put("metadataId", metadataId);
        params.put("pageIndex", pageIndex * pageSize);
        params.put("pageSize", pageSize);

        List<MetadataVersion> metadataVersions = metadataVersionService.getMetadataVersionList(params);
        if (metadataVersions == null) {
            metadataVersions = new ArrayList<>();
        }
        int selectedId = metadataPublishService.getPublishedVersionIdByRelationId(Long.parseLong(aosRelationId));
        modelMap.put("metadataVersions", metadataVersions);
        modelMap.put("selectedId", selectedId);
        modelMap.put("aosRelationId", aosRelationId);
        modelMap.put("aosNodeId", aosNodeId);
        mav.addAllObjects(modelMap);
        return mav;
    }

    /**
     * 发布数据推动到zk节点 push下面去
     * <p>
     * 1.发布数据<br/>
     * <p>
     * 2.
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "publish", method = RequestMethod.GET)
    public ResponseEntity<byte[]> publish(HttpServletRequest request, HttpServletResponse response) {
        String msg = StringUtils.EMPTY;
        String metadataId = request.getParameter("metadataId");
        String nodeRelationId = request.getParameter("nodeRelationId");
        String versionId = request.getParameter("versionId");
        String nodeId = request.getParameter("nodeId");
        if (StringUtils.isBlank(metadataId) && StringUtils.isBlank(nodeRelationId) && StringUtils.isBlank(versionId)
                && StringUtils.isBlank(nodeId)) {
            msg = "参数不正确!";
            return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
        }
        // 调用
        int result = metadataPublishService.publish(Integer.parseInt(nodeId), Integer.parseInt(nodeRelationId),
                Integer.parseInt(metadataId), Integer.parseInt(versionId));
        if (result == 0) {
            msg = "发布成功";
            metadataPublishService.updataPublishState();
        } else if (result == 1) {
            msg = "调用AOS失败";
        } else if (result == 2) {
            msg = "推送失败";
        }
        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }

    /**
     * 新版发布方法<br/>
     * 1.数据库表维护记录关系,并将数据推送到zookeeper<br/>
     * 2.线程池维护插入表的对应关系<br/>
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "publishnew", method = RequestMethod.GET)
    public ResponseEntity<byte[]> publishNew(HttpServletRequest request, HttpServletResponse response) {
        String msg = StringUtils.EMPTY;
        String versionId = request.getParameter("versionId");
        String metadataId = request.getParameter("metadataId");
        if (StringUtils.isBlank(versionId)) {
            msg = "参数不正确!versionId[" + versionId + "]";
            return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
        }
        // 1.
        int result = metadataPublishService.publishNew(versionId, metadataId);

        if (result == 0) {
            msg = "发布数据版本[" + versionId + "]成功";
            System.out.println("********" + msg);
            System.out.println("********msgLength:" + msg.getBytes());
            // 2.
            metadataPublishService.updataPublishState();
        } else if (result == 1) {
            msg = "调用AOS失败";
        } else if (result == 2) {
            msg = "推送失败";
        } else if (result == 3) {
            msg = "Metadata(Node) 没有对应的机器列表请先维护机器列表";
        }
        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }


    @RequestMapping(value = "getPublishHistory", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getPublishHistory(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
        mav.setViewName("publishHistory");

        String pageIndexStr = request.getParameter("pageIndex");
        String pageSizeStr = request.getParameter("pageSize");
        String metadataIdStr = request.getParameter("metadataId");

        int pageIndex = 0;
        int pageSize = 10;
        long metadataId = 0;
        if (StringUtils.isNotBlank(pageIndexStr)) {
            pageIndex = Integer.parseInt(pageIndexStr);
        }
        if (StringUtils.isNotBlank(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (StringUtils.isNotBlank(metadataIdStr)) {
            metadataId = Integer.parseInt(metadataIdStr);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("metadataId", metadataId);
        params.put("pageIndex", pageIndex * pageSize);
        params.put("pageSize", pageSize);

        List<MetadataPublish> publishHistory = metadataPublishService.getPublishHistoryByNodeRelationId(params);
        if (publishHistory == null) {
            publishHistory = new ArrayList<>();
        }

        int count = metadataPublishService.getPublishHistoryCount(params);
        int pageCount = count == 0 ? 0 : (count % pageSize == 0 ? count / pageSize : count / pageSize + 1);

        modelMap.put("pageIndex", pageIndex);
        modelMap.put("pageSize", pageSize);
        modelMap.put("pageCount", pageCount);
        modelMap.put("publishHisyory", publishHistory);

        mav.addAllObjects(modelMap);

        return mav;
    }

    @RequestMapping(value = "getPublishDetail", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getPublishDetail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
        mav.setViewName("publishDetail");

        String publishIdStr = request.getParameter("publishId");
        String nodeRelationId = request.getParameter("nodeRelationId");

        long publishId = 0;
        if (StringUtils.isNotBlank(publishIdStr)) {
            publishId = Long.parseLong(publishIdStr);
        }
        List<MetadataPublishIp> publishDetail = metadataPublishService.getPublishDetail(publishId);
        if (publishDetail == null) {
            publishDetail = new ArrayList<>();
        }

        modelMap.put("publishDetail", publishDetail);
        modelMap.put("nodeRelationId", nodeRelationId);

        mav.addAllObjects(modelMap);
        return mav;
    }
}
