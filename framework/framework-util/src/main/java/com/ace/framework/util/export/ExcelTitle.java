package com.ace.framework.util.export;

import org.springframework.util.Assert;

/**
 * 导出Excel的标题类
 *
 * @author WuZhiWei@HF
 * @since 2015-4-24
 */
public class ExcelTitle {

    //标题名称
    private String titleName;
    //标题对应的对象的属性名
    private String fieldName;
    //该标题这一列的列宽
    private int columnWidth;

    public ExcelTitle(String titleName, String fieldName) {
        Assert.hasLength(titleName, "Title name is empty while create excel title !");
        Assert.hasLength(fieldName, "Field name is empty while create excel title !");

        this.titleName = titleName;
        this.fieldName = fieldName;
        this.columnWidth = 5120;
    }

    public ExcelTitle(String titleName, String fieldName, int columnWidth) {
        Assert.hasLength(titleName, "Title name is empty while create excel title !");
        Assert.hasLength(fieldName, "Field name is empty while create excel title !");
        if (columnWidth <= 0) {
            columnWidth = 5120;
        }

        this.titleName = titleName;
        this.fieldName = fieldName;
        this.columnWidth = columnWidth;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }
}
