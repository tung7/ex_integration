package org.http.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class ProxyBrowser {
    String proxyIp = "computer.hdu.edu.cn";
    int proxyPort = 808;
    public ProxyBrowser() {
        try{
            print();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public InputStream getInput(String domain,String path) throws UnknownHostException, IOException{
        Socket proxy=new Socket(proxyIp,proxyPort);
        PrintWriter headSender=new PrintWriter(proxy.getOutputStream(),true);
        headSender.println("GET " + domain+path +" HTTP/1.1");	//debugged:path end with '\r' before
        headSender.println("Host:" + domain);
        //headSender.println("Connection:Close");
        headSender.println();

        return proxy.getInputStream();
    }
    public void print() throws UnknownHostException, IOException{
        InputStream input = getInput("www.baidu.com","/");
        int t;
        while((t=input.read())!=-1) {
            //System.out.print((char)t);
            System.out.print((char)t);
        }
    }
}