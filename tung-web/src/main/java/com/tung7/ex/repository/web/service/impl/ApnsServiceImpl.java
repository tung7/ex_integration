package com.tung7.ex.repository.web.service.impl;

import com.clevertap.apns.ApnsClient;
import com.clevertap.apns.Notification;
import com.clevertap.apns.NotificationResponse;
import com.clevertap.apns.NotificationResponseListener;
import com.clevertap.apns.clients.ApnsClientBuilder;
import com.tung7.ex.repository.apns.ApnsAuthTokenBean;
import com.tung7.ex.repository.web.service.IApnsService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author Tung
 * @version 1.0
 * @date 2016/12/19
 * @update
 */
@Service
public class ApnsServiceImpl implements IApnsService, InitializingBean {
    private static  final String topic = "com.KND.test.debug";  // bundle id
    private static  final String deviceToken = "bbb2f97b1f83ec693406aab54a9dd53d4411424427ac69856883287029ad8209"; //18000 tung
//    private static  final String deviceToken = "31b13c0de7ba5111b60492993b08b436435c3a7417d64d5bd8864cb8f2ceff47"; //18000 tung -guanghui
    private static  final String apnsAuthKey = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgkd3o12oSOSDgZiX1hsQpFVfFby6/43+w/6czk7pQP1KgCgYIKoZIzj0DAQehRANCAASQqxzm0MqGGWJvT3WM1ykwUPet4cIxeb3anM5n5s8O0UbW5L9ACpiMQPJIxm+LMOLfdmhUXIqc5Hy2nY+Xkl8i";
    private static  final String keyID = "62654WNT7L";
    private static  final String teamID = "TKZ6NJZD4M";

    private ApnsClient syncClient;
    private ApnsClient asyncClient;

    @Override
    public String pushInSync(String msg) {
          /* build Notification  */
        Notification n = new Notification.Builder(deviceToken)
                .badge(1)
                .sound("msg.mp3")
                .alertBody(msg).build();
        NotificationResponse response =  syncClient.push(n);
        return JSONObject.fromObject(response).toString();
    }

    public void pushInAsync(String msg) {
        Notification n = new Notification.Builder(deviceToken)
                .badge(1)
                .sound("msg.mp3")
                .alertBody(msg).build();
        final long start = System.currentTimeMillis();

        asyncClient.push(n, new NotificationResponseListener() {
            @Override
            public void onSuccess(Notification notification) {
                System.out.println("success! : " + JSONObject.fromObject(notification.getPayload()) + ", Used " + (System.currentTimeMillis() - start) +  "ms");
            }
            @Override
            public void onFailure(Notification notification, NotificationResponse nr) {
                System.out.println("failure! : " + JSONObject.fromObject(notification.getPayload()) + ", Used " + (System.currentTimeMillis() - start) +  "ms");
            }
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
         /* build ApnsClient  */
        ApnsAuthTokenBean bean = new ApnsAuthTokenBean(apnsAuthKey, teamID, keyID, topic);
        syncClient = new ApnsClientBuilder()
                 .inSynchronousMode()
                .withProductionGateway()
                .withApnsAuthKey(bean.getApnsAuthKey())
                .withTeamID(bean.getTeamID())
                .withKeyID(bean.getKeyID())
                .withDefaultTopic(bean.getDefaultTopic())
                .build();


        asyncClient = new ApnsClientBuilder()
                .inAsynchronousMode()
                .withProductionGateway()
                .withApnsAuthKey(bean.getApnsAuthKey())
                .withTeamID(bean.getTeamID())
                .withKeyID(bean.getKeyID())
                .withDefaultTopic(bean.getDefaultTopic())
                .build();

        Thread hook = new Thread(new Runnable(){
            @Override
            public void run() {
                asyncClient.getHttpClient().getDispatcher().executorService().shutdown();
                asyncClient.getHttpClient().getDispatcher().executorService().shutdownNow();
            }
        });

        Runtime.getRuntime().addShutdownHook(hook);
    }
}
