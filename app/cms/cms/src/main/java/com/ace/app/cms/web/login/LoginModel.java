package com.ace.app.cms.web.login;

import com.ace.app.cms.account.Account;
import com.ace.framework.base.BaseModel;

/**
 * @author WuZhiWei
 * @since 2015-11-21 15:11:21
 */
public class LoginModel extends BaseModel {
    private static final long serialVersionUID = -6739100145398947882L;

    private Account account;

    private String accountId;

    private String defaultCorpAdmin;
    private String newGoodsOrderCnt;
    private String newAppointmentOrderCnt;
    private String canUpdateRechargePsd;

    public String getCanUpdateRechargePsd() {
        return canUpdateRechargePsd;
    }

    public void setCanUpdateRechargePsd(String canUpdateRechargePsd) {
        this.canUpdateRechargePsd = canUpdateRechargePsd;
    }

    public String getNewGoodsOrderCnt() {
        return newGoodsOrderCnt;
    }

    public void setNewGoodsOrderCnt(String newGoodsOrderCnt) {
        this.newGoodsOrderCnt = newGoodsOrderCnt;
    }

    public String getNewAppointmentOrderCnt() {
        return newAppointmentOrderCnt;
    }

    public void setNewAppointmentOrderCnt(String newAppointmentOrderCnt) {
        this.newAppointmentOrderCnt = newAppointmentOrderCnt;
    }

    public String getDefaultCorpAdmin() {
        return defaultCorpAdmin;
    }

    public void setDefaultCorpAdmin(String defaultCorpAdmin) {
        this.defaultCorpAdmin = defaultCorpAdmin;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
