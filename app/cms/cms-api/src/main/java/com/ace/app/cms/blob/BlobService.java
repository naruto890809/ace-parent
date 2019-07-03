package com.ace.app.cms.blob;

import java.util.List;
import java.util.Map;

/**
 * 大字段服务
 *
 * @author WuZhiWei
 * @since 2015-12-15 14:23:44
 */
public interface BlobService {
    /**
     * 保存大字段实体
     *
     * @param blob 大字段
     * @return blobId
     * @throws IllegalArgumentException 当blob为空时
     */
    String save(Blob blob);


    /**
     * 根据资源id和字段名称以及字段值，更新对应字段值
     *
     * @param resourceId 资源id
     * @param columnName 字段名称
     * @param txtValue   字段值
     * @throws java.lang.IllegalArgumentException 当resourceId、columnsName任一为空时
     */
    void update(String resourceId, String columnName, String txtValue);

    /**
     * 根据资源id和字段名称获取字段值
     *
     * @param resourceId 资源id
     * @param columnName 字段名称
     * @return 字段值
     * @throws java.lang.IllegalArgumentException 当resourceId、columnsName任一为空时
     */
    String getColumnValue(String resourceId, String columnName);
    /**
     * 根据主键获取字段值
     *
     * @param blobId 主键
     * @return 字段值
     * @throws java.lang.IllegalArgumentException 当blobId为空时
     */
    String getColumnValueByBlobId(String blobId);

    /**
     * 根据资源id和字段名称获取字段名称和字段值的map
     *
     * @param resourceId   资源id
     * @param columnsNames 一组字段名称
     * @return key:字段名称，value:字段值
     * @throws java.lang.IllegalArgumentException 当resourceId、columnsNames任一为空时
     */
    Map<String, String> getColumnsValueMap(String resourceId, List<String> columnsNames);

    /**
     * 当columnsName不为空时，根据resourceId和columnsName删除指定字段名称大字段的值;<p/>
     * 当columnsName不为空时，根据resourceId删除相关字段名称大字段的值;<p/>
     *
     * @param resourceId 资源id
     * @throws java.lang.IllegalArgumentException 当resourceId为空时
     */
    void delete(String resourceId, String columnName);

    int updateSelect(Blob blob);
}
