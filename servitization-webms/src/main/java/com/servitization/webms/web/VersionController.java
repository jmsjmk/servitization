package com.servitization.webms.web;

import com.servitization.commons.permission.annotation.Permission;
import com.servitization.metadata.define.XmlSerializer;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.metadata.define.embedder.DeployModel;
import com.servitization.metadata.define.embedder.impl.ServiceDefineImpl;
import com.servitization.webms.entity.Metadata;
import com.servitization.webms.entity.MetadataVersion;
import com.servitization.webms.service.IMetadataService;
import com.servitization.webms.service.IMetadataVersionService;
import com.servitization.webms.util.ConcreteSubject;
import com.servitization.webms.util.adapter.BaseObserver;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.util.*;

@Controller
@RequestMapping(value = "webms/version")
public class VersionController extends BaseObserver {

    @Resource
    private IMetadataVersionService metadataVersionService;

    @Resource
    private IMetadataService metadataService;

    /**
     * 获取版本管理界面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getVersionPage", method = RequestMethod.GET)
    public ModelAndView getVersionPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> modelMap = new HashMap<>();
        mav.setViewName("version");

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

        List<MetadataVersion> metadataVersions = metadataVersionService.getMetadataVersionList(params);
        if (metadataVersions == null) {
            metadataVersions = new ArrayList<>();
        }
        int count = metadataVersionService.getMetadataVersionCount(params);
        int pageCount = count == 0 ? 0 : (count % pageSize == 0 ? count / pageSize : count / pageSize + 1);

        modelMap.put("pageIndex", pageIndex);
        modelMap.put("pageSize", pageSize);
        modelMap.put("pageCount", pageCount);
        modelMap.put("metadataVersions", metadataVersions);

        mav.addAllObjects(modelMap);

        return mav;
    }

    /**
     * 创建新版本元数据
     * <p>
     * 1.获取元数据版本id,版本描述数据 <br/>
     * <p>
     * 2.获取元数据 <br/>
     * <p>
     * 3.ConcreteSubject 获取对象 <br/>
     * 注意此对象中有个数据 observerMap 观察者map, 通过spring的后处理器来进行设置的<br/>
     * 3.1  构造上行链（数据库up_chain字符串构建) <br/>
     * 3.2  构造下行连 （数据库up_chain字符串构建) <br/>
     * 3.3  序列化 <br/>
     * <p>
     * 4.创建 metadata_version数据(插入数据库记录) <br/>
     * 注意:metadata_xml 数据是空的 他在实现方法里面给添加到了<br/>
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Permission(name = "update")
    @RequestMapping(value = "addVersion", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<byte[]> addVersion(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String msg = StringUtils.EMPTY;

        // 1.
        String metadataIdStr = request.getParameter("metadataId");
        long metadataId = Long.parseLong(metadataIdStr);
        String description = request.getParameter("description");

        // 2.
        Metadata metadata = metadataService.getMetadataById(metadataId);
        if (StringUtils.isBlank(metadata.getUpChain()) || StringUtils.isBlank(metadata.getDownChain())) {
            msg = "请先配置完整的上下行链条!";
            return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
        }
        // 3
        ConcreteSubject concreteSubject = ConcreteSubject.instances();
        // 3.1
        List<ChainElementDefine> upDefine = metadataService.chainList(concreteSubject, metadataId,
                metadata.getUpChain());
        // 3.2
        List<ChainElementDefine> downDefine = metadataService.chainList(concreteSubject, metadataId,
                metadata.getDownChain());

        ServiceDefineImpl serviceDefine = new ServiceDefineImpl();

        serviceDefine.setName(metadata.getMetaKey());
        serviceDefine.setUpChainList(upDefine);
        serviceDefine.setDownChainList(downDefine);
        serviceDefine.setDeployModel(DeployModel.valueOf(metadata.getDeployModel()));
        // 3.3
        String xml = XmlSerializer.serialize(serviceDefine);
        if (StringUtils.isBlank(xml)) {
            msg = "生成xml出错,请稍后重试!";
            return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
        }
        // System.out.println("formatXml(xml):" + formatXml(xml));
        // 4
        MetadataVersion version = new MetadataVersion();
        version.setMetadataId(metadataId);
        version.setDescription(description);
        version.setMetadataXml(formatXml(xml));
        version.setCreateTime(new Date());

        int count = 0;
        try {
            count = metadataVersionService.addVersion(version);
        } catch (Exception err) {
            msg = err.getMessage() + "\n";
        }
        msg = msg + (count > 0 ? "添加成功" : "添加失败");

        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }

    /**
     * 获取格式化xml数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "previewVersion", method = RequestMethod.GET)
    public ModelAndView previewVersion(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("previewversion");

        String id = request.getParameter("id");
        MetadataVersion version = metadataVersionService.getMetadataVersionById(Long.parseLong(id));

        // String xml1 = version.getMetadataXml();
        String xml = formatXml(version.getMetadataXml());
        if (StringUtils.isBlank(xml)) {
            xml = "数据有问题!!!";
        }

        mav.addObject("xml", xml);
        return mav;
    }

    @Override
    public void deleteMetadata(List<String> metadataId) {
        metadataVersionService.deleteVersionByMetadataid(metadataId);
        metadataVersionService.deleteXmlVersion();
    }

    /**
     * @param str
     * @return
     */
    public String formatXml(String str) {
        try {
            Document document = null;
            document = DocumentHelper.parseText(str);
            // 格式化输出格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("utf-8");
            StringWriter writer = new StringWriter();
            // 格式化输出流
            XMLWriter xmlWriter = new XMLWriter(writer, format);
            // 将document写入到输出流
            xmlWriter.write(document);
            xmlWriter.close();
            return writer.toString();

        } catch (Exception e) {

        }
        return "";
    }

    @Override
    public String getHandlerName() {
        return "versionHandler";
    }
}
