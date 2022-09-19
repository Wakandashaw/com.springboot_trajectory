# 实时轨迹展示平台

本平台基于Web开发，需要将实时定位的结果展示在平台上，并画出行驶路径。

后端主要完成两个功能：

1. 监听移动站发来的坐标信息
2. 将坐标信息通过websocket向前端推送

前端进行展示

**注：目前只支持一个展示一个路径**

# 1 Prerequisites

## 1.1 后端

- Spring Boot

- JDK 18
- IDEA 2022

## 1.2 前端

- 前端采用高德地图API
- 图层使用WGS-84坐标系下的谷歌图层

高德地图API参考资料https://lbs.amap.com/demo/amap-ui/demos/amap-ui-pathsimplifier/simple-demo



# 2 Describe

## 2.1 后端

采用Spring boot 框架进行搭建，网上有基本教程整理不做过多描述。通过Java的**severSocket.accept**阻塞方式，给每一个接收机请求创建一个线程。详细请参考WebSocket.java内容。当接收到消息后就推送给前端。



