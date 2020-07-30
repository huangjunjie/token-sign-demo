package com.stone.demo.sign.bean;

import com.stone.demo.sign.constants.ApiCodeEnum;
import com.stone.demo.sign.utils.ApiUtil;
import com.stone.demo.sign.utils.MD5Util;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Data
public class ApiResponse<T> {

    private ApiResult result;

    private T data;

    private String sign;

    public static <T> ApiResponse<T> success(T t) {
        return response(ApiCodeEnum.SUCCESS.getCode(), ApiCodeEnum.SUCCESS.getValue(), t);
    }

    public static ApiResponse error(String code, String msg) {
        return response(code, msg, null);
    }

    public static <T> ApiResponse response(String code, String msg, T data) {
        ApiResult result = new ApiResult(code, msg);
        ApiResponse response = new ApiResponse();
        response.setResult(result);
        response.setData(data);

        String sign = signData(data);
        response.setSign(sign);
        return response;
    }

    private static <T> String signData(T data) {

        // TODO 查询Key值
        String key = "123456";
        Map<String, String> responseMap = null;
        try {
            responseMap = getFileds(data);
        } catch (IllegalAccessException e) {
            return null;
        }
        String urlComponent = ApiUtil.concatSignString(responseMap);
        String signature = urlComponent + "key=" + key;
        String sign = MD5Util.encode(signature);

        return sign;

    }

    /***
     *
     * @param data data 反射的对象,获取对象的字段名和值
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private static <T> Map<String, String> getFileds(T data) throws IllegalAccessException,IllegalArgumentException {
        if (null == data) return null;
        Map<String, String> map = new HashMap<String, String>();
        Field[] fields = data.getClass().getDeclaredFields();
        for (Field filed : fields) {
            filed.setAccessible(true);
            String name = filed.getName();
            Object value = filed.get(data);
            if (null != filed.get(data)) {
                map.put(name, value.toString());
            }
        }
        return map;
    }
}
