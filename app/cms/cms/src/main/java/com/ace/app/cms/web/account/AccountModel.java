package com.ace.app.cms.web.account;

import com.ace.app.cms.account.Account;
import com.ace.app.cms.account.proRel.AccountProRel;
import com.ace.app.cms.account.rolerel.AccountUserRel;
import com.ace.app.cms.privilege.domain.Role;
import com.ace.framework.base.BaseModel;
import com.ace.framework.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author WuZhiWei
 * @since 2015-11-21 15:11:21
 */
public class AccountModel extends BaseModel {

    private static final long serialVersionUID = -6013419065402047613L;
    private Account account;
    private String accountStatus;
    private String accountId;
    private String subCorpCode;
    private String checkType;
    private String checkValue;
    private String roleCodes;
    private String excelName;
    private String optType;
    private String accountIds;
    private List<Role> manageRoles;
    private Date workStartTime;
    private Date workEndTime;
    private String workStartTimeStr;
    private String workEndTimeStr;
    private List<String> workTimeIds;
    private AccountProRel accountProRel;
    private List<String> accountProjectRelIds;
    private List<String> proIds;
    private Integer week;
    private String preRechargePsd;
    private String newRechargePsd;
    private String rechargePsdAgain;

    private List<String> userIds;

    private AccountUserRel accountUserRel;

    public String getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(String accountIds) {
        this.accountIds = accountIds;
    }

    public AccountUserRel getAccountUserRel() {
        return accountUserRel;
    }

    public void setAccountUserRel(AccountUserRel accountUserRel) {
        this.accountUserRel = accountUserRel;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public String getPreRechargePsd() {
        return preRechargePsd;
    }

    public void setPreRechargePsd(String preRechargePsd) {
        this.preRechargePsd = preRechargePsd;
    }

    public String getNewRechargePsd() {
        return newRechargePsd;
    }

    public void setNewRechargePsd(String newRechargePsd) {
        this.newRechargePsd = newRechargePsd;
    }

    public String getRechargePsdAgain() {
        return rechargePsdAgain;
    }

    public void setRechargePsdAgain(String rechargePsdAgain) {
        this.rechargePsdAgain = rechargePsdAgain;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public List<String> getProIds() {
        return proIds;
    }

    public void setProIds(List<String> proIds) {
        this.proIds = proIds;
    }

    public List<String> getAccountProjectRelIds() {
        return accountProjectRelIds;
    }

    public void setAccountProjectRelIds(List<String> accountProjectRelIds) {
        this.accountProjectRelIds = accountProjectRelIds;
    }

    public AccountProRel getAccountProRel() {
        return accountProRel;
    }

    public void setAccountProRel(AccountProRel accountProRel) {
        this.accountProRel = accountProRel;
    }

    public List<String> getWorkTimeIds() {
        return workTimeIds;
    }

    public void setWorkTimeIds(List<String> workTimeIds) {
        this.workTimeIds = workTimeIds;
    }

    public String getWorkStartTimeStr() {
        return workStartTimeStr;
    }

    public void setWorkStartTimeStr(String workStartTimeStr) {
        this.workStartTimeStr = workStartTimeStr;
    }

    public String getWorkEndTimeStr() {
        return workEndTimeStr;
    }

    public void setWorkEndTimeStr(String workEndTimeStr) {
        this.workEndTimeStr = workEndTimeStr;
    }

    public Date getWorkStartTime() {
        if (StringUtils.isNotEmpty(this.workStartTimeStr)){
            return DateUtil.convertByFormat(workStartTimeStr,DateUtil.DATE_PATTERN_yyyy_MM_dd_HH_MM);
        }

        return workStartTime;
    }

    public void setWorkStartTime(Date workStartTime) {
        this.workStartTime = workStartTime;
    }

    public Date getWorkEndTime() {
        if (StringUtils.isNotEmpty(this.workEndTimeStr)){
            return DateUtil.convertByFormat(workEndTimeStr,DateUtil.DATE_PATTERN_yyyy_MM_dd_HH_MM);
        }

        return workEndTime;
    }

    public void setWorkEndTime(Date workEndTime) {
        this.workEndTime = workEndTime;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }


    public String getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(String roleCodes) {
        this.roleCodes = roleCodes;
    }

    public List<Role> getManageRoles() {
        return manageRoles;
    }

    public void setManageRoles(List<Role> manageRoles) {
        this.manageRoles = manageRoles;
    }

    public String getCheckValue() {
        return checkValue;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setCheckValue(String checkValue) {
        this.checkValue = checkValue;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getSubCorpCode() {
        return subCorpCode;
    }

    public void setSubCorpCode(String subCorpCode) {
        this.subCorpCode = subCorpCode;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
