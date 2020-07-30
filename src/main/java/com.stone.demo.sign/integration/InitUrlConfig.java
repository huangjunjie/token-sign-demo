package com.stone.demo.sign.integration;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Component
public class InitUrlConfig implements ApplicationRunner {

    @Resource
    WebApplicationContext applicationContext;

    public static List<String> allUrl;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("---------RequstMapping init start------------");
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        //获取url和对应类，方法信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<String> urlList = new ArrayList<>();
        for (RequestMappingInfo requestMappingInfo : map.keySet()) {
            Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
            urlList.addAll(patterns);
            log.info("RequstMapping:" + JSON.toJSONString(patterns));
        }
        allUrl = urlList;

        log.info("---------RequstMapping init end------------");
    }
}
