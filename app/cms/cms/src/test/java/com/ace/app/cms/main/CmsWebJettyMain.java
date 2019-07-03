package com.ace.app.cms.main;

import com.ace.framework.util.ExecutionContext;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class CmsWebJettyMain {

    public static void main(String[] args) throws Exception {

        ExecutionContext.setCorpCode("default");
        ExecutionContext.setAppCode("cms");
        Server jettyServer = new Server();
//test push
        SocketConnector conn = new SocketConnector();
        conn.setPort(80);
        jettyServer.setConnectors(new Connector[]{conn});
        WebAppContext wah = new WebAppContext();
        wah.setContextPath("/");
        wah.setWar("src/main/webapp");
        jettyServer.setHandler(wah);
        jettyServer.start();

    }

}
