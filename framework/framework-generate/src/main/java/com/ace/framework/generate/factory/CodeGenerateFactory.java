package com.ace.framework.generate.factory;

import com.ace.framework.generate.ColumnData;
import com.ace.framework.generate.CommonPageParser;
import com.ace.framework.generate.CreateBean;
import com.ace.framework.generate.bytecode.ClazzTool;
import com.ace.framework.generate.def.CodeResourceUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author WuZhiWei
 * @since 2015-11-10 16:26
 */
public class CodeGenerateFactory {
    private static final Log log = LogFactory.getLog(CodeGenerateFactory.class);
    private static String url = CodeResourceUtil.URL;
    private static String username = CodeResourceUtil.USERNAME;
    private static String passWord = CodeResourceUtil.PASSWORD;

    private static String buss_package = CodeResourceUtil.bussiPackage;
    private static String projectPath = getProjectPath();


    // annotation
    public static final String ANNOTATION = "annotation";
    public static final String ANNOTATION_AUTHOR_NAME = "代码生成器 v1.0";
    public static final String ANNOTATION_VERSION = "1.0";

    // date formate
    public static final String DATE_FROMATE = "yyyy-MM-dd HH:mm:ss";

    public static void codeGenerate(String tableName, String codeName,String codeTarget,String module, String logicModule,String clazzName, boolean isGenerateJsp) {
        CreateBean createBean = new CreateBean();
        createBean.setMysqlInfo(url, username, passWord);

        String className = createBean.getTablesNameToClassName(tableName);
        String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());
        String lowerClazzName=clazzName.substring(0, 1).toLowerCase() + clazzName.substring(1, clazzName.length());
//        String srcPath = projectPath +"\\"+module+"\\" +CodeResourceUtil.source_root_package + "\\";
        String srcPath = codeTarget +"\\"+module+"\\" +CodeResourceUtil.source_root_package + "\\";

        String pckPath = srcPath + CodeResourceUtil.bussiPackageUrl + "\\"+module+"\\";

        String beanSrcPath= codeTarget +"\\"+module+"-api\\" +CodeResourceUtil.source_root_package + "\\"+ CodeResourceUtil.bussiPackageUrl + "\\"+module+"\\";

        String webPath = projectPath + CodeResourceUtil.web_root_package + "\\WEB-INF\\page\\"+logicModule+"\\"+lowerClazzName+"\\" ;


        String beanPath = logicModule+"\\domain\\" + clazzName + ".java";
        String daoPath = logicModule+"\\dao\\" + clazzName + "Dao.java";
        String servicePath =logicModule+ "\\service\\" + clazzName + "Service.java";
        String servicePathImpl =logicModule+ "\\service\\impl\\" + clazzName + "ServiceImpl.java";
        String modelPath = logicModule+"\\model\\" + clazzName + "Model.java";
        String actionPath = logicModule+"\\action\\" + clazzName + "Action.java";
        String sqlMapperPath = logicModule+"\\domain\\" + clazzName + ".xml";

       /* String controllerPath = "controller\\" + entityPackage + "\\" + className + "Controller.java";

        webPath = webPath + entityPackage + "\\";*/

        String jspPath = lowerName + ".ftl";
        String jsPath = "page-" + lowerName + ".js";

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("className", className);
        context.put("lowerName", lowerName);
        context.put("codeName", codeName);
        context.put("tableName", tableName);
        context.put("bussPackage", buss_package);


        context.put("module", module);
        context.put("logicModule", logicModule);
        context.put("clazzName", clazzName);
        context.put("lowerClazzName", lowerClazzName);


        Annotation annotation = new Annotation();
        annotation.setAuthorName(ANNOTATION_AUTHOR_NAME);
        annotation.setVersion(ANNOTATION_VERSION);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FROMATE);
        annotation.setDate(simpleDateFormat.format(new Date()));
        context.put(ANNOTATION, annotation);

        try {


            List<ColumnData> beanDates = createBean.getBeanDates(tableName);
            List<ColumnData> beanDatesPage= new ArrayList<>();
            for (ColumnData beanDate : beanDates) {
                if ("id".equals(beanDate.getColumnName())){
                    continue;
                }

                beanDatesPage.add(beanDate);
            }
            context.put("beanDates", beanDatesPage);
            Class<?> clazz = ClazzTool.builderClazz(beanDates, clazzName, buss_package + "." + module + "." + logicModule + ".domain");
            Long serialVersionUID=ClazzTool.getSerialVersionUID(clazz);
            context.put("serialVersionUID", serialVersionUID+"");
            context.put("feilds", createBean.getBeanFeilds(tableName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Map sqlMap = createBean.getAutoCreateSql(tableName);
            context.put("columnDatas", createBean.getColumnDatas(tableName));
            context.put("SQL", sqlMap);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        CommonPageParser.WriterPage(context, "EntityTemplate.ftl", beanSrcPath, beanPath);
        CommonPageParser.WriterPage(context, "MapperTemplate.ftl", pckPath, sqlMapperPath);
        CommonPageParser.WriterPage(context, "DaoTemplate.ftl", pckPath, daoPath);
        CommonPageParser.WriterPage(context, "ServiceTemplate.ftl", beanSrcPath, servicePath);
        CommonPageParser.WriterPage(context, "ServiceImplTemplate.ftl", pckPath, servicePathImpl);
        CommonPageParser.WriterPage(context, "ModelTemplate.ftl", pckPath, modelPath);
        CommonPageParser.WriterPage(context, "ActionTemplate.ftl", pckPath, actionPath);
        CommonPageParser.WriterPage(context, "AddOrEditTemplate.ftl", webPath, "addOrEdit.ftl");
        CommonPageParser.WriterPage(context, "IndexTemplate.ftl", webPath,"index.ftl");

       /* CommonPageParser.WriterPage(context, "PageTemplate.ftl", pckPath, modelPath);



        CommonPageParser.WriterPage(context, "ControllerTemplate.ftl", pckPath, controllerPath);

        CommonPageParser.WriterPage(context, "jsTemplate.ftl", webPath, jsPath);

        if (isGenerateJsp) {
            CommonPageParser.WriterPage(context, "jspTemplate.ftl", webPath, jspPath);
            CommonPageParser.WriterPage(context, "jsTemplate.ftl", webPath, jsPath);
        }*/
        log.info("----------------------------代码生成完毕---------------------------");
    }

    public static String getProjectPath() {
        String path = System.getProperty("user.dir").replace("\\", "/") + "/";
        return path;
    }
}