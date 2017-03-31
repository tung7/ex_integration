package com.tung7.ex.repository.web.util;

import com.tung7.ex.repository.web.rest.TcpRedirectController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * TODO Complete The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/3/28.
 * @update
 */

public class TransPortData extends Thread {
    private transient static Logger log = LoggerFactory.getLogger(TransPortData.class);

    Socket getDataSocket;
    Socket putDataSocket;
    String type;

    public TransPortData(Socket getDataSocket, Socket putDataSocket, String type) {
        this.getDataSocket = getDataSocket;
        this.putDataSocket = putDataSocket;
        this.type = type;
    }

    public void run() {
        try {
            while (true) {
                InputStream in = getDataSocket.getInputStream();
                OutputStream out = putDataSocket.getOutputStream();
                //读入数据
                byte[] data = new byte[1024];
                int readlen = -1;
                try {
                     readlen = in.read(data);
                } catch (Exception e) {
                    if(type.equals("1")) {
                        log.error("从Client获取数据异常");
                    } else {
                        log.error("从Remote获取数据异常");
                    }
                    throw e;
                }
                //如果没有数据，则暂停
                if (readlen <= 0) {
                    Thread.sleep(300);
                    continue;
                }
                try {
                    out.write(data, 0, readlen);
                } catch (Exception e) {
                    if(type.equals("1")) {
                        log.error("写入Remote异常");
                    } else {
                        log.error("写入Client异常");
                    }
                    throw e;
                }
                out.flush();
            }
        } catch (Exception e) {
            log.error("type:" + type);

        } finally {
            //关闭socket
            try {
                if (putDataSocket != null) {
                    putDataSocket.close();
                }
            } catch (Exception exx) {
            }
            try {
                if (getDataSocket != null) {
                    getDataSocket.close();
                }
            } catch (Exception exx) {
            }
        }
    }
}
