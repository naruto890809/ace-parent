package com.ace.app.cms.drug.action;


import com.ace.app.cms.drug.domain.Biz;
import com.ace.app.cms.drug.domain.Department;
import com.ace.app.cms.drug.domain.Hospital;
import com.ace.app.cms.drug.domain.Salesman;
import com.ace.app.cms.drug.model.BizModel;
import com.ace.app.cms.drug.service.*;
import com.ace.framework.base.BaseAction;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.base.Page;
import com.ace.framework.util.CollectionUtil;
import com.ace.framework.util.DateUtil;
import com.ace.framework.util.ExecutionContext;
import javax.annotation.Resource;
import java.util.*;

import com.ace.framework.util.StringUtil;
import org.springframework.context.annotation.Scope;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.account.AccountService;

/**
* 业务管理
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:30
*/
@Scope("prototype")
public class BizAction  extends BaseAction<BizModel> {
    @Resource
    private BizService bizService;
    @Resource
    private AccountService accountService;
    @Resource
    private HospitalService hospitalService;
    @Resource
    private DrugSpecService drugSpecService;
    @Resource
    private SalesmanService salesmanService;
    @Resource
    private DepartmentService departmentService;

    public String index() {
        super.setTarget("index");
        setModel();
        Department entity = new Department();
        entity.setApprove(CmsConstant.PASSED);
        List<Department> departments = departmentService.findList(entity);
        model.setDepartments(departments);
        return COMMON;
    }

    private void setModel(){
        model.setSalesmanIdNameMap(salesmanService.getSalesmanIdNameMap());
        Hospital hospital = new Hospital();
        hospital.setApprove("PASSED");
        model.setHospitals(hospitalService.findList(hospital));
        model.setDrugSpecIdsNamesMap(drugSpecService.idsDrugSpecNameStrMap());
    }

    public String addOrEditIndex(){
        String id = model.getId();
        Biz biz = null;
        if (StringUtils.isBlank(id)){
            biz = new Biz();
            biz.setSpecDrugName("");
            biz.setSalesmanId("");
            biz.setHospitalId("");
            biz.setType("");
        }else {
            biz = bizService.getById(id);
            Map<String, String> idsDrugSpecNameStrMap = drugSpecService.idsDrugSpecNameStrMap();
            biz.setSpecDrugName(idsDrugSpecNameStrMap.get(biz.getDrugId()+","+biz.getSpecDrugId()));
            biz.setSaleDateText(DateUtil.convertTo_yyyy_MM_dd(biz.getSaleDate()));
            biz.setTraceStartDateText(DateUtil.convertTo_yyyy_MM_dd(biz.getTraceStartDate()));
            biz.setTraceEndDateText(DateUtil.convertTo_yyyy_MM_dd(biz.getTraceEndDate()));
        }

        model.setBiz(biz);
        setModel();
        super.setTarget("addOrEdit");
        return COMMON;
    }

    public String editSalesmanIndex(){
        model.setSalesmanIdNameMap(salesmanService.getSalesmanIdNameMap());
        super.setTarget("editSalesmanIndex");
        return COMMON;
    }

    public String addOrEdit(){
        Biz biz = model.getBiz();
        //增加医院名称
        Map<String, String> hospitalIdNameMap = hospitalService.getIdNameMap();
        String hospitalNamedStr = hospitalIdNameMap.get(biz.getHospitalId());
        biz.setNote(hospitalNamedStr);

        Map<String, String> drugSpecNameIdsStrMap = drugSpecService.drugSpecNameIdsStrMap();
        String drugSpecIdStr = drugSpecNameIdsStrMap.get(biz.getSpecDrugName());
        if (StringUtils.isBlank(drugSpecIdStr)){
            return renderJson(JsonResultUtil.err("请选择品规"));
        }

        Date traceStartDate = biz.getTraceStartDate();
        Date traceEndDate = biz.getTraceEndDate();
        if ("申请".equals(biz.getType()) && (traceEndDate == null || traceStartDate == null)){
            return renderJson(JsonResultUtil.err("类型为申请时，跟踪时间不能为空"));
        }

        String[] split = drugSpecIdStr.split(",");
        biz.setDrugId(split[0]);
        biz.setSpecDrugId(split[1]);

        String drugId = biz.getDrugId();
        String specDrugId = biz.getSpecDrugId();
        String hospitalId = biz.getHospitalId();

        List<Biz> bizList = bizService.findList(new Biz(biz.getSaleDate(),hospitalId,drugId, specDrugId));
        String id = biz.getId();
        if (CollectionUtils.isNotEmpty(bizList)){
            if( StringUtils.isBlank(id)){
                return renderJson(JsonResultUtil.err("当前医院对应品规在此销售时间已分配业务员"));
            }

            for (Biz tmp : bizList) {
                if (!tmp.getId().equals(id)){
                    return renderJson(JsonResultUtil.err("当前医院对应品规在此销售时间已分配业务员"));
                }
            }
        }


        if (StringUtils.isBlank(id)){
            biz.setId(null);
            biz.setApprove(CmsConstant.APPROVING);
            bizService.save(biz);
        }else {
            bizService.update(biz);
        }

        return renderJson(JsonResultUtil.location("biz.index.do"));
    }


