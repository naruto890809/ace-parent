package com.ace.app.cms.account;

import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.privilege.service.RoleService;
import com.ace.app.cms.util.CmsUtils;
import com.ace.framework.base.Page;
import com.ace.framework.util.ExecutionContext;
import com.ace.framework.util.redis.MyjJedisCommend;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

/**
 * 账户服务
 * @author WuZhiWei
 * @since 2015-11-09 17:30:21
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao accountDao;
    @Resource
    private RoleService roleService;



    @Override
    @Transactional(readOnly = false,isolation = Isolation.READ_COMMITTED)
    public String save(Account account) {
        Assert.notNull(account);
        String accountStatus = account.getAccountStatus();
        if (StringUtils.isEmpty(accountStatus)){
            account.setAccountStatus(CmsConstant.ACCOUNT_STATUS_ACTIVATED);
        }

        String roleCodes = account.getRoleCodes();
        if (StringUtils.isEmpty(roleCodes)){
            account.setRechargePsd(null);
        }

        String accountId = accountDao.save(account);
        if (StringUtils.isEmpty(roleCodes)){
            return accountId;
        }

        return accountId;
    }



    @Override
    @Transactional(readOnly = true)
    public Account getByCorpCodeAndAccountName(String corpCode ,String accountName) {
        Assert.hasText(corpCode);
        return accountDao.getByCorpCodeAndAccountName(corpCode,accountName);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Account> search(Account condition, Page<Account> page) {
        CmsUtils.validPage(page);
        return accountDao.searchAccountPage(condition, page);
    }

    @Override
    @Transactional(readOnly = true)
    public int findTotalByCondition(Account account){
        Assert.notNull(account);
        return accountDao.findTotalByCondition(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Account getById(String accountId,String corpCode){
        Assert.hasText(accountId);
//        Assert.hasText(corpCode);
        Account account = new Account();
        account.setAccountId(accountId);
        account.setCorpCode(corpCode);
        return accountDao.getById(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> listByCondition(Account account){
        Assert.notNull(account);
        return accountDao.findListByCondition(account);
    }

    @Override
    @Transactional(readOnly = false,isolation = Isolation.READ_COMMITTED)
    public void updateBySelective (Account account){
        Assert.notNull(account);
        accountDao.updateBySelective(account);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void updateStatus(List<String> accountIds, String status, String corpCode) {
        Assert.notEmpty(accountIds);
        Assert.hasText(corpCode);
        Assert.isTrue(CmsConstant.ACCOUNT_STATUS_ACTIVATED.equals(status) || CmsConstant.ACCOUNT_STATUS_FROZEN.equals(status));
        accountDao.updateStatus(accountIds, status, corpCode);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void resetAccountPsd(List<String> accountIds, String corpCode) {
        Assert.notEmpty(accountIds);
        Assert.hasText(corpCode);
        Account account = new Account();
        account.setAccountIds(accountIds);
        account.setCorpCode(corpCode);
        List<Account> accounts = listByCondition(account);
        if (CollectionUtils.isEmpty(accounts)){
            return;
        }

        Account tmp = new Account();
        for (Account accountTmp : accounts) {
            tmp.setLoginPsd(CmsConstant.DEFAULT_LOGIN_PSD);
            tmp.setAccountId(accountTmp.getAccountId());
            accountDao.updateBySelective(tmp);
        }
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void resetAdminPsd(String corpCode) {
        Assert.hasText(corpCode);
        Account account = new Account();
        account.setCorpCode(corpCode);
        account.setEmployeeCode("admin");
        List<Account> accounts = listByCondition(account);
        if (CollectionUtils.isEmpty(accounts)){
            return;
        }

        for (Account accountTmp : accounts) {
            accountTmp.setLoginPsd(CmsConstant.DEFAULT_LOGIN_PSD);
            accountDao.updateBySelective(accountTmp);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Account> getEmployeeCodeAndAccountMap(String corpCode, List<String> employeeCodes) {
        Assert.hasText(corpCode);
        Assert.notEmpty(employeeCodes);
        Account account = new Account();
        account.setCorpCode(corpCode);
        account.setEmployeeCodes(employeeCodes);
        List<Account> accounts = accountDao.findListByCondition(account);
        if (CollectionUtils.isEmpty(accounts)){
            return new HashMap<String, Account>(0);
        }

        Map<String, Account> employeeCodeAndAccountMap = new HashMap<String, Account>(accounts.size());
        for (Account accTmp : accounts) {
            employeeCodeAndAccountMap.put(accTmp.getEmployeeCode(),accTmp);
        }

        return employeeCodeAndAccountMap;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Account> getAccountNameAndAccountMap(String corpCode, List<String> accountNames) {
        Assert.hasText(corpCode);
        Assert.notEmpty(accountNames);
        Account account = new Account();
        account.setCorpCode(corpCode);
        account.setAccountNames(accountNames);
        List<Account> accounts = accountDao.findListByCondition(account);
        if (CollectionUtils.isEmpty(accounts)){
            return new HashMap<String, Account>(0);
        }

        Map<String, Account> accountNameAndAccountMap = new HashMap<String, Account>(accounts.size());
        for (Account accTmp : accounts) {
            accountNameAndAccountMap.put(accTmp.getAccountName(),accTmp);
        }

        return accountNameAndAccountMap;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Account> getPhoneAndAccountMap(String corpCode, List<String> phoneList) {
        Assert.hasText(corpCode);
        Assert.notEmpty(phoneList);
        Account account = new Account();
        account.setCorpCode(corpCode);
        account.setPhones(phoneList);
        List<Account> accounts = accountDao.findListByCondition(account);
        if (CollectionUtils.isEmpty(accounts)){
            return new HashMap<String, Account>(0);
        }

        Map<String, Account> phoneAndAccountMap = new HashMap<String, Account>(accounts.size());
        for (Account accTmp : accounts) {
            phoneAndAccountMap.put(accTmp.getPhone(), accTmp);
        }

        return phoneAndAccountMap;
    }

    @Override
    @Transactional(readOnly = true)
    public Account getByPhone(String phone) {
        Assert.hasText(phone);
        Account account = new Account();
        account.setPhone(phone);
        List<Account> accounts = accountDao.findList(account);
        if (CollectionUtils.isEmpty(accounts)){
            return null;
        }

        Account result = accounts.get(0);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllWorkAccount(String corpCode, String roleCode) {
        Assert.hasText(corpCode,"CorpCode must not empty when getAllWorkAccount");
        Assert.hasText(roleCode,"RoleCode must not empty when getAllWorkAccount");

        Account account = new Account();
        account.setAccountStatus(CmsConstant.ACCOUNT_STATUS_ACTIVATED);
        account.setCorpCode(corpCode);
        account.setRoleCode(roleCode);
        Page<Account> page =new Page<Account>();
        page.setAutoPaging(false);
        return page.getRows();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllAccountIds(String corpCode) {
        Assert.hasText(corpCode);
        return accountDao.getAllAccountIds(corpCode);
    }

    @Override
    public Map<String, String> getAidNameMap() {
        Account account = new Account();
        account.setCorpCode(ExecutionContext.getCorpCode());
        List<Account> accounts = accountDao.findListByCondition(account);
        if (CollectionUtils.isEmpty(accounts)){
            return new HashMap<>(0);
        }

        Map<String, String> phoneAndAccountMap = new HashMap<>(accounts.size());
        for (Account accTmp : accounts) {
            phoneAndAccountMap.put(accTmp.getAccountId(), accTmp.getAccountName());
        }

        return phoneAndAccountMap;
    }
}
