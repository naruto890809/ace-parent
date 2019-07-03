package com.ace.app.cms.drug.service.impl;

import com.ace.app.cms.drug.dao.DrugDao;
import com.ace.app.cms.drug.domain.Drug;
import com.ace.app.cms.drug.domain.DrugSpec;
import com.ace.app.cms.drug.service.DrugService;
import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.drug.service.DrugSpecService;
import com.ace.framework.base.BaseCrudServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ace.framework.base.BaseDao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 药品

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
@Service("drugService")
public class DrugServiceImpl  extends BaseCrudServiceImpl<Drug> implements DrugService {
    @Resource
    private DrugDao drugDao;
    @Resource
    private DrugSpecService drugSpecService;

    @Override
    public BaseDao<Drug> getEntityDao() {
        return drugDao;
    }

    @Override
    public List findListForExport(Drug condition) {
        List<Drug> list = this.findList(condition);
        if (CollectionUtils.isEmpty(list)){
            return list;
        }

        Map<String, String> drugIdSpecNameMap = drugSpecService.getDrugIdSpecNameMap();
        Map<String, String> drugIdSpecCoefficientMap = drugSpecService.getDrugIdSpecCoefficientMap();
        for (Drug drug : list) {
            String id = drug.getId();
            drug.setSpecName(drugIdSpecNameMap.get(id));
            drug.setCoefficientText(drugIdSpecCoefficientMap.get(id));
        }
        return list;
    }

    @Override
    public String importBatch(List<Drug> list,String extParam) {
        String result = "";
        List<Drug> drugs = this.findList(null);
        List<String> db = new ArrayList<>(drugs.size());
        for (Drug drug : drugs) {
            db.add(drug.getName());
        }

        Map<String,String> drugNameSpecNameMap = new HashMap<>(list.size());
        Map<String,String> specNameSpecCntMap = new HashMap<>(list.size());
        for (Drug drug : list) {
            String indexResult = "";
            String name = drug.getName();
            if (StringUtils.isBlank(name)){
                indexResult += "药品名称为空、";
            }else {
                drug.setName(drug.getName().trim());
                if ( db.contains(name)){
                    indexResult += "药品名称已存在、";
                }
            }

            String specName = drug.getSpecName();
            if (StringUtils.isBlank(specName)){
                indexResult += "规格名称为空";
            }else {

            }

            String coefficientText = drug.getCoefficientText();
            if (StringUtils.isBlank(coefficientText)){
                indexResult += "规格系数为空";
            }

            specName = specName.replaceAll("，",",");
            coefficientText = coefficientText.replaceAll("，",",");
            if (specName.split(",").length != coefficientText.split(",").length){
                indexResult += "规格名称与系数数量不匹配";
            }

            if (StringUtils.isNotBlank(indexResult)){
                int index = list.indexOf(drug )+2;
                indexResult = "第"+index+"行数据有误："+indexResult+"<br/>";
                result += indexResult;
                continue;
            }

            drugNameSpecNameMap.put(name,specName);
            specNameSpecCntMap.put(specName, coefficientText);
            drug.setApprove(CmsConstant.APPROVING);
        }


        if (StringUtils.isNotBlank(result)){
            return result;
        }

        this.saveBatch(list);
        List<DrugSpec> drugSpecs = new ArrayList<>();
        for (Drug drug : list) {
            String specName = drugNameSpecNameMap.get(drug.getName());
            if (StringUtils.isBlank(specName)){
                continue;
            }

            String coefficientText = specNameSpecCntMap.get(specName);
            String[] specNameArr = specName.split(",");
            String[] coefficientTextArr = coefficientText.split(",");
            String drugId = drug.getId();
            for (int i = 0; i < specNameArr.length; i++) {
                String specNameStr = specNameArr[i];
                drugSpecs.add(new DrugSpec(specNameStr, drugId,new BigDecimal(coefficientTextArr[i])));
            }
        }

        if (CollectionUtils.isNotEmpty(drugSpecs)){
            drugSpecService.saveBatch(drugSpecs);
        }

        return result;
    }


    @Override
    public Map<String, String> getDrugIdNameMap() {
        Drug drug = new Drug();
        drug.setApprove(CmsConstant.PASSED);
        List<Drug> list = this.findList(drug);
        Map<String,String> map = new HashMap<>();
        if (CollectionUtils.isEmpty(list)){
            return map;
        }

        for (Drug tmp : list) {
            map.put(tmp.getId(),tmp.getName());
        }

        return map;
    }

    @Override
    public Map<String,Drug> getDrugIdMap() {
        Drug drug = new Drug();
        drug.setApprove(CmsConstant.PASSED);
        List<Drug> list = this.findList(drug);
        Map<String,Drug> map = new HashMap<>();
        if (CollectionUtils.isEmpty(list)){
            return map;
        }

        for (Drug tmp : list) {
            map.put(tmp.getId(),tmp);
        }

        return map;
    }

    @Override
    public Map<String, String> getDrugNameIdMap() {
        Drug drug = new Drug();
        drug.setApprove(CmsConstant.PASSED);
        List<Drug> list = this.findList(drug);
        Map<String,String> map = new HashMap<>();
        if (CollectionUtils.isEmpty(list)){
            return map;
        }

        for (Drug tmp : list) {
            map.put(tmp.getName(),tmp.getId());
        }

        return map;
    }
}
