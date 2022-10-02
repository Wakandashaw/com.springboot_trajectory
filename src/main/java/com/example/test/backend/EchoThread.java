package com.example.test.backend;

import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

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
