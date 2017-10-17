package http;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/4/14.
 * @update
 */

public class SocketProxy {
    static final int listenPort=8002;

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        ServerSocket serverSocket = new ServerSocket(listenPort);
        final ExecutorService tpe= Executors.newCachedThreadPool();
        System.out.println("Proxy Server Start At "+sdf.format(new Date()));
        System.out.println("listening port:"+listenPort+"……");
        System.out.println();
        System.out.println();

        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                socket.setKeepAlive(true);
                //加入任务列表，等待处理
                tpe.execute(new ProxyTask(socket));
                System.out.println(tpe.isTerminated());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
