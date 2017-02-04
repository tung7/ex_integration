package com.tung7.ex.repository.apns;

import com.clevertap.apns.ApnsClient;
import com.clevertap.apns.Notification;
import com.clevertap.apns.NotificationResponse;
import com.clevertap.apns.NotificationResponseListener;
import com.clevertap.apns.clients.ApnsClientBuilder;
import net.sf.json.JSONObject;
import okhttp3.internal.Util;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author Tung
 * @version 1.0
 * @date 2016/12/16
 * @update 8.1.3.v20150130
 */
public class ApnsTest {
    public volatile boolean isFin = false;

    private String topic = "com.KND.test.debug";  // bundle id
//    private String deviceToken = "cf3dab7648df6313c85bb51166de94e4775043feec9daaa368944a42112f5d99"; //guanghui
    private String deviceToken = "b09540ae9ff106ee77dfd2fec44bad3a7c8b52934f7a1c05c66d01ffac7d1df4"; //ipad
    private String apnsAuthKey = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgkd3o12oSOSDgZiX1hsQpFVfFby6/43+w/6czk7pQP1KgCgYIKoZIzj0DAQehRANCAASQqxzm0MqGGWJvT3WM1ykwUPet4cIxeb3anM5n5s8O0UbW5L9ACpiMQPJIxm+LMOLfdmhUXIqc5Hy2nY+Xkl8i";
    private String keyID = "62654WNT7L";
    private String teamID = "TKZ6NJZD4M";

    private String certPath = "E:\\push_cert.p12"; //p12
    private String password = "123";

    private   int num = 0;

    @Test
    public void test() throws InterruptedException {


         /* build ApnsClient  */
        final long allBefore = System.currentTimeMillis();
        final ApnsAuthTokenBean bean = new ApnsAuthTokenBean(apnsAuthKey, teamID, keyID, topic);
        final ApnsClient client = buildClientUsingToken(bean);

//        Notification n = new Notification.Builder(deviceToken)
//                .badge(1)
//                .sound("msg.mp3")
//                .alertBody("MSG-TEST-" + i).build();
//        NotificationResponse response =  client.push(n);
//        System.out.println(JSONObject.fromObject(response).toString());

//        ExecutorService es = new ThreadPoolExecutor(10, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
//                new SynchronousQueue<Runnable>(), ApnsTest.threadFactory("Apns-HTTP2 Dispatcher", false));
        ExecutorService es = new ThreadPoolExecutor(10, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10000), ApnsTest.threadFactory("Apns-HTTP2 Dispatcher", false));

        final Random r = new Random(1000);
        System.out.println("============= start ============");


        Runnable task = new Runnable() {
            @Override
            public void run() {
                    /* build Notification  */
                    Notification n = new Notification.Builder(deviceToken)
                            .badge(1)
                            .sound("msg.mp3")
                            .alertBody("MSG-TEST-" + r.nextInt()).build();

                    /* push  */
                    client.push(n, new NotificationResponseListener() {
                        @Override
                        public void onSuccess(Notification notification) {
                            System.out.println("success! : " + JSONObject.fromObject(notification).toString());
                            System.out.println("all Used: " + (System.currentTimeMillis() - allBefore) + "ms");
                        }

                        @Override
                        public void onFailure(Notification notification, NotificationResponse nr) {
                            System.out.println("failure: \n" + nr);
                            System.out.println("all Used: " + (System.currentTimeMillis() - allBefore) + "ms");
                        }
                    });
            }
        };

        while (num < 100) {
            es.execute(task);
            num++;
        }

        while (true) {
            Thread.sleep(1000L);
        }
    }

    public ApnsClient buildClientUsingToken(ApnsAuthTokenBean bean) {
        ApnsClient client = null;
        try {
            client = new ApnsClientBuilder()
                    .inAsynchronousMode()
//                    .inSynchronousMode()
                    .withProductionGateway()
                    .withApnsAuthKey(bean.getApnsAuthKey())
                    .withTeamID(bean.getTeamID())
                    .withKeyID(bean.getKeyID())
                    .withDefaultTopic(bean.getDefaultTopic())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return client;
    }


    public ApnsClient buildClientUsingCert() {
        ApnsClient client = null;
        try {
            FileInputStream cert = new FileInputStream(certPath);
            client = new ApnsClientBuilder()
                    .withProductionGateway()
                    .inSynchronousMode()
                    .withCertificate(cert)
                    .withPassword(password)
                    .withDefaultTopic(topic)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return client;
    }

    public static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            @Override public Thread newThread(Runnable runnable) {
                Thread result = new Thread(runnable, name);
                result.setDaemon(daemon);
                return result;
            }
        };
    }
}
