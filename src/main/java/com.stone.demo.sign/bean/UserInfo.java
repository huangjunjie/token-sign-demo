package com.stone.demo.sign.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private String username;

    private String password;

    private String salt;

    private AccessToken accessToken;

    public UserInfo(String username,String password, String salt) {
        this.username = username;
        this.password = password;
        this.salt = salt;
    }
}
