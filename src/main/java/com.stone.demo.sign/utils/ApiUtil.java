package com.stone.demo.sign.utils;

import com.stone.demo.sign.annotation.NotRepeatSubmit;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

public class ApiUtil {

    public static String    concatSignString(HttpServletRequest request) {
        Map<String,String> parameterMap = new HashMap<>();
        request.getParameterMap().forEach((key,value)-> parameterMap.put(key,value[0]));
        Set<String> keySet = parameterMap.keySet();
        String [] keyArray =keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for(String k : keyArray) {
            if("sign".equals(k)) {
                continue;
            }
            if(parameterMap.get(k).trim().length() > 0 ) {
                //参数为空则不参与签名
                sb.append(k).append("=").append(parameterMap.get(k).trim()).append("&");
            }
        }
        return sb.toString();
    }


    /***
     *
     * @param map
     * @return
     */
    public static String concatSignString(Map<String,String> map) {
        Map<String,String> parameterMap = new HashMap<>();
        map.forEach((key,value)->parameterMap.put(key,value));
        Set<String> keySet = parameterMap.keySet();
        String [] keyArray =keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for(String k : keyArray) {
            if("sign".equals(k)) {
                continue;
            }
            if(parameterMap.get(k).trim().length() > 0 ) {
                //参数为空则不参与签名
                sb.append(k).append("=").append(parameterMap.get(k).trim()).append("&");
            }
        }
        return sb.toString();

    }


    /**
     * 获取方法上的 NotRepeatSubmit注解
     * @param handler
     * @return
     */
    public static NotRepeatSubmit getNotRepeatSubmit(Object handler) {
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method =handlerMethod.getMethod();
            NotRepeatSubmit annotation = method.getAnnotation(NotRepeatSubmit.class);
            return annotation;
        }
        return null;
    }
}
