package com.servitization.webms.web;

import com.servitization.webms.service.IMetadataBootService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "webms/boot")
public class BootController {
    @Resource
    private IMetadataBootService metadataBootService;

    /**
     * 启动支持
     *
     * @return
     */
    @RequestMapping(value = "bootScan", method = RequestMethod.GET)
    public ResponseEntity<byte[]> bootScan() {
        String msg = StringUtils.EMPTY;
        int result = metadataBootService.bootScan();
        if (result == 0) {
            msg = "启动成功";
        } else if (result == 1) {
            msg = "启动异常";
        } else if (result == 2) {
            msg = "查询AOS节点信息异常";
        } else if (result == 3) {
            msg = "部分机器推送失败,请检查";
        }
        return new ResponseEntity<>(msg.getBytes(), HttpStatus.OK);
    }
}
