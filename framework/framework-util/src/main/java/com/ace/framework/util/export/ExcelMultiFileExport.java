package com.ace.framework.util.export;

import com.ace.framework.util.UUIDUtil;
import org.springframework.util.Assert;

import java.io.File;
import java.util.List;

/**
 * Excel多文件导出，导出的多个Excel文件进行压缩后返回zip格式的压缩文件
 *
 * @author WuZhiWei@HF
 * @since 2015-4-28
 */
public class ExcelMultiFileExport {

    //一个Excel存放的最大数据条数
    private static final int MAX_ONE_EXCEL_DATA_SIZE = 180000;

    /**
     * 获取导出Excel的数量
     *
     * @param totalDataSize 数据的总条数
     * @return Excel的数量
     */
    public static int getExcelNum(int totalDataSize) {
        int excelNum = totalDataSize / MAX_ONE_EXCEL_DATA_SIZE;
        if (totalDataSize % MAX_ONE_EXCEL_DATA_SIZE > 0) {
            excelNum++;
        }
        return excelNum;
    }

    /**
     * 生成导出的多个Excel文件的zip压缩文件。<br>
     * 这个方法用于大数据量的导出，能够有效的解决大数据量导出时内存溢出的问题（具体的数量级与机器配置和JVM配置有关）。<br>
     * 这个方法会先生成多个Excel,在每个Excel中存放180000条数据，然后把这些Excel压缩成zip格式的压缩文件。<br>
     *
     * @param zipFileName    生成压缩文件的文件名
     * @param excelTitleList ExcelTitle列表
     * @param dataList       要导出的数据列表
     */
    public static void generateMultiExcelZipFile(String zipFileName, List<ExcelTitle> excelTitleList, List<?> dataList) {
        Assert.hasLength(zipFileName, "Zip file Name is empty while generate multi sheet excel file !");
        Assert.notEmpty(dataList, "Data list is empty while generate multi sheet excel file !");
        Assert.notEmpty(excelTitleList, "Excel title list is empty while generate multi sheet excel file !");

        int totalDataSize = dataList.size();
        int excelNum = getExcelNum(totalDataSize);
        File[] excelFileArr = new File[excelNum];
        String tempDirPath = ExcelExportUtil.getTempDirPath();
        for (int i = 0; i < excelNum; i++) {
            int lastIndexNum = (i + 1) * MAX_ONE_EXCEL_DATA_SIZE;
            if (lastIndexNum > totalDataSize) {
                lastIndexNum = totalDataSize;
            }
            List<?> subDataList = dataList.subList(i * MAX_ONE_EXCEL_DATA_SIZE, lastIndexNum);
            String excelFileName = (i + 1) + "_" + UUIDUtil.genUUID();
            String excelFilePath = tempDirPath + excelFileName + ExcelExportUtil.XLS_2003_SUFFIX;
            excelFileArr[i] = new File(excelFilePath);
            ExcelMultiSheetExport.generateMultiSheetExcel2003(excelFileName, excelTitleList, subDataList);
        }

        String zipFilePath = tempDirPath + zipFileName + ExcelExportUtil.ZIP_SUFFIX;
        File zipFile = new File(zipFilePath);
        FileZipUtil.compressToZipFiles(excelFileArr, zipFile);
    }
}
