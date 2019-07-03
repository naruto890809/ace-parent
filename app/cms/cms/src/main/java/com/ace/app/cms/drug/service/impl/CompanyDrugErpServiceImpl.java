package com.ace.app.cms.drug.service.impl;

import com.ace.app.cms.drug.dao.CompanyDrugErpDao;
import com.ace.app.cms.drug.domain.CompanyDrugErp;
import com.ace.app.cms.drug.service.CompanyDrugErpService;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseCrudServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ace.framework.base.BaseDao;

import java.math.BigDecimal;
import java.util.List;

/**
* 商业公司药品Erp

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-29 18:41:55
*/
@Service("companyDrugErpService")
public class CompanyDrugErpServiceImpl  extends BaseCrudServiceImpl<CompanyDrugErp> implements CompanyDrugErpService {
    @Resource
    private CompanyDrugErpDao companyDrugErpDao;

    @Override
    public BaseDao<CompanyDrugErp> getEntityDao() {
        return companyDrugErpDao;
    }

    @Override
    public List findListForExport(CompanyDrugErp condition) {
        List list = this.findList(condition);
        return list;
    }

    @Override
    public String importBatch(List<CompanyDrugErp> list, String extParam) {
        return null;
    }


    @Override
    public void addCnt(String companyId, String drugId, String specDrugId, BigDecimal inCnt, BigDecimal outCnt) {
        if (StringUtils.isBlank(companyId) || StringUtils.isBlank(drugId) || StringUtils.isBlank(specDrugId)){
            return;
        }

        CompanyDrugErp companyDrugErp = new CompanyDrugErp( companyId,  drugId,  specDrugId);
        List<CompanyDrugErp> list = this.findList(companyDrugErp);
        if (CollectionUtils.isEmpty(list)){
            companyDrugErp.setInCountD(inCnt);
            companyDrugErp.setOutCountD(outCnt);
            this.save(companyDrugErp);
            return;
        }

        companyDrugErp = list.get(0);
        companyDrugErp.setInCountD(companyDrugErp.getInCountD().add(inCnt));
        companyDrugErp.setOutCountD(companyDrugErp.getOutCountD().add(outCnt));
        this.update(companyDrugErp);
    }

    @Override
    public void subCnt(String companyId, String drugId, String specDrugId, BigDecimal inCnt, BigDecimal outCnt) {
        if (StringUtils.isBlank(companyId) || StringUtils.isBlank(drugId) || StringUtils.isBlank(specDrugId)){
            return;
        }

        CompanyDrugErp companyDrugErp = new CompanyDrugErp( companyId,  drugId,  specDrugId);
        List<CompanyDrugErp> list = this.findList(companyDrugErp);
        if (CollectionUtils.isEmpty(list)){
            companyDrugErp.setInCountD(inCnt);
            companyDrugErp.setOutCountD(outCnt);
            this.save(companyDrugErp);
            return;
        }

        companyDrugErp = list.get(0);
        BigDecimal inCount = companyDrugErp.getInCountD().subtract( inCnt);
        int r=inCount.compareTo(BigDecimal.ZERO);
        if (r==-1){
            inCount =new BigDecimal("0.00");
        }
        companyDrugErp.setInCountD(inCount);
        BigDecimal outCount = companyDrugErp.getOutCountD().subtract(outCnt);
        int k=outCount.compareTo(BigDecimal.ZERO);
        if (k==-1){
            outCount = new BigDecimal("0.00");
        }
        companyDrugErp.setOutCountD(outCount);
        this.update(companyDrugErp);
    }
}