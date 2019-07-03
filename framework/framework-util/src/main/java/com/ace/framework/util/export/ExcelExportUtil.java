package com.ace.framework.util.export;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;

/**
 * Excel导出工具类
 *
 * @author WuZhiWei@HF
 * @since 2015-12-08 18:03:44
 */
public class ExcelExportUtil {

    private static Log log = LogFactory.getLog(ExcelExportUtil.class);

    static {
        try {
            storeFilePath = PropertiesLoaderUtils.loadAllProperties("env.properties").getProperty("common.storedFilePath");
        } catch (IOException e) {
            log.error("Get store file path from [env.properties] with key [common.storedFilePath] failed !", e);
            storeFilePath = "/web/aceData/";
        }
    }

    //storeFilePath
    private static String storeFilePath;

    //空单元格的value值
    public static final String EMPTY_CELL_VALUE = "-";
    //Excel 2003文件的后缀名
    public static final String XLS_2003_SUFFIX = ".xls";
    //Excel 2007文件的后缀名
    public static final String XLS_2007_SUFFIX = ".xlsx";
    //zip格式压缩文件的文件名后缀
    public static final String ZIP_SUFFIX = ".zip";

    /**
     * 获取ace工程存放临时文件目录的路径
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
}
