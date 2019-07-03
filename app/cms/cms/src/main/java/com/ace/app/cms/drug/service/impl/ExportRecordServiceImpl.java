package com.ace.app.cms.drug.service.impl;

import com.ace.app.cms.drug.dao.ExportRecordDao;
import com.ace.app.cms.drug.domain.ExportRecord;
import com.ace.app.cms.drug.service.ExportRecordService;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseCrudServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ace.framework.base.BaseDao;

import java.util.List;

/**
* 导入记录

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
@Service("exportRecordService")
public class ExportRecordServiceImpl  extends BaseCrudServiceImpl<ExportRecord> implements ExportRecordService {
    @Resource
    private ExportRecordDao exportRecordDao;

    @Override
    public BaseDao<ExportRecord> getEntityDao() {
        return exportRecordDao;
    }

    @Override
    public List findListForExport(ExportRecord condition) {
        List list = this.findList(condition);
        return list;
    }

    @Override
    public String importBatch(List<ExportRecord> list,String extParam) {
        for (ExportRecord exportRecord : list) {
        //TODO 导入条件判断，空、重复
        //exportRecord.setApprove(CmsConstant.APPROVING);
        }

        this.saveBatch(list);
        return null;
    }


}
