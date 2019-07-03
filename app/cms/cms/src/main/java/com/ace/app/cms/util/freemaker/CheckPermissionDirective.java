package com.ace.app.cms.util.freemaker;


import com.ace.app.cms.privilege.domain.Menu;
import com.ace.app.cms.privilege.service.MenuService;
import com.ace.app.cms.privilege.service.RoleMenuRelService;
import com.ace.app.cms.privilege.service.RoleSpService;
import com.ace.framework.util.ExecutionContext;
import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *  <@check module="member" privilege="update">复制</@check>
 *
 *  前端权限校验
 *
 * @author WuZhiWei
 * @since 2016-01-18 20:55
 */
public class CheckPermissionDirective implements TemplateDirectiveModel{
    @Resource
    private RoleSpService roleSpService;
    @Resource
    private MenuService menuService;
    @Resource
    private RoleMenuRelService roleMenuRelService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        String module = getRequiredParam(map, "module");
        String privilege = getRequiredParam(map, "privilege");
//        RoleSp roleSp = new RoleSp(privilege,module);

       /* if (StringUtils.isNotBlank(ExecutionContext.getRoleId())) {
            //获取该用户有哪些角色
            String[] roleIds = ExecutionContext.getRoleId().split(",");
            List<RoleSp> roleSpList = roleSpService.findListByRoleIds(roleIds);
            if (roleSpList.contains(roleSp)) {
                templateDirectiveBody.render(environment.getOut());
            }
        }*/

        List<Menu> menuList;
        if (ExecutionContext.isPresident()){
            menuList = menuService.findList(null);
        }else {
            List<String> listMenuId = roleMenuRelService.findListByRoleId(ExecutionContext.getRoleId().split(","));
            String[] menuIds = (String[])listMenuId.toArray(new String[0]);
            menuList = menuService.findListByMenuId(menuIds);
        }

        for(Menu menu:menuList){
            if(privilege.equals(menu.getPrivilege())&&module.equals(menu.getModule()) ){
                templateDirectiveBody.render(environment.getOut());
                return;
            }
        }

    }

    static String getRequiredParam(Map params,String key) throws TemplateException {
        Object value = params.get(key);
        if(value == null || StringUtils.isEmpty(value.toString())) {
            throw new TemplateModelException("not found required parameter:"+key+" for directive");
        }
        return value.toString();
    }

    static String getParam(Map params,String key,String defaultValue) throws TemplateException {
        Object value = params.get(key);
        return value == null ? defaultValue : value.toString();
    }
}
