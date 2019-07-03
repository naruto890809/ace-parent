package com.ace.app.cms.account.login;

import com.ace.app.cms.account.Account;

import java.io.Serializable;

/**
 * @author WuZhiWei
 * @since 2015-11-25 14:50:00
 */
public class LoginResult implements Serializable {

    private static final long serialVersionUID = 8386881721683731464L;

    private boolean isSuccess;

    private Account account;

    private String errCode;

    private String errMsg;


    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
