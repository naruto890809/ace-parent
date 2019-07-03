package com.ace.app.cms.blob;

import com.ace.app.cms.BaseTest;
import com.ace.framework.util.ExecutionContext;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WuZhiWei
 * @since 2015-11-20 15:26:21
 */
public class BlobServiceTest extends BaseTest {

    @Resource
    private BlobService blobService;

    @Test
    @Rollback(false)
    public void testSave() {
        Blob blob = new Blob();
        blob.setBlobId("a933c0798f8f4e0a9ca0bcc0a7fe1d51");
        blob.setResourceId("resourceId");
        blob.setColumnName("name");
        blob.setTxtValue("wzw");
        blob.setCorpCode(ExecutionContext.getCorpCode());
        blobService.save(blob);
    }

    @Test
    @Rollback(false)
    public void testUpdate(){
        blobService.update("resourceId","name","wjq");
    }
    @Test
    public void testGetColumnsValue(){
        System.out.println("========="+blobService.getColumnValue("resourceId", "name"));
    }

    @Test
    public void testGetColumnsValueMap(){
        List<String> columns= new ArrayList<String>();
        columns.add("name");
        columns.add("age");
        Map<String, String> columnsValueMap = blobService.getColumnsValueMap("resourceId", columns);
    }

    @Test
    @Rollback(false)
    public void test(){
        blobService.delete("resourceId",null);
    }

}
