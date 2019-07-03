package com.ace.framework.util.export;

import com.ace.framework.util.UUIDUtil;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel模板导出工具类
 *
 * @author WuZhiWei@HF
 * @since 2015-4-16
 */
public class ExcelTemplateExportUtil {

    static Log log = LogFactory.getLog(ExcelTemplateExportUtil.class);

    static {
        try {
            storeFilePath = PropertiesLoaderUtils.loadAllProperties("env.properties").getProperty("common.storedFilePath");
        } catch (IOException e) {
            log.error("Get store file path from [env.properties] with key [common.storedFilePath] failed !", e);
            storeFilePath = "/web/aceData/";
        }
    }

    private static String storeFilePath;

    /**
     * 获取ace工程存放临时文件目录的路径s
     *
     * @return ace存放临时文件目录的路径，如果没有则创建一个，如果创建失败，则使用工程路径
     */
    public static String getTempDirPath() {
        String tempDirPath = storeFilePath + "ace/";
        File file = new File(tempDirPath);
        if (!file.exists()) {
            boolean isSuccess = file.mkdirs();
            //如果为创建成功，则使用工程路径
            if (!isSuccess) {
                log.error("Create temp dir path failed with tempDirPath is : " + tempDirPath);
                return ServletActionContext.getServletContext().getRealPath("temp");
            }
        }
        return tempDirPath;
    }

    /**
     * 生成导出的Excel文件
     * 生成的Excel文件是Excel 2003版本的，以 .xls 为后缀名
     *
     * @param templateFilePath 导出模版的路径地址，例如：/WEB-INF/page/export/***.xls
     * @param excelFileName    导出Excel文件的文件名，不需要包含 .xls
     * @param dataList         要导出的数据列表
     * @return 导出成功，则返回true，否则返回false
     */
    public static boolean generateExportExcelFile(String templateFilePath, String excelFileName, List<?> dataList) {
        Assert.hasLength(templateFilePath, "Template file path is empty while generate export excel file !");
        Assert.hasLength(excelFileName, "Excel file name is empty while generate export excel file !");
        Assert.notNull(dataList, "Data list is empty while generate excel file !");

        boolean isSuccess = true;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("dataList", dataList);
        XLSTransformer transformer = new XLSTransformer();
        try {
            String tempDirPath = getTempDirPath();
            String tempFilePath = tempDirPath + excelFileName + ".xls";
            transformer.transformXLS(ServletActionContext.getServletContext().getRealPath(templateFilePath), paramMap, tempFilePath);
        } catch (Exception e) {
            isSuccess = false;
            log.error("Exception occurred while generate export excel file !", e);
        }
        return isSuccess;
    }

    /**
     * 生成导出的Excel文件，并返回导出Excel文件的输入流
     * 生成的Excel文件是Excel 2003版本的
     *
     * @param templateFilePath 导出模版的路径地址，例如：/WEB-INF/page/export/***.xls
     * @param dataList         要导出的数据列表
     * @return 导出Excel文件成功，则返回该文件的输入流，否则返回null
     */
    public static InputStream generateAndGetExportFileInputStream(String templateFilePath, List<?> dataList) {
        Assert.hasLength(templateFilePath, "Template file path is empty while get export excel file input stream !");
        Assert.notNull(dataList, "Data list is empty while get export excel file input stream !");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("dataList", dataList);
        XLSTransformer transformer = new XLSTransformer();
        try {
            String tempDirPath = getTempDirPath();
            String tempFilePath = tempDirPath + UUIDUtil.genUUID() + ".xls";
            transformer.transformXLS(ServletActionContext.getServletContext().getRealPath(templateFilePath), paramMap, tempFilePath);
            return new FileInputStream(tempFilePath);
        } catch (Exception e) {
            log.error("Exception occurred while get export excel file input stream !", e);
        }
        return null;
    }

    /**
     * 根据Excel的文件名，获取该Excel文件的输入流
     *
     * @param excelFileName Excel的文件名
     * @return 返回该Excel文件的输入流，如果失败，则返回null
     */
    public static InputStream getFileInputStream(String excelFileName) {
        Assert.hasLength(excelFileName, "Excel file name is empty while get file input stream !");

        String filePath = getTempDirPath() + excelFileName + ".xls";
        File file = new File(filePath);
        try {
            return new FileInputStream(file);
        } catch (Exception e) {
            log.error("Exception occurred while get file input stream with excel file name is : " + excelFileName, e);
        }
        return null;
    }
}
