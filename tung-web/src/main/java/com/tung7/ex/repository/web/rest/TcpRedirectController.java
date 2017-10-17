package com.tung7.ex.repository.web.rest;

import com.tung7.ex.repository.web.util.TransPortData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/3/28.
 * @update
 */
@Controller
public class TcpRedirectController {
    private transient static Logger log = LoggerFactory.getLogger(TcpRedirectController.class);
    public static ExecutorService clientDetector = Executors.newSingleThreadExecutor();
    public static volatile boolean detectorRunning = true;

    private static class Task implements Runnable{
        private Socket socket;
        public Task (Socket clientSocket) {
            super();
            this.socket = clientSocket;
        }

        @Override
        public void run() {
            while (detectorRunning) {
                System.out.println("isClosed:" + socket.isClosed());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * TCP 连接转发。
     * @param args
     */
    public static void main(String[] args) {
        try {
            if (args == null || args.length < 3) {
                log.error("输出参数不能为空，分别是 本地监听端口、远程IP、远程端口");
                return;
            }
            //获取本地监听端口、远程IP和远程端口
            int localPort = Integer.parseInt(args[0].trim());
            String remoteIp = args[1].trim();
            int remotePort = Integer.parseInt(args[2].trim());
            //启动本地监听端口
            ServerSocket serverSocket = new ServerSocket(localPort);
            log.debug("localPort=" + localPort + ";remoteIp=" + remoteIp +
                    ";remotePort=" + remotePort + ";启动本地监听端口" + localPort + "成功！");

            while (true) {
                Socket clientSocket = null;
                Socket remoteServerSocket = null;
                try {
                    //获取客户端连接
                     clientSocket = serverSocket.accept();
                    log.debug("accept one client");

//                    clientDetector.execute(new Task(clientSocket));

                    //建立远程连接
                    remoteServerSocket = new Socket(remoteIp, remotePort);
                    log.debug("create remoteip and port success");
                    //启动数据转换接口
                    new TransPortData(clientSocket, remoteServerSocket, "1").start();
                    new TransPortData(remoteServerSocket, clientSocket, "2").start();
                } catch (Exception ex) {
                    log.error("", ex);
                }
                //建立连接远程
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

}
