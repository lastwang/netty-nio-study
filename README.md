# netty-nio-study

##借鉴quartz集群配置

##引入redis为了方便测试性能,主要是计数

##引入数据库mariadb,主要是quartz集群需要

###jdk11 netty启动会有日志级错误 需要加 jvm 参数配置--add-opens java.base/jdk.internal.misc=ALL-UNNAMED -Dio.netty.tryReflectionSetAccessible=true