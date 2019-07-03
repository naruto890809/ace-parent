package com.ace.framework.util.export;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.TempFile;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Excel多sheet导出的工具类
 *
 * @author WuZhiWei@HF
 * @since 2015-4-24
 */
public class ExcelMultiSheetExport {

    private static Log log = LogFactory.getLog(ExcelMultiSheetExport.class);

    //Excel2003单个sheet存放的最大数据条数
    private static final int EXCEL_2003_SHEET_MAX_ROW_NUM = 60000;
    //Excel2007单个sheet存放的最大数据条数
    private static final int EXCEL_2007_SHEET_MAX_ROW_NUM = 1000000;

    //是否冻结标题行，默认为true
    private static boolean isFreezeTitleRow = true;
    //Excel 2007生成过程中临时文件的目录
    private static SpecifiedTempFileCreationStrategy tempFileCreationStrategy;

    /**
     * 生成Excel 2003文件，每个sheet放60000条，对于总条数超过60000条的数据，则分多个sheet导出。
     * <br>这个方法适用于导出几十万级别的数据。注意在导出海量数据时，会占据很大的内存，可能会导致内存溢出。
     * <br>如果需要导出百万级的数据，可以使用 generateMultiSheetExcel2007 这个方法，注意这个方法生成的是 Excel 2007格式的文件。
     *
     * @param excelFileName  要导出Excel文件的文件名
     * @param excelTitleList ExcelTitle列表
     * @param dataList       数据列表
     */
    public static void generateMultiSheetExcel2003(String excelFileName, List<ExcelTitle> excelTitleList, List<?> dataList) {
        Assert.hasLength(excelFileName, "Excel file Name is empty while generate multi sheet excel file !");
        Assert.notEmpty(dataList, "Data list is empty while generate multi sheet excel file !");
        Assert.notEmpty(excelTitleList, "Excel title list is empty while generate multi sheet excel file !");

        HSSFWorkbook workbook = new HSSFWorkbook();
        String excelFilePath = ExcelExportUtil.getTempDirPath() + excelFileName + ExcelExportUtil.XLS_2003_SUFFIX;
        generateMultiSheetExcelFile(excelFilePath, excelTitleList, dataList, workbook, EXCEL_2003_SHEET_MAX_ROW_NUM);
    }

    /**
     * 生成Excel 2007文件，每个sheet放60000条，对于总条数超过60000条的数据，则分多个sheet导出。
     * <br>这个方法在内存中始终只保留10000条Excel的Row，及时的释放了内存，适用于大数据量的导出。
     *
     * @param excelFileName  要导出Excel文件的文件名
     * @param excelTitleList ExcelTitle列表
     * @param dataList       数据列表
     */
    public static void generateMultiSheetExcel2007(String excelFileName, List<ExcelTitle> excelTitleList, List<?> dataList) {
        Assert.hasLength(excelFileName, "Excel file Name is empty while generate multi sheet excel file !");
        Assert.notEmpty(dataList, "Data list is empty while generate multi sheet excel file !");
        Assert.notEmpty(excelTitleList, "Excel title list is empty while generate multi sheet excel file !");

        //在内存中只存放10000条Excel的Row
        SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
        TempFile.setTempFileCreationStrategy(getTempFileCreationStrategy());
        String excelFilePath = ExcelExportUtil.getTempDirPath() + excelFileName + ExcelExportUtil.XLS_2007_SUFFIX;
        generateMultiSheetExcelFile(excelFilePath, excelTitleList, dataList, workbook, EXCEL_2007_SHEET_MAX_ROW_NUM);
    }

