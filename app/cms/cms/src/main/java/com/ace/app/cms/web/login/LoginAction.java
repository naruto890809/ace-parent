package com.ace.app.cms.web.login;

import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.account.Account;
import com.ace.app.cms.account.AccountService;
import com.ace.app.cms.privilege.domain.Menu;
import com.ace.app.cms.privilege.service.MenuService;
import com.ace.app.cms.privilege.service.RoleMenuRelService;
import com.ace.app.cms.privilege.service.RoleService;
import com.ace.app.cms.util.SecurityCodeUtils;
import com.ace.framework.base.*;
import com.ace.framework.util.CookieUtil;
import com.ace.framework.util.ExecutionContext;
import com.ace.framework.util.PropertiesUtil;
import com.ace.framework.util.UUIDUtil;
import com.ace.framework.util.encryption.DesUtil;
import com.ace.framework.util.redis.MyjJedisCommend;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.text.Collator;
import java.util.*;

/**
 * @author WuZhiWei
 * @since 2015-11-20 17:07:21
 */
public class LoginAction extends BaseAction<LoginModel> {

    private static final long serialVersionUID = -6279490155773927487L;
    @Resource
    private AccountService accountService;
    @Resource
    private MyjJedisCommend jedis;
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;

    @Resource
    private RoleMenuRelService roleMenuRelService;

    private final static String WX_URL = PropertiesUtil.getEnv("wx_url");

    public String index() {
        return "index";
    }

    public String login() {
        Account account = model.getAccount();
        JsonResult jsonResult = new JsonResult();
        if (account == null) {
            return renderErrorJson(jsonResult, "账户信息不能为空");
        }

        String phone = account.getPhone();
        if (StringUtils.isEmpty(phone)) {
            return renderErrorJson(jsonResult, "手机号不能为空");
        }

        String loginPsd = account.getLoginPsd();
        if (StringUtils.isEmpty(loginPsd)) {
            return renderErrorJson(jsonResult, "密码不能为空");
        }

        account = accountService.getByPhone(phone);
        if (account == null) {
            return renderErrorJson(jsonResult, "手机号不存在");
        }

        if (CmsConstant.ACCOUNT_STATUS_FROZEN.equals(account.getAccountStatus())) {
            return renderErrorJson(jsonResult, "用户被冻结");
        }

        String corpCode = account.getCorpCode();
        ExecutionContext.setCorpCode(corpCode);

        String loginPsdDb = account.getLoginPsd();

        String decPsd = DesUtil.strDec(loginPsdDb.substring(32,loginPsdDb.length()), account.getAccountId(),
                loginPsdDb.substring(0,32), CmsConstant.MYJ);

        if (!loginPsd.equals(decPsd)) {
            return renderErrorJson(jsonResult, "密码错误");
        }

        String accountId = account.getAccountId();
        ExecutionContext.setUserId(accountId);
        String accountName = account.getAccountName();
        ExecutionContext.setUserName(accountName);

        String key = CmsConstant.MYJ_SESSION_ID +
                CmsConstant.REDIS_KEY_CONTACT + corpCode +
                CmsConstant.REDIS_KEY_CONTACT + accountId;

        String aceSessionId = jedis.get(key);
        String parentCorpCodeByCorpCode = "default";
        String cookieMyjSessionId = CookieUtil.getCookieValue(request, CmsConstant.MYJ_SESSION_ID);
        if (StringUtils.isNotEmpty(aceSessionId) && StringUtils.isNotBlank(cookieMyjSessionId)) {
            jedis.expire(key, CmsConstant.SESSION_EXPIRE_TIME);
            jedis.expire(aceSessionId, CmsConstant.SESSION_EXPIRE_TIME);
            setCookie(aceSessionId,parentCorpCodeByCorpCode);
            return renderJson(jsonResult);
        }

        setSessionAndCookie(corpCode, accountName, accountId, key,parentCorpCodeByCorpCode,account.getEmployeeCode());
        return renderJson(jsonResult);
    }

    private void setSessionAndCookie(String corpCode, String accountName, String accountId,
                                     String key,String parentCorpCodeByCorpCode,String roleId) {
        String aceSessionId;
        aceSessionId = UUIDUtil.genUUID();
        jedis.set(key, aceSessionId);
        jedis.expire(key, CmsConstant.SESSION_EXPIRE_TIME);
        Map<String, String> userMap = new HashMap<String, String>();
        userMap.put("corp_code", corpCode);
        userMap.put("user_id", accountId);
        userMap.put("ace_session_id", aceSessionId);
        userMap.put("user_name", accountName);
        userMap.put("role_id", roleId);
        userMap.put("role_code", roleId);
        userMap.put("parent_corp_code",parentCorpCodeByCorpCode);
        jedis.hmset(aceSessionId, userMap);
        jedis.expire(aceSessionId, CmsConstant.SESSION_EXPIRE_TIME);
        setCookie(aceSessionId,parentCorpCodeByCorpCode);
    }

