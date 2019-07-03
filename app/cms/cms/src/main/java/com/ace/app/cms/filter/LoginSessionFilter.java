package com.ace.app.cms.filter;


import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseConstant;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.util.CookieUtil;
import com.ace.framework.util.ExecutionContext;
import com.ace.framework.util.JsonUtil;
import com.ace.framework.util.redis.MyjJedisCommend;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这个过滤器用来检验登录，路由静态、动态资源
 *  TODO 检查corpCode
 * @author WuZhiWei
 * @since 2015-11-20 17:07:21
 */
public class LoginSessionFilter implements Filter {
    private MyjJedisCommend jedis;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            jedis = getJedis(filterConfig.getServletContext());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(
                    "Failed to initialize Jedis for Session", e);
        }
    }

    MyjJedisCommend getJedis(ServletContext servletContext)
            throws Exception {
        ApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContext);
        return (MyjJedisCommend) context.getBean("jedis");
    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse rep,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;
        String serverName = request.getServerName();
        request.setAttribute(CmsConstant.SERVER_NAME, serverName);
        String loginIndexUrl = "http://" + serverName + "/html/login.index.do";
        String manageIndexUrl = "http://" + serverName + "/html/login.manageIndex.do#report.UI.do";
        final String requestUri = request.getRequestURI();
        String cookieDomain = request.getServerName();
        request.setAttribute("cookie_domain", cookieDomain);

        if (requestUri.endsWith("/assets/") || requestUri.endsWith("/images/") ||
                requestUri.endsWith("/css/") || requestUri.endsWith("/js/") ||
                requestUri.endsWith("/img/") || requestUri.endsWith("/image/")||
                requestUri.endsWith("/fonts/")) {
            response.setStatus(403);
            return;
        }

        if (requestUri.contains("/assets/") || requestUri.contains("/images/") ||
                requestUri.contains("/css/") || requestUri.contains("/js/") ||
                requestUri.contains("/img/") || requestUri.contains("/image/")||
                requestUri.contains("/fonts/")) {
            chain.doFilter(req, rep);
            return;
        }

        String aceSessionId = CookieUtil.getCookieValue(request, CmsConstant.MYJ_SESSION_ID);

        // 用户没有登录
        if (StringUtils.isBlank(aceSessionId)) {
            //登陆页面跳过
            if (requestUri.endsWith("/html/login.index.do") ||
                    requestUri.endsWith("/html/login.login.do") ||
                    requestUri.endsWith("/html/login.genSecurityCode.do")) {
                chain.doFilter(req, rep);
                return;
            }

            String ace=req.getParameter("ace");
            if ("ajax".equals(ace)){
                response.getWriter().print("<script>window.location.href='"+loginIndexUrl+"'</script>");
            }

            String ajaxSubmit = request.getHeader("X-Requested-With");
            if(ajaxSubmit != null && ajaxSubmit.equals("XMLHttpRequest")){
                response.getWriter().print(JsonUtil.obj2Json(JsonResultUtil.location(loginIndexUrl), false));
            }else {
                ((HttpServletResponse) rep).sendRedirect(loginIndexUrl);
            }
        } else {
            List<String> list = jedis.hmget(aceSessionId, "corp_code", "user_id", "user_name","role_id","role_code","parent_corp_code");
            if (CollectionUtils.isEmpty(list) || StringUtils.isBlank(list.get(0))){
                //解决多人登录，有人退出，redis取不到session，页面报错问题
                Cookie sessionCookie = CookieUtil.getCookie(request, CmsConstant.MYJ_SESSION_ID);
                CookieUtil.deleteCookie(response, sessionCookie, serverName,CmsConstant.COOKIE_PATH);
                ((HttpServletResponse) rep).sendRedirect(loginIndexUrl);
                return;
            }

            if (requestUri.endsWith("/html/login.index.do") ||
                    requestUri.endsWith("/html/login.login.do") ||
                    requestUri.equals("/")) {
                ((HttpServletResponse) rep).sendRedirect(manageIndexUrl);
                return;
            }

            //default运维权限过滤
            /*if (requestUri.contains("/corp.") && !ExecutionContext.getCorpCode().equals("default")) {
                response.setStatus(403);
                return;
            }*/

            //设置session
            jedis.expire(aceSessionId, CmsConstant.SESSION_EXPIRE_TIME);
            String key = CmsConstant.MYJ_SESSION_ID +
                    CmsConstant.REDIS_KEY_CONTACT + list.get(1) +
                    CmsConstant.REDIS_KEY_CONTACT + list.get(2);
            jedis.expire(key, CmsConstant.SESSION_EXPIRE_TIME);
            Map<String, String> sessionMap = new HashMap<String, String>();
            sessionMap.put("ace_session_id", aceSessionId);
            String corpCode = list.get(0);
            sessionMap.put("corp_code", corpCode);
            sessionMap.put("user_id", list.get(1));
            sessionMap.put("user_name", list.get(2));
            sessionMap.put("role_id",list.get(3));
            sessionMap.put("role_code",list.get(4));
            sessionMap.put("parent_corp_code",list.get(5));
            String clientIp = request.getHeader("X-FORWARDED-FOR");
            if (StringUtils.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
                clientIp = request.getRemoteAddr();
            }

            sessionMap.put("client_ip", clientIp);
            request.setAttribute("ace_session_map", sessionMap);
            ExecutionContext.setContextMap(sessionMap);
            ExecutionContext.setAppCode(BaseConstant.MYM_PC);
            ExecutionContext.setDeviceType(BaseConstant.PC);

            //设置cookie
            CookieUtil.setCookie(response, CmsConstant.MYJ_SESSION_ID, aceSessionId,
                    CmsConstant.COOKIE_PATH, "30", cookieDomain);
            CookieUtil.setCookie(response, CmsConstant.CORP_CODE, corpCode,
                    CmsConstant.COOKIE_PATH, "30", cookieDomain);

            chain.doFilter(req, rep);
        }
    }


    @Override
    public void destroy() {
        jedis = null;
    }

}
