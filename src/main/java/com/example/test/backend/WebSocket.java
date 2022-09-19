package com.example.test.backend;


import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.Session;
import javax.websocket.server.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


@ServerEndpoint("/websocket")
@Component
public class WebSocket {
    private static int onlineCount = 0;
    private String br =null;

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

    private static synchronized void getLocation(Session session) throws IOException {
        //创建一个服务器对象，端口8001
        ServerSocket serverSocket=new ServerSocket(8001);
        //创建一个客户端对象，这里的作用是用作多线程，必经服务器服务的不是一个客户端
        Socket client=null;
        boolean flag=true;

        System.out.println("服务器已启动，等待客户端请求。。。。");
        //accept是阻塞式方法，对新手来说这里很有可能出错，下面的注意事项我会说到
        client=serverSocket.accept();
        //创建一个线程，每个客户端对应一个线程
        new Thread(new EchoThread(client,session)).start();

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

class EchoThread implements Runnable {
    private Socket client;
    private Session session;

    public EchoThread(Socket client, Session session) {
        this.client = client;
        this.session  = session;
    }

    public void run() {
        //run不需要自己去执行，好像是线程器去执行了来着，可以去看api
        try {
            BufferedReader in = null;
            String br = null;
            boolean flag = true;
            while (flag == true) {
                //Java流的操作没意见吧
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                br = in.readLine();
                if(br != null){
                    System.out.println(br);
                    session.getBasicRemote().sendText(br);
                }
            }

        } catch (IOException e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("error");
        }
    }
}