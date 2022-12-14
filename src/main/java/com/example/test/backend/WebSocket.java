package com.example.test.backend;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.*;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;


@ServerEndpoint("/websocket")
@Component
public class WebSocket {
    private static int onlineCount = 0;
    protected ServerSocket serverSocket;
    //用来存放每个客户端对应的MyWebSocket对象。
    //与某个客户端的连接会话，需要通过它来给客户端发送数据

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println("开始建立了链接...");
        System.out.println("当前session的id是：" + session.getId());
        addOnlineCount();

        getLocation(session);
    }

    @OnClose
    public void onClose(Session session) throws IOException{
        System.out.println("当前session的id是：" + session.getId());
        System.out.println("网页关闭");
        serverSocket = null;
    }

    private synchronized void getLocation(Session session) throws IOException {
        try{
            //创建一个服务器对象，端口8001
            serverSocket=new ServerSocket(8001);
            //创建一个客户端对象，这里的作用是用作多线程，必经服务器服务的不是一个客户端
            Socket client=null;
            //accept是阻塞式方法
            while (true){
                System.out.println("服务器已启动，等待客户端请求。。。。");
                client = serverSocket.accept();
                //创建一个线程，每个客户端对应一个线程
                new Thread(new EchoThread(client,session)).start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }


}

