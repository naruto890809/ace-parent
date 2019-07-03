package com.ace.app.cms;



import java.util.List;

/**
* 销售区域
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-06 21:00:55
*/
public interface ExcelService<T> {

    List findListForExport(T condition);

    String importBatch(List<T> list,String extParam);
}
