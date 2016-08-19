package com.servitization.metadata.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *	mobileapi(/mobileapi-根节点,内容: "NONE")
 *	│
 *	├──1.servitization(/servitization-二级节点，内容:"NONE")
 *	│    	│                            
 *	│       ├──1.1.boot(/boot-启动节点,内容："NONE")
 *	│		│                             
 *	│		├──1.2.status(/status-状态节点,内容："NONE")  
 *	│		│ 
 *	│		├──1.3.push(/push-推送节点,内容："NONE")
 */
public class ZKBaseStructureBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(ZKBaseStructureBuilder.class);

    public static void buildBaseStructure() {
        ZooKeeper zk = ZKConnection.zk();
        if (zk == null)
            LOG.error("Can't create the zk connection!");
        try {
            if (zk.exists(Constants.root, false) == null)
                zk.create(Constants.root, Constants.NONE, Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);

            if (zk.exists(Constants.root + Constants.trunk, false) == null)
                zk.create(Constants.root + Constants.trunk, Constants.NONE,
                        Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            if (zk.exists(Constants.boot, false) == null)
                zk.create(Constants.boot, Constants.NONE, Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);

            if (zk.exists(Constants.status, false) == null)
                zk.create(Constants.status, Constants.NONE,
                        Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            if (zk.exists(Constants.push, false) == null)
                zk.create(Constants.push, Constants.NONE, Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
        } catch (Exception e) {
            LOG.error("Build the base structure failed!", e);
        }
    }
}
