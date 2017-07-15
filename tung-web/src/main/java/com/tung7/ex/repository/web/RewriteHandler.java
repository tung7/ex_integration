package com.tung7.ex.repository.web;

import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/6/9.
 * @update
 */

public class RewriteHandler implements PreHandler, HttpRequestHandler {
    @Override
    public boolean applyPreHandle(String urlPath) {
        System.out.println("tung7 ======= urlPath ： " + urlPath);
        if (urlPath.startsWith("/api/v")) {
            return true;
        }
        return false;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("tung7 =============== rewrite handle!!!!!!!!!");
    }
}
