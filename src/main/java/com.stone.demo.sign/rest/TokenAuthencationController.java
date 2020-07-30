package com.stone.demo.sign.rest;


import com.stone.demo.sign.annotation.NotRepeatSubmit;
import com.stone.demo.sign.bean.AccessToken;
import com.stone.demo.sign.bean.ApiResponse;
import com.stone.demo.sign.bean.AppInfo;
import com.stone.demo.sign.bean.TokenInfo;
import com.stone.demo.sign.bean.UserInfo;
import com.stone.demo.sign.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class TokenAuthencationController {

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/api/token/api_token")
    public ApiResponse<AccessToken> apiToken(@RequestBody String appId, @RequestHeader("timestamp") String timestamp, @RequestHeader("sign") String sign) {
        Assert.isTrue(!StringUtils.isEmpty(appId) && !StringUtils.isEmpty(timestamp)
                && !StringUtils.isEmpty(sign), "[TokenAuthencationController.apiToken] 入参为空");

        long requestInterval = System.currentTimeMillis() - Long.valueOf(timestamp);
        Assert.isTrue(requestInterval < 5 * 60 * 1000, "请求过期，请重新请求");

        //1.根据appId查询数据库获取appSecret
        AppInfo appInfo = new AppInfo();

        //2.签名校验
        String signString = timestamp + appId + appInfo.getKey();
        String signature = MD5Util.encode(signString);
        log.info(signature);
        Assert.isTrue(sign.equals(signature), "签名错误");

        AccessToken accessToken = this.saveToken(0, appInfo, null);

        return ApiResponse.success(accessToken);
    }

    @NotRepeatSubmit(5000)
    @PostMapping("/api/token/user_token")
    public ApiResponse<UserInfo> userToken(String username, String password) {
        UserInfo userInfo = new UserInfo(username, "123456", "123");
        String pwd = password + userInfo.getSalt();
        String passwordMD5 = MD5Util.encode(pwd);
        Assert.isTrue(passwordMD5.equals(userInfo.getPassword()), "密码错误");

        //2.保存Token
        AppInfo appInfo = new AppInfo("1", "12356");
        AccessToken accessToken = this.saveToken(1, appInfo, userInfo);
        userInfo.setAccessToken(accessToken);
        return ApiResponse.success(userInfo);

    }


    private AccessToken saveToken(int tokenType, AppInfo appInfo, UserInfo userInfo) {
        String token = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 7200);
        Date expireTime = calendar.getTime();

        ValueOperations<String, TokenInfo> operations = redisTemplate.opsForValue();
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setTokenType(tokenType);
        tokenInfo.setAppInfo(appInfo);

        if (1 == tokenType) {
            tokenInfo.setUserInfo(userInfo);
        }

        operations.set(token, tokenInfo, 7200, TimeUnit.SECONDS);

        AccessToken accessToken = new AccessToken(token, expireTime);

        return accessToken;
    }

}
