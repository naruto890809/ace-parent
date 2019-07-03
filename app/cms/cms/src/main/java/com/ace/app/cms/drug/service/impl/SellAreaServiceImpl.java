package com.ace.app.cms.drug.service.impl;

import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.drug.dao.SellAreaDao;
import com.ace.app.cms.drug.domain.SellArea;
import com.ace.app.cms.drug.service.SellAreaService;
import com.ace.framework.base.BaseCrudServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ace.framework.base.BaseDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 销售区域

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-06 21:00:55
*/
@Service("sellAreaService")
public class SellAreaServiceImpl  extends BaseCrudServiceImpl<SellArea> implements SellAreaService{
    @Resource
    private SellAreaDao sellAreaDao;

    @Override
    public BaseDao<SellArea> getEntityDao() {
        return sellAreaDao;
    }

    @Override
    public List findListForExport(SellArea condition) {
        condition.setProvince(condition.getProvinceSearch());
        condition.setCity(condition.getCitySearch());
        condition.setArea(condition.getAreaSearch());

        List list = this.findList(condition);
        return list;
    }

    @Override
    public String importBatch(List<SellArea> list,String extParam) {
        String result = "";
        List<SellArea> sellAreas = this.findList(null);
        List<String> db = new ArrayList<>(sellAreas.size());
        for (SellArea sellArea : sellAreas) {
            db.add(sellArea.getProvince()+sellArea.getCity()+sellArea.getArea());
        }

        for (SellArea sellArea : list) {
            String indexResult = "";
            String province = sellArea.getProvince();
            String city = sellArea.getCity();
            String area = sellArea.getArea();
            if (StringUtils.isBlank(province)){
                indexResult += "省为空、";
            }else {
                if (!CmsConstant.ADDRESS.contains(province+"$")){
                    indexResult += "省不存在、";
                }
            }

            if (StringUtils.isBlank(city)){
                indexResult += "市为空、";
            }else {
                if (!CmsConstant.ADDRESS.contains(city)){
                    indexResult += "市不存在、";
                }
            }

            if (StringUtils.isBlank(area)){
                indexResult += "区域为空、";
            }else {
                sellArea.setArea(sellArea.getArea().trim());
            }

            if (StringUtils.isBlank(indexResult) && db.contains(province+city+area)){
                indexResult += "省市区域已存在";
            }

            if (StringUtils.isNotBlank(indexResult)){
                int index = list.indexOf(sellArea)+2;
                indexResult = "第"+index+"行数据有误："+indexResult+"<br/>";
                result += indexResult;
                continue;
            }

            sellArea.setApprove(CmsConstant.APPROVING);
        }

        if (StringUtils.isBlank(result)){
            this.saveBatch(list);
        }

        return result;
    }

    @Override
    public List<SellArea> findPassed() {
        SellArea sellArea = new SellArea();
        sellArea.setApprove(CmsConstant.PASSED);
        return this.findList(sellArea);
    }

    @Override
    public Map<String, String> sellAreaNameIdMap() {
        List<SellArea> areas = findPassed();
        if (CollectionUtils.isEmpty(areas)){
            return MapUtils.EMPTY_MAP;
        }

        Map<String,String> result = new HashMap<>();
        for (SellArea area : areas) {
            result.put(area.getArea(),area.getId());
        }
        return result;
    }

    @Override
    public Map<String, String> sellAreaIdNameMap() {
        List<SellArea> areas = findPassed();
        if (CollectionUtils.isEmpty(areas)){
            return MapUtils.EMPTY_MAP;
        }

        Map<String,String> result = new HashMap<>();
        for (SellArea area : areas) {
            result.put(area.getId(),area.getArea());
        }
        return result;
    }
}