    private void setCookie(String aceSessionId,String parentCorpCodeByCorpCode) {
        String cookieDomain = request.getServerName();
        String corpCode = model.getCorpCode();
        if (StringUtils.isEmpty(corpCode)) {
            corpCode = ExecutionContext.getCorpCode();
        }

        CookieUtil.setCookie(response, CmsConstant.MYJ_SESSION_ID, aceSessionId,
                CmsConstant.COOKIE_PATH, "30", cookieDomain);
        CookieUtil.setCookie(response, CmsConstant.CORP_CODE, corpCode,
                CmsConstant.COOKIE_PATH, "30", cookieDomain);
        CookieUtil.setCookie(response, CmsConstant.PARENT_CORP_CODE, parentCorpCodeByCorpCode,
                CmsConstant.COOKIE_PATH, "30", cookieDomain);
    }

    /*public void genSecurityCode() {
        String securityCode = SecurityCodeUtils.randomCode(4);
        RemoteSession session = ((RemoteActionContext) ActionContext.getContext()).getRemoteSession(jedis);
        session.setAttribute(CmsConstant.SECURITY_CODE,securityCode);
        SecurityCodeUtils.generateSecCode(securityCode,120,33);
    }*/

    private String renderErrorJson(JsonResult jsonResult, String errorMsg) {
        jsonResult.setStatus(JsonStatus.ERROR);
        jsonResult.setMessage(errorMsg);
        return renderJson(jsonResult);
    }

    public String manageIndex() {
        Account account = accountService.getById(ExecutionContext.getUserId(), ExecutionContext.getCorpCode());
        model.setAccount(account);
        request.setAttribute("wxUrl",WX_URL);
        String roIds= ExecutionContext.getRoleId();
        if(StringUtils.isNotBlank(roIds)){
            List<Menu> menuList;
            if (ExecutionContext.isPresident()){
                menuList = menuService.findList(null);
            }else {
                List<String> listMenuId = roleMenuRelService.findMenuListByRoleId(roIds.split(","));
                String[] menuIds = listMenuId.toArray(new String[0]);
                if(menuIds.length==0){
                    return "manageIndex";
                }

                menuList = menuService.findListByMenuId(menuIds);
            }

            Collections.sort(menuList);//注意：是根据的
            request.setAttribute("menuTree", menuService.resolveTree(menuList, ""))  ;
        }

        if ("default".equals(ExecutionContext.getCorpCode()) && ExecutionContext.isPresident()){
            model.setDefaultCorpAdmin(CmsConstant.YES);
        }

        String corpCode = ExecutionContext.getCorpCode();

        String newGoodsOrderCnt = jedis.get(CmsConstant.REDIS_NEW_GOODS_ORDER_CNT_KEY + corpCode);
        String newAppointmentOrderCnt = jedis.get(CmsConstant.REDIS_NEW_APPOINTMENT_ORDER_CNT_KEY + corpCode);
        model.setNewAppointmentOrderCnt(StringUtils.isEmpty(newAppointmentOrderCnt)? "0":newAppointmentOrderCnt);
        model.setNewGoodsOrderCnt(StringUtils.isEmpty(newGoodsOrderCnt)? "0":newGoodsOrderCnt);

        if (ExecutionContext.isShopManager() || ExecutionContext.isReception() || ExecutionContext.isPresident()){
            model.setCanUpdateRechargePsd("YES");
        }

        return "manageIndex";
    }

    public String loginOut(){
        String accountId = model.getAccountId();
        String userId = ExecutionContext.getUserId();
        if (StringUtils.isEmpty(userId)){
           return "index";
        }

        if (!userId.equals(accountId)){
            throw new RuntimeException("LoginOut not current account!");
        }

        Cookie sessionCookie = CookieUtil.getCookie(request, CmsConstant.MYJ_SESSION_ID);
        Cookie corpCodeCookie = CookieUtil.getCookie(request, CmsConstant.CORP_CODE);
        String serverName = request.getServerName();
        CookieUtil.deleteCookie(response, sessionCookie, serverName,CmsConstant.COOKIE_PATH);
        CookieUtil.deleteCookie(response, corpCodeCookie, serverName,CmsConstant.COOKIE_PATH);

        String key = CmsConstant.MYJ_SESSION_ID +
                CmsConstant.REDIS_KEY_CONTACT + ExecutionContext.getCorpCode() +
                CmsConstant.REDIS_KEY_CONTACT + accountId;
        String aceSessionId = jedis.get(key);
        if (StringUtils.isEmpty(aceSessionId)){
            return "index";
        }

        jedis.del(key,aceSessionId);
        return "index";
    }
}
