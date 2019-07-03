package com.ace.app.cms.account.rolerel;

import com.ace.framework.base.BaseCrudService;
import com.ace.framework.base.Page;

/**
* 用户客户关系
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2016-05-17 17:13:41
*/
public interface AccountUserRelService extends BaseCrudService<AccountUserRel>  {

    void unLockAccount(String[] ids);

    Page<AccountUserRel> findAccountList(AccountUserRel accountUserRel, Page<AccountUserRel> page);
}
