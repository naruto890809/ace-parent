package com.ace.app.cms.account;

import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseDao;
import com.ace.framework.base.Page;
import com.ace.framework.util.ExecutionContext;
import com.ace.framework.util.UUIDUtil;
import com.ace.framework.util.encryption.DesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 商家基本信息dao服务
 * @author WuZhiWei
 * @since 2015年11月19日 16:52:15
 */
@Repository
public class AccountDao extends BaseDao<Account> {

    private static final String STATEMENT = "com.ace.app.cms.account.Account.";


    public String save(Account account) {
        String accountId = account.getAccountId();
        if (StringUtils.isEmpty(accountId)){
            accountId = UUIDUtil.genUUID();
            account.setAccountId(accountId);
        }

        Date nowTime = new Date();
        account.setCreateTime(nowTime);
        account.setLastModifyTime(nowTime);
        account.setCreateBy(ExecutionContext.getUserId());
        account.setLastModifyBy(ExecutionContext.getUserId());
        account.setLoginPsd(encPsd(account));
        if (StringUtils.isNotEmpty(account.getRechargePsd())){
            account.setRechargePsd(encRechargePsd(account));
        }

        insert(account);
        return accountId;
    }

    public  Account getByCorpCodeAndAccountName(String corpCode ,String accountName){
        Account account = new Account();
        account.setCorpCode(corpCode);
        account.setAccountName(accountName);
        return getObject(account);
    }

    private String encPsd(Account account){
        /**
         * DES加密
         * @param data 密码
         * @param firstKey accountId
         * @param secondKey accountName
         * @param thirdKey create_time
         * @return 加密字符串
         */
        String uuid = UUIDUtil.genUUID();
        return uuid + DesUtil.strEnc(account.getLoginPsd(),account.getAccountId(),
                uuid, CmsConstant.MYJ);
    }

    private String encRechargePsd(Account account){
        String uuid = UUIDUtil.genUUID();
        return uuid + DesUtil.strEnc(account.getRechargePsd(),account.getAccountId(),
                uuid, CmsConstant.MYJ);
    }

    public Page<Account> searchRolePage(Account account, Page<Account> page){
        if (account == null){
            account = new Account();
        }

        return search(STATEMENT + "searchRolePage",account,page);
    }

    public Page<Account> searchAccountPage(Account account, Page<Account> page){
        if (account == null){
            account = new Account();
        }

        return search(STATEMENT + "searchAccountPage",account,page);
    }

    public int findTotalByCondition(Account account){
        return getOne(STATEMENT+"findTotalByCondition",account);
    }

    public Account getById(Account account){
        Account accountDb = getOne(STATEMENT + "getById", account);

        if (accountDb == null){
            return  null;
        }

        String loginPsdDb = accountDb.getLoginPsd();
        String decPsd = DesUtil.strDec(loginPsdDb.substring(32,loginPsdDb.length()), accountDb.getAccountId(),
                loginPsdDb.substring(0,32), CmsConstant.MYJ);
        accountDb.setLoginPsd(decPsd);

        String rechargePsd = accountDb.getRechargePsd();
        if (StringUtils.isNotEmpty(rechargePsd)){
            String decRechargePsd = DesUtil.strDec(rechargePsd.substring(32,rechargePsd.length()), accountDb.getAccountId(),
                    rechargePsd.substring(0,32), CmsConstant.MYJ);
            accountDb.setRechargePsd(decRechargePsd);
        }

        return accountDb;
    }

    public List<Account> findListByCondition(Account account){
        return findList(STATEMENT + "findList", account);
    }

    public void updateBySelective (Account account){
        String loginPsd = account.getLoginPsd();
        if (StringUtils.isNotEmpty(loginPsd)){
            account.setLoginPsd(encPsd(account));
        }

        if (StringUtils.isNotEmpty(account.getRechargePsd())){
            account.setRechargePsd(encRechargePsd(account));
        }

        update(STATEMENT+"updateBySelective",account);
    }

    public void updateStatus(List<String> accountIds, String status,String corpCode) {
        Account account = new Account();
        account.setAccountIds(accountIds);
        account.setAccountStatus(status);
        account.setCorpCode(corpCode);
        update(STATEMENT + "updateStatus", account);
    }

    public List<String> getAllAccountIds(String corpCode){
        return findList(STATEMENT + "getAllAccountIds" , corpCode);
    }
}
