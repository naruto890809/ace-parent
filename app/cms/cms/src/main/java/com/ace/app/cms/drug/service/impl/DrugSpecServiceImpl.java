package com.ace.app.cms.drug.service.impl;

import com.ace.app.cms.drug.dao.DrugSpecDao;
import com.ace.app.cms.drug.domain.DrugSpec;
import com.ace.app.cms.drug.service.DrugSpecService;
import com.ace.app.cms.CmsConstant;
import com.ace.framework.base.BaseCrudServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ace.framework.base.BaseDao;

import java.math.BigDecimal;
import java.util.*;

/**
* 药品规格

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
@Service("drugSpecService")
public class DrugSpecServiceImpl  extends BaseCrudServiceImpl<DrugSpec> implements DrugSpecService {
    @Resource
    private DrugSpecDao drugSpecDao;

    @Override
    public BaseDao<DrugSpec> getEntityDao() {
        return drugSpecDao;
    }

    @Override
    public Map<String, String> getDrugIdSpecNameMap() {
        List<DrugSpec> drugSpecs = this.findList(null);
        Map<String,String> drugIdSpecNameMap = new HashMap<>();
        for (DrugSpec spec : drugSpecs) {
            String drugId = spec.getDrugId();
            String specName = drugIdSpecNameMap.get(drugId);
            if (StringUtils.isBlank(specName)){
                specName = "";
            }

            specName += spec.getSpecName()+",";
            drugIdSpecNameMap.put(drugId,specName);
        }
        return drugIdSpecNameMap;
    }

    @Override
    public Map<String, String> getDrugIdSpecCoefficientMap() {
        List<DrugSpec> drugSpecs = this.findList(null);
        Map<String,String> drugIdSpecNameMap = new HashMap<>();
        for (DrugSpec spec : drugSpecs) {
            String drugId = spec.getDrugId();
            String specName = drugIdSpecNameMap.get(drugId);
            if (StringUtils.isBlank(specName)){
                specName = "";
            }

            BigDecimal coefficient = spec.getCoefficient();
            if (coefficient == null){
                coefficient = BigDecimal.ZERO;
            }
            specName += coefficient +",";
            drugIdSpecNameMap.put(drugId,specName);
        }
        return drugIdSpecNameMap;
    }

    @Override
    public List findListForExport(DrugSpec condition) {
        List list = this.findList(condition);
        return list;
    }

    @Override
    public String importBatch(List<DrugSpec> list,String extParam) {
        for (DrugSpec drugSpec : list) {
        //TODO 导入条件判断，空、重复
        //drugSpec.setApprove(CmsConstant.APPROVING);
        }

        this.saveBatch(list);
        return null;
    }

    @Override
    public Map<String, String> drugSpecNameIdsStrMap() {
        List<DrugSpec> list = drugSpecDao.findJoin();
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        Map<String,String> result = new HashMap<>();
        for (DrugSpec spec : list) {
            result.put(spec.getDrugName()+spec.getSpecName(),spec.getDrugId()+","+spec.getId());
        }

        return result;
    }

    @Override
    public Map<String, String> drugSpecAliaNameIdsStrMap() {
        List<DrugSpec> list = drugSpecDao.findJoin();
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        Map<String,String> result = new HashMap<>();
        for (DrugSpec spec : list) {
            String value = spec.getDrugId() + "," + spec.getId();
            List<String> drugNames = new ArrayList<>();
            List<String> specNames = new ArrayList<>();
            String specName = spec.getSpecName();
            String drugName = spec.getDrugName();
            drugNames.add(drugName);
            specNames.add(specName);
            String drugAliasName = spec.getDrugAliasName();
            if (StringUtils.isNotBlank(drugAliasName)){
                drugNames.addAll(Arrays.asList(drugAliasName.replaceAll("，", ",").split(",")));
            }

            String specAliasName = spec.getCode();
            if (StringUtils.isNotBlank(specAliasName)){
                specNames.addAll(Arrays.asList(specAliasName.replaceAll("，", ",").split(",")));
            }

            for (String drugNameTmp : drugNames) {
                for (String specNameTmp : specNames) {
                    result.put(drugNameTmp + specNameTmp, value);
                }
            }
        }

        return result;
    }

    @Override
    public Map<String,BigDecimal> drugSpecNameCoefficientStrMap() {
        List<DrugSpec> list = drugSpecDao.findJoin();
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        Map<String,BigDecimal> result = new HashMap<>();
        for (DrugSpec spec : list) {
            result.put(spec.getDrugName()+spec.getSpecName(),spec.getCoefficient());
        }

        return result;
    }

    @Override
    public Map<String, String> idsDrugSpecNameStrMap() {
        List<DrugSpec> list = drugSpecDao.findJoin();
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        Map<String,String> result = new HashMap<>();
        for (DrugSpec spec : list) {
            result.put(spec.getDrugId()+","+spec.getId(),spec.getDrugName()+spec.getSpecName());
        }

        return result;
    }

    @Override
    public List<DrugSpec> drugSpecIdNameList() {
        List<DrugSpec> list = drugSpecDao.findJoin();
        if (CollectionUtils.isEmpty(list)){
            return new ArrayList<>(0);
        }

        for (DrugSpec spec : list) {
            spec.setId(spec.getDrugId()+","+spec.getId());
            spec.setSpecName(spec.getDrugName()+spec.getSpecName());
        }

        return list;
    }

    @Override
    public Map<String, String> getIdSpecNameMap() {
        DrugSpec drug = new DrugSpec();
        List<DrugSpec> list = this.findList(drug);
        Map<String,String> map = new HashMap<>();
        if (CollectionUtils.isEmpty(list)){
            return map;
        }

        for (DrugSpec tmp : list) {
            map.put(tmp.getId(),tmp.getSpecName());
        }

        return map;
    }

}