    public String editSalesman(){
        Biz biz = model.getBiz();
        biz.setIds(Arrays.asList(biz.getId().split(",")));
        biz.setId(null);
        biz.setEditCheck("editCheck");
        bizService.update(biz);
        return renderJson(JsonResultUtil.location("biz.index.do"));
    }

    public String search() {
        Biz biz = model.getBiz();
        if(biz==null){
            biz=new Biz();
        }

        biz.setCorpCode(ExecutionContext.getCorpCode());
        Page<Biz>  page = model.getPage();

        String specDrugName = biz.getSpecDrugName();
        if (StringUtils.isNotBlank(specDrugName)){
            Map<String, String> drugSpecNameIdsStrMap = drugSpecService.drugSpecNameIdsStrMap();
            String drugSpecIdStr = drugSpecNameIdsStrMap.get(specDrugName);
            if (StringUtils.isNotBlank(drugSpecIdStr)){
                String[] split = drugSpecIdStr.split(",");
                biz.setDrugId(split[0]);
                biz.setSpecDrugId(split[1]);
            }
        }


        String departmentId = biz.getDepartmentId();
        if (StringUtils.isNotBlank(departmentId)){
            Salesman salesman = new Salesman();
            salesman.setDepartmentId(departmentId);
            List<Salesman> list = salesmanService.findList(salesman);
            if (CollectionUtils.isNotEmpty(list)){
                List<String> salesmanIds = new ArrayList<>(list.size());
                for (Salesman salesmanTmp : list) {
                    salesmanIds.add(salesmanTmp.getId());
                }

                biz.setSalesmanIds(salesmanIds);
            }else {
                return renderJson(page);
            }
        }

        page = bizService.search(biz, page);
        List<Biz> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return renderJson(page);
        }

        Map<String, String> hosIdNameMap = hospitalService.getIdNameMap();
        Map<String,String> aidNameMap = accountService.getAidNameMap();
        Map<String, String> idsDrugSpecNameStrMap = drugSpecService.idsDrugSpecNameStrMap();
        Map<String, String> salesmanIdNameMap = salesmanService.getSalesmanIdNameMap();
        for (Biz row : rows) {
            row.setSalesmanName(salesmanIdNameMap.get(row.getSalesmanId()));
            row.setHospitalName(hosIdNameMap.get(row.getHospitalId()));
            row.setCreateByName(aidNameMap.get(row.getCreateBy()));
            row.setSpecDrugName(idsDrugSpecNameStrMap.get(row.getDrugId() +","+ row.getSpecDrugId()));
            row.setSaleDateText(DateUtil.convertTo_yyyy_MM_dd(row.getSaleDate()));
            row.setTraceStartDateText(DateUtil.convertTo_yyyy_MM_dd(row.getTraceStartDate()));
            row.setTraceEndDateText(DateUtil.convertTo_yyyy_MM_dd(row.getTraceEndDate()));
        }

        return renderJson(page);
    }


    /**
    * 删除
    * @return
    */
    public String remove() {
        bizService.deleteById(model.getId());
        return renderJson(JsonResultUtil.location("biz.index.do"));
    }


    public String removeBatch() {
        String ids[]=request.getParameterValues("ids[]");
        if(ids.length>0){
            bizService.batchRemove(ids,ExecutionContext.getCorpCode(),ExecutionContext.getParentCorpCode());
        }

        return renderJson(JsonResultUtil.location("biz.index.do"));
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
        Biz biz = new Biz();
        biz.setIds(ids);
        biz.setApprove(CmsConstant.PASSED);
        biz.setEditCheck("editCheck");
        bizService.update(biz);
        return renderJson(JsonResultUtil.location("biz.index.do"));
    }

}
