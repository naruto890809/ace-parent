package com.ace.app.cms.drug.service.impl;

import com.ace.app.cms.drug.dao.HospitalDao;
import com.ace.app.cms.drug.domain.Hospital;
import com.ace.app.cms.drug.service.DepartmentService;
import com.ace.app.cms.drug.service.DicService;
import com.ace.app.cms.drug.service.HospitalService;
import com.ace.app.cms.CmsConstant;
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
 * 医院

 * WuZhiWei v1.0
 * @author 代码生成器 v1.0
 * @since  2018-09-12 07:52:33
 */
@Service("hospitalService")
public class HospitalServiceImpl  extends BaseCrudServiceImpl<Hospital> implements HospitalService {
    @Resource
    private HospitalDao hospitalDao;
    @Resource
    private DicService dicService;
    @Resource
    private DepartmentService departmentService;


    @Override
    public BaseDao<Hospital> getEntityDao() {
        return hospitalDao;
    }

    @Override
    public List findListForExport(Hospital condition) {
        List<Hospital> list = this.findList(condition);
        if (CollectionUtils.isNotEmpty(list)){
            Map<String, String> dicIdNameMap = dicService.getIdNameMap();
            Map<String, String> getDeptIdNameMap = departmentService.getDeptIdNameMap();
            for (Hospital row : list) {
                row.setLevelName(dicIdNameMap.get(row.getLevel()));
                row.setTypeName(dicIdNameMap.get(row.getType()));
                row.setDepartmentName(getDeptIdNameMap.get(row.getDepartmentId()));
            }
        }
        return list;
    }

    @Override
    public String importBatch(List<Hospital> list,String extParam) {
        String result = "";
        List<Hospital> hospitals = this.findList(null);
        List<String> db = new ArrayList<>(hospitals.size());
        for (Hospital hospital : hospitals) {
            db.add(hospital.getName());
        }

        Map<String, String> dicNameIdMap = dicService.getNameIdMap();
        Map<String, String> getDeptNameIdMap = departmentService.getDeptNameIdMap();
        List<String> excelHosNames = new ArrayList<>();
        for (Hospital hospital : list) {
            String indexResult = "";
            String name = hospital.getName();
            if (StringUtils.isBlank(name)){
                indexResult += "医院名称为空、";
            }else {
                hospital.setName(hospital.getName().trim());
                if ( db.contains(name)){
                    indexResult += "医院名称已存在、";
                }
                if (excelHosNames.contains(name)) {
                    indexResult += "医院名称重复、";
                }else {
                    excelHosNames.add(name);
                }
            }
            //部门不是必填项
            String departmentName = hospital.getDepartmentName();
            if (StringUtils.isNotBlank(departmentName)) {
                String deptId = getDeptNameIdMap.get(departmentName);
                if (StringUtils.isNotBlank(deptId)) {
                    hospital.setDepartmentId(deptId);
                }
            }

            String levelName = hospital.getLevelName();
            if (StringUtils.isBlank(levelName)) {
                indexResult += "医院等级为空、";
            } else {
                String levelId = dicNameIdMap.get(levelName);
                if (StringUtils.isBlank(levelId)) {
                    indexResult += "医院等级不存在、";
                }else {
                    hospital.setLevel(levelId);
                }
            }

            String typeName = hospital.getTypeName();
            if (StringUtils.isBlank(typeName)) {
                indexResult += "医院性质为空、";
            } else {
                String typeId = dicNameIdMap.get(typeName);
                if (StringUtils.isBlank(typeId)) {
                    indexResult += "医院性质不存在、";
                }else {
                    hospital.setType(typeId);
                }
            }

            /*String province = hospital.getProvince();
            String city = hospital.getCity();
            String area = hospital.getArea();
            if (StringUtils.isNotBlank(province) && !CmsConstant.ADDRESS.contains(province+"$")){
                indexResult += "省不存在、";
            }

            if (StringUtils.isNotBlank(city) && !CmsConstant.ADDRESS.contains(city)){
                indexResult += "市不存在、";
            }

            if (StringUtils.isNotBlank(area) && !CmsConstant.ADDRESS.contains(area)){
                indexResult += "区|县不存在、";
            }*/

            if (StringUtils.isNotBlank(indexResult)){
                int index = list.indexOf(hospital)+2;
                indexResult = "第"+index+"行数据有误："+indexResult+"<br/>";
                result += indexResult;
                continue;
            }

            hospital.setApprove(CmsConstant.APPROVING);
        }

        if (StringUtils.isBlank(result)){
            this.saveBatch(list);
        }

        return result;
    }


    @Override
    public Map<String, String> getIdNameMap() {
        Hospital entity = new Hospital();
        entity.setApprove(CmsConstant.PASSED);
        List<Hospital> list = findList(entity);
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        Map<String,String> result = new HashMap<>();
        for (Hospital hospital : list) {
            result.put(hospital.getId(),hospital.getName());
        }

        return result;

    }


    @Override
    public Map<String, String> getNameIdMap() {
        Hospital entity = new Hospital();
        entity.setApprove(CmsConstant.PASSED);
        List<Hospital> list = findList(entity);
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        Map<String,String> result = new HashMap<>();
        for (Hospital hospital : list) {
            result.put(hospital.getName(),hospital.getId());
        }

        return result;

    }

    @Override
    public Map<String, Hospital> getIdHosMap() {
        Hospital entity = new Hospital();
        entity.setApprove(CmsConstant.PASSED);
        List<Hospital> list = findList(entity);
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        Map<String,Hospital> result = new HashMap<>();
        for (Hospital hospital : list) {
            result.put(hospital.getId(),hospital);
        }

        return result;

    }

    @Override
    public Map<String, Hospital> getNameHosMap() {
        Hospital entity = new Hospital();
        entity.setApprove(CmsConstant.PASSED);
        List<Hospital> list = findList(entity);
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        Map<String,Hospital> result = new HashMap<>();
        for (Hospital hospital : list) {
            result.put(hospital.getName(),hospital);
        }

        return result;

    }

    @Override
    public Map<String, Hospital> getAliasNameHosMap() {
        Hospital entity = new Hospital();
        entity.setApprove(CmsConstant.PASSED);
        List<Hospital> list = findList(entity);
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        Map<String,Hospital> result = new HashMap<>();
        for (Hospital hospital : list) {
            String alias = hospital.getAlias();
            result.put(hospital.getName()+","+ alias,hospital);
        }

        return result;
    }

    @Override
    public Map<String, String> getHosIdCityMap() {
        Hospital entity = new Hospital();
        entity.setApprove(CmsConstant.PASSED);
        List<Hospital> list = findList(entity);
        if (CollectionUtils.isEmpty(list)){
            return MapUtils.EMPTY_MAP;
        }

        Map<String,String> result = new HashMap<>();
        for (Hospital hospital : list) {
            String city = hospital.getCity();
            if (StringUtils.isNotBlank(city)){
                result.put(hospital.getId(), city);
            }
        }

        return result;
    }
}
