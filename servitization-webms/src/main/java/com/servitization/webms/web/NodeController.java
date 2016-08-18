package com.servitization.webms.web;

import com.servitization.commons.permission.annotation.Permission;
import com.servitization.webms.entity.MetadataNode;
import com.servitization.webms.mapper.MetadataPublishIpMapper;
import com.servitization.webms.mapper.MetadataPublishMapper;
import com.servitization.webms.service.IMetadataNodeService;
import com.servitization.webms.util.adapter.BaseObserver;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
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
@RequestMapping(value = "webms/node")
public class NodeController extends BaseObserver {

    @Resource
    private IMetadataNodeService metadataNodeService;
    @Resource
    private MetadataPublishMapper publishMapper;
    @Resource
    private MetadataPublishIpMapper publishIpMapper;

    private DefaultHttpClient httpClient = new DefaultHttpClient();

    private static final Logger LOG = Logger.getLogger(NodeController.class);

    /**
     * 展示aos管理页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getNodePage", method = RequestMethod.GET)
    public ModelAndView getNodePage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
        mav.setViewName("node");

        String metadataId = request.getParameter("metadataId");

        if (StringUtils.isBlank(metadataId)) {
            return mav;
        }

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
        params.put("pageIndex", pageIndex * pageSize);
        params.put("pageSize", pageSize);

        List<MetadataNode> metadataNodes = metadataNodeService.getMetadataNodeList(params);
        if (metadataNodes == null) {
            metadataNodes = new ArrayList<>();
        }

        int count = metadataNodeService.getMetadataNodeCount(params);
        int pageCount = count == 0 ? 0 : (count % pageSize == 0 ? count / pageSize : count / pageSize + 1);

        HttpGet get = new HttpGet(
                "http://aos.corp.elong.com/sag/api/node/descendants/43536?secret=9d63d35f55d5b0ee&authkey=b1a3aa52a45b4d67");
        String resultStr = null;
        /*try {
			HttpResponse result = httpClient.execute(get);
			resultStr = EntityUtils.toString(result.getEntity());
		} catch (Exception e) {
			LOG.error("getTree httpget error#", e);
		}*/

        modelMap.put("pageIndex", pageIndex);
        modelMap.put("pageSize", pageSize);
        modelMap.put("pageCount", pageCount);
        modelMap.put("metadataNodes", metadataNodes);
        modelMap.put("tree", resultStr);

        mav.addAllObjects(modelMap);

        return mav;
    }


    /**
     * 显示发布页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getNodePageNew", method = RequestMethod.GET)
    public ModelAndView getNodePageNew(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
        mav.setViewName("nodeNew");

        String metadataId = request.getParameter("metadataId");

        if (StringUtils.isBlank(metadataId)) {
            return mav;
        }

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
        params.put("pageIndex", pageIndex * pageSize);
        params.put("pageSize", pageSize);

        List<MetadataNode> metadataNodes = metadataNodeService.getMetadataNodeList(params);
        if (metadataNodes == null) {
            metadataNodes = new ArrayList<>();
        }

        int count = metadataNodeService.getMetadataNodeCount(params);
        int pageCount = count == 0 ? 0 : (count % pageSize == 0 ? count / pageSize : count / pageSize + 1);

        String resultStr = null;

        modelMap.put("pageIndex", pageIndex);
        modelMap.put("pageSize", pageSize);
        modelMap.put("pageCount", pageCount);
        modelMap.put("metadataNodes", metadataNodes);
        modelMap.put("tree", resultStr);

        mav.addAllObjects(modelMap);

        return mav;
    }


    /**
     * 删除关联的节点
     *
     * @param request
     * @param response
     * @return
     */
    @Permission(name = "update")
    @RequestMapping(value = "delNode", method = RequestMethod.POST)
    public ResponseEntity<byte[]> delNode(HttpServletRequest request, HttpServletResponse response) {

        String msg = "删除成功";
        String id = request.getParameter("id");

        if (StringUtils.isBlank(id)) {
            msg = "请求参数异常!";
            return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
        }

        int count = metadataNodeService.delNode(Long.parseLong(id));
        msg = count > 0 ? msg : "记录删除失败：没找到相应的记录";

        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }

    /**
     * 添加关联节点
     *
     * @param request
     * @param response
     * @return
     */
    @Permission(name = "update")
    @RequestMapping(value = "addNodes", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addNodes(HttpServletRequest request, HttpServletResponse response) {

        String msg = "添加%d个节点,成功%d个节点";

        String metadataId = request.getParameter("metadataId");
        String ids = request.getParameter("nodeIds");
        if (StringUtils.isBlank(ids)) {
            msg = "请求参数异常!";
            return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
        }

        String[] nodeIds = StringUtils.split(ids, ",");

        int num = metadataNodeService.addNodes(nodeIds, Long.parseLong(metadataId));

        msg = String.format(msg, nodeIds.length, num);
        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }

    @Override
    public void deleteMetadata(List<String> metadataId) {
        metadataNodeService.deleteNodeRelationByMetadataid(metadataId);
        publishMapper.deletePublicData();
        publishIpMapper.deletePublicIp();
    }

    @Override
    public String getHandlerName() {
        return "nodeHandler";
    }

}
