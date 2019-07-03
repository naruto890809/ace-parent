package com.ace.framework.generate;

import com.ace.framework.generate.factory.CodeGenerateFactory;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Locale;
import java.util.Map;

/**
 * @author WuZhiWei
 * @since 2015-11-10 16:04
 */
public class CommonPageParser
{

    private static final String CONTENT_ENCODING = "UTF-8";
    private static final Log log = LogFactory.getLog(CommonPageParser.class);

    private static boolean isReplace = true;
    private static Configuration configuration;


    public static void WriterPage( Map<String, Object> data, String templateName, String fileDirPath, String targetFile)
    {
        try
        {
           String templateBasePath=CommonPageParser.class.getClassLoader().getResource("ace/ace_config.properties").getPath().replace("/ace_config.properties","")+"/template";//CodeGenerateFactory.getProjectPath() +frameworkPath+ "/ace/template";
            templateBasePath=templateBasePath.replace("%20"," ");
            // 创建Freemarker配置实例
            configuration = new Configuration();
            configuration.setDirectoryForTemplateLoading(new File(templateBasePath));
            configuration.setEncoding(Locale.getDefault(), "UTF-8");
            // 设置对象的包装器
            configuration.setObjectWrapper(new DefaultObjectWrapper());
            // 设置异常处理器//这样的话就可以${a.b.c.d}即使没有属性也不会出错
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

            Template template = configuration.getTemplate(templateName);
            template.setEncoding("UTF-8");

            File file = new File(fileDirPath + targetFile);
            if (!file.exists()) {
                new File(file.getParent()).mkdirs();
            }
            else if (isReplace) {
                log.info("替换文件:" + file.getAbsolutePath());
            }

            // 生成静态页面
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            template.process(data, out);
            out.flush();
            out.close();
            log.info("生成文件：" + file.getAbsolutePath());
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
}
