package com.ace.app.cms.blob;

import com.ace.framework.base.BaseDao;
import com.ace.framework.util.ExecutionContext;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 大字段dao服务
 * @author WuZhiWei
 * @since 2015年11月19日 16:52:15
 */
@Repository
public class BlobDao extends BaseDao<Blob> {

    private static final String STATEMENT = "com.ace.app.cms.blob.Blob.";


    public String save(Blob blob) {
        Date nowTime = new Date();
        blob.setCreateTime(nowTime);
        blob.setLastModifyTime(nowTime);
        blob.setCreateBy(ExecutionContext.getUserId());
        blob.setLastModifyBy(ExecutionContext.getUserId());

        insert(blob);
        return blob.getBlobId();
    }

    public void updateTxtValue (String resourceId, String columnName, String txtValue){
        Blob blob = getBlobCondition(resourceId, columnName);
        blob.setTxtValue(txtValue);
        update(STATEMENT + "updateTxtValue", blob);
    }

    public String getColumnValue(String resourceId, String columnName) {
        Blob blob = getBlobCondition(resourceId, columnName);

        return getOne(STATEMENT + "getColumnValue" ,blob);
    }

    public String getColumnValueByBlobId(String blobId) {
        return getOne(STATEMENT + "getColumnValueByBlobId" ,blobId);
    }

    private Blob getBlobCondition(String resourceId, String columnName) {
        Blob blob = new Blob();
        blob.setResourceId(resourceId);
        blob.setColumnName(columnName);
        blob.setCorpCode(ExecutionContext.getCorpCode());
        return blob;
    }

    public List<Blob> getColumnNameAndTxtValue(String resourceId, List<String> columnNames) {
        Blob blob = new Blob();
        blob.setResourceId(resourceId);
        blob.setCorpCode(ExecutionContext.getCorpCode());
        blob.setColumnNames(columnNames);

        return findList(STATEMENT + "getColumnNameAndTxtValue" ,blob);
    }

    public void deleteByResourceIdAndColumnName(String resourceId,String columnName) {
        Blob blob = getBlobCondition(resourceId, columnName);
        delete(STATEMENT + "deleteByResourceIdAndColumnName" ,blob);
    }

    public int updateSelect(Blob blob){
        return update(STATEMENT +"updateSelect",blob);
    }
}
