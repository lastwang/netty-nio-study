package com.http.demo.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;

import java.io.Closeable;
import java.io.IOException;
import java.util.function.Function;

public class RedisClient implements Closeable {

    private static final Logger log = LoggerFactory.getLogger(RedisClient.class);

    private JedisPool jedisPool;

    private static RedisClient instance = new RedisClient();

    public static RedisClient getInstance() {

        if (instance != null) {
            return instance;
        }

        return instance = new RedisClient();
    }

    private void init() {
        jedisPool = new JedisPool(new GenericObjectPoolConfig(), "127.0.0.1", 6379, Protocol.DEFAULT_TIMEOUT, "123");
    }

    private RedisClient() {
        init();
    }

    public synchronized void restart() {
        try {
            close();
        } catch (IOException e) {
            System.err.println("关闭错误: " + e.getMessage());
        }
        init();
        System.out.println("重启成功!");
    }

    @Override
    public void close() throws IOException {
        if (jedisPool != null) {
            jedisPool.close();
            System.out.println("关闭成功!");
        }
    }

    private <T extends Jedis, R> R exe(Function<Jedis, R> function) {
        try (Jedis jedis = jedisPool.getResource()) {
            return function.apply(jedis);
        } catch (Exception e) {
            log.error("redis操作错误!", e);
        }
        return null;
    }

    public String get(final String key) {
        return exe((e) -> e.get(key));
    }

    public String set(final String key, final String val) {
        return exe((e) -> e.set(key, val));
    }

    public Long incr(final String key) {
        return exe((e) -> e.incr(key));
    }

    public Long desc(final String key) {
        return exe((e) -> e.decr(key));
    }

}
