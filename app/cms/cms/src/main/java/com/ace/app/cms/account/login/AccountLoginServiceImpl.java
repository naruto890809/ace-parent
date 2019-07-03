package com.ace.app.cms.account.login;

import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.account.Account;
import com.ace.app.cms.account.AccountService;
import com.ace.framework.util.encryption.DesUtil;
import com.ace.framework.util.encryption.EncodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author WuZhiWei
 * @since 2015-11-23 14:25:00
 */
@Service("accountLoginService")
public class AccountLoginServiceImpl implements AccountLoginService {

    @Resource
    private AccountService accountService;


    @Override
    public LoginResult checkAccountLogin(LoginForm loginForm, String loginToken) {
        if (StringUtils.isBlank(loginForm.getUsername())) {
            return errRecord("101", "用户名不能为空");
        }
        if (StringUtils.isBlank(loginForm.getPassword())) {
            return errRecord("102", "密码不能为空");
        }
        Account account = accountService.getByPhone(loginForm.getUsername());

        if (account == null) {
            return errRecord("201", "用户名不存在");
        }
        String loginPsd = account.getLoginPsd();
        String decPsd = DesUtil.strDec(loginPsd.substring(32, loginPsd.length()), account.getAccountId(),
                loginPsd.substring(0, 32), CmsConstant.MYJ);
        String enStr = EncodeUtil.md5(EncodeUtil.md5(account.getPhone() + loginToken) + decPsd);
        if (!enStr.equals(loginForm.getPassword())) {
            return errRecord("202", "密码错误");
        }
        if (CmsConstant.ACCOUNT_STATUS_FROZEN.equals(account.getAccountStatus())) {
            return errRecord("301", "该帐号已冻结");
        }

        LoginResult result = new LoginResult();
        result.setIsSuccess(true);
        result.setAccount(account);
        return result;
    }


    private LoginResult errRecord(String errCode, String errMsg) {
        LoginResult result = new LoginResult();
        result.setIsSuccess(false);
        result.setErrCode(errCode);
        result.setErrMsg(errMsg);
        return result;
    }

}
