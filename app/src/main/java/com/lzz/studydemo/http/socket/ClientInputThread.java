package com.lzz.studydemo.http.socket;

import com.example.lzz.studtdemo.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientInputThread extends Thread {


    private Socket socket;
    private DataInputStream inputStream;//对象输入流
    private MessageListener mMessageListener;
    private boolean isStart=true;
    private InputStreamReader iReader;

    public ClientInputThread(Socket socket) {

        this.socket = socket;
        try {
            inputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //设置消息监听对象
    public void setMessageListener(MessageListener messageListener)
    {
        this.mMessageListener = messageListener;
    }

    public void setStart(Boolean isStart)
    {
        this.isStart = isStart;
    }

    @Override
    public void run() {
        Logger.e("socketClientInputThread run");
        try {
            System.out.println("Input isStart: " + isStart);
            while (isStart) {
                //接收从服务器发送过来的消息
                iReader = new InputStreamReader(inputStream, "UTF-8");
                //缓冲数组
                char[] buffer = new char[1024];
                int count = 0;
                StringBuffer sBuilder = new StringBuffer();
                while ((count = iReader.read(buffer,0,buffer.length))>-1) {
                    sBuilder.append(buffer,0,count);
                    if(count<1024)
                    {
                        break;
                    }
                }
                //将消息传递给监听器 （发送给需要的页面）
                mMessageListener.Message(sBuilder.toString());
            }
            if(inputStream !=null)
            {
                inputStream.close();
            }
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
