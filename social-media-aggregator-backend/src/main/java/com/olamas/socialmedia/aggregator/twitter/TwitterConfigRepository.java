package com.olamas.socialmedia.aggregator.twitter;

import com.olamas.socialmedia.aggregator.exception.ConfigServerException;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.utils.EnsurePath;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwitterConfigRepository {

    @Value("${zookeeper.server.host}")
    private String zookeeperHost;

    @Value("${zookeeper.server.base.node}")
    public String zookeeperBaseNode;

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterConfigRepository.class);

    private CuratorFramework curator;

    public TwitterFilter addNewFilter(TwitterFilter filter) throws ConfigServerException {
        this.initConfigServer();
        try {
            curator.blockUntilConnected();
            // Ensure the group node exists
            new EnsurePath(zookeeperBaseNode).ensure(curator.getZookeeperClient());
            // Read users filters

            String userPath = zookeeperBaseNode+"/"+filter.getUserName();

            byte[] bytesData = filter.getFilterText().getBytes();
            CuratorOp curatorOp;
            if(curator.checkExists().forPath(userPath) == null) {
                 curatorOp = curator.transactionOp().create().withMode(CreateMode.PERSISTENT)
                         .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(userPath, bytesData);
            }
            else{
                curatorOp = curator.transactionOp().setData().forPath(userPath, bytesData);
            }
            String result = curator.transaction().forOperations(curatorOp).get(0).toString();
            LOGGER.info("Configuration Node value = {0} was added succesfully", result);
            curator.close();
            LOGGER.info("Closing connection to Configuration Server - zookeeper client - close");
            if (result != null)
                filter.setValidFilter(true);

        }
        catch (Exception e){
            throw new ConfigServerException("Unable to start reading nodes - Config Server error");
        }
        return filter;
    }

    private void initConfigServer(){
        LOGGER.info("Initializing Configuration Server client - zookeeper client - starting");
        curator = CuratorFrameworkFactory.newClient(zookeeperHost, 10000, 2000, new RetryOneTime(2000));
        curator.start();
    }

}
