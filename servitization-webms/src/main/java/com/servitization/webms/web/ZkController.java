package com.servitization.webms.web;

import com.alibaba.fastjson.JSON;
import com.servitization.metadata.zk.Constants;
import com.servitization.metadata.zk.ZKBaseStructureBuilder;
import com.servitization.metadata.zk.ZKConnection;
import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "webms/zk")
public class ZkController {

    private static Logger LOG = Logger.getLogger(ZkController.class);

    /**
     * 获取mapi项目的zk信息
     *
     * @return
     */
    @RequestMapping(value = "getZkList", method = RequestMethod.GET)
    public ModelAndView getMetadataPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("zookeeper");
        return mav;
    }


    @RequestMapping(value = "getZkInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<byte[]> getZkInfo() {
        Map<String, List<ZkNodeInfo>> zkDataMap = null;
        try {
            zkDataMap = new HashMap<>();
            ZKBaseStructureBuilder.buildBaseStructure();
            ZooKeeper zk = ZKConnection.zk();
            List<String> ipList = zk.getChildren(Constants.status, false);
            List<ZkNodeInfo> bootNode = new ArrayList<>();
            List<ZkNodeInfo> statusNode = new ArrayList<>();
            List<ZkNodeInfo> pushNode = new ArrayList<>();
            zkDataMap.put("boot", bootNode);
            zkDataMap.put("status", statusNode);
            zkDataMap.put("push", pushNode);
            ZkNodeInfo zni;
            for (String ip : ipList) {
                String pushInfo;
                String statusInfo;
                try {
                    pushInfo = new String(zk.getData(Constants.push + "/" + ip, null, null));
                } catch (KeeperException e) {
                    e.printStackTrace();
                    pushInfo = e.getMessage();
                }
                try {
                    statusInfo = new String(zk.getData(Constants.status + "/" + ip, null, null));
                } catch (KeeperException e) {
                    e.printStackTrace();
                    statusInfo = e.getMessage();
                }
                zni = new ZkNodeInfo(ip, pushInfo);
                pushNode.add(zni);
                zni = new ZkNodeInfo(ip, statusInfo);
                statusNode.add(zni);
            }
            LOG.info(zkDataMap.toString());
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        byte[] byteArr = JSON.toJSONString(zkDataMap).getBytes();
        return new ResponseEntity<>(byteArr, HttpStatus.OK);
    }

    class ZkNodeInfo {
        public String ip;
        public String info;

        @Override
        public String toString() {
            return "ZkNodeInfo{" +
                    "ip='" + ip + '\'' +
                    ", info='" + info + '\'' +
                    '}';
        }

        public String getInfo() {
            return info;
        }

        public ZkNodeInfo(String ip, String info) {
            this.ip = ip;
            this.info = info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getIp() {
            return ip;
        }
    }
}
