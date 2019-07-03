package com.ace.app.cms.account;

import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseEntity;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 账户表 test wzw
 * @author WuZhiWei 
 * @since 2015-11-20 14:18:21
 */
public class Account extends BaseEntity{
    private static final long serialVersionUID = -4112625236477588780L;

    private String accountId;
    private String employeeCode;
    private String accountName;
    private Integer gender;
    private String loginPsd;
    private String accountStatus;
    private String phone;
    private String email;

    private String faceUrl;
    private String rechargePsd;
    private Integer age;
    private Integer workYear;
    private String weiXin;
    private String qq;
    private String selfIntroduction;

    //////////////////////以下为非持久化属性///////////////////////////////
    private String securityCode;
    private String roleCode;
    private String roleName;
    private String statusText;
    private String genderText;
    private String roleCodes;  //角色编号，多个以英文逗号分割
    private List<String> accountIds;
    private List<String> employeeCodes;
    private List<String> accountNames;
    private List<String> phones;
    private String accountNameForArchives;
    private List<String> accountEmailList;

    //////////////////////////////////////////////////////////////////
    private Float workTime ;//用户openB  用户列表展示时间

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<String> getAccountNames() {
        return accountNames;
    }

    public void setAccountNames(List<String> accountNames) {
        this.accountNames = accountNames;
    }

    public List<String> getEmployeeCodes() {
        return employeeCodes;
    }

    public void setEmployeeCodes(List<String> employeeCodes) {
        this.employeeCodes = employeeCodes;
    }

    public Integer getWorkYear() {
        return workYear;
    }

    public void setWorkYear(Integer workYear) {
        this.workYear = workYear;
    }

    public String getWeiXin() {
        return weiXin;
    }

    public void setWeiXin(String weiXin) {
        this.weiXin = weiXin;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getRechargePsd() {
        return rechargePsd;
    }

    public void setRechargePsd(String rechargePsd) {
        this.rechargePsd = rechargePsd;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public List<String> getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(List<String> accountIds) {
        this.accountIds = accountIds;
    }

    public String getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(String roleCodes) {
        this.roleCodes = roleCodes;
    }

    public String getStatusText() {
        if (CmsConstant.ACCOUNT_STATUS_ACTIVATED.equals(accountStatus)){
            return "激活";
        }

        return "冻结";
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getGenderText() {
        if (gender == null || 0 == gender ){
            return "女";
        }

        return "男";
    }

    public void setGenderText(String genderText) {
        this.genderText = genderText;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getLoginPsd() {
        return loginPsd;
    }

    public void setLoginPsd(String loginPsd) {
        this.loginPsd = loginPsd;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Float getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Float workTime) {
        this.workTime = workTime;
    }

    public String getAccountNameForArchives() {
        return accountNameForArchives;
    }

    public void setAccountNameForArchives(String accountNameForArchives) {
        this.accountNameForArchives = accountNameForArchives;
    }


    public List<String> getAccountEmailList() {
        if (StringUtils.isNotBlank(email)){
            email = email.replaceAll("，",",");
            accountEmailList = Arrays.asList(email.split(","));
        }


        return accountEmailList;
    }

    public void setAccountEmailList(List<String> accountEmailList) {
        this.accountEmailList = accountEmailList;

}
}