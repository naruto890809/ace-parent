package com.ace.app.cms.drug.action;


import com.ace.app.cms.account.Account;
import com.ace.app.cms.account.AccountService;
import com.ace.app.cms.drug.domain.*;
import com.ace.app.cms.drug.domain.report.*;
import com.ace.app.cms.drug.model.ReportModel;
import com.ace.app.cms.drug.service.*;
import com.ace.app.cms.util.ExcelUtils;
import com.ace.framework.base.BaseAction;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.base.Page;
import com.ace.framework.util.CollectionUtil;
import com.ace.framework.util.DateUtil;
import com.ace.framework.util.ExecutionContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
* 业务管理
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:30
*/
@Scope("prototype")
public class ReportAction extends BaseAction<ReportModel> {

    @Resource
    private HospitalService hospitalService;
    @Resource
    private DrugService drugService;
    @Resource
    private OrderService orderService;
    @Resource
    private BizService bizService;
    @Resource
    private SalesmanService salesmanService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private DepartmentJobService departmentJobService;
    @Resource
    private DrugSpecService drugSpecService;
    @Resource
    private AccountService accountService;

    public static final String EXCEPTION = "<span style='color:red'>异常</span>";

    //登录的用户信息
    //Account account = accountService.getById(ExecutionContext.getUserId(), ExecutionContext.getCorpCode());
    public String UI() {
        setModel();
        super.setTarget("UI");
        return COMMON;
    }

    private void setModel(){
        Account account = accountService.getById(ExecutionContext.getUserId(), ExecutionContext.getCorpCode());
        List bums = account.getAccountEmailList();
        model.setAccount(account);

        Hospital hospital = new Hospital();
        hospital.setApprove("PASSED");
        List<Hospital> hospitals = hospitalService.findList(hospital);
        model.setHospitals(hospitals);

        Drug drug = new Drug();
        drug.setApprove("PASSED");
        List<Drug> drugs = drugService.findList(drug);
        model.setDrugs(drugs);

        Department department = new Department();
        department.setApprove("PASSED");
        List<Department> departments = departmentService.findList(department);
        model.setDepartments(departments);

        List<DrugSpec> drugSpecs = drugSpecService.drugSpecIdNameList();
        model.setDrugSpecs(drugSpecs);

        Salesman salesman = new Salesman();
        salesman.setApprove("PASSED");
        List<Salesman> salesmen = salesmanService.findList(salesman);
        model.setSalesmen(salesmen);

        Date date = new Date();
        model.setStartDateText(DateUtil.convert(DateUtil.addMonth(date,-1),DateUtil.DATE_PATTERN_yyyy_MM_dd));
        model.setEndDateText(DateUtil.convertTo_yyyy_MM_dd(date));
    }


    public String sumary() {
        setModel();
        super.setTarget("summary");
        return COMMON;
    }

    public String drugHos() {
        setModel();
        super.setTarget("drugHos");
        return COMMON;
    }

    public String cSale() {
        setModel();
        model.setEndDateText(DateUtil.convert(new Date(),"yyyy-MM"));
        super.setTarget("cSale");
        return COMMON;
    }


    public String newHos() {
        setModel();
        super.setTarget("newHos");
        return COMMON;
    }


    public String hosCnt() {
        setModel();
        super.setTarget("hosCnt");
        Date date = new Date();
        model.setStartDateText(DateUtil.convert(DateUtil.addMonth(date,-1),"yyyy-MM"));
        model.setEndDateText(DateUtil.convert(date,"yyyy-MM"));
        return COMMON;
    }


    private Page<Order> getSummaryPage(Page<Order> page){
        Order order = model.getOrder();
        if(order==null){
            order=new Order();
        }
        String hospitalCity = order.getHospitalCity();
        if (StringUtils.isNotBlank(hospitalCity)){
            Hospital hospital = new Hospital();
            hospital.setCity(hospitalCity);
            List<Hospital> hospitals = hospitalService.findList(hospital);
            if (CollectionUtils.isEmpty(hospitals)){
                return page;
            }

            List<String> hosIds = new ArrayList<>(hospitals.size());
            for (Hospital tmpHos : hospitals) {
                hosIds.add(tmpHos.getId());
            }

            order.setHospIds(hosIds);
        }

        Date date = new Date();

        if (order.getStartTime() == null){
            order.setStartTime(DateUtil.convert(DateUtil.convert(DateUtil.addMonth(date,-1),DateUtil.DATE_PATTERN_yyyy_MM_dd)));
        }

        if (order.getEndTime() == null){
            order.setEndTime(DateUtil.convert(DateUtil.convertTo_yyyy_MM_dd(date)));
        }

        order.setEndTime(DateUtil.getSpecifiedDateByDays(order.getEndTime(),1));

        if (StringUtils.isBlank(order.getReportType())){
            order.setReportType("coefficient");
        }

        page = orderService.summaryPage(order,page);
        List<Order> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return page;
        }

        String summaryType = order.getSummaryType();
        if (StringUtils.isBlank(summaryType)){
            summaryType = "整体";
        }else if ("cx".equals(summaryType)){
            summaryType = "纯销";
        }else if ("ls".equals(summaryType)){
            summaryType = "零售";
        }

        Date startTime = order.getStartTime();
        Date endTime = order.getEndTime();

        //计算同比
        order.setStartTime(DateUtil.getSpecifiedDateByYear(startTime,-1));
        order.setEndTime(DateUtil.getSpecifiedDateByYear(endTime,-1));
        Map<String, BigDecimal> drugHosTBMap = orderService.summaryMap(order);

        //计算环比
        order.setStartTime(DateUtil.getSpecifiedDateByDays(startTime,-DateUtil.calculateDayCnt(startTime,endTime)));
        order.setEndTime(startTime);
        Map<String, BigDecimal> drugHosHBMap = orderService.summaryMap(order);


        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal hundred = new BigDecimal(100);//一百
        for (Order row : rows) {
            row.setSummaryType(summaryType);
            BigDecimal fZ = null; //计算同比环比的分子
            if ("coefficient".equals(order.getReportType())){
                BigDecimal drugCnt = row.getDrugCnt();
                if (drugCnt == null){
                    continue;
                }
                fZ = drugCnt;
                row.setAmountOrMoney(drugCnt.toString());
            }else {
                BigDecimal totalMoney = row.getTotalMoney();
                fZ = totalMoney;
                row.setAmountOrMoney(totalMoney +"元");
            }


            //设置同比
            String drugHosIdStr = row.getDrugId() + row.getHospitalId();
            BigDecimal tB = drugHosTBMap.get(drugHosIdStr);
            if (fZ == null || fZ.compareTo(zero) == 0){
                row.setTb(zero.toString()+"%");
            }else if (tB == null || tB.compareTo(zero) == 0){
                row.setTb(EXCEPTION);
            }else {
                row.setTb(fZ.divide(tB,2,BigDecimal.ROUND_HALF_UP).multiply(hundred).toString()+"%");
            }

            //设置环比
            BigDecimal hB = drugHosHBMap.get(drugHosIdStr);
            if (fZ == null || fZ.compareTo(zero) == 0){
                row.setHb(zero.toString()+"%");
            }else if (hB == null || hB.compareTo(zero) == 0){
                row.setHb(EXCEPTION);
            }else {
                row.setHb(fZ.divide(hB,2,BigDecimal.ROUND_HALF_UP).multiply(hundred).toString()+"%");
            }
        }

