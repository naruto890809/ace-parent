package com.ace.app.cms.common;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.ace.app.cms.ExcelService;
import com.ace.app.cms.drug.domain.Order;
import com.ace.app.cms.drug.service.OrderService;
import com.ace.app.cms.util.ExcelUtils;
import com.ace.framework.base.BaseAction;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.util.DateUtil;
import com.ace.framework.util.ExecutionContext;
import com.ace.framework.util.PropertiesUtil;
import com.ace.framework.util.SpringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* 通用生成器
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-06 23:03:54
*/
@Scope("prototype")
public class CommonAction extends BaseAction<CommonModel> {
    public static final Log logger = LogFactory.getLog(CommonAction.class);
    @Resource
    private OrderService orderService;
    public static  String FILE_PATH;

    static {
        try {
            if(File.separator.equals("/")){
                FILE_PATH = PropertiesUtil.getEnv("file_path_linux");
            }else{
                FILE_PATH = PropertiesUtil.getEnv("file_path_windows");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //导出
    public void export() throws Exception {
        String fileName = model.getFileName();
        String exportClass = model.getExportClass();
        Assert.hasText(fileName);
        Assert.hasText(exportClass);
        Class<?> clazz = Class.forName(exportClass);

        String tmpClazzName = getClazzName(exportClass);
        String serviceName = tmpClazzName + "Service";
        ExcelService excelService = SpringUtil.getBean(serviceName, ExcelService.class);

        Map<String,Object[]> parameterMap = request.getParameterMap();
        Object object = clazz.newInstance();
        if (MapUtils.isNotEmpty(parameterMap)){
            String paramPre = tmpClazzName+".";
            for (Map.Entry<String, Object[]> entry : parameterMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue()[0];
                if (value.toString().split("-").length==3){
                    value = DateUtil.convert(value.toString());
                }
                String valueStr = value.toString();
                if (StringUtils.isBlank(valueStr) || value == null || !key.startsWith(paramPre)){
                    continue;
                }

                String[] split = key.split("\\.");

                String name = split[1];
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                field.set(object,value);
            }
        }

        ExcelUtils.exportExcel(excelService.findListForExport(object),  clazz, fileName+".xlsx", response);
    }


    //导出
    public void exportReport() throws Exception {
        String fileName = model.getFileName();
        String exportClass = model.getExportClass();
        Assert.hasText(fileName);
        Assert.hasText(exportClass);
        Class<?> clazz = Class.forName(exportClass);

        String tmpClazzName = getClazzName(exportClass);

        Map<String,Object[]> parameterMap = request.getParameterMap();
        Order object = model.getOrder();
        if (MapUtils.isNotEmpty(parameterMap)){
            String paramPre = tmpClazzName+".";
            for (Map.Entry<String, Object[]> entry : parameterMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue()[0];
                if (value == null || !key.startsWith(paramPre)){
                    continue;
                }

                String[] split = key.split("\\.");

                String name = split[1];
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                field.set(object,value);
            }
        }

        ExcelUtils.exportExcel(orderService.findListForReport(object),  clazz, fileName+".xlsx", response);
    }

    //导入页面
    public String importIndex() {
        super.setTarget("importIndex");
        return COMMON;
    }

    private String getClazzName(String exportClass) {
        String tmpClazzName = exportClass.substring(exportClass.lastIndexOf(".") + 1, exportClass.length());
        tmpClazzName = tmpClazzName.substring(0,1).toLowerCase()+tmpClazzName.substring(1,tmpClazzName.length());
        return tmpClazzName;
    }

    //导入表头选择
    public String returnImportHeads() throws ClassNotFoundException {
        String url = model.getFileUrl();
        String exportClass = model.getExportClass();
        Assert.hasText(url);
        Assert.hasText(exportClass);

        String filePath = FILE_PATH + url;
        ImportParams params = new ImportParams();

        List<Map<String, Object>> lists = null;
        try {
            lists = ExcelImportUtil.importExcel(
                    new File(filePath), Map.class, params);
        } catch (Exception e) {
            return renderJson(JsonResultUtil.err("表头不能为空"));
        }

        if (CollectionUtils.isEmpty(lists)){
            return renderJson(JsonResultUtil.err("数据为空，无法上传"));
        }

        Map<String, Object> map = lists.get(0);
        if (MapUtils.isEmpty(map)){
            return renderJson(JsonResultUtil.err("数据为空，无法上传"));
        }

        List<ImportEntity> importEntities = new ArrayList<>();
        ImportEntity importEntityTmp = new ImportEntity("请选择","",0);
        importEntities.add(importEntityTmp);

        Class<?> clazz = Class.forName(exportClass);
        Field[] fields =  clazz.getDeclaredFields();
        for (Field tmpField : fields) {//反射获取需要导入的属性
            Excel annotation = tmpField.getAnnotation(Excel.class);
            if (annotation == null){
                continue;
            }

            String field = tmpField.getName();
            String fieldName = annotation.name();
            ImportEntity importEntity = new ImportEntity(fieldName,field,0);
            importEntities.add(importEntity);
        }


        Map<String,Object> result = new HashMap<>(2);
        result.put("importEntities",importEntities);//对应字段设置
        List<ImportEntity> heads = new ArrayList<>(map.size());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key == null){
                key = "";
            }

            if (value == null){
                value = "";
            }


            ImportEntity importEntity = new ImportEntity(key, value.toString());
            heads.add(importEntity);
        }

        result.put("heads",heads);//对应字段设置
        return renderJson(JsonResultUtil.success(result));
    }

    //确定导入
    public String importExcel() throws Exception {
        ImportEntity importEntity = model.getImportEntity();
        List<ImportEntity> importEntities = importEntity.getChild();

        String filePath = FILE_PATH + importEntity.getUrl();
        ImportParams params = new ImportParams();
        List<Map<String, Object>> importExcelFieldList = ExcelImportUtil.importExcel(
                new File(filePath), Map.class, params);


        if (CollectionUtils.isEmpty(importExcelFieldList)){
            return  renderJson(JsonResultUtil.err("Excel数据不能为空"));
        }

        int totalCustomerCnt = importExcelFieldList.size();
        if (totalCustomerCnt < 1){
            return  renderJson(JsonResultUtil.err("数据为空，无法上传"));
        }

        if (totalCustomerCnt > 5000){
            return  renderJson(JsonResultUtil.err("最多支持5000条,请分批导入"));
        }

        Map<String,String> fieldNameFieldMap = new HashMap<>(importEntities.size());
        for (ImportEntity importEntityTmp : importEntities) {
            fieldNameFieldMap.put(importEntityTmp.getHeadName(),importEntityTmp.getField());
        }

        String exportClass = model.getExportClass();
        Class<?> clazz = Class.forName(exportClass);
        Map<String, Method> setMethods = getSetMethods(clazz);

        List objectList =new ArrayList<>(importExcelFieldList.size());
        for (Map<String, Object> map : importExcelFieldList) {
            if (MapUtils.isEmpty(map)){
                continue;
            }

            Object customer = clazz.newInstance();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String fieldName = entry.getKey();
                String field = fieldNameFieldMap.get(fieldName);
                if (StringUtils.isEmpty(field)){
                    continue;
                }

                Object value = entry.getValue();
                if (value == null){
                    continue;
                }

                Method method = setMethods.get(field);

                try {
                    String name = method.getParameterTypes()[0].getName();
                    if (name.endsWith("String")){
                        value = value.toString().trim();
                    }else if (name.contains("Integer")){
                        value = Integer.parseInt(value.toString());
                    }
                    method.invoke(customer, value);
                } catch (Exception e) {
                    logger.error("==== reflect invoke method error "+ e.getLocalizedMessage());
                }
            }

            objectList.add(customer);
        }

        if (CollectionUtils.isEmpty(objectList)){
            return renderJson(JsonResultUtil.success("Excel数据为空，无法导入"));
        }

        String tmpClazzName = getClazzName(exportClass);
        String serviceName = tmpClazzName + "Service";
        ExcelService excelService = SpringUtil.getBean(serviceName, ExcelService.class);
        String result = excelService.importBatch(objectList,importEntity.getFileName());
        return renderJson(JsonResultUtil.success(result));
    }

