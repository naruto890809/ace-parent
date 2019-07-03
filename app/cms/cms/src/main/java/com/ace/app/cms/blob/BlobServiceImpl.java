package com.ace.app.cms.blob;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 大字段服务
 * @author WuZhiWei
 * @since 2015-11-09 17:30:21
 */
@Service("blobService")
public class BlobServiceImpl implements BlobService {

    @Resource
    private BlobDao blobDao;

    @Override
    @Transactional(readOnly = false,isolation = Isolation.READ_COMMITTED)
    public String save(Blob blob) {
        Assert.notNull(blob);
        return blobDao.save(blob);
    }

    @Override
    @Transactional(readOnly = false,isolation = Isolation.READ_COMMITTED)
    public void update(String resourceId, String columnsName, String txtValue) {
        Assert.hasText(resourceId);
        Assert.hasText(columnsName);
        Assert.hasText(txtValue);
        blobDao.updateTxtValue(resourceId,columnsName,txtValue);
    }

    @Override
    @Transactional(readOnly = true)
    public String getColumnValue(String resourceId, String columnsName) {
        Assert.hasText(resourceId);
        Assert.hasText(columnsName);
        return blobDao.getColumnValue(resourceId, columnsName)+"<style>img{ width: 100%; margin: 10px 0;}</style>";
    }

    @Override
    @Transactional(readOnly = true)
    public String getColumnValueByBlobId(String blobId) {
        Assert.hasText(blobId);
        return blobDao.getColumnValueByBlobId(blobId)+"<style>img{ width: 100%; margin: 10px 0;}</style>";
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> getColumnsValueMap(String resourceId, List<String> columnsNames) {
        Assert.hasText(resourceId);
        Assert.notEmpty(columnsNames);
        List<Blob> blobs = blobDao.getColumnNameAndTxtValue(resourceId,columnsNames);
        if (CollectionUtils.isEmpty(blobs)){
            return new HashMap<String, String>(0);
        }

        HashMap<String, String> map = new HashMap<String, String>(blobs.size());
        for (Blob blobTmp : blobs) {
            map.put(blobTmp.getColumnName(),blobTmp.getTxtValue());
        }

        return map;
    }

    @Override
    @Transactional(readOnly = false , isolation = Isolation.READ_COMMITTED)
    public void delete(String resourceId,String columnName) {
        Assert.hasText(resourceId);
        blobDao.deleteByResourceIdAndColumnName(resourceId,columnName);
    }

    @Override
    @Transactional(readOnly = false , isolation = Isolation.READ_COMMITTED)
    public int updateSelect(Blob blob) {
        Assert.notNull(blob);
        return blobDao.updateSelect(blob);
    }
}
