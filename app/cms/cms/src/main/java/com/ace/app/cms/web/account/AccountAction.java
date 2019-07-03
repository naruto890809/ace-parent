package com.ace.app.cms.web.account;

import com.ace.app.cms.account.Account;
import com.ace.app.cms.account.AccountService;
import com.ace.app.cms.privilege.Permission;
import com.ace.app.cms.privilege.domain.Role;
import com.ace.app.cms.privilege.service.RoleService;
import com.ace.framework.base.*;
import com.ace.framework.util.ExecutionContext;
import com.ace.framework.util.UUIDUtil;
import com.ace.framework.util.UploadDownloadUtil;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author WuZhiWei
 * @since 2015-12-15 13:26:21
 */
public class AccountAction extends BaseAction<AccountModel> {

    private static final long serialVersionUID = 8558230610092039579L;
    private Log log = LogFactory.getLog(AccountAction.class);
    private static final String IMPORT_MEMBER_TEMPLATE = "/WEB-INF/template/accountExport.xls";
    private static final String IMPORT_ACCOUNT_TEMPLATE = "/WEB-INF/template/importAccountTemplate.xls";

    @Resource
    private AccountService accountService;
    @Resource
    private RoleService roleService;
    @Value("${file.tmp.path}")
    private String fileTmpPath;


    @Permission(module = "account",privilege="view")
    public String accountManageIndex(){
        setModelManageRoles();
        
        setTarget("accountManageIndex");
        return COMMON;
    }

    private void setModelManageRoles() {
        List<Role> manageRoles = roleService.findList(null);
        model.setManageRoles(manageRoles);
    }


    @Permission(module = "account",privilege="view")
    public String search() {
        Page<Account> page = model.getPage();
        page = getAccountPage(page);
        return renderJson(page);
    }

    private Page<Account> getAccountPage(Page<Account> page) {
        Account account = model.getAccount();
        if (account == null){
            account = new Account();
        }

        account.setCorpCode(ExecutionContext.getCorpCode());
        page = accountService.search(account, page);
        List<Account> rows = page.getRows();
        if (CollectionUtils.isNotEmpty(rows)){
            List<Role> list = roleService.findList(null);
            Map<String,String> roleIdNameMap = new HashMap<>(list.size());
            for (Role role : list) {
                roleIdNameMap.put(role.getRoleId(),role.getRoleName());
            }
            for (Account row : rows) {
                String roleName = roleIdNameMap.get(row.getEmployeeCode());
                if (StringUtils.isEmpty(roleName)){
                    roleName = "未设置";
                }

                row.setRoleName(roleName);
            }
        }
        return page;
    }

    private String getSubCorpCode() {
        String subCorpCode = model.getSubCorpCode();
        if (StringUtils.isEmpty(subCorpCode)){
            subCorpCode = ExecutionContext.getCorpCode();
        }
        return subCorpCode;
    }

    @Permission(module = "account",privilege="add")
    public String addIndex(){
        setModelManageRoles();
        setTarget("addIndex");
        request.setAttribute("roleCode",request.getParameter("roleCode"));
        return COMMON;
    }

    public String checkUnique(){
        String checkType = model.getCheckType();
        String checkValue = model.getCheckValue();
        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isEmpty(checkType) || StringUtils.isEmpty(checkValue)){
            jsonResult.setStatus(JsonStatus.ERROR);
            return renderJson(jsonResult);
        }

        String accountId = model.getAccountId();
        String corpCode = getSubCorpCode();
        if (StringUtils.isNotEmpty(accountId)){
            Account account = accountService.getById(accountId, corpCode);
            if (("phone".equals(checkType) && checkValue.equals(account.getPhone())) ||
                    ("employeeCode".equals(checkType) && checkValue.equals(account.getEmployeeCode())) ||
                    ("accountName".equals(checkType) && checkValue.equals(account.getAccountName()))){
                return renderJson(jsonResult);
            }
        }

        Account accountCondition = new Account();
        if ("phone".equals(checkType)){
            accountCondition.setPhone(checkValue);
        }else if ("employeeCode".equals(checkType)){
            accountCondition.setCorpCode(corpCode);
            accountCondition.setEmployeeCode(checkValue);
        }else if ("accountName".equals(checkType)){
            accountCondition.setCorpCode(corpCode);
            accountCondition.setAccountName(checkValue);
        }

        int total = accountService.findTotalByCondition(accountCondition);
        if (total > 0){
            jsonResult.setStatus(JsonStatus.ERROR);
            return renderJson(jsonResult);
        }

