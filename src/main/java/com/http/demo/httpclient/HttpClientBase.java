package com.http.demo.httpclient;

import com.http.demo.jedis.RedisClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpClientBase {

    public HttpClientBase() {
    }

    private void init() {
//        CloseableHttpClient minimal = HttpClients.createMinimal();
    }

    public static void main(String[] args) throws IOException {
        HttpClientBase httpClientBase = new HttpClientBase();
        httpClientBase.test();
    }

    public void test() throws IOException {

//        RedisFactory redisFactory = RedisFactory.getInstance();

        ExecutorService executorService = Executors.newFixedThreadPool(8);

        final CountDownLatch countDownLatch = new CountDownLatch(5000);
        CloseableHttpClient httpClient = new DefaultHttpClient();
        try (RedisClient redisClient = RedisClient.getInstance()) {
            HttpPost post = new HttpPost("http://127.0.0.1:8012/test");
            System.out.println("开始！");
            for (int i = 0; i < 5000; i++) {
                executorService.execute(() -> {
                    try {
                        CloseableHttpResponse execute = httpClient.execute(post);


                        String msg = EntityUtils.toString(execute.getEntity());
                        CloseableHttpResponse execute1 = httpClient.execute(post);
                        String msg2 = EntityUtils.toString(execute1.getEntity());
                        redisClient.incr(msg);
                        redisClient.incr(msg2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                        countDownLatch.countDown();
                    }

                });
            }
            countDownLatch.await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
            executorService.shutdown();
//            try {
//                httpClient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
        System.out.println("推出");

    }
}
