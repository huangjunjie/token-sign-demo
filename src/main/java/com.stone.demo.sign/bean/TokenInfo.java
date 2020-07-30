package com.stone.demo.sign.bean;


import lombok.Data;

@Data
public class TokenInfo {

    private Integer tokenType;

    private AppInfo appInfo;

    private UserInfo userInfo;
}