        return renderJson(jsonResult);
    }

    @Permission(module = "account",privilege="add")
    public String add(){
        JsonResult jsonResult = new JsonResult();
        Account account = model.getAccount();
        if (account == null){
            jsonResult.setStatus(JsonStatus.ERROR);
            return renderJson(jsonResult);
        }

        account.setCorpCode(getSubCorpCode());

        try {
            accountService.save(account);
        } catch (Exception e) {
            log.error(e);
            jsonResult.setStatus(JsonStatus.ERROR);
            return renderJson(jsonResult);
        }

        return renderJson(jsonResult);
    }

    @Permission(module="account", privilege="update")
    public String edit(){
        String accountId = model.getAccountId();
        if (StringUtils.isEmpty(accountId)){
            return null;
        }

        Account account = accountService.getById(accountId, getSubCorpCode());
        model.setAccount(account);

        setModelManageRoles();
        setTarget("editAccount");
        return COMMON;
    }


    @Permission(module="account", privilege="update")
    public String update(){
        JsonResult jsonResult = new JsonResult();
        Account account = model.getAccount();
        if (account == null){
            jsonResult.setStatus(JsonStatus.ERROR);
            return renderJson(jsonResult);
        }

        account.setCorpCode(getSubCorpCode());
        try {
            accountService.updateBySelective(account);
        } catch (Exception e) {
            log.error(e);
            jsonResult.setStatus(JsonStatus.ERROR);
            return renderJson(jsonResult);
        }

        return renderJson(jsonResult);
    }

    @Permission(module="account", privilege="freeze&activate")
    public String updateStatus(){
        String accountIds = model.getAccountIds();
        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isEmpty(accountIds)){
            jsonResult.setStatus(JsonStatus.WARNING);
            jsonResult.setMessage("请选择操作的列！");
            return renderJson(jsonResult);
        }

        try {
            accountService.updateStatus(Arrays.asList(accountIds.split(",")), model.getAccountStatus(), getSubCorpCode());
        } catch (Exception e) {
            jsonResult.setStatus(JsonStatus.ERROR);
            jsonResult.setMessage(e.getMessage());
        }

        return renderJson(jsonResult);
    }

    //
    @Permission(module="account", privilege="resetaccountpsd")
    public String resetAccountPsd(){
        String accountIds = model.getAccountIds();
        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isEmpty(accountIds)){
            jsonResult.setStatus(JsonStatus.WARNING);
            jsonResult.setMessage("请选择操作的列！");
            return renderJson(jsonResult);
        }

        try {
            accountService.resetAccountPsd(Arrays.asList(accountIds.split(",")),getSubCorpCode());
        } catch (Exception e) {
            jsonResult.setStatus(JsonStatus.ERROR);
            jsonResult.setMessage(e.getMessage());
        }

        return renderJson(jsonResult);
    }

    @Permission(module="account", privilege="download")
    public String exportAccount() {
        return "exportAccount";
    }

    @Permission(module="account", privilege="download")
    public String getExportAccountFileName(){
        return UploadDownloadUtil.toAttachmentFileName("用户信息.xls", request);
    }

    public InputStream getExportAccountInputStream() {
        Page page = model.getPage();
        page.setAutoPaging(false);
        page = getAccountPage(page);

        //模板变量
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("accounts", page.getRows());

        XLSTransformer transformer = new XLSTransformer();
        String fileTmpPath = this.fileTmpPath +"cms/";
        try {
            File file = new File(fileTmpPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            String fileTmp = fileTmpPath + UUIDUtil.genUUID() + ".xls";
            transformer.transformXLS(ServletActionContext.getServletContext().getRealPath(IMPORT_MEMBER_TEMPLATE), map, fileTmp);
            return new FileInputStream(fileTmp);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } catch (InvalidFormatException e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    public String setProjectIndex(){
        String accountId = model.getAccountId();
        Account account = accountService.getById(accountId, getSubCorpCode());
        model.setAccount(account);
        setTarget("setProjectIndex");
        return COMMON;
    }


    public String selectProjectIndex(){
        super.setTarget("selectProjectIndex");
        return COMMON;
    }

    public String editPersonal(){
        String accountId = model.getAccountId();
        if (StringUtils.isEmpty(accountId)){
            return null;
        }

        Account account = accountService.getById(accountId, getSubCorpCode());
        model.setAccount(account);
        super.setTarget("editPersonal");
        return COMMON;
    }

    public String updateRechargePsdIndex(){
        setTarget("updateRechargePsdIndex");
        return COMMON;
    }

}
