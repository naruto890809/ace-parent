package com.ace.app.cms.account.login;

import java.io.Serializable;

/**
 * @author WuZhiWei
 * @since 2015-11-24 09:29:00
 */
public class LoginForm implements Serializable{

    private static final long serialVersionUID = -4401193625991148222L;

    private String username;

    private String password;

    private String random;

    private Long time;

    private String env; //pc,web,ios,android

    private String sign;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
