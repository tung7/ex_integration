package com.tung7.ex.repository.apns;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/5/8.
 * @update
 */

public class SimpleApns {
    private static final String CERT_PASS = "coracle2017";
    private static final String CERT_PATH = "f://push.crm.p12";
    private static final String TOKEN = "";
    private static final String ALERT_BODY = "HI";

    public static void main(String[] args) {
        ApnsService service = APNS.newService()
                        .withCert(CERT_PATH, CERT_PASS)
                        .withProductionDestination()
                        .build();

        String payload = APNS.newPayload().alertBody(ALERT_BODY).build();
        service.push(TOKEN, payload);

        // 检查是否feedback 列表， 查看发送是否成功。
        Map<String, Date> inactiveDevices = service.getInactiveDevices();
        for (String deviceToken : inactiveDevices.keySet()) {
            Date inactiveAsOf = inactiveDevices.get(deviceToken);
            System.out.println("fail: ==== token: " + deviceToken + " time :" + new SimpleDateFormat("HH:mm:ss SSS").format(inactiveAsOf));
        }
    }
}