    private Map<String,Method> getSetMethods(Class clazz) {
        Field[] fields =  clazz.getDeclaredFields();
        Map<String,Method> result = new HashMap<>();
        for (Field tmpField : fields) {//反射获取需要导入的属性
            Excel annotation = tmpField.getAnnotation(Excel.class);
            if (annotation == null){
                continue;
            }

            StringBuffer sb = new StringBuffer();
            sb.append("set");
            String field = tmpField.getName();
            sb.append(field.substring(0, 1).toUpperCase());
            sb.append(field.substring(1));

            Class<?>[] aa = new Class[]{tmpField.getType()};
            Method method = null;
            try {
                method = clazz.getMethod(sb.toString(), aa);
            } catch (NoSuchMethodException e) {
                logger.error("==== reflect error " + e.getLocalizedMessage());
                e.printStackTrace();
            }

            result.put(field,method);
        }

        return result;
    }

    //导出模版
    public void exportTemplate() throws Exception {
        String fileName = "导入模版";
        String exportClass = model.getExportClass();
        Assert.hasText(fileName);
        Assert.hasText(exportClass);
        Class<?> clazz = Class.forName(exportClass);
        ExcelUtils.exportExcel(new ArrayList<>(),  clazz, fileName+".xlsx", response);
    }
}
