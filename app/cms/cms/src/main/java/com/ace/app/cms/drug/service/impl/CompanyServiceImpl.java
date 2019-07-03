package com.ace.app.cms.drug.service.impl;

import com.ace.app.cms.drug.dao.CompanyDao;
import com.ace.app.cms.drug.domain.Company;
import com.ace.app.cms.drug.domain.SellArea;
import com.ace.app.cms.drug.service.CompanyService;
import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.drug.service.DicService;
import com.ace.app.cms.drug.service.SellAreaService;
import com.ace.framework.base.BaseCrudServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ace.framework.base.BaseDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 商业公司

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
@Service("companyService")
public class CompanyServiceImpl  extends BaseCrudServiceImpl<Company> implements CompanyService {
    @Resource
    private CompanyDao companyDao;
    @Resource
    private DicService dicService;
    @Resource
    private SellAreaService sellAreaService;

    @Override
    public BaseDao<Company> getEntityDao() {
        return companyDao;
    }

    @Override
    public List findListForExport(Company condition) {
        List<Company> list = this.findList(condition);
        if (CollectionUtils.isEmpty(list)){
            return list;
        }

        Map<String,String> channelIdNameMap = dicService.getIdNameMap();
        Map<String, String> areaIdNameMap = sellAreaService.sellAreaIdNameMap();
        for (Company company : list) {
            company.setSellAreaName(areaIdNameMap.get(company.getSellAreaId()));
            company.setChannelName(channelIdNameMap.get(company.getChannel()));
        }
        return list;
    }

    @Override
    public String importBatch(List<Company> list,String extParam) {
        String result = "";
        List<Company> companyList = this.findList(null);
        List<String> db = new ArrayList<>(companyList.size());
        for (Company company : companyList) {
            db.add(company.getName());
        }

        Map<String, String> dicNameIdMap = dicService.getNameIdMap();
        SellArea sellArea = new SellArea();
        sellArea.setApprove(CmsConstant.PASSED);
        List<SellArea> sellAreas = sellAreaService.findList(sellArea);
        Map<String, String> areaNameIdMap = new HashMap<>(sellAreas.size());
        for (SellArea area : sellAreas) {
            areaNameIdMap.put(area.getArea(),area.getId());
        }
        for (Company company : list) {
            String indexResult = "";
            String name = company.getName();
            if (StringUtils.isBlank(name)){
                indexResult += "名称为空、";
            }else {
                company.setName(company.getName().trim());
                if ( db.contains(name)){
                    indexResult += "名称已存在、";
                }
            }

            String channelName = company.getChannelName();
            if (StringUtils.isBlank(channelName)) {
                indexResult += "渠道为空、";
            } else {
                String channel = dicNameIdMap.get(channelName);
                if (StringUtils.isBlank(channel)) {
                    indexResult += "渠道不存在、";
                }else {
                    company.setChannel(channel);
                }
            }

            /*String sellAreaName = company.getSellAreaName();
            if (StringUtils.isBlank(sellAreaName)) {
                indexResult += "销售区域为空、";
            } else {
                String areaId = areaNameIdMap.get(sellAreaName);
                if (StringUtils.isBlank(areaId)) {
                    indexResult += "销售区域不存在、";
                }else {
                    company.setSellAreaId(areaId);
                }
            }*/

            if (StringUtils.isNotBlank(indexResult)){
                int index = list.indexOf(company)+2;
                indexResult = "第"+index+"行数据有误："+indexResult+"<br/>";
                result += indexResult;
                continue;
            }

            company.setApprove(CmsConstant.APPROVING);
        }

        if (StringUtils.isBlank(result)){
            this.saveBatch(list);
        }

        return result;
    }


    @Override
    public Map<String, String> getComIdNameMap() {
        Map<String, String> result = new HashMap<>();
        Company entity = new Company();
        entity.setApprove(CmsConstant.PASSED);
        List<Company> list = this.findList(entity);
        for (Company company : list) {
            result.put(company.getId(),company.getName());
        }

        return result;
    }

    @Override
    public Map<String, String> getComNameIdMap() {
        Map<String, String> result = new HashMap<>();
        Company entity = new Company();
        entity.setApprove(CmsConstant.PASSED);
        List<Company> list = this.findList(entity);
        for (Company company : list) {
            result.put(company.getName(),company.getId());
        }

        return result;
    }

    @Override
    public Map<String, Company> getComNameCompanyMap() {
        Map<String, Company> result = new HashMap<>();
        Company entity = new Company();
        entity.setApprove(CmsConstant.PASSED);
        List<Company> list = this.findList(entity);
        for (Company company : list) {
            result.put(company.getName()+","+company.getAlias(),company);
        }

        return result;
    }
}
