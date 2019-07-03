package com.ace.app.cms.drug.service.impl;

import com.ace.app.cms.drug.dao.DicDao;
import com.ace.app.cms.drug.domain.Dic;
import com.ace.app.cms.drug.service.DicService;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseCrudServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ace.framework.base.BaseDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 数据字典

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
@Service("dicService")
public class DicServiceImpl  extends BaseCrudServiceImpl<Dic> implements DicService {
    @Resource
    private DicDao dicDao;

    @Override
    public BaseDao<Dic> getEntityDao() {
        return dicDao;
    }

    @Override
    public List findListForExport(Dic condition) {
        List list = this.findList(condition);
        return list;
    }

    @Override
    public String importBatch(List<Dic> list,String extParam) {
        for (Dic dic : list) {
        //TODO 导入条件判断，空、重复
        //dic.setApprove(CmsConstant.APPROVING);
        }

        this.saveBatch(list);
        return null;
    }


    @Override
    public List<Dic> findByCode(String code) {
        Dic dic = new Dic();
        dic.setCode(code);
        return this.findList(dic);
    }

    @Override
    public Map<String, String> getIdNameMap() {
        List<Dic> list = this.findList(null);
        Map<String,String> idNameMap = new HashMap<>();
        for (Dic dic : list) {
            idNameMap.put(dic.getId(),dic.getName().trim());
        }

        return idNameMap;
    }

    @Override
    public Map<String, String> getNameIdMap() {
        List<Dic> list = this.findList(null);
        Map<String,String> idNameMap = new HashMap<>();
        for (Dic dic : list) {
            idNameMap.put(dic.getName().trim(),dic.getId());
        }

        return idNameMap;
    }
}
