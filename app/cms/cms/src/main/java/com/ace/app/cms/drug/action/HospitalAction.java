package com.ace.app.cms.drug.action;


import com.ace.app.cms.drug.domain.*;
import com.ace.app.cms.drug.model.HospitalModel;
import com.ace.app.cms.drug.service.*;
import com.ace.framework.base.BaseAction;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.base.Page;
import com.ace.framework.util.ExecutionContext;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import com.ace.app.cms.CmsConstant;
import java.util.Arrays;
import com.ace.app.cms.account.AccountService;
import org.springframework.dao.DuplicateKeyException;

/**
* 医院
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
@Scope("prototype")
public class HospitalAction  extends BaseAction<HospitalModel> {
    @Resource
    private HospitalService hospitalService;
    @Resource
    private AccountService accountService;
    @Resource
    private DicService dicService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private SellAreaService sellAreaService;
    @Resource
    private RebateService rebateService;
    @Resource
    private CompanyDrugService companyDrugService;
    @Resource
    private BizService bizService;



    public String index() {
        setModels();
        super.setTarget("index");
        return COMMON;
    }

    public String editDeptIndex(){
        setDept();
        super.setTarget("editDeptIndex");
        return COMMON;
    }

    public String editDept(){
        Hospital biz = model.getHospital();
        biz.setIds(Arrays.asList(biz.getId().split(",")));
        biz.setId(null);
        biz.setApprove("PASSED");
        hospitalService.update(biz);
        return renderJson(JsonResultUtil.location("hospital.index.do"));
    }

    private void setDept(){
        Department entity = new Department();
        entity.setApprove(CmsConstant.PASSED);
        List<Department> departments = departmentService.findList(entity);
        model.setDepartments(departments);
    }


    private void setModels(){
        Department entity = new Department();
        entity.setApprove(CmsConstant.PASSED);
        List<Department> departments = departmentService.findList(entity);
        model.setDepartments(departments);
        List<Dic> levels = dicService.findByCode(CmsConstant.DIC_TYPE_HOSPITAL_LEVEL);
        model.setLevels(levels);
        List<Dic> types = dicService.findByCode(CmsConstant.DIC_TYPE_HOSPITAL_TYPE);
        model.setTypes(types);
        SellArea sellArea = new SellArea();
        sellArea.setApprove(CmsConstant.PASSED);
        List<SellArea> sellAreas = sellAreaService.findList(sellArea);
        List<String> areas = new ArrayList<>();
        for (SellArea area : sellAreas) {
            areas.add(area.getArea());
        }

        model.setAreas(areas);
    }

    public String addOrEditIndex(){
        String id = model.getId();
        Hospital hospital = null;
        if (StringUtils.isBlank(id)){
            hospital = new Hospital();
        }else {
            hospital = hospitalService.getById(id);
        }

        setModels();
        model.setHospital(hospital);
        super.setTarget("addOrEdit");
        return COMMON;
    }

    public String addOrEdit(){
        Hospital hospital = model.getHospital();
        try {
            hospital.setName(hospital.getName().trim());
            String departmentId = hospital.getDepartmentId();
            if (StringUtils.isNotBlank(departmentId)){
                Department department = departmentService.getById(departmentId);
                hospital.setDepartmentName(department.getName());
            }

            if (StringUtils.isBlank(hospital.getId())){
                hospital.setId(null);
                hospital.setApprove(CmsConstant.APPROVING);
                hospitalService.save(hospital);
            }else {
                hospitalService.update(hospital);
            }
        } catch (DuplicateKeyException e) {
            return renderJson(JsonResultUtil.err("名称已存在"));
        }

        return renderJson(JsonResultUtil.location("hospital.index.do"));
    }

    public String search() {
        Hospital hospital = model.getHospital();
        if(hospital==null){
            hospital=new Hospital();
        }

        hospital.setCorpCode(ExecutionContext.getCorpCode());
        Page<Hospital>  page = model.getPage();

        String citySearch = hospital.getCitySearch();
        if (StringUtils.isNotBlank(citySearch)){
            hospital.setCity(citySearch);
        }

        page = hospitalService.search(hospital, page);
        List<Hospital> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return renderJson(page);
        }

        Map<String,String> aidNameMap = accountService.getAidNameMap();
        Map<String, String> dicIdNameMap = dicService.getIdNameMap();
        Map<String, String> getDeptIdNameMap = departmentService.getDeptIdNameMap();
        for (Hospital row : rows) {
            row.setCreateByName(aidNameMap.get(row.getCreateBy()));
            row.setLevelName(dicIdNameMap.get(row.getLevel()));
            row.setTypeName(dicIdNameMap.get(row.getType()));
            row.setDepartmentName(getDeptIdNameMap.get(row.getDepartmentId()));
        }

        return renderJson(page);
    }


    /**
    * 删除
    * @return
    */
    public String remove() {
        String id = model.getId();
        if (!checkRebateDel(id)){
            return renderJson(JsonResultUtil.err("返利信息中存在关联数据，无法删除！"));
        }
        if (!checkBizDel(id)){
            return renderJson(JsonResultUtil.err("业务管理中存在关联数据，无法删除！"));
        }
        hospitalService.deleteById(id);
        return renderJson(JsonResultUtil.location("hospital.index.do"));
    }


    private boolean checkRebateDel(String id){
        Rebate rebate = new Rebate();
        rebate.setHospitalId(id);
        List<Rebate> list = rebateService.findList(rebate);
        return CollectionUtils.isEmpty(list) || list.size() <= 0;
    }

    private boolean checkBizDel(String id){
        Biz huming = new Biz();
        huming.setHospitalId(id);
        List<Biz> list =bizService.findList(huming);
        return CollectionUtils.isEmpty(list) || list.size() <= 0;
    }

    public String removeBatch() {

        String ids[]=request.getParameterValues("ids[]");
        if(ids.length>0){
            for (String id : ids) {
                String hname = hospitalService.getIdNameMap().get(id);
                if (!checkRebateDel(id)){
                    return renderJson(JsonResultUtil.err(hname+" -返利信息中存在关联数据，无法删除！"));
                }
                if (!checkBizDel(id)){
                    return renderJson(JsonResultUtil.err(hname+" -业务管理中存在关联数据，无法删除！"));
                }
            }

            hospitalService.batchRemove(ids,ExecutionContext.getCorpCode(),ExecutionContext.getParentCorpCode());
        }

        return renderJson(JsonResultUtil.location("hospital.index.do"));
    }

    /**
    * 审批
    * @return
    */
    public String approve() {
        String idsStr = model.getIdsStr();
        if (StringUtils.isEmpty(idsStr)){
            return renderJson(JsonResultUtil.err("请选择操作的列"));
        }

        List<String> ids = Arrays.asList(idsStr.split(","));
        Hospital hospital = new Hospital();
        hospital.setIds(ids);
        hospital.setApprove(CmsConstant.PASSED);
        hospitalService.update(hospital);
        return renderJson(JsonResultUtil.location("hospital.index.do"));
    }

}
