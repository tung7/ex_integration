package com.tung7.ex.repository.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/6/9.
 * @update
 */

public class RewriteHandlerMapping extends AbstractUrlHandlerMapping {
    private Logger logger = LoggerFactory.getLogger(RewriteHandlerMapping.class);

    public RewriteHandlerMapping setRewrtieHandler(PreHandler rewrtieHandler) {
        this.rewrtieHandler = rewrtieHandler;
        return this;
    }
    @Autowired
    private PreHandler rewrtieHandler;

    public RewriteHandlerMapping(){
        super();
        System.out.println("tung7 ------------- fuckyou init");
    }

    @Override
    protected Object lookupHandler(String urlPath, HttpServletRequest request) throws Exception {
        logger.debug("RewriteHandlerMapping.lookupHandler");
        if (rewrtieHandler.applyPreHandle(urlPath)) {
            return rewrtieHandler;
        }

        return null;
    }

    @Override
    protected void validateHandler(Object handler, HttpServletRequest request) throws Exception {
        logger.debug("RewriteHandlerMapping.validateHandler");
        super.validateHandler(handler, request);
    }

    @Override
    protected Object buildPathExposingHandler(Object rawHandler, String bestMatchingPattern, String pathWithinMapping,
                                              Map<String, String> uriTemplateVariables) {
        logger.debug("RewriteHandlerMapping.buildPathExposingHandler");
        return super.buildPathExposingHandler(rawHandler, bestMatchingPattern, pathWithinMapping, uriTemplateVariables);
    }

    @Override
    protected void exposePathWithinMapping(String bestMatchingPattern, String pathWithinMapping,
                                           HttpServletRequest request) {
        logger.debug("RewriteHandlerMapping.exposePathWithinMapping");
        super.exposePathWithinMapping(bestMatchingPattern, pathWithinMapping, request);
    }

    @Override
    protected void exposeUriTemplateVariables(Map<String, String> uriTemplateVariables, HttpServletRequest request) {
        logger.debug("RewriteHandlerMapping.exposeUriTemplateVariables");
        super.exposeUriTemplateVariables(uriTemplateVariables, request);
    }

    @Override
    protected void registerHandler(String[] urlPaths, String beanName) throws BeansException, IllegalStateException {
        logger.debug("RewriteHandlerMapping.registerHandler");
        super.registerHandler(urlPaths, beanName);
    }

    @Override
    protected void registerHandler(String urlPath, Object handler) throws BeansException, IllegalStateException {
        logger.debug("RewriteHandlerMapping.registerHandler");
        super.registerHandler(urlPath, handler);
    }

    @Override
    protected boolean supportsTypeLevelMappings() {
        logger.debug("RewriteHandlerMapping.supportsTypeLevelMappings");
        return super.supportsTypeLevelMappings();
    }


}
