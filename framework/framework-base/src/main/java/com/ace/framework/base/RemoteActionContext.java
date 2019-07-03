package com.ace.framework.base;

import com.ace.framework.util.redis.MyjJedisCommend;
import com.opensymphony.xwork2.ActionContext;

import java.util.Map;

/**
 * @author WuZhiWei
 * @since 2015-11-23 10:24:00
 */
public class RemoteActionContext extends ActionContext{

    private RemoteSession remoteSession;

    public RemoteActionContext(ActionContext actionContext,String sessionId) {
        super(actionContext.getContextMap());
        remoteSession = new RemoteSession(sessionId);
    }

    //在获取RemoteSession 就注入jedis
    public RemoteSession getRemoteSession(MyjJedisCommend jedis){
        remoteSession.setJedis(jedis);
        return this.remoteSession;
    }

    @Override
    public Map<String, Object> getSession() {
        return remoteSession;
    }
}
