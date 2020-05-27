package com.http.demo.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ZookeeperClient implements AutoCloseable {

    private final String conString;

    private ZooKeeper zooKeeper;

    private final int timeOut;

    private final CountDownLatch latch = new CountDownLatch(1);

    public ZookeeperClient(String conString, int timeOut) throws IOException, InterruptedException {
        this.conString = conString;
        this.timeOut = timeOut;
        init();
    }

    private void init() throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(conString, timeOut, new WatchZK(latch));
        latch.await(10, TimeUnit.SECONDS);
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    private static class WatchZK implements Watcher {
        private final CountDownLatch latch;

        private WatchZK(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void process(WatchedEvent event) {
            if (event.getState() == Event.KeeperState.SyncConnected) {
                latch.countDown();
            }
            System.out.println("启动心跳！");
        }
    }

    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        return zooKeeper.getChildren(path, false);
    }

    private static class WatchPath implements Watcher {
        @Override
        public void process(WatchedEvent event) {
            System.out.println("监听路径！");
        }
    }

    @Override
    public void close() throws Exception {
        if (zooKeeper != null) {
            zooKeeper.close();

            zooKeeper = null;
        }
        System.out.println("zk close");
    }
}
