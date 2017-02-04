package com.tung7.ex.repository.apns;

import com.clevertap.apns.CertificateUtils;
import com.clevertap.apns.Notification;
import com.clevertap.apns.internal.Constants;
import com.clevertap.apns.internal.JWT;
import okhttp3.Protocol;
import okhttp3.internal.framed.FramedConnection;
import okhttp3.internal.framed.FramedStream;
import okhttp3.internal.framed.Header;
import okhttp3.internal.framed.Ping;
import okhttp3.internal.http.Http2xStream;
import okhttp3.internal.http.HttpStream;
import okio.Buffer;
import org.junit.Test;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Tung
 * @version 1.0
 * @date 2016/12/19
 * @update
 */
public class FramedConnectionTest {
    private String host = "api.push.apple.com";
    private int port = 443;
    private String topic = "com.KND.test.debug";  // bundle id
    //    private String deviceToken = "ff050709098f3405c21cc305904ade3661d29eeee3678c068912696ab7436051";
//    private String deviceToken = "c88c3456b61c5e7db0cdae656b9aa3591e77b1fae104b80f4261803b1c517091";
//    private String deviceToken = "bbb2f97b1f83ec693406aab54a9dd53d4411424427ac69856883287029ad8209"; //18000 tung
    private String deviceToken = "cf3dab7648df6313c85bb51166de94e4775043feec9daaa368944a42112f5d99"; // guanghui

    private String apnsAuthKey = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgkd3o12oSOSDgZiX1hsQpFVfFby6/43+w/6czk7pQP1KgCgYIKoZIzj0DAQehRANCAASQqxzm0MqGGWJvT3WM1ykwUPet4cIxeb3anM5n5s8O0UbW5L9ACpiMQPJIxm+LMOLfdmhUXIqc5Hy2nY+Xkl8i";
    private String keyID = "62654WNT7L";
    private String teamID = "TKZ6NJZD4M";
    private String content = "FramedContent:!!!!";

    FramedConnection.Listener lister = new FramedConnection.Listener() {
        @Override
        public void onStream(FramedStream stream) throws IOException {
            System.out.println("==========");
            System.out.println(stream.getErrorCode().httpCode);
        }
    };

    @Test
    public void testFramedConnection() throws Exception {
        Socket socket = createSocket();
        FramedConnection framedConnection = new FramedConnection.Builder(true)
                .socket(socket)
                .protocol(Protocol.HTTP_2)
                .listener(lister)
                .build();
        System.out.println("send ConnectionPreface");
        framedConnection.sendConnectionPreface();
        System.out.println("send ConnectionPreface success");
//      pingFuture = pingService.scheduleAtFixedRate(new PingTask(), 0, PING_PERIOD, TimeUnit.SECONDS);
        /* Bulid Notification */
        System.out.println("Bulid Notification");
        Notification notification = new Notification.Builder(deviceToken)
                .topic(topic)
                .badge(1)
                .sound("msg.mp3")
                .alertBody(content).build();
        System.out.println("Bulid Notification success");

        /* Bulid Header */
        System.out.println("Bulid Header");
        List<Header> headers = new ArrayList<Header>();
        headers.add(new Header("content-type", "application/json"));
        headers.add(new Header(":method", "POST"));
        headers.add(new Header(":path", "/3/device/" + notification.getToken()));
        headers.add(new Header("authority", host));
        headers.add(new Header("authorization", "bearer " + getJWTToken()));
        headers.add(new Header("content-length", notification.getPayload().getBytes(Constants.UTF_8).length+""));

        if (notification.getTopic() != null) {
            headers.add(new Header("apns-topic", notification.getTopic()));
        }

        if (notification.getCollapseId() != null) {
            headers.add(new Header("apns-collapse-id", notification.getCollapseId()));
        }

        System.out.println("Bulid Header success");

        // 创建 stream
        System.out.println("create  stream");
        FramedStream framedStream = framedConnection.newStream(headers, true, true);
        framedStream.readTimeout().timeout(30, TimeUnit.SECONDS);
        framedStream.writeTimeout().timeout(30, TimeUnit.SECONDS);
        System.out.println("create  stream success");

         /* write Body */
        System.out.println("write  Body");
        Buffer buffer = new Buffer();
        byte[] bytes =  notification.getPayload().getBytes(Constants.UTF_8);
        buffer.write(bytes);
        framedStream.getSink().write(buffer, bytes.length);
        framedStream.getSink().flush();
        buffer.clear();
        System.out.println("write  Body success");

        List<Header> resHeaders = new ArrayList<Header>();
        framedStream.reply(resHeaders, false);

        System.out.println("read  source");

        long l = 0L;
        do {
            l = framedStream.getSource().read(buffer, 2048L);
        } while (l!=-1);
       byte[] res =  buffer.readByteArray();

        System.out.println(new String(res, "UTF-8"));

        Ping ping = framedConnection.ping();
        System.out.println(ping.roundTripTime());

        System.out.println("read  source success");
        Thread.sleep(10000L);
    }

    private String getJWTToken() {
            try {
                return JWT.getToken(teamID, keyID, apnsAuthKey);
            } catch (InvalidKeySpecException | NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
                return null;
            }
    }

    private Socket createSocket() throws Exception {

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null,null,null);
        SSLSocketFactory socketFactory = sslContext.getSocketFactory();


        System.out.println("connect socket");
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(host, port));
        System.out.println("socket connected");
        SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket(
                socket, host, port, true);
        sslSocket.setEnabledProtocols(new String[] {"TLSv1.2"});
        sslSocket.setKeepAlive(true);
        System.out.println("start ssl handshake");
        sslSocket.startHandshake();
        System.out.println("handshake success");
        return sslSocket;

    }

    private void intiSSLContext(SSLContext sslContext) throws Exception {
        final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init((KeyStore) null);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        tmf.init((KeyStore) null);
        KeyStore ks = KeyStore.getInstance("PKCS12");
//        kmf.init(ks, password.toCharArray());
//        KeyManager[] keyManagers = kmf.getKeyManagers();
        sslContext.init(null, null, null);
    }
}
