package com.lzz.studydemo.http.socket;

import com.example.lzz.studtdemo.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ClientOutputThread extends Thread {


    private Socket socket;
    private DataOutputStream outputStream;//输出流
    private String msg;
    private OutputStreamWriter outputStreamWriter;
    private boolean isStart = true;

    public ClientOutputThread(Socket socket) {
        this.socket = socket;

        try {
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 设置发送给服务器的消息
    public void setMsg(String msg) {
        this.msg = msg;
        synchronized (this) {
            notify();
        }
    }

    public void setStart(Boolean isStart) {
        this.isStart = isStart;
    }

    @Override
    public void run() {
        Logger.e("socketClientOutPutThread run");
        try {
            while (isStart) {
                if (msg != null) {
                    if (socket == null)
                        return;

                    outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");

                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(msg);

                    outputStreamWriter.write(stringBuffer.toString());

                    outputStreamWriter.flush();

                    synchronized (this) {
                        wait();// 发送完消息后，线程进入等待状态
                    }

                }

            }

            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
            if (outputStream != null)// 循环结束后，关闭流，释放资源
                outputStream.close();
            // 循环结束后，关闭输出流和socket
            if (socket != null)
                socket.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
