package com.ace.framework.base.freemarker;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import javax.servlet.ServletContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * @author WuZhiWei
 * @since 2016-01-19 13:20
 */
public class MyFreemarkerManager extends FreemarkerManager
{
    private FreeMarkerConfigurer freemarkerConfig;

    protected Configuration createConfiguration(ServletContext servletContext)
            throws TemplateException
    {
        return this.freemarkerConfig.getConfiguration();
    }

    public FreeMarkerConfigurer getFreemarkerConfig() {
        return this.freemarkerConfig;
    }

    public void setFreemarkerConfig(FreeMarkerConfigurer freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }
}
