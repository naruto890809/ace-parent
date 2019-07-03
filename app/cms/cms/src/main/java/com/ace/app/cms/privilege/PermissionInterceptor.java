package com.ace.app.cms.privilege;

import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.privilege.domain.Menu;
import com.ace.app.cms.privilege.service.MenuService;
import com.ace.app.cms.privilege.service.RoleMenuRelService;
import com.ace.app.cms.privilege.service.RoleSpService;
import com.ace.framework.util.CookieUtil;
import com.ace.framework.util.ExecutionContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * struts2 权限拦截器
 *
 * @author WuZhiWei
 * @since 2015-11-23 15:21
 */
class PermissionInterceptor extends AbstractInterceptor {
    /**
     *
     */
    private static final long serialVersionUID = -4060095020632026548L;

    @Resource
    private RoleSpService roleSpService;
    @Resource
    private MenuService menuService;
    @Resource
    private RoleMenuRelService roleMenuRelService;


    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ActionContext context = invocation.getInvocationContext();
        ActionProxy proxy = invocation.getProxy();
        String methodName = proxy.getMethod();
        Object action = proxy.getAction();
        String auth = null;
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String aceSessionId = CookieUtil.getCookieValue(request, CmsConstant.MYJ_SESSION_ID);

        if (ExecutionContext.isPresident()){
            return invocation.invoke();
        }

        if (StringUtils.isNotBlank(aceSessionId)) {
            if (!validate(action.getClass(), methodName, request)) {
                //ActionContext.getContext().put("urladdress",SiteUrl.readUrl("control.control.right"));
                ActionContext.getContext().put("message", "您没有执行该操作的权限");
                return "message";
            }
        }
        return invocation.invoke();
    }

    private boolean validate(Class<?> clazz, String methodName,
                             HttpServletRequest request) throws NoSuchMethodException {
        Permission permission = parsePermission(clazz, methodName, null);

        if (permission == null) {
            return true;
        }
        if (StringUtils.isBlank(ExecutionContext.getRoleId())) {
            return false;
        }
//        RoleSp roleSp = new RoleSp(permission.privilege(), permission.module());

        //获取该用户有哪些角色
        /*String[] roleIds = ExecutionContext.getRoleId().split(",");
        List<RoleSp> roleSpList = roleSpService.findListByRoleIds(roleIds);
        if (roleSpList.contains(roleSp)) {
            return true;
        }*/

        List<String> listMenuId = roleMenuRelService.findListByRoleId(ExecutionContext.getRoleId().split(","));
        String[] menuIds = (String[])listMenuId.toArray(new String[0]);
        List<Menu> menuList = menuService.findListByMenuId(menuIds);
        for(Menu menu:menuList){
            if(permission.privilege().equals(menu.getPrivilege())&& permission.module().equals(menu.getModule()) ){
                return true;
            }
        }


        return false;
    }

    public static Permission parsePermission(Class<?> clazz, String methodName,
                                             Class<?>... parameterTypes) throws NoSuchMethodException {
        // 根据方法名，取得方法，如果有则返回
        Method method = clazz.getMethod(methodName, parameterTypes);
        if (method != null && method.isAnnotationPresent(Permission.class)) {
            Permission permission = method.getAnnotation(Permission.class);
            if (null != permission)
                return permission;
        }
        return null;
    }

}