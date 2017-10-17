package com.tung7.ex.repository.base;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.*;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @author Tung
 * @version 1.0
 * @date 2017/1/1
 * @update
 */
public class ProcessTest {
    @Test
    public void testAddress() {
        try {
            String spec = "tcp://192.168.8.16:1883/";
            URI uri = new URI(spec);
            System.out.println(uri.getScheme());
            System.out.println(uri.getHost());
            System.out.println(uri.getPort());
            System.out.println(uri.getPath());
            System.out.println("===============================");
            URL u = new URL(spec);
            System.out.println(u.getProtocol());
            System.out.println(u.getHost());
            System.out.println(u.getPort());
            System.out.println(u.getPath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testArray() {
        String ids = "132,sd2 , 1233,14";
        String[] idsStrArr = ids.split(",");
        List<Long> idsList = new ArrayList<>(idsStrArr.length);
        for (int i = 0; i < idsStrArr.length; i++) {
            try {
                Long tmp = Long.parseLong(idsStrArr[i].trim());
                idsList.add(tmp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Long l : idsList) {
            System.out.println(l);
        }
    }

    @Test
    public void testSerial() {
        Serializable numint = "132"; // yes
//        Serializable numint = 123; // exception
        invoke(Integer.parseInt((String) numint));
    }

    public void invoke(int num) {
        System.out.println(num);
    }

    private String sendHttpsNew(String httpUrl, String json, String method, Map<String, String> header, RequestConfig requestConfig) {
        String result = "";
        CloseableHttpClient httpClient = getHttpClient();

        HttpRequestBase request = null;
        HttpResponse response = null;
        try {
//            if ("get".equalsIgnoreCase(method)) {
//                request = new HttpGet(httpUrl);
//            } else {
//                HttpPost post = new HttpPost(httpUrl);
//                if (!(json == null || json.length() == 0)) {
//                    StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
//                    entity.setContentEncoding("UTF-8");
//                    entity.setContentType("application/json; charset=UTF-8");
//                    post.setEntity(entity);
//                }
//                request = post;
//            }
//            if (header != null && !header.isEmpty()) {
//                for (Map.Entry<String, String> entry : header.entrySet()) {
//                    String k = entry.getKey();
//                    String v = entry.getValue();
//                    request.addHeader(k, v);
//                }
//            }
//            response = httpClient.execute(request);
//            result = EntityUtils.toString(response.getEntity());
            if ("get".equalsIgnoreCase(method)) {
                result =  httpGet(httpUrl, header);
            } else if ("post".equalsIgnoreCase(method)) {
                result = httpPost(json, httpUrl, header);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public  String httpPost(String param,String url,Map<String, String> httpHead) throws Exception{

        CloseableHttpClient httpclient = getHttpClient();
        String result = null;
        try {
            HttpPost request = new HttpPost(url);
            //解决中文乱码问题
            StringEntity entity = new StringEntity(param, "utf-8");

            request.setEntity(entity);

            //设置http头参数
            if(httpHead!=null&&httpHead.size()>0)
                for(Iterator<Map.Entry<String, String>> ies = httpHead.entrySet().iterator(); ies.hasNext();){
                    Map.Entry<String, String> entry = ies.next();

                    String key =entry.getKey();
                    String value = entry.getValue();

                    request.addHeader(key, value);
                }

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();//设置请求和传输超时时间
            request.setConfig(requestConfig);
            CloseableHttpResponse response = httpclient.execute(request);
            url = URLDecoder.decode(url, "UTF-8");

            System.out.println("请求接口："+url+",执行状态："+response.getStatusLine().getStatusCode());

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                try {
                    /**读取服务器返回过来的字符串数据**/
                    result = EntityUtils.toString(response.getEntity(),"UTF-8");
                    //System.out.println("result:"+result);
                } catch (Exception e) {
                    System.out.println("post请求提交失败:" + url);
                    e.printStackTrace();
                } finally {
                    response.close();
                }
            } else {
                result = response.getStatusLine().getStatusCode()+"-"+response.getStatusLine().getReasonPhrase();

            }
        } catch (Exception e) {
            System.out.println("post请求提交失败:" + url);
            e.printStackTrace();
        }finally{
            httpclient.close();
        }
        return result;
    }

    public  String httpGet(String url ,Map<String ,String > httpHeader) throws IOException{
        //get请求返回结果
        CloseableHttpClient httpclient = getHttpClient();
        String strResult = null;
        try {
            //发送get请求
            HttpGet request = new HttpGet(url);

            //设置http头参数
            if(httpHeader!=null && httpHeader.size()>0){
                for(Iterator<Map.Entry<String, String>> ies = httpHeader.entrySet().iterator(); ies.hasNext();){
                    Map.Entry<String, String> entry = ies.next();
                    String key =entry.getKey();
                    String value = entry.getValue();
                    request.addHeader(key, value);
                }
            }
            CloseableHttpResponse response = httpclient.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    strResult = EntityUtils.toString(response.getEntity(),"UTF-8");
                    url = URLDecoder.decode(url, "UTF-8");
                }catch(Exception e) {
                    System.out.println("get请求提交失败:" + url);
                    e.printStackTrace();
                }finally {
                    response.close();
                }
            }

        } catch (IOException e) {
            System.out.println("get请求提交失败:" + url);
            e.printStackTrace();
        } finally {
            httpclient.close();
        }
        return strResult;
    }

    private  CloseableHttpClient getHttpClient() {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
//指定信任密钥存储对象和连接套接字工厂
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //信任任何链接
            TrustStrategy anyTrustStrategy = new TrustStrategy() {

                public boolean isTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                    return true;
                }
            };
            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy).build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        //设置连接管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
//      connManager.setDefaultConnectionConfig(connConfig);
//      connManager.setDefaultSocketConfig(socketConfig);
        //构建客户端
        return HttpClientBuilder.create().setConnectionManager(connManager).build();
    }

    @Test
    public void testHTTPS() {
        // public static String jsonHttpBak(String httpUrl, String json, String method, Map<String, String> header) throws Exception {
        String httpUrl = "https://test.coracle.com:15000/mchl/jsse/message/push";
//          String httpUrl = "http://192.168.8.16:9999/mchl/jsse/message/push";
        String json = "{\n" +
                "  \"appKey\" : \"c7e5424b-da56-4367-bebb-232a10f3cf21\",\n" +
                "  \"boardcast\" : \"true\",\n" +
                "  \"userList\" : [\"230900\"],\n" +
                "  \"sender\": \"230900\",\n" +
                "  \"title\" : \"\",\n" +
                "  \"msgContent\" : {\n" +
                "    \"type\": \"IM_txt\",\n" +
                "    \"content\": \"${receivers}-test2016-10-19-${msg}\",\n" +
                "    \"mk\": \"chat\",\n" +
                "    \"rids\": \"${rids}\"\n" +
                "  },\n" +
                "  \"badge\" : \"\"\n" +
                "}";
        String method = "POST";
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("X-xSimple-AuthToken", "accc910dc04cce2bf802026eb3331e92");
        header.put("X-xSimple-LoginName", "230900");
        header.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 7.0; zh-cn; HUAWEI NXT-TL00) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");


        String result = "";
        // 设置为30分
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1800000).setSocketTimeout(1800000).setConnectionRequestTimeout(7000).setStaleConnectionCheckEnabled(true).build();
        if (httpUrl != null && httpUrl.toLowerCase().startsWith("https://")) {
//            result = sendHttps(httpUrl, json, method, header, requestConfig);
            result = sendHttpsNew(httpUrl, json, method, header, requestConfig);
        }
        System.out.println(result);
    }

    @Test
    public void testProccess() {
        String[] cmd = new String[]{"/bin/sh", "-c", "ps -ef | grep usb | grep -v grep | awk '{print $2}'"};

        BufferedReader bis = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bis = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = bis.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) process.destroy();
        }
    }

    @Test
    public void testIsMobile() {
//        String userAgent = "ijomoo_test 1.0.1701140 (iPad; iOS 10.1.1; zh_CN)";
//        String userAgent = "KndCRMv2/3.0.20170210 (iPad; iOS 9.3; Scale/2.00)";
        String userAgent = "移动应用门户 3.0.20170210 (iPad; iPhone OS 9.3; zh_CN)";
        String detectMobileBrowsersFullMatch = "(?i).*((android|bb\\d+|meego).+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)|vodafone|wap|windows ce|xda|xiino).*";
        String detectMobileBrowsersPartMatch = "(?i)1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\\-|your|zeto|zte\\-";

        if (userAgent.matches(detectMobileBrowsersFullMatch)
                || userAgent.substring(0, 4).matches(detectMobileBrowsersPartMatch)) {
            System.out.println("is Mobile");
        } else {
            System.out.println("is PC");
        }
    }


}