    /*
    生成多sheet的Excel文件
     */
    private static void generateMultiSheetExcelFile(String excelFilePath, List<ExcelTitle> excelTitleList, List<?> dataList, Workbook workbook, int maxRowNumber) {
        CellStyle titleCellStyle = getTitleRowCellStyle(workbook);
        CellStyle commonCellStyle = getCommonCellStyle(workbook);
        int totalDataSize = dataList.size();
        int sheetNum = getSheetNum(maxRowNumber, totalDataSize);
        Class<?> clazz = dataList.get(0).getClass();

        for (int i = 0; i < sheetNum; i++) {
            Sheet sheet = workbook.createSheet("数据列表" + (i + 1));
            setTitleRow(sheet, titleCellStyle, excelTitleList);

            int lastIndexNum = (i + 1) * maxRowNumber;
            if (lastIndexNum > totalDataSize) {
                lastIndexNum = totalDataSize;
            }
            List<?> subDataList = dataList.subList(i * maxRowNumber, lastIndexNum);
            for (int j = 0; j < subDataList.size(); j++) {
                Object data = subDataList.get(j);
                Row row = sheet.createRow(j + 1);
                for (int k = 0; k < excelTitleList.size(); k++) {
                    ExcelTitle excelTitle = excelTitleList.get(k);
                    String fieldName = excelTitle.getFieldName();
                    Object fieldValue;
                    try {
                        String getMethodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                        Method method = clazz.getMethod(getMethodName);
                        fieldValue = method.invoke(data);
                    } catch (Exception e) {
                        log.error("Exception occurred while get filed value with filed name is : " + fieldName, e);
                        fieldValue = ExcelExportUtil.EMPTY_CELL_VALUE;
                    }
                    if (fieldValue == null) {
                        fieldValue = ExcelExportUtil.EMPTY_CELL_VALUE;
                    }

                    Cell cell = row.createCell(k);
                    cell.setCellStyle(commonCellStyle);
                    if (fieldValue instanceof Number) {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Double.valueOf(fieldValue.toString()));
                    } else {
                        cell.setCellValue(fieldValue.toString());
                    }
                }
            }

        }

        try {
            FileOutputStream fos = new FileOutputStream(excelFilePath);
            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            log.error("Error occurred while get file output stream while file path is : " + excelFilePath, e);
        }

        isFreezeTitleRow = true;
    }

    /**
     * 获取标题行的单元格样式
     *
     * @param workbook workbook
     * @return 单元格样式对象
     */
    private static CellStyle getTitleRowCellStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(IndexedColors.BLACK.getIndex());

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setBorderBottom((short) 1);
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        return cellStyle;
    }

    /**
     * 获取普通单元格的样式
     *
     * @param workbook workbook
     * @return 单元格样式对象
     */
    private static CellStyle getCommonCellStyle(Workbook workbook){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

    /**
     * 获取sheet的数量
     *
     * @param totalDataSize 数据的总条数
     * @return sheet的数量
     */
    private static int getSheetNum(int maxRowNumber, int totalDataSize) {
        int sheetNum = totalDataSize / maxRowNumber;
        if (totalDataSize % maxRowNumber > 0) {
            sheetNum++;
        }
        return sheetNum;
    }

    /*
    设置标题行的信息
     */
    private static void setTitleRow(Sheet sheet, CellStyle titleCellStyle, List<ExcelTitle> excelTitleList) {
        if(ExcelMultiSheetExport.isFreezeTitleRow){
            //设置首行冻结
            sheet.createFreezePane(0,1);
        }

        Row titleRow = sheet.createRow(0);
        for (int index = 0; index < excelTitleList.size(); index++) {
            ExcelTitle excelTitle = excelTitleList.get(index);
            String titleName = excelTitle.getTitleName();
            int columnWidth = excelTitle.getColumnWidth();
            sheet.setColumnWidth(index, columnWidth);

            titleRow.setHeight((short) 370);
            Cell cell = titleRow.createCell(index);
            cell.setCellStyle(titleCellStyle);
            cell.setCellValue(titleName);
        }
    }

    /**
     * 设置是否冻结标题行
     *
     * @param isFreezeTitleRow 是否冻结标题行
     */
    public static void setIsFreezeTitleRow(boolean isFreezeTitleRow) {
        ExcelMultiSheetExport.isFreezeTitleRow = isFreezeTitleRow;
    }

    /**
     * 获取Excel 2007生成过程中临时文件目录策略的对象
     * @return 临时目录策略对象
     */
    private static SpecifiedTempFileCreationStrategy getTempFileCreationStrategy() {
        if(ExcelMultiSheetExport.tempFileCreationStrategy == null){
            String tempDirPath = ExcelExportUtil.getTempDirPath();
            ExcelMultiSheetExport.tempFileCreationStrategy = new SpecifiedTempFileCreationStrategy(new File(tempDirPath));
        }

        return tempFileCreationStrategy;
    }

}
