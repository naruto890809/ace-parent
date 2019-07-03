package com.ace.framework.base;

import com.ace.framework.util.ExecutionContext;
import com.ace.framework.util.UUIDUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author WuZhiWei
 * @since 2015-11-23 11:04:00
 */
public class RemoteSessionInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = -7645085314244367241L;

    private static final String SESSION_KEY = "r_session_id";
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        //处理ip
        HttpServletRequest request = ServletActionContext.getRequest();
        String clientIp = request.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isBlank(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        ExecutionContext.setClientIp(clientIp);

        //请求header中取sessionId
        String sessionId = request.getHeader(SESSION_KEY);
        if (StringUtils.isBlank(sessionId)){
            sessionId = request.getParameter("r_session_id");
        }
        if(StringUtils.isBlank(sessionId)){
            //请求cookie中取sessionId
            Cookie[] cookies = request.getCookies();
            if(cookies!=null){
                for(Cookie cookie : cookies) {
                    if(cookie.getName().equals(SESSION_KEY)) {
                        sessionId =  cookie.getValue();
                    }
                }
            }
        }
        if(StringUtils.isBlank(sessionId)){
            //未取到sessionId,生成sessionId
            sessionId = UUIDUtil.genUUID();
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        //header中增加sessionId
        response.setHeader(SESSION_KEY, sessionId);

        Cookie cookie = new Cookie(SESSION_KEY,sessionId);
        //cookie中存30分钟sessionId
        cookie.setMaxAge(1800);
        cookie.setPath("/");
        cookie.setDomain("127.0.0.1");;
        response.addCookie(cookie);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type,r_session_id");
        ActionContext.setContext(new RemoteActionContext(ActionContext.getContext(), sessionId));
        return actionInvocation.invoke();
    }
}
