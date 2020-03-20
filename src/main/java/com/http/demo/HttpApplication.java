package com.http.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HttpApplication {

	public static void main(String[] args) {
		SpringApplication.run(HttpApplication.class, args);


//		try {
//			Settings settings = Settings.builder().put("cluster.name", "elastic").build(); // 自动嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中
//
//			TransportClient transPort = new PreBuiltTransportClient(settings);
//			TransportAddress transportAddress = new TransportAddress(InetAddress.getByName("192.168.107.128"), 9300);
//			transPort.addTransportAddress(transportAddress);
//			ClusterHealthRequest re = new ClusterHealthRequest();
//			ActionFuture<ClusterHealthResponse> health =
//					transPort.admin().cluster().health(re);
//
//			ClusterHealthStatus status = health.actionGet().getStatus();
//			System.out.println("192.168.107.128 es tcp 9300 连接结果:" + status);
//
//			transPort.close();
//			System.out.println("成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("192.168.107.128 error");
//		}
//
//		try {
//			Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build(); // 自动嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中
//
//			TransportClient transPort = new PreBuiltTransportClient(settings);
//			TransportAddress transportAddress = new TransportAddress(InetAddress.getByName("20.26.17.228"), 9309);
//			transPort.addTransportAddress(transportAddress);
//			ClusterHealthRequest re = new ClusterHealthRequest();
//			ActionFuture<ClusterHealthResponse> health =
//					transPort.admin().cluster().health(re);
//
//			ClusterHealthStatus status = health.actionGet().getStatus();
//			System.out.println("20.26.17.228 es tcp 9309 连接结果:" + status);
//
//			transPort.close();
//			System.out.println("成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("20.26.17.228 error");
//		}
	}

//
//		try {
//			Settings settings = Settings.builder().put("cluster.name", "elasticsearch_cluster").build(); // 自动嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中
//
//			TransportClient transPort = new PreBuiltTransportClient(settings);
//			TransportAddress transportAddress = new TransportAddress(InetAddress.getByName("20.26.31.6"), 24147);
//			transPort.addTransportAddress(transportAddress);
//			ClusterHealthRequest re = new ClusterHealthRequest();
//			ActionFuture<ClusterHealthResponse> health =
//					transPort.admin().cluster().health(re);
//
//			ClusterHealthStatus status = health.actionGet().getStatus();
//			System.out.println("20.26.31.6 es tcp 24147 连接结果:"+status);
//
//			transPort.close();
//			System.out.println("成功");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			System.out.println("20.26.31.6:24147 连接失败");
//		}
//
//		try {
//			Settings settings = Settings.builder().put("cluster.name", "elasticsearch_cluster").build(); // 自动嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中
//
//			TransportClient transPort = new PreBuiltTransportClient(settings);
//			TransportAddress transportAddress = new TransportAddress(InetAddress.getByName("20.26.31.6"), 24101);
//			transPort.addTransportAddress(transportAddress);
//			ClusterHealthRequest re = new ClusterHealthRequest();
//			ActionFuture<ClusterHealthResponse> health =
//					transPort.admin().cluster().health(re);
//
//			ClusterHealthStatus status = health.actionGet().getStatus();
//			System.out.println("20.26.31.6 es tcp 24101连接结果:"+status);
//
//			transPort.close();
//			System.out.println("成功");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			System.out.println("20.26.31.6:24101 连接失败");
//		}
//    }

}
