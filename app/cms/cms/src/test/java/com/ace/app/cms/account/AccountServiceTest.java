package com.ace.app.cms.account;

import com.ace.app.cms.BaseTest;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

/**
 * @author WuZhiWei
 * @since 2015-11-20 15:26:21
 */
public class AccountServiceTest extends BaseTest{

    @Resource
    private AccountService accountService;

    @Test
    @Rollback(false)
    public void testSave() {
        Account account = new Account();
        account.setLoginPsd("000");
        account.setAccountName("1234567");
        account.setCorpCode("wzw");
        account.setAccountStatus("ACTIVATED");
        account.setPhone("18796t787");
        account.setEmail("e2wdsads");
        accountService.save(account);
    }

    @Test
    public void testGetByCorpCodeAndAccountName(){
        Account account = accountService.getByCorpCodeAndAccountName("wzw", "wzw");
        System.out.println(account.getAccountName()+"==============");
    }
}
