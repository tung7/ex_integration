package com.tung7.ex.repository.base.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Tung
 * @version 1.0
 * @date 2017/1/13
 * @update
 */
public class HttpUtil {

    public static String httpPost(String param,String url,Map<String, String> httpHead) throws Exception{
        CloseableHttpClient httpclient = getHttpsClient();
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

    public static String httpGet(String url ,Map<String ,String > httpHeader) throws IOException {
        //get请求返回结果
        CloseableHttpClient httpclient = getHttpsClient();
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

    private static CloseableHttpClient getHttpsClient() {
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

    public static String jsonHttpBak(String httpUrl, String json, String method, Map<String, String> header) throws Exception {
        String result = "";
        CloseableHttpClient httpClient = null;
        // 设置为30分
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1800000).setSocketTimeout(1800000).setConnectionRequestTimeout(7000).setStaleConnectionCheckEnabled(true).build();
        if( httpUrl == null) {
            throw new IllegalArgumentException("Url is null");
        }
        if (httpUrl.toLowerCase().startsWith("https://")) {
            httpClient = getHttpsClient();
        } else {
            httpClient = HttpClients.createDefault();
        }

        HttpRequestBase request = null;
        HttpResponse response = null;
        try {
            if ("get".equalsIgnoreCase(method)) {
                request = new HttpGet(httpUrl);
            } else {
                HttpPost post = new HttpPost(httpUrl);
                if (!(json == null || json.length() == 0)) {
                    StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
                    entity.setContentEncoding("UTF-8");
                    entity.setContentType("application/json; charset=UTF-8");
                    post.setEntity(entity);
                }
                request = post;
            }
            if (header != null && !header.isEmpty()) {
                for (Map.Entry<String,String> entry : header.entrySet()) {
                    String k = entry.getKey();
                    String v = entry.getValue();
                    request.addHeader(k, v);
                }
            }
            request.setConfig(requestConfig);
            response = httpClient.execute(request);
            // 请求结束，返回结果
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
           e.printStackTrace();
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return result;
    }

    public static void main(String[] args) {
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
        try {
            result =  jsonHttpBak(httpUrl, json, "post", header);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);

    }
}
