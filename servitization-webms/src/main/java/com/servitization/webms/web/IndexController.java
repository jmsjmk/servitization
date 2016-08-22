package com.servitization.webms.web;

import com.alibaba.fastjson.JSON;
import com.servitization.webms.common.CookieUtil;
import com.servitization.webms.entity.Metadata;
import com.servitization.webms.service.IMetadataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("webms")
public class IndexController {

    @Resource
    private IMetadataService metadataService;

    @RequestMapping(value = "views/{page}", method = RequestMethod.GET)
    public ModelAndView page(@PathVariable String page) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(page);
        return mav;
    }

    @RequestMapping(value = "getMetadataList", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getMetadataList() {
        Map<String, Integer> params = new HashMap<>();
        params.put("pageIndex", 0);
        params.put("pageSize", Integer.MAX_VALUE);
        List<Metadata> list = metadataService.getMetadataList(params);
        if (list == null) {
            list = new ArrayList<>();
        }
        return new ResponseEntity<>(JSON.toJSONString(list).getBytes(), HttpStatus.OK);
    }

    @RequestMapping(value = "getDataConfigPage", method = RequestMethod.GET)
    public ModelAndView getDataConfigPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dataconfig");
        return mav;
    }

    @RequestMapping(value = "getVersionAndMachinePage", method = RequestMethod.GET)
    public ModelAndView getVersionAndMachinePage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("versionandmachine");
        return mav;
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/loginout", method = RequestMethod.GET)
    public String loginOut(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = CookieUtil.getCookieFromLocalByName(request, "utoken");
        if (cookie != null) {
            cookie.setDomain(".");
            cookie.setPath("/");
            cookie.setMaxAge(-3);
            response.addCookie(cookie);
        }
        return "requestAosLoginView";
    }
}
