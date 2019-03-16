package com.lzz.studydemo.http.socket;

import com.lzz.studydemo.Logger;

import java.io.IOException;
import java.net.Socket;

public class Client {

    private int port;//端口号
    private String address = null;//地址
    private Socket socket;
    private ClientThread clientThread;


    public Client() {
    }

    /**
     * 初始化
     *
     * @param port    端口
     * @param address 地址
     */
    public Client(int port, String address) {
        this.port = port;
        this.address = address;
    }

    public boolean start() {

        try {
            socket = new Socket(address, port);
            if (socket.isConnected()) {
                clientThread = new ClientThread(socket);
                clientThread.start();
                Logger.e("socket链接成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 直接通过client得到读线程
    public ClientInputThread getClientInputThread() {
        return clientThread.getIn();
    }

    // 直接通过client得到写线程
    public ClientOutputThread getClientOutputThread() {
        return clientThread.getOut();
    }

    // 直接通过client停止读写消息
    public void setIsStart(boolean isStart) {
        clientThread.getIn().setStart(isStart);
        clientThread.getOut().setStart(isStart);
    }


    public class ClientThread extends Thread {

        private ClientInputThread clientInputThread;//读线程
        private ClientOutputThread clientOutputThread;//写线程

        public ClientThread(Socket socket) {
            clientInputThread = new ClientInputThread(socket);
            clientOutputThread = new ClientOutputThread(socket);
        }

        @Override
        //开启读线程和写线程
        public void run() {
            clientInputThread.setStart(true);
            clientOutputThread.setStart(true);
            clientInputThread.start();
            clientOutputThread.start();
        }


        // 得到读消息线程
        public ClientInputThread getIn() {
            return clientInputThread;
        }

        // 得到写消息线程
        public ClientOutputThread getOut() {
            return clientOutputThread;
        }
    }
}
