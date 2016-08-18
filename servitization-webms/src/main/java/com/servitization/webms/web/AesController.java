package com.servitization.webms.web;

import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.metadata.define.encrypt.impl.EncryptDefineImpl;
import com.servitization.webms.entity.MetadataModule;
import com.servitization.webms.service.IMetadataAesService;
import com.servitization.webms.util.IPListUtil;
import com.servitization.webms.util.adapter.BaseObserver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "webms/aes")
public class AesController extends BaseObserver {

    @Resource
    private IMetadataAesService metadataAesService;

    @RequestMapping(value = "getWhitelistPage", method = RequestMethod.GET)
    public ModelAndView getWhitelistPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("aes_whitelist");          //
        Map<String, Object> modelMap = new HashMap<>();

        String metadataId = request.getParameter("metadataId");
        Map<String, Object> params = new HashMap<>();
        params.put("metadata_id", Integer.parseInt(metadataId));

        String ips = metadataAesService.getAesWhitelist(params);

        modelMap.put("ips", ips == null ? "" : ips);
        mav.addAllObjects(modelMap);
        return mav;
    }

    @RequestMapping(value = "addOrUpdateWhitelist", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addOrUpdateWhitelist(HttpServletRequest request, HttpServletResponse response) {
        String ips = request.getParameter("ips");
        String metadataId = request.getParameter("metadataId");
        // Format ips
        Set<String> ips_set = null;
        try {
            ips_set = IPListUtil.ipSpliter(ips);
        } catch (Exception e) {
            return new ResponseEntity<byte[]>(e.getMessage().getBytes(), HttpStatus.OK);
        }
        if (ips_set == null)
            ips = "";
        else
            ips = IPListUtil.format(ips_set);
        Map<String, Object> params = new HashMap<>();
        params.put("metadata_id", Integer.parseInt(metadataId));
        String has_ips = metadataAesService.getAesWhitelist(params);
        String msg = "更新成功";
        if (has_ips == null) {
            //insert
            params = new HashMap<>();
            params.put("metadata_id", metadataId);
            params.put("ips", ips);
            params.put("createTime", new Date());
            int rst = metadataAesService.addAesWhitelist(params);
            if (rst <= 0) msg = "添加失败";
        } else {
            //update
            params = new HashMap<>();
            params.put("metadata_id", metadataId);
            params.put("ips", ips);
            int rst = metadataAesService.updateAesWhitelist(params);
            if (rst <= 0) msg = "更新失败";
        }
        return new ResponseEntity<byte[]>(msg.getBytes(), HttpStatus.OK);
    }


    /**
     * 作为观察者，当抽象主题角色删除操作的时候会调用该方法
     */
    @Override
    public void deleteMetadata(List<String> args) {
        metadataAesService.deleteAesWhitelist(args);
    }

    @Override
    public String getHandlerName() {
        return "aESChainHandler";
    }

    @Override
    public void builderXml(long metadataId, MetadataModule module,
                           List<ChainElementDefine> chainList) {
        EncryptDefineImpl define = new EncryptDefineImpl();
        define.setName(module.getHandlerName());
        define.setHandlerClazz(module.getHandlerClazz());
        Map<String, Object> params = new HashMap<>();
        params.put("metadata_id", metadataId);
        String ips = metadataAesService.getAesWhitelist(params);
        if (ips != null && ips.length() > 0) {
            define.setIPWhiteList(IPListUtil.unformat(ips));
        }
        chainList.add(define);
    }

}