package com.ace.app.cms.drug.action;


import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.account.Account;
import com.ace.app.cms.drug.domain.*;
import com.ace.app.cms.drug.model.OrderModel;
import com.ace.app.cms.drug.service.*;
import com.ace.framework.base.BaseAction;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.base.Page;
import com.ace.framework.util.DateUtil;
import com.ace.framework.util.ExecutionContext;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import org.springframework.context.annotation.Scope;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import com.ace.app.cms.account.AccountService;

/**
* 流向清单
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
@Scope("prototype")
public class OrderAction  extends BaseAction<OrderModel> {
    @Resource
    private OrderService orderService;
    @Resource
    private HospitalService hospitalService;
    @Resource
    private DrugSpecService drugSpecService;
    @Resource
    private CompanyService companyService;
    @Resource
    private DrugService drugService;
    @Resource
    private SellAreaService sellAreaService;
    @Resource
    private CompanyDrugService companyDrugService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private RebateService rebateService;
    @Resource
    private SalesmanService salesmanService;
    @Resource
    private DicService dicService;
    @Resource
    private CompanyDrugErpService companyDrugErpService;
    @Resource
    private AccountService accountService;

    private void setModel(){
//        model.setHospitalIdNameMap(hospitalService.getIdNameMap());
        Hospital hospital = new Hospital();
        hospital.setApprove("PASSED");
        model.setHospitals(hospitalService.findList(hospital));
        model.setDrugSpecIdsNamesMap(drugSpecService.idsDrugSpecNameStrMap());
        model.setCompanyIdNameMap(companyService.getComIdNameMap());
        model.setSalesmans(salesmanService.findList(null));
        model.setAccounts(accountService.listByCondition(new Account()));

        //
        List<Dic> priceTopics = dicService.findByCode(CmsConstant.DIC_TYPE_REBATE_PRICE_TOPIC);
        model.setPriceTopics(priceTopics);
        List<Dic> levels = dicService.findByCode(CmsConstant.DIC_TYPE_HOSPITAL_LEVEL);
        model.setLevels(levels);
        List<Dic> types = dicService.findByCode(CmsConstant.DIC_TYPE_HOSPITAL_TYPE);
        model.setTypes(types);
    }

    public String index() {
        setModel();
        super.setTarget("index");
        return COMMON;
    }

    public String addOrEditIndex(){
        String id = model.getId();
        Order order = null;
        if (StringUtils.isBlank(id)){
            order = new Order();
        }else {
            order = orderService.getById(id);
            Map<String, String> hosIdNameMap = hospitalService.getIdNameMap();
            Map<String, String> idsDrugSpecNameStrMap = drugSpecService.idsDrugSpecNameStrMap();
            Map<String, String> comIdNameMap = companyService.getComIdNameMap();

            String drugSpecName = idsDrugSpecNameStrMap.get(order.getDrugId() + ","+ order.getSpecId()) ;
            if (StringUtils.isNotBlank(drugSpecName)){
                order.setDrugSpecName(drugSpecName);
            }

            String hospitalName = hosIdNameMap.get(order.getHospitalId());
            if (StringUtils.isNotBlank(hospitalName)){
                order.setHospitalName(hospitalName);
            }

            String companyName = comIdNameMap.get(order.getCompanyId());
            if (StringUtils.isNotBlank(companyName)){
                order.setCompanyName(companyName);
            }

            order.setPriceText(order.getPrice().toString());
            order.setOrderDateText(DateUtil.convertTo_yyyy_MM_dd(order.getOrderDate()));
        }

        model.setOrder(order);
        setModel();
        super.setTarget("addOrEdit");
        return COMMON;
    }

    public String addOrEdit(){
        Order order = model.getOrder();
        Map<String, String> drugSpecNameIdsStrMap = drugSpecService.drugSpecAliaNameIdsStrMap();
        Map<String, BigDecimal> drugSpecNameCoefficientStrMap = drugSpecService.drugSpecNameCoefficientStrMap();
        Map<String, Drug> drugIdNameMap = drugService.getDrugIdMap();
        Map<String, String> specIdNameMap = drugSpecService.getIdSpecNameMap();
        Map<String, Hospital> nameHosMap = hospitalService.getNameHosMap();
        Map<String, Company> comNameIdMap = companyService.getComNameCompanyMap();
        Map<String, String> sellAreaIdNameMap = sellAreaService.sellAreaIdNameMap();
        Map<String, String> deptIdNameMap = departmentService.getDeptIdNameMap();
        Map<String, Salesman> salesmanIdNameMap = salesmanService.getIdSalesmanMap();
        Map<String, String> dicIdNameMap = dicService.getIdNameMap();

        String hospitalName = order.getHospitalName();
        if (StringUtils.isNotBlank(hospitalName)){
            order.setHospitalPreName(hospitalName);
        }

        String checkResult = orderService.checkOrderInfo(order, drugSpecNameIdsStrMap, drugSpecNameCoefficientStrMap,
                drugIdNameMap, specIdNameMap, nameHosMap,
                comNameIdMap, sellAreaIdNameMap, deptIdNameMap, salesmanIdNameMap, dicIdNameMap);

        if (StringUtils.isNotBlank(checkResult)){
            return renderJson(JsonResultUtil.success(checkResult));
        }

        if (StringUtils.isBlank(order.getId())){
            order.setId(null);
            companyDrugErpService.addCnt(order.getCompanyId(),order.getDrugId(),order.getSpecId(),BigDecimal.ZERO,order.getDrugCnt());
            companyDrugErpService.addCnt(order.getHospitalId(),order.getDrugId(),order.getSpecId(),order.getDrugCnt(),BigDecimal.ZERO);
            orderService.save(order);
        }else {
            orderService.update(order);
        }

        return renderJson(JsonResultUtil.location("order.index.do"));
    }


    public String search() {
        Order order = model.getOrder();

        if (order == null){
            OrderSummary condition = model.getOrderSummary();
            if(condition==null){
                condition=new OrderSummary();
            }

            order = new Order();
            order.setCompanyId(condition.getCompanyId());
            order.setSalesmanId(condition.getSalesmanId());
            order.setSalesmanName(condition.getSalesmanName());
            order.setDrugSpecName(condition.getDrugSpecName());
            order.setDepartmentName(condition.getDepartmentName());
            order.setBigArea(condition.getBigArea());
            order.setStartTime(condition.getStartTime());
            order.setEndTime(condition.getEndTime());
            order.setDrugNum(condition.getDrugNum());
            order.setHospitalArea(condition.getHospitalArea());
            order.setHospitalNameSplit(condition.getHospitalNameSplit());
            order.setStatus(condition.getStatus());
            //获取当前登录用户用户信息-总表搜索
            order.setDepartmentNameSplit(accountService.getById( ExecutionContext.getUserId(),"default").getEmail());

            String priceTopicName = condition.getPriceTopicName();
            if (StringUtils.isNotBlank(priceTopicName)){
                order.setPriceTopicName(priceTopicName.trim());
            }

            String hospitalLavel = condition.getHospitalLavel();
            if (StringUtils.isNotBlank(hospitalLavel)){
                order.setHospitalLavel(hospitalLavel.trim());
            }

            String hospitalType = condition.getHospitalType();
            if (StringUtils.isNotBlank(hospitalType)){
                order.setHospitalType(hospitalType.trim());
            }

            Map<String, String> drugSpecNameIdsStrMap = drugSpecService.drugSpecNameIdsStrMap();
            String drugSpecIdStr = drugSpecNameIdsStrMap.get(order.getDrugSpecName());
            String drugId = "";
            String specId = "";
            if (StringUtils.isNotBlank(drugSpecIdStr)){
                String[] split = drugSpecIdStr.split(",");
                drugId = split[0];
                order.setDrugId(drugId);
                specId = split[1];
                order.setSpecId(specId);
                order.setDrugSpecName(null);
            }

            java.util.Date endTime = order.getEndTime();
            if (endTime != null){
                order.setEndTime(DateUtil.getSpecifiedDateByDays(endTime,1));
            }

            java.util.Date createEnd = order.getCreateEnd();
            if (createEnd != null){
                order.setCreateEnd(DateUtil.getSpecifiedDateByDays(createEnd, 1));
            }

            String bigArea = order.getBigArea();
            if (StringUtils.isNotBlank(bigArea)){
                Department entity = new Department();
                entity.setFuck(bigArea);
                List<Department> list = departmentService.findList(entity);
                List<String> deptIds = new ArrayList<>();
                for (Department department : list) {
                    deptIds.add(department.getId());
                }

                if (CollectionUtils.isNotEmpty(deptIds)){
                    order.setDeptIds(deptIds);
                }
            }
            System.out.printf("我是if-");
        }else {
            //流向搜索
            //order.setDepartmentNameSplit(accountService.getById( ExecutionContext.getUserId(),"default").getEmail());
            Map<String, String> drugSpecNameIdsStrMap = drugSpecService.drugSpecNameIdsStrMap();
            String drugSpecIdStr = drugSpecNameIdsStrMap.get(order.getDrugSpecName());
            String drugId = "";
            String specId = "";
            if (StringUtils.isNotBlank(drugSpecIdStr)){
                String[] split = drugSpecIdStr.split(",");
                drugId = split[0];
                order.setDrugId(drugId);
                specId = split[1];
                order.setSpecId(specId);
                order.setDrugSpecName(null);
            }

            order.setCorpCode(ExecutionContext.getCorpCode());
            Page<Order>  page = model.getPage();
            Date endTime = order.getEndTime();
            if (endTime != null){
                order.setEndTime(DateUtil.getSpecifiedDateByDays(endTime,1));
            }

            Date createEnd = order.getCreateEnd();
            if (createEnd != null){
                order.setCreateEnd(DateUtil.getSpecifiedDateByDays(createEnd, 1));
            }

            String bigArea = order.getBigArea();
            if (StringUtils.isNotBlank(bigArea)){
                Department entity = new Department();
                entity.setFuck(bigArea);
                List<Department> list = departmentService.findList(entity);
                List<String> deptIds = new ArrayList<>();
                for (Department department : list) {
                    deptIds.add(department.getId());
                }

                if (CollectionUtils.isNotEmpty(deptIds)){
                    order.setDeptIds(deptIds);
                }
            }
            System.out.printf("我是else-");
        }

        Page<Order>  page = model.getPage();
        page = orderService.search(order, page);

        List<Order> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return renderJson(page);
        }

        Map<String,String> aidNameMap = accountService.getAidNameMap();
        Map<String, Department> deptIdDeptMap = departmentService.getDeptIdDeptMap();
        for (Order row : rows) {
            row.setCreateByName(aidNameMap.get(row.getCreateBy()));
            Department department = deptIdDeptMap.get(row.getDeptmentId());
            //Department department2 = deptIdDeptMap.get(departmentService.getDeptNameIdMap().get(row.getDepartmentName()));
          //huo qu da qu!
            if (department == null){
                continue;
            }

            String fuck = department.getFuck();
            if (StringUtils.isBlank(fuck)){
                continue;
            }

            row.setBigArea(fuck);
        }

        orderService.packageInfoList(rows);
        return renderJson(page);
    }


    /**
    * 删除
    * @return
    */
    public String remove() {
        String id = model.getId();

        Order order = orderService.getById(id);
        if (order != null){
            companyDrugErpService.subCnt(order.getCompanyId(),order.getDrugId(),order.getSpecId(),BigDecimal.ZERO,order.getDrugCnt());
            companyDrugErpService.subCnt(order.getHospitalId(),order.getDrugId(),order.getSpecId(),order.getDrugCnt(),BigDecimal.ZERO);
        }

        orderService.deleteById(id);
        return renderJson(JsonResultUtil.location("order.index.do"));
    }


    public String removeBatch() {

        String ids[]=request.getParameterValues("ids[]");

        if(ids.length>0){
            Order entity = new Order();
            entity.setIds(Arrays.asList(ids));
            List<Order> orders = orderService.findList(entity);
            for (Order order : orders) {
                companyDrugErpService.subCnt(order.getCompanyId(),order.getDrugId(),order.getSpecId(),BigDecimal.ZERO,order.getDrugCnt());
                companyDrugErpService.subCnt(order.getHospitalId(),order.getDrugId(),order.getSpecId(),order.getDrugCnt(),BigDecimal.ZERO);
            }

            orderService.batchRemove(ids,ExecutionContext.getCorpCode(),ExecutionContext.getParentCorpCode());
        }

        return renderJson(JsonResultUtil.location("order.index.do"));
    }


    ///////////////////////////////////////////////////////////////////
    public String summaryIndex() {
    //    return renderJson(JsonResultUtil.location("order.search.do"));

        setModel();
        Department entity = new Department();
        entity.setApprove(CmsConstant.PASSED);
        List<Department> departments = departmentService.findList(entity);
        Set<String> bigAreaSet = new HashSet<>();
        for (Department department : departments) {
            bigAreaSet.add(department.getFuck());
        }

        List<String> bigAreas = new ArrayList<>(bigAreaSet);
        model.setBigAreas(bigAreas);
        model.setDepartments(departments);
        //用户部门权限
        Order order = new Order();
        order.setDepartmentNameSplit(accountService.getById( ExecutionContext.getUserId(),"default").getEmail());
        model.setOrder(order);
        super.setTarget("summaryIndex");
       return COMMON;

    }


    public String editDeptIndex() {
        Department entity = new Department();
        entity.setApprove(CmsConstant.PASSED);
        List<Department> departments = departmentService.findList(entity);
        model.setDepartments(departments);
        super.setTarget("editDeptIndex");
        return COMMON;
    }

    public String editSalesmanIndex() {
        Salesman entity = new Salesman();
        entity.setApprove(CmsConstant.PASSED);
        List<Salesman> salesmens = salesmanService.findList(entity);
        model.setSalesmans(salesmens);
        super.setTarget("editSalesmanIndex");
        return COMMON;
    }

    public String editDept(){
        Order order = model.getOrder();
        order.setIds(Arrays.asList(order.getId().split(",")));
        order.setId(null);

        String deptmentId = order.getDeptmentId();
        Department department = departmentService.getById(deptmentId);
        order.setDepartmentName(department.getName());

        orderService.update(order);
        return renderJson(JsonResultUtil.location("order.summaryIndex.do"));
    }


    public String editSalesman(){
        Order order = model.getOrder();
        order.setIds(Arrays.asList(order.getId().split(",")));
        order.setId(null);

        /*String salesmanId = order.getSalesmanId();
        Salesman salesman = salesmanService.getById(salesmanId);*/
        order.setSalesmanName(order.getSalesmanName());

        orderService.update(order);
        return renderJson(JsonResultUtil.location("order.summaryIndex.do"));
    }


    public String editHosAreaIndex() {
        super.setTarget("editHosAreaIndex");
        return COMMON;
    }


    public String editPriceTopicNameIndex() {
        super.setTarget("editPriceTopicNameIndex");
        return COMMON;
    }

    public String editHospitalLavelIndex() {
        super.setTarget("editHospitalLavelIndex");
        return COMMON;
    }

    public String editHospitalTypeIndex() {
        super.setTarget("editHospitalTypeIndex");
        return COMMON;
    }

    public String editHosArea(){
        Order order = model.getOrder();
        order.setIds(Arrays.asList(order.getId().split(",")));
        order.setId(null);

        String hospitalArea = order.getHospitalArea();
        order.setHospitalArea(hospitalArea);

        orderService.update(order);
        return renderJson(JsonResultUtil.location("order.summaryIndex.do"));
    }

    public String editPriceTopicName(){
        Order order = model.getOrder();
        order.setIds(Arrays.asList(order.getId().split(",")));
        order.setId(null);

        order.setPriceTopicName(order.getPriceTopicName());

        orderService.update(order);
        return renderJson(JsonResultUtil.location("order.summaryIndex.do"));
    }

    public String editHospitalLavel(){
        Order order = model.getOrder();
        order.setIds(Arrays.asList(order.getId().split(",")));
        order.setId(null);

        order.setHospitalLavel(order.getHospitalLavel());

        orderService.update(order);
        return renderJson(JsonResultUtil.location("order.summaryIndex.do"));
    }

    public String editHospitalType(){
        Order order = model.getOrder();
        order.setIds(Arrays.asList(order.getId().split(",")));
        order.setId(null);

        order.setHospitalType(order.getHospitalType());

        orderService.update(order);
        return renderJson(JsonResultUtil.location("order.summaryIndex.do"));
    }


    public String editDarkIndex() {
        super.setTarget("editDarkIndex");
        return COMMON;
    }

    public String editDark(){
        Order orderTmp = model.getOrder();
        String[] ids = orderTmp.getId().split(",");
        BigDecimal darkPrice = orderTmp.getDarkPrice();
        for (String id : ids) {
            Order order = orderService.getById(id);
            if (order == null){
                continue;
            }

            order.setDarkPrice(darkPrice);
            order.setRebatePrice(order.getBrightPrice().add(order.getDarkPrice()));
            order.setRebateRate(BigDecimal.ZERO);
            if (order.getBiddingPrice() !=null && order.getBiddingPrice().compareTo(BigDecimal.ZERO) > 0){
                order.setRebateRate(order.getRebatePrice().divide(order.getBiddingPrice(), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
            }

            //实际结算价=医保支付价-返利单价
            BigDecimal rebatePrice = order.getRebatePrice();
            BigDecimal biddingPrice = order.getBiddingPrice();

            if (biddingPrice != null && rebatePrice != null) {
                BigDecimal actualPrice = biddingPrice.subtract(rebatePrice);
                order.setActualPrice(actualPrice);
                // totalMoney 销售金额【实际结算价格*数量】
                BigDecimal totalMoney = actualPrice.multiply(order.getDrugCnt());
                order.setTotalMoney(totalMoney);
            }

            orderService.update(order);
        }


        return renderJson(JsonResultUtil.location("order.summaryIndex.do"));
    }
}
