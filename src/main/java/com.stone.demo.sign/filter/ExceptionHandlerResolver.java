package com.stone.demo.sign.filter;

import java.io.*;

import com.stone.demo.sign.bean.ApiResponse;
import com.stone.demo.sign.constants.ApiCodeEnum;
import com.stone.demo.sign.bean.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerResolver {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseBody
    public ApiResponse errorHandlerResovler(Exception ex) {
        log.error("返回前端报错",ex);
        ApiResponse apiResponse = new ApiResponse();
        ApiResult apiResult     = new ApiResult();
        apiResult.setCode(ApiCodeEnum.UNKOWN_ERROR.getCode());
        apiResult.setMsg(ApiCodeEnum.UNKOWN_ERROR.getValue());
        apiResponse.setResult(apiResult);
        apiResponse.setData(expectErrorMessage(ex));
        return apiResponse;
    }


    public String expectErrorMessage(Exception ex) {
        return getSimpleErrorMessage(ex);
    }


    private String getSimpleErrorMessage(Exception ex) {
        StringBuffer sb =new StringBuffer();
        sb.append("Message:").append(ex.getMessage()).append("\r\n");
        sb.append("Exception:").append(ex.getClass().getName()).append("\r\n");
        StackTraceElement[] stackTraces = ex.getStackTrace();
        if(stackTraces.length >= 1) {
            sb.append("ClassPoint:").append(stackTraces[0].getClassName()).append("\r\n");
            sb.append("MethodPoint:").append(stackTraces[0].getMethodName()).append("\r\n");
            sb.append("LineNumber:").append(stackTraces[0].getLineNumber()).append("\r\n");
        }
        return  sb.toString();
    }

    private String getCompleteErrorMessage(Exception ex) {
        //获取详细信息
        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        ex.printStackTrace(printWriter);
        //信息序列化
        return sw.toString();
    }
}