        return page;
    }

    public String summaryPage() {
        Page<Order> page = model.getPage();
        page = getSummaryPage(page);
        return renderJson(page);
    }

    public void exportSummary(){
        Page<Order> page = model.getPage();
        page.setAutoPaging(false);
        page = getSummaryPage(page);
        List<Order> rows = page.getRows();
        List<SummaryReport> summaryReports = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(rows)){
            for (Order row : rows) {
                String tb = (StringUtils.isNotBlank(row.getTb()) && row.getTb().contains("span"))?"异常":row.getTb();
                String hb =(StringUtils.isNotBlank(row.getHb()) && row.getHb().contains("span"))?"异常":row.getHb();
                summaryReports.add(new SummaryReport(row.getHospitalCity(), row.getHospitalArea(),
                        row.getDepartmentName(),row.getYear(),row.getMonth(), row.getDrugName(), row.getSummaryType()
                        , row.getAmountOrMoney(), tb, hb));
            }
        }
        ExcelUtils.exportExcel(summaryReports, SummaryReport.class,  "销售概况.xlsx", response);
    }


    public void exportDrugHos(){
        Page<Order> page = model.getPage();
        page.setAutoPaging(false);

        page = getDrugHosPage(page);
        List<Order> rows = page.getRows();
        List<DrugHosReport> summaryReports = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(rows)){
            for (Order row : rows) {
                String tb = (StringUtils.isNotBlank(row.getTb()) && row.getTb().contains("span"))?"异常":row.getTb();
                String hb =(StringUtils.isNotBlank(row.getHb()) && row.getHb().contains("span"))?"异常":row.getHb();
                //summaryReports.add(new DrugHosReport(row.getDrugName(), row.getHospitalName(), row.getDepartmentName(),
                       // row.getSalesmanName(), row.getAmountOrMoney(), tb, hb));
                summaryReports.add(new DrugHosReport(row.getDrugName(), row.getHospitalName(), row.getDepartmentName(),
                        row.getSalesmanName(), row.getAmountOrMoney(), tb, hb));
            }
        }
        ExcelUtils.exportExcel(summaryReports, DrugHosReport.class,  "产品医院报表.xlsx", response);
    }

    private Page<Order> getDrugHosPage(Page<Order> page){
        Order order = model.getOrder();
        if(order==null){
            order=new Order();
        }
        Account account = accountService.getById(ExecutionContext.getUserId(), ExecutionContext.getCorpCode());
        order.setDepartmentNameSplit(account.getEmail());
        Date date = new Date();

        if (order.getStartTime() == null){
            order.setStartTime(DateUtil.convert(DateUtil.convert(DateUtil.addMonth(date,-1),DateUtil.DATE_PATTERN_yyyy_MM_dd)));
        }

        if (order.getEndTime() == null){
            order.setEndTime(DateUtil.convert(DateUtil.convertTo_yyyy_MM_dd(date)));
        }

        order.setEndTime(DateUtil.getSpecifiedDateByDays(order.getEndTime(),1));

        if (StringUtils.isBlank(order.getReportType())){
            order.setReportType("coefficient");
        }

        page = orderService.drugHosPage(order,page);
        List<Order> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return page;
        }

        Date startTime = order.getStartTime();
        Date endTime = order.getEndTime();

        //计算同比
        order.setStartTime(DateUtil.getSpecifiedDateByYear(startTime,-1));
        order.setEndTime(DateUtil.getSpecifiedDateByYear(endTime,-1));
        Map<String, BigDecimal> drugHosTBMap = orderService.drugHosBMap(order);

        //计算环比
        order.setStartTime(DateUtil.getSpecifiedDateByDays(startTime,-DateUtil.calculateDayCnt(startTime,endTime)));
        order.setEndTime(startTime);
        Map<String, BigDecimal> drugHosHBMap = orderService.drugHosBMap(order);


        Map<String, String> drugIdNameMap = drugService.getDrugIdNameMap();
        Map<String, String> hostIdNameMap = hospitalService.getIdNameMap();
        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal hundred = new BigDecimal(100);//一百
        for (Order row : rows) {
            BigDecimal fZ = null; //计算同比环比的分子
            row.setDrugName(drugIdNameMap.get(row.getDrugId()));
            row.setHospitalName(hostIdNameMap.get(row.getHospitalId()));
            if ("coefficient".equals(order.getReportType())){
                BigDecimal drugCnt = row.getDrugCnt();
                fZ = drugCnt;
                row.setAmountOrMoney(drugCnt.toString());
            }else {
                BigDecimal totalMoney = row.getTotalMoney();
                fZ = totalMoney;
                row.setAmountOrMoney(totalMoney +"元");
            }


            //设置同比
            String drugHosIdStr = row.getDrugId() + row.getHospitalId();
            BigDecimal tB = drugHosTBMap.get(drugHosIdStr);
            if (fZ == null || fZ.compareTo(zero) == 0){
                row.setTb(zero.toString()+"%");
            }else if (tB == null || tB.compareTo(zero) == 0){
                row.setTb(EXCEPTION);
            }else {
                row.setTb(fZ.divide(tB,2,BigDecimal.ROUND_HALF_UP).multiply(hundred).toString()+"%");
            }

            //设置环比
            BigDecimal hB = drugHosHBMap.get(drugHosIdStr);
            if (fZ == null || fZ.compareTo(zero) == 0){
                row.setHb(zero.toString()+"%");
            }else if (hB == null || hB.compareTo(zero) == 0){
                row.setHb(EXCEPTION);
            }else {
                row.setHb(fZ.divide(hB,2,BigDecimal.ROUND_HALF_UP).multiply(hundred).toString()+"%");
            }


            //根据drugId和hospitalId去biz查业务员
            String drugId = row.getDrugId();
            String hospitalId = row.getHospitalId();
            System.out.println("业务员"+drugId+" "+hospitalId);
            List<Biz> bizs = bizService.findList(new Biz(hospitalId,  drugId, null,  startTime,  endTime));
            if (CollectionUtils.isNotEmpty(bizs)){
                System.out.println("业务员xx"+bizs.get(0).toString());
                String salesmanId = bizs.get(0).getSalesmanId();
                Salesman salesman = salesmanService.getById(salesmanId);
                System.out.println("业务员项目"+salesman.getName());
                if (salesman != null){
                    row.setSalesmanName(salesman.getName());
                    String departmentId = salesman.getDepartmentId();
                    Department department = departmentService.getById(departmentId);
                    if (department != null){
                        row.setDepartmentName(department.getName());
                        System.out.println("部门项目"+department.getName());
                    }
                }
            }
        }

        return page;
    }
    public String drugHosPage() {
        Page<Order> page = model.getPage();
        page = getDrugHosPage(page);
        return renderJson(page);
    }


    public void exportCSale(){
        Page<Order> page = model.getPage();
        page.setAutoPaging(false);
        page = getCSalePage(page);
        List<Order> rows = page.getRows();
        List<CSaleReport> summaryReports = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(rows)){
            for (Order row : rows) {
                String monthTb = (StringUtils.isNotBlank(row.getMonthTb()) && row.getMonthTb().contains("span"))?"异常":row.getMonthTb();
                String yearTb =(StringUtils.isNotBlank(row.getYearTb()) && row.getYearTb().contains("span"))?"异常":row.getYearTb();
                summaryReports.add(new CSaleReport(row.getDepartmentName(), row.getDrugName(), row.getTaskCnt(),
                        row.getDrugCnt(), monthTb, row.getYearCnt(), yearTb, row.getCompleteRatio(), row.getDif()));
            }
        }
        ExcelUtils.exportExcel(summaryReports, CSaleReport.class,  "纯销数据.xlsx", response);
    }

    private Page<Order> getCSalePage(Page<Order> page){
        Order order = model.getOrder();
        if(order==null){
            order=new Order();
            order.setEndTimeText(DateUtil.convert(new Date(),"yyyy-MM"));
        }
        Account account = accountService.getById(ExecutionContext.getUserId(), ExecutionContext.getCorpCode());
        order.setDepartmentNameSplit(account.getEmail());
        //当月开始结束时间
        Date startTime = DateUtil.convert(order.getEndTimeText()+"-01");
        Date endTime = DateUtil.getSpecifiedDateByMonths(startTime,1);
        order.setStartTime(startTime);
        order.setEndTime(endTime);

        page = orderService.cSalePage(order,page);
        List<Order> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return page;
        }


        //计算当月同比 去年这个月的销量
        order.setStartTime(DateUtil.getSpecifiedDateByYear(startTime,-1));
        order.setEndTime(DateUtil.getSpecifiedDateByYear(endTime,-1));
        Map<String, BigDecimal> lastYearThisMonthCntMap = orderService.cSaleBMap(order);

        //计算当年累计销量
        String currentYearStr = order.getEndTimeText().substring(0, 4);
        java.sql.Date currentYearBegin = DateUtil.convert(currentYearStr + "-01-01");
        order.setStartTime(currentYearBegin);
        order.setEndTime(endTime);
        Map<String, BigDecimal> currentYearTotalCntMap = orderService.cSaleBMap(order);

        //计算去年累计销量
        order.setStartTime(DateUtil.getSpecifiedDateByYear(currentYearBegin,-1));
        order.setEndTime(DateUtil.getSpecifiedDateByYear(endTime,-1));
        Map<String, BigDecimal> lastYearTotalCntMap = orderService.cSaleBMap(order);


        Map<String, String> deptIdNameMap = departmentService.getDeptIdNameMap();
        Map<String, String> drugIdNameMap = drugService.getDrugIdNameMap();
        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal hundred = new BigDecimal(100);//一百
        Integer currentYear = Integer.parseInt(currentYearStr);

        for (Order row : rows) {
            //Integer thisYearMonthCnt = row.getDrugCnt();//当月销量
            BigDecimal thisYearMonthCnt = row.getCoefficient();//当月zhh销量
            String deptmentId = row.getDeptmentId();
            String drugId = row.getDrugId();
            String drugDepIdStr = drugId + deptmentId;
            BigDecimal lastYearMonthCntTb = lastYearThisMonthCntMap.get(drugDepIdStr);

            //设置当月同比
            if (thisYearMonthCnt == null || thisYearMonthCnt.equals(BigDecimal.ZERO)){
                row.setMonthTb(zero.toString()+"%");
            }else if (lastYearMonthCntTb == null || lastYearMonthCntTb.compareTo(zero) == 0){
                row.setMonthTb(EXCEPTION);
            }else {
                row.setMonthTb(thisYearMonthCnt.divide(lastYearMonthCntTb,2,BigDecimal.ROUND_HALF_UP).multiply(hundred).toString()+"%");
            }

            //设置当年累计销量、同比
            BigDecimal currentYearCnt = currentYearTotalCntMap.get(drugDepIdStr);
            row.setYearCnt(currentYearCnt);
            BigDecimal lastYearCnt = lastYearTotalCntMap.get(drugDepIdStr);
            if (currentYearCnt == null || currentYearCnt.compareTo(zero) == 0){
                currentYearCnt = zero;
                row.setYearTb(zero.toString()+"%");
            }else if (lastYearCnt == null || lastYearCnt.compareTo(zero) == 0){
                row.setYearTb(EXCEPTION);
            }else {
                row.setYearTb(currentYearCnt.divide(lastYearCnt,2,BigDecimal.ROUND_HALF_UP).multiply(hundred).toString()+"%");
            }


            //设置部门名称
            String deptName = deptIdNameMap.get(deptmentId);
            row.setDrugName(drugIdNameMap.get(drugId));
            row.setDepartmentName(deptName);

            //设置任务量
            DepartmentJob departmentJob = new DepartmentJob();
            departmentJob.setDepartmentId(deptmentId);
            departmentJob.setDrugId(drugId);
            departmentJob.setYear(currentYear);
            List<DepartmentJob> departmentJobs = departmentJobService.findList(departmentJob);
            if (CollectionUtils.isNotEmpty(departmentJobs)){
                Integer jobAmount = departmentJobs.get(0).getJobAmount();
                BigDecimal taskCnt = new BigDecimal(jobAmount);
                row.setTaskCnt(taskCnt);
                row.setCompleteRatio(currentYearCnt.divide(taskCnt,2,BigDecimal.ROUND_HALF_UP).multiply(hundred));
                row.setDif(currentYearCnt.subtract(taskCnt));
            }
        }

        return page;
    }

    public String cSalePage() {
        Page<Order> page = model.getPage();
        page = getCSalePage(page);
        return renderJson(page);
    }

    public void exportNewHos(){
        Page<Order> page = model.getPage();
        page.setAutoPaging(false);
        page = getNewHosPage(page);
        List<Order> rows = page.getRows();
        List<NewHosReport> summaryReports = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(rows)){
            for (Order row : rows) {
                String ratio =(StringUtils.isNotBlank(row.getRatio()) && row.getRatio().contains("span"))?"异常":row.getRatio();

                summaryReports.add(new NewHosReport(row.getDepartmentName(), row.getDrugSpecName(),
                        row.getAmountOrMoney(), row.getCxAddCnt(), ratio));
            }
        }
        ExcelUtils.exportExcel(summaryReports, NewHosReport.class,  "新申请医院.xlsx", response);
    }

    private Page<Order> getNewHosPage(Page<Order> page){
        Order order = model.getOrder();
        if(order==null){
            order=new Order();
        }
        Date date = new Date();
        if (order.getStartTime() == null){
            order.setStartTime(DateUtil.convert(DateUtil.convert(DateUtil.addMonth(date,-1),DateUtil.DATE_PATTERN_yyyy_MM_dd)));
        }
        if (order.getEndTime() == null){
            order.setEndTime(DateUtil.convert(DateUtil.convertTo_yyyy_MM_dd(date)));
        }
        order.setEndTime(DateUtil.getSpecifiedDateByDays(order.getEndTime(),1));
        Date startTime = order.getStartTime();
        Date endTime = order.getEndTime();



        //根据时间段找biz中类型为申请、跟踪时间段在该时间端范围内的医院
        Biz biz = new Biz();
        biz.setConditionStartDate(startTime);
        biz.setConditionEndDate(endTime);
        biz.setType("申请");
        biz.setApprove("PASSED");
        List<Biz> bizList = bizService.findList(biz);
        if (CollectionUtils.isEmpty(bizList)){
            return page;
        }
        List<String> hospIds = new ArrayList<>(bizList.size());
        for (Biz tmp : bizList) {
            hospIds.add(tmp.getHospitalId());
        }

        order.setHospIds(hospIds);
        if (StringUtils.isBlank(order.getReportType())){
            order.setReportType("coefficient");
        }

        String drugIdTmp = order.getDrugId();
        String specIdTmp = "";
        if (StringUtils.isNotBlank(drugIdTmp)){
            String[] split = drugIdTmp.split(",");
            drugIdTmp = split[0];
            specIdTmp = split[1];
            order.setDrugId(drugIdTmp);
            order.setSpecId(specIdTmp);
        }

        page = orderService.newHosPage(order,page);
        List<Order> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return page;
        }

        //纯销环比增长量
        order.setStartTime(DateUtil.getSpecifiedDateByDays(startTime,-DateUtil.calculateDayCnt(startTime,endTime)));
        order.setEndTime(startTime);
        order.setCx("YES");
        Map<String, BigDecimal> newHosBMap = orderService.newHosBMap(order);

        Map<String, String> drugIdNameMap = drugSpecService.idsDrugSpecNameStrMap();
        Map<String, String> deptIdNameMap = departmentService.getDeptIdNameMap();
        BigDecimal hundred = new BigDecimal(100);//一百
        for (Order row : rows) {
            BigDecimal fZ = null; //当前时间销量、金额
            String drugId = row.getDrugId();
            String specId = row.getSpecId();
            row.setDrugSpecName(drugIdNameMap.get(drugId +","+ specId));
            String deptmentId = row.getDeptmentId();
            row.setDepartmentName(deptIdNameMap.get(deptmentId));
            if ("coefficient".equals(order.getReportType())){
                BigDecimal drugCnt = row.getDrugCnt();
                fZ = drugCnt;
                row.setAmountOrMoney(drugCnt.toString());
            }else {
                BigDecimal totalMoney = row.getTotalMoney();
                fZ = totalMoney;
                row.setAmountOrMoney(totalMoney +"元");
            }


            //纯销环比数量
            BigDecimal cXhBCnt = newHosBMap.get(drugId + specId + deptmentId);
            if (cXhBCnt == null){
                cXhBCnt = BigDecimal.ZERO;
            }

            if (fZ == null){
                fZ = BigDecimal.ZERO;
            }

            BigDecimal dif = fZ.subtract(cXhBCnt);
            row.setCxAddCnt(dif.toString());
            if (dif.compareTo(BigDecimal.ZERO) == 0){
                row.setRatio(EXCEPTION);
            }else {
                row.setRatio(fZ.divide(dif,2,BigDecimal.ROUND_HALF_UP).multiply(hundred).toString()+"%");
            }
        }

        return page;
    }

    public String newHosPage() {
        Page<Order> page = model.getPage();
        page = getNewHosPage(page);
        return renderJson(page);
    }


    public void exportHosCnt(){
        Page<Order> page = model.getPage();
        page.setAutoPaging(false);
        page = getHosCntPage(page);
        List<Order> rows = page.getRows();
        List<HosCntReport> summaryReports = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(rows)){
            for (Order row : rows) {
                String ratio =(StringUtils.isNotBlank(row.getRatio()) && row.getRatio().contains("span"))?"异常":row.getRatio();

                summaryReports.add(new HosCntReport(row.getDepartmentName(), row.getSalesmanName(),
                        row.getHospitalName(), row.getDrugSpecName(), row.getDrugCnt(), row.getTaskCnt(), row.getDif()));
            }
        }
        ExcelUtils.exportExcel(summaryReports, HosCntReport.class,  "考核医院数量.xlsx", response);
    }

    private Page<Order> getHosCntPage(Page<Order> page){
        Order order = model.getOrder();
        if(order==null){
            order=new Order();
            Date date = new Date();
            order.setStartTimeText(DateUtil.convert(DateUtil.addMonth(date,-1),"yyyy-MM"));
            order.setEndTimeText(DateUtil.convert(date,"yyyy-MM"));
        }


        Date startTime = DateUtil.convert(order.getStartTimeText()+"-01");
        Date endTime = DateUtil.getSpecifiedDateByMonths(DateUtil.convert(order.getEndTimeText()+"-01"),1);
        //根据时间段找biz中类型为申请、跟踪时间段在该时间端范围内的医院
        Biz biz = new Biz();
        biz.setConditionStartDate(startTime);
        biz.setConditionEndDate(endTime);
        biz.setType("申请");
        biz.setApprove("PASSED");
        biz.setSalesmanId(order.getSalesmanId());
        biz.setHospitalId(order.getHospitalId());
        String drugId = order.getDrugId();
        String specId = "";
        if (StringUtils.isNotBlank(drugId)){
            String[] split = drugId.split(",");
            drugId = split[0];
            specId = split[1];
            biz.setDrugId(drugId);
            biz.setSpecDrugId(specId);
            order.setDrugId(drugId);
            order.setSpecId(specId);
        }

        List<Biz> bizList = bizService.findList(biz);
        if (CollectionUtils.isEmpty(bizList)){
            return page;
        }

        List<String> hospIds = new ArrayList<>(bizList.size());
        List<String> drugIds = new ArrayList<>(bizList.size());
        List<String> specIds = new ArrayList<>(bizList.size());
        List<String> salesmanIds = new ArrayList<>(bizList.size());
        Map<String,Integer> saleHosDrugSpecIdTaskMap = new HashMap<>();
        Map<String,String> saleHosDrugSpecIdDateMap = new HashMap<>();

        for (Biz tmp : bizList) {
            String hospitalId = tmp.getHospitalId();
            hospIds.add(hospitalId);
            String drugIdTmp = tmp.getDrugId();
            drugIds.add(drugIdTmp);
            String specDrugId = tmp.getSpecDrugId();
            specIds.add(specDrugId);
            String salesmanId = tmp.getSalesmanId();
            salesmanIds.add(salesmanId);
            String key = salesmanId + hospitalId + tmp.getDrugId() + specDrugId;
            saleHosDrugSpecIdTaskMap.put(key,tmp.getAmount());
            saleHosDrugSpecIdDateMap.put(key,DateUtil.convert(tmp.getTraceStartDate(),DateUtil.DATE_PATTERN_yyyyMMdd)+"-"+DateUtil.convert(tmp.getTraceEndDate(),DateUtil.DATE_PATTERN_yyyyMMdd));
        }

        order.setStartTime(startTime);
        order.setEndTime(endTime);
        order.setHospIds(hospIds);
        order.setDrugIds(drugIds);
        order.setSpecIds(specIds);
        order.setSalesmanIds(salesmanIds);

        page = orderService.hosCntPage(order,page);
        List<Order> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return page;
        }


        Map<String, String> drugIdNameMap = drugSpecService.idsDrugSpecNameStrMap();
        Map<String, String> deptIdNameMap = departmentService.getDeptIdNameMap();
        Map<String, Salesman> idSalesmanMap = salesmanService.getIdSalesmanMap();
        Map<String, String> hospitalIdNameMap = hospitalService.getIdNameMap();
        int monthCnt = DateUtil.calculateMonthCnt(startTime,endTime);
        if (monthCnt == 0){
            monthCnt = 1;
        }
        for (Order row : rows) {
            String drugIdTmp = row.getDrugId();
            String specIdTmp = row.getSpecId();
            row.setDrugSpecName(drugIdNameMap.get(drugIdTmp +","+ specIdTmp));
            String hospitalId = row.getHospitalId();
            row.setHospitalName(hospitalIdNameMap.get(hospitalId));
            String salesmanId = row.getSalesmanId();
            Salesman salesman = idSalesmanMap.get(salesmanId);
            if (salesman != null){
                row.setSalesmanName(salesman.getName());
                row.setDepartmentName(deptIdNameMap.get(salesman.getDepartmentId()));
            }

            //任务量
            String key = salesmanId + hospitalId + drugIdTmp + specIdTmp;
            Integer taskCnt = saleHosDrugSpecIdTaskMap.get(key);
            String traceRange = saleHosDrugSpecIdDateMap.get(key);
            if (StringUtils.isNotBlank(traceRange)){
                row.setTraceRange(traceRange);
            }

            if (taskCnt == null){
                taskCnt = 0;
            }
            row.setTaskCnt(new BigDecimal(taskCnt));
            //月均销售量  任务量 ，如果小于任务量，标记红色
            BigDecimal drugCnt = row.getDrugCnt();
            if (drugCnt == null){
                drugCnt = BigDecimal.ZERO;
            }
            BigDecimal monthCntD = new BigDecimal(monthCnt);
            //胡明-相除
            drugCnt = drugCnt.divide(monthCntD);
            row.setDrugCnt(drugCnt);
            row.setDif(drugCnt.subtract(new BigDecimal(taskCnt)));
        }

        return page;
    }
    public String hosCntPage() {
        Page<Order> page = model.getPage();
        page = getHosCntPage(page);
        return renderJson(page);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String departmentReport(){
        Order order = model.getOrder();
        Map<String,Object> result = new HashMap<>();
        List<String> depNames = new ArrayList<>();
        order.setEndTime(DateUtil.getSpecifiedDateByDays(order.getEndTime(),1));
        List<Chat> chats = null;
        List<Order> orders = new ArrayList<>();
        try {
            order.setLimitCnt(10);
            orders = orderService.departmentReport(order);
            chats = new ArrayList<>();
            BigDecimal totalMoney = BigDecimal.ZERO;
            for (Order tmp : orders) {
                depNames.add(tmp.getDepartmentName());
                chats.add(new Chat(tmp.getDepartmentName(),tmp.getTotalMoney()));
                totalMoney = totalMoney.add(tmp.getTotalMoney());

                String hospitalCity = tmp.getHospitalCity();
                if (order.getReportDif().equals("hospital_area") && StringUtils.isNotBlank(hospitalCity)){
                    tmp.setDepartmentName(hospitalCity +tmp.getDepartmentName());
                }
            }

            BigDecimal hundred = new BigDecimal(100);
            for (Order tmp : orders) {
                String radio = tmp.getTotalMoney().divide(totalMoney, 4, BigDecimal.ROUND_HALF_UP).multiply(hundred).toString();
                tmp.setRatio(radio.substring(0,radio.length()-2) +"%");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            result.put("names",depNames);
            result.put("nameValues",chats);
            result.put("orders",orders);
        }

        return renderJson(JsonResultUtil.success(result));
    }


    public String departmentDrugReportIndex() {
        setModel();
        super.setTarget("departmentDrugReportIndex");
        return COMMON;
    }
    public String departmentDrugReport(){
        Order order = model.getOrder();
        order.setEndTime(DateUtil.getSpecifiedDateByDays(order.getEndTime(),1));
        Map<String,Object> result = new HashMap<>();
        List<String> names = new ArrayList<>();
        List<Chat> chats = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        String reportDifName = order.getReportDifName();
        if (StringUtils.isBlank(reportDifName)){
            /*List<Department> departments = departmentService.findList(new Department());
            if (CollectionUtils.isNotEmpty(departments)){
                reportDifName = departments.get(0).getName();
                order.setReportDifName(reportDifName);
            }*/

            order.setReportDif(null);
        }

        try {
            orders = orderService.departmentDrugReport(order);
            if (StringUtils.isBlank(reportDifName)){
                reportDifName = "浙江省";
            }
            for (Order tmp : orders) {
                BigDecimal totalMoney = tmp.getTotalMoney();
                if ("coefficient".equals(order.getReportType())){
                    totalMoney = tmp.getDrugCnt();
                }

                String totalMoneyText = totalMoney.toString() + ("total_money".equals(order.getReportType()) ? "元" : "");

                tmp.setTotalMoneyText(totalMoneyText);
                tmp.setReportDifName(reportDifName);
                names.add(tmp.getDrugName());
                chats.add(new Chat(tmp.getDrugName(),totalMoney));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            result.put("names",names);
            result.put("nameValues",chats);
            result.put("reportDifName", reportDifName);
            result.put("orders",orders);
        }

        return renderJson(JsonResultUtil.success(result));
    }

    public String drugDepartmentReportIndex() {
        setModel();
        super.setTarget("drugDepartmentReportIndex");
        return COMMON;
    }

    public String drugDepartmentReport(){
        Order order = model.getOrder();
        order.setEndTime(DateUtil.getSpecifiedDateByDays(order.getEndTime(),1));
        Map<String,Object> result = new HashMap<>();
        List<String> names = new ArrayList<>();
        List<BigDecimal> values = new ArrayList<>();
        List<Chat> chats = new ArrayList<>();

        String drugName = "";
        List<Order> orders = new ArrayList<>();
        try {
            orders = orderService.drugDepartmentReport(order);
            drugName = drugService.getById(order.getDrugId()).getName();
            for (Order tmp : orders) {
                tmp.setDrugName(drugName);
                names.add(tmp.getDepartmentName());
                BigDecimal totalMoney = tmp.getTotalMoney();
                if ("coefficient".equals(order.getReportType())){
                    BigDecimal drugCnt = tmp.getDrugCnt();
                    if (drugCnt == null){
                        drugCnt = BigDecimal.ZERO;
                    }
                    totalMoney =drugCnt;
                }

                values.add(totalMoney);
                chats.add(new Chat(tmp.getDepartmentName(), totalMoney));
                String name = totalMoney.toString() + ("total_money".equals(order.getReportType()) ? "元" : "");
                tmp.setTotalMoneyText(name);
                tmp.setDrugName(drugName);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            result.put("names",names);
            result.put("nameValues",chats);
            result.put("drugName", drugName);
            result.put("values", values);
            result.put("orders", orders);
        }

        return renderJson(JsonResultUtil.success(result));
    }

    public String drugAreaReportIndex() {
        setModel();
        super.setTarget("drugAreaReportIndex");
        return COMMON;
    }

    public String drugAreaReport(){
        Order order = model.getOrder();
        order.setEndTime(DateUtil.getSpecifiedDateByDays(order.getEndTime(),1));
        Map<String,Object> result = new HashMap<>();
        List<String> names = new ArrayList<>();
        List<BigDecimal> values = new ArrayList<>();
        List<Chat> chats = new ArrayList<>();

        String drugName = "";
        List<Order> orders = new ArrayList<>();
        try {
            order.setLimitCnt(10);
            orders = orderService.drugAreaReport(order);
            drugName = drugService.getById(order.getDrugId()).getName();
            for (Order tmp : orders) {
                names.add(tmp.getHospitalArea());
                BigDecimal totalMoney = tmp.getTotalMoney();
                if ("coefficient".equals(order.getReportType())){
                    totalMoney = tmp.getDrugCnt();
                }

                values.add(totalMoney);
                chats.add(new Chat(tmp.getHospitalArea(), totalMoney));
                String name = totalMoney.toString() + ("total_money".equals(order.getReportType()) ? "元" : "");
                tmp.setTotalMoneyText(name);
                tmp.setDrugName(drugName);
                if (StringUtils.isNotBlank(tmp.getHospitalCity())){
                    tmp.setHospitalArea(tmp.getHospitalCity() + tmp.getHospitalArea());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            result.put("names",names);
            result.put("nameValues",chats);
            result.put("drugName", drugName);
            result.put("values", values);
            result.put("orders", orders);
        }

        return renderJson(JsonResultUtil.success(result));
    }


    public String hospitalDrugReportIndex() {
        setModel();
        super.setTarget("hospitalDrugReportIndex");
        return COMMON;
    }


    public String hospitalDrugReport(){
        Order order = model.getOrder();
        order.setEndTime(DateUtil.getSpecifiedDateByDays(order.getEndTime(),1));
        Map<String,Object> result = new HashMap<>();
        List<String> names = new ArrayList<>();
        List<BigDecimal> values = new ArrayList<>();
        List<Chat> chats = new ArrayList<>();

        String drugName = "";
        List<Order> orders = new ArrayList<>();
        try {
            order.setLimitCnt(20);
            orders = orderService.hospitalDrugReport(order);
            drugName = drugService.getById(order.getDrugId()).getName();
            for (Order tmp : orders) {
                names.add(tmp.getHospitalName());
                BigDecimal totalMoney = tmp.getTotalMoney();
               /* if ("coefficient".equals(order.getReportType())){
                    totalMoney = new BigDecimal(tmp.getDrugCnt());
                }*/

                values.add(totalMoney);
                chats.add(new Chat(tmp.getHospitalName(), totalMoney));
                String name = totalMoney.toString() +"元";
                tmp.setTotalMoneyText(name);
                tmp.setDrugName(drugName);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            result.put("names",names);
            result.put("nameValues",chats);
            result.put("drugName", drugName);
            result.put("values", values);
            result.put("orders", orders);
        }
        return renderJson(JsonResultUtil.success(result));
    }

    public String drugHospitalReportIndex2() {
        setModel();
        super.setTarget("drugHospitalReportIndex2");
        return COMMON;
    }


    public String drugHospitalReport(){
        Order order = model.getOrder();
        order.setEndTime(DateUtil.getSpecifiedDateByDays(order.getEndTime(),1));
        Map<String,Object> result = new HashMap<>();
        List<String> names = new ArrayList<>();
        List<BigDecimal> values = new ArrayList<>();
        List<Chat> chats = new ArrayList<>();

        String hosName = "";
        List<Order> orders = new ArrayList<>();
        try {
            orders = orderService.drugHospitalReport(order);
            hosName = hospitalService.getById(order.getHospitalId()).getName();
            for (Order tmp : orders) {
                names.add(tmp.getDrugName());
                BigDecimal totalMoney = tmp.getTotalMoney();
               /* if ("coefficient".equals(order.getReportType())){
                    totalMoney = new BigDecimal(tmp.getDrugCnt());
                }*/

                values.add(totalMoney);
                chats.add(new Chat(tmp.getDrugName(), totalMoney));
                String name = totalMoney.toString() +"元";
                tmp.setTotalMoneyText(name);
                tmp.setHospitalName(hosName);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            result.put("names",names);
            result.put("nameValues",chats);
            result.put("drugName", hosName);
            result.put("values", values);
            result.put("orders", orders);
        }

        return renderJson(JsonResultUtil.success(result));
    }
}
