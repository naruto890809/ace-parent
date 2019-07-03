package com.ace.app.cms.account.login;

/**
 * 用户登录服务
 * @author WuZhiWei
 * @since 2015-11-23 09:47:00
 */
public interface AccountLoginService {

    /**
     * 帐号登录信息验证
     * @param loginForm
     * @param loginToken
     * @return
     */
    LoginResult checkAccountLogin(LoginForm loginForm, String loginToken);
}
