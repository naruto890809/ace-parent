package com.ace.app.cms.drug.service.impl;

import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.drug.dao.OrderDao;
import com.ace.app.cms.drug.domain.*;
import com.ace.app.cms.drug.domain.report.*;
import com.ace.app.cms.drug.service.*;
import com.ace.framework.base.BaseCrudServiceImpl;
import com.ace.framework.base.Page;
import com.ace.framework.util.DateUtil;
import com.ace.framework.util.ExecutionContext;
import com.ace.framework.util.UUIDUtil;
import com.ace.framework.util.common.NumberUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.ace.framework.base.BaseDao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* 流向清单

* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
@Service("orderService")
public class OrderServiceImpl  extends BaseCrudServiceImpl<Order> implements OrderService {
    @Resource
    private OrderDao orderDao;
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
    private BizService bizService;
    @Resource
    private ExportRecordService exportRecordService;
    @Resource
    private CompanyDrugErpService companyDrugErpService;

    @Override
    public BaseDao<Order> getEntityDao() {
        return orderDao;
    }

    @Override
    public void packageInfoList(List<Order> orders) {
        for (Order order : orders) {
            order.setDrugSpecName(order.getDrugName()+order.getSpecName());
            order.setPriceText(order.getPrice().toString());
            order.setOrderDateText(DateUtil.convertTo_yyyy_MM_dd(order.getOrderDate()));
        }
    }

    @Override
    public Page<Order> drugHosPage(Order order, Page<Order> page) {
        return orderDao.drugHosPage(order,page);
    }

    @Override
    public  Map<String,BigDecimal> drugHosBMap(Order order){
        return orderDao.drugHosBMap(order);
    }

    @Override
    public Page<Order> summaryPage(Order order, Page<Order> page) {
        return orderDao.summaryPage(order,page);
    }

    @Override
    public  Map<String,BigDecimal> summaryMap(Order order){
        return orderDao.summaryMap(order);
    }

    @Override
    public Page<Order> cSalePage(Order order, Page<Order> page) {
        return orderDao.cSalePage(order,page);
    }

    @Override
    public  Map<String,BigDecimal> cSaleBMap(Order order){
        return orderDao.cSaleBMap(order);
    }

    @Override
    public Page<Order> newHosPage(Order order, Page<Order> page) {
        return orderDao.newHosPage(order,page);
    }

    @Override
    public  Map<String,BigDecimal> newHosBMap(Order order){
        return orderDao.newHosBMap(order);
    }

    @Override
    public  Page<Order> hosCntPage(Order order,Page<Order> page){
        return orderDao.hosCntPage(order,page);
    }



    @Override
    public List findListForExport(Order order) {
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
        }

        order.setCorpCode(ExecutionContext.getCorpCode());
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
        order.setDrugSpecName(null);
        List<Order> list = this.findList(order);
        if (CollectionUtils.isEmpty(list)){
            return list;
        }

        packageInfoList(list);
        return list;
    }

    @Override
    public String importBatch(List<Order> list,String extParam) {
        if (CollectionUtils.isEmpty(list)){
            return "数据不能为空";
        }
        Map<String, String> drugSpecNameIdsStrMap = drugSpecService.drugSpecAliaNameIdsStrMap();
        Map<String, BigDecimal> drugSpecNameCoefficientStrMap = drugSpecService.drugSpecNameCoefficientStrMap();
        Map<String, Drug> drugIdNameMap = drugService.getDrugIdMap();
        Map<String, String> specIdNameMap = drugSpecService.getIdSpecNameMap();
        Map<String, Hospital> nameHosMap = hospitalService.getAliasNameHosMap();
        Map<String, Company> comNameIdMap = companyService.getComNameCompanyMap();
        Map<String, String> sellAreaIdNameMap = sellAreaService.sellAreaIdNameMap();
        Map<String, String> deptIdNameMap = departmentService.getDeptIdNameMap();
        Map<String, Salesman> salesmanIdNameMap = salesmanService.getIdSalesmanMap();
        Map<String, String> dicIdNameMap = dicService.getIdNameMap();
        String result = "";

        String exportId = UUIDUtil.genUUID();
        for (Order order : list) {
            String indexResult = this.checkOrderInfo(order, drugSpecNameIdsStrMap, drugSpecNameCoefficientStrMap, drugIdNameMap, specIdNameMap, nameHosMap,
                    comNameIdMap, sellAreaIdNameMap, deptIdNameMap, salesmanIdNameMap, dicIdNameMap);

            if (StringUtils.isNotBlank(indexResult)){
                int index = list.indexOf(order )+2;
                indexResult = "第"+index+"行数据有误："+indexResult+"<br/>";
                result += indexResult;
                continue;
            }

            order.setBranchCompanyName(exportId);
        }

        if (StringUtils.isBlank(result)){
            ExportRecord exportRecord = new ExportRecord(extParam,list.size());
            exportRecord.setId(exportId);
            exportRecordService.save(exportRecord);
            for (Order order : list) {
                companyDrugErpService.addCnt(order.getCompanyId(),order.getDrugId(),order.getSpecId(),BigDecimal.ZERO,order.getDrugCnt());
                companyDrugErpService.addCnt(order.getHospitalId(),order.getDrugId(),order.getSpecId(),order.getDrugCnt(),BigDecimal.ZERO);
            }

            this.saveBatch(list);
        }

        return result;
    }

    private boolean check(String alias,String name){
        if (StringUtils.isBlank(alias) || StringUtils.isBlank(name)){
            return false;
        }

        String[] split = alias.replaceAll("，", ",").split(",");
        for (String s : split) {
            if (s.trim().equals(name.trim())){
                return true;
            }
        }

        return false;
    }

    @Override
    public String checkOrderInfo(Order order, Map<String, String> drugSpecNameIdsStrMap,Map<String, BigDecimal> drugSpecNameCoefficientStrMap ,
                                 Map<String, Drug> drugIdNameMap, Map<String, String> specIdNameMap,
                                 Map<String, Hospital> nameHosMap, Map<String, Company> comNameIdMap,
                                 Map<String, String> sellAreaIdNameMap, Map<String, String> deptIdNameMap,
                                 Map<String, Salesman> salesmanIdNameMap, Map<String, String> dicIdNameMap) {
        String checkResult = "";
        String drugSpecName = order.getDrugSpecName();
        String drugSpecIdStr = drugSpecNameIdsStrMap.get(drugSpecName);
        String drugId = "";
        String specId = "";

        //品规校验
        if (StringUtils.isBlank(drugSpecIdStr)){
            checkResult += "品规名称不存在";
        }else {
            String[] split = drugSpecIdStr.split(",");
            drugId = split[0];
            order.setDrugId(drugId);
            Drug drug = drugIdNameMap.get(drugId);
            String drugName = drug.getName();
            order.setDrugName(drugName);
            order.setDrugCode(drug.getCode());

            specId = split[1];
            order.setSpecId(specId);
            String specName = specIdNameMap.get(specId);
            order.setSpecName(specName);

            drugSpecName = drugName+specName;
        }

        BigDecimal coefficient = drugSpecNameCoefficientStrMap.get(drugSpecName);
        if (coefficient == null){
            checkResult += "药品对应规格没有设置系数、";
        }else {
            if (order.getDrugCnt() != null){
                order.setCoefficient(order.getDrugCnt().multiply(coefficient));
            }
        }

        if (StringUtils.isBlank(order.getDrugNum())){
            checkResult += "批号不能为空、";
        }

        BigDecimal drugCnt = order.getDrugCnt();
               if (drugCnt == null){
            checkResult += "数量不能为空、";
            drugCnt = BigDecimal.ZERO;
        }

        //医院校验
        Hospital hospital = null;
        String hospitalName = order.getHospitalPreName();
        String hospitalId = "";
        if (StringUtils.isNotBlank(hospitalName)){
            for (Map.Entry<String, Hospital> hospitalEntry : nameHosMap.entrySet()) {
                String hosNameAlias = hospitalEntry.getKey();
                if (check(hosNameAlias,hospitalName)){
                    hospital = hospitalEntry.getValue();
                    //医院名称是 根据流向里的原始名称匹配出来的标准名称
                    order.setHospitalName(hospital.getName());
                    break;
                }
            }

            if (hospital != null){
                order.setHospitalName(hospital.getName());
                hospitalId = hospital.getId();
                order.setHospitalId(hospitalId);


                order.setHospitalArea(hospital.getArea());
                order.setHospitalCity(hospital.getCity());
                order.setHospitalProvince(hospital.getProvince());
                order.setHospitalLavel(dicIdNameMap.get(hospital.getLevel()));
                order.setHospitalType(dicIdNameMap.get(hospital.getType()));
            }else {
                //医院不存在，查商业公司
                Company company = null;
                for (Map.Entry<String, Company> entry : comNameIdMap.entrySet()) {
                    String key = entry.getKey();
                    if (check(key,hospitalName)){
                        company = entry.getValue();
//                        order.setCompanyName(company.getName());
                        break;
                    }
                }

                if (company == null){
                    checkResult += "医院信息不存在【"+hospitalName+"】、";
                }else {
                    order.setHospitalName(company.getName());
                    String companyId = company.getId();
                    order.setHospitalId(companyId);
                    String areaName = sellAreaIdNameMap.get(company.getSellAreaId());
                    order.setHospitalArea(areaName);
                    order.setHospitalCity(company.getSellAreaId());
                    order.setHospitalProvince(company.getSellAreaName());

                    String channel = dicIdNameMap.get(company.getChannel());
                    order.setHospitalLavel(channel);
                    order.setHospitalType(channel);
                    order.setSalesmanName("医药公司");
                }

            }
        }

        //日期校验
        String orderDateText = order.getOrderDateText();
        Date orderDate = null;
        if (StringUtils.isBlank(orderDateText)){
            checkResult += "日期不能为空、";
        }else {
            orderDate = DateUtil.convert(orderDateText);
            order.setOrderDate(orderDate);
            Map<String, Integer> splitDate = DateUtil.splitDate(orderDate);
            order.setYear(splitDate.get("YEAR"));
            order.setMonth(splitDate.get("MONTH"));
            order.setDay(splitDate.get("DATE"));
        }

        String priceText = order.getPriceText();
        if (StringUtils.isBlank(priceText)){
//            checkResult += "单价不能为空、";  //有些二级、下游商业提供的原始流向无单价，导入时单价为必填项（流向单价不是必填项，可以为空。）
            order.setPrice(BigDecimal.ZERO);
        }else {
            order.setPrice(new BigDecimal(priceText));
        }


        //商业公司校验
        String companyId = null;
        String companyName = order.getCompanyName();
        if (StringUtils.isBlank(companyName)){
            checkResult += "商业公司不能为空、";
        }else {
            Company company = null;
            for (Map.Entry<String, Company> entry : comNameIdMap.entrySet()) {
                String key = entry.getKey();
                if (check(key,companyName)){
                    company = entry.getValue();
                    order.setCompanyName(company.getName());
                    break;
                }
            }

            if (company == null){
                checkResult += "商业公司【"+companyName+"】不存在、";
            }else {
                companyId = company.getId();
                order.setCompanyId(companyId);
                String sellAreaId = company.getSellAreaId();
                order.setCompanySellAreaId(sellAreaId);
                order.setCompanyArea(company.getSellAreaName());

                // 商业公司药品关联表
                if (StringUtils.isNotBlank(drugId) && StringUtils.isNotBlank(specId) && orderDate != null){
                    CompanyDrug companyDrug = new CompanyDrug();
                    companyDrug.setCompanyId(companyId);
                    companyDrug.setDrugId(drugId);
                    companyDrug.setSpecDrugId(specId);
                    companyDrug.setOrderDate(orderDate);
                    CompanyDrug companyDrugResult = companyDrugService.findForOrder(companyDrug);
                    if (companyDrugResult == null){
                        checkResult += "商业公司对应品规在订单时间内未设置价格、";
                        order.setDeductionRate(BigDecimal.ZERO);
                        order.setBiddingPrice(BigDecimal.ZERO);
                        order.setBillingPrice(BigDecimal.ZERO);
                    }else {
                        order.setCompanyDrugId(companyDrugResult.getId());
                        order.setDeductionRate(companyDrugResult.getDeductionRate());
                        order.setBiddingPrice(companyDrugResult.getBiddingPrice());
                        order.setBillingPrice(companyDrugResult.getBillingPrice());
                    }
                }else {
                    order.setDeductionRate(BigDecimal.ZERO);
                    order.setBiddingPrice(BigDecimal.ZERO);
                    order.setBillingPrice(BigDecimal.ZERO);
                }

            }
        }

        //返利设置
        if (StringUtils.isNotBlank(companyId) && StringUtils.isNotBlank(hospitalId) &&
                StringUtils.isNotBlank(drugId) && StringUtils.isNotBlank(specId) && orderDate != null){
            Rebate rebate = new Rebate();
            rebate.setCompanyId(companyId);
            rebate.setHospitalId(hospitalId);
            rebate.setDrugId(drugId);
            rebate.setSpecDrugId(specId);
            rebate.setOrderDate(orderDate);
            rebate.setApprove(CmsConstant.PASSED);
            Rebate rebateBright = rebateService.findForBright(rebate);
            if (rebateBright != null){
                order.setRebateId(rebateBright.getId());
                order.setBrightPrice(rebateBright.getBrightPrice());
                order.setExecutePrice(rebateBright.getExecutePrice());
                order.setPriceTopicName(rebateBright.getPriceTopic());
            }else {
                order.setBrightPrice(BigDecimal.ZERO);
            }

            Rebate rebateDark = rebateService.findForDark(rebate);
            if (rebateDark != null){
                order.setRebateId(rebateDark.getId());
                order.setDarkPrice(rebateDark.getDarkPrice());
                order.setExecutePrice(rebateDark.getExecutePrice());
                order.setPriceTopicName(rebateDark.getPriceTopic());
            }else {
                order.setDarkPrice(BigDecimal.ZERO);
            }

            if (order.getExecutePrice() == null){
                order.setExecutePrice(BigDecimal.ZERO);
            }
            //设置返利单价及rate
            order.setRebatePrice(order.getBrightPrice().add(order.getDarkPrice()));
            order.setRebateRate(BigDecimal.ZERO);
            if (order.getBiddingPrice() !=null && order.getBiddingPrice().compareTo(BigDecimal.ZERO) > 0){
                order.setRebateRate(order.getRebatePrice().divide(order.getBiddingPrice(), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
            }

            //设置议价主体
            String priceTopicName = order.getPriceTopicName();
            if (StringUtils.isNotBlank(priceTopicName)){
                order.setPriceTopicName(dicIdNameMap.get(priceTopicName));
            }
        }else {
            order.setRebateRate(BigDecimal.ZERO);
            order.setBrightPrice(BigDecimal.ZERO);
            order.setDarkPrice(BigDecimal.ZERO);
            order.setRebatePrice(BigDecimal.ZERO);
            order.setExecutePrice(BigDecimal.ZERO);
        }

        BigDecimal price = order.getPrice();
        String rebateId = order.getRebateId();
        BigDecimal billingPrice = order.getBillingPrice();
        BigDecimal executePrice = order.getExecutePrice();
        String hospitalTypeName = order.getHospitalType();
        BigDecimal biddingPrice = order.getBiddingPrice();

        if (price != null && billingPrice != null && price.compareTo(billingPrice) < 0){
            // 2.终端开票价格小于开票价为异常
            order.setStatus("2");
        }else if (StringUtils.isNotBlank(rebateId)  && executePrice != null && price.compareTo(executePrice) != 0){
            // 3.(医院名称在返利信息表内,品规一致)终端开票价格不等于返利信息表中的执行价则为异常
            order.setStatus("3");
        }else if (StringUtils.isBlank(rebateId) &&StringUtils.isNotBlank(hospitalTypeName) &&
                "公立".equals(hospitalTypeName) && biddingPrice != null &&
                biddingPrice.compareTo(price) != 0){
            // 1.不在返利信息表内的医院，性质为医院的，终端开票价不等于医保支付价
            order.setStatus("1");
        }else {
            order.setStatus("0");
        }


        //业务员
        if (StringUtils.isBlank(order.getSalesmanName())){
            if ( StringUtils.isNotBlank(hospitalId) &&
                    StringUtils.isNotBlank(drugId) && StringUtils.isNotBlank(specId) && orderDate != null){
                Biz biz = new Biz(hospitalId,  drugId,  specId, orderDate);
                Biz bizResult = bizService.findForOrder(biz);
                if (bizResult == null){
//                    checkResult += "业务管理中未设置该医院对应品规跟踪业务员、";
                }else {
                    String salesmanId = bizResult.getSalesmanId();
                    Salesman salesman = salesmanIdNameMap.get(salesmanId);
                    if (salesman == null){
//                        checkResult += "业务员不存在、";
                        order.setSalesmanName("零售");
                    } else {
                        order.setSalesmanId(salesmanId);
                        order.setSalesmanName(salesman.getName());

                        //业务员所在部门如果为空，就去医院查医院所在部门  FUCK
                        String departmentId = salesman.getDepartmentId();
                        order.setDeptmentId(departmentId);
                        String deptName = deptIdNameMap.get(departmentId);
                        order.setDepartmentName(deptName);
                    }
                }
            }
        }

        if (StringUtils.isBlank(order.getDepartmentName())){
            if (hospital != null){
                String hospitalDepartmentId = hospital.getDepartmentId();
                String hospitalDepartmentName = deptIdNameMap.get(hospitalDepartmentId);
                if (StringUtils.isNotBlank(hospitalDepartmentName) && StringUtils.isNotBlank(hospitalDepartmentId)){
                    order.setDepartmentName(hospitalDepartmentName);
                    order.setDeptmentId(hospitalDepartmentId);
                }else {
                    checkResult += "业务员所属部门及医院所在部门不存在、";
                }
            }
        }

       /* //实际结算价=医保支付价-返利单价
        BigDecimal rebatePrice = order.getRebatePrice();

        if (biddingPrice != null && rebatePrice != null) {
            BigDecimal actualPrice = biddingPrice.subtract(rebatePrice);
            order.setActualPrice(actualPrice);
            // totalMoney 销售金额【实际结算价格*数量】
            BigDecimal totalMoney = actualPrice.multiply(new BigDecimal(drugCnt));
            order.setTotalMoney(totalMoney);
        }*/

        //销售金额=开票价*数量
        if (billingPrice != null){
            BigDecimal totalMoney = billingPrice.multiply(drugCnt);
            order.setTotalMoney(totalMoney);
        }else {
            order.setTotalMoney(BigDecimal.ZERO);
        }

        if (StringUtils.isBlank(order.getSalesmanName())){
            order.setSalesmanName("零售");
        }

        return checkResult;
    }



    @Override
    public List<Order> departmentReport(Order order) {
        return orderDao.departmentReport(order);
    }

    @Override
    public List<Order> departmentDrugReport(Order order) {
        return orderDao.departmentDrugReport(order);
    }
    @Override
    public List<Order> drugDepartmentReport(Order order) {
        return orderDao.drugDepartmentReport(order);
    }

    @Override
    public List<Order> drugAreaReport(Order order) {
        return orderDao.drugAreaReport(order);
    }

    @Override
    public List<Order> hospitalDrugReport(Order order) {
        return orderDao.hospitalDrugReport(order);
    }

    @Override
    public List<Order> drugHospitalReport(Order order) {
        return orderDao.drugHospitalReport(order);
    }

    @Override
    public List findListForReport(Order order) {
        String exportType = order.getExportType();
        List<Order> orders = null;
        if ("departmentReport".equals(exportType)){
            order.setEndTime(DateUtil.getSpecifiedDateByDays(order.getEndTime(),1));
            String reportDif = order.getReportDif();
            if(StringUtils.isBlank(reportDif)){
                order.setReportDif("hospital_area");
            }

            orders = departmentReport(order);
            BigDecimal totalMoney = BigDecimal.ZERO;
            for (Order tmp : orders) {
                totalMoney = totalMoney.add(tmp.getTotalMoney());
            }

            BigDecimal hundred = new BigDecimal(100);
            List<DepartmentReport> result = new ArrayList<>();
            for (Order tmp : orders) {
                String radio = tmp.getTotalMoney().divide(totalMoney, 4, BigDecimal.ROUND_HALF_UP).multiply(hundred).toString();
                tmp.setRatio(radio.substring(0,radio.length()-2) +"%");
                String hospitalCity = tmp.getHospitalCity();
                if (order.getReportDif().equals("hospital_area") && StringUtils.isNotBlank(hospitalCity)){
                    tmp.setDepartmentName(hospitalCity +tmp.getDepartmentName());
                }

                result.add(new DepartmentReport(tmp.getDepartmentName(),tmp.getTotalMoney(),tmp.getRatio()));
            }

            return result;
        }else if ("departmentDrugReport".equals(exportType)){
            String reportDifName = order.getReportDifName();
            if (StringUtils.isBlank(reportDifName)){
                /*List<Department> departments = departmentService.findList(new Department());
                if (CollectionUtils.isNotEmpty(departments)){
                    reportDifName = departments.get(0).getName();
                    order.setReportDifName(reportDifName);
                }*/
                order.setReportDif(null);
            }
            orders = this.departmentDrugReport(order);
            List<DepartmentDrugReport> departmentDrugReports = new ArrayList<>();
            if (StringUtils.isBlank(reportDifName)){
                reportDifName = "浙江省";
            }

            for (Order tmp : orders) {
                BigDecimal totalMoney = tmp.getTotalMoney();
                if ("coefficient".equals(order.getReportType())){
                    totalMoney = tmp.getDrugCnt();
                }

                String totalMoneyText = totalMoney.toString() + ("total_money".equals(order.getReportType()) ? "元" : "");

                departmentDrugReports.add(new DepartmentDrugReport( reportDifName, tmp.getDrugName(),totalMoneyText));
            }

            return departmentDrugReports;
        }else if ("drugDepartmentReport".equals(exportType)){
            orders = this.drugDepartmentReport(order);
            String drugName = drugService.getById(order.getDrugId()).getName();
            List<DrugDepartmentReport> departmentDrugReports = new ArrayList<>();
            for (Order tmp : orders) {
                BigDecimal totalMoney = tmp.getTotalMoney();
                if ("coefficient".equals(order.getReportType())){
                    totalMoney = tmp.getDrugCnt();
                }

                String name = totalMoney.toString() + ("total_money".equals(order.getReportType()) ? "元" : "");
                departmentDrugReports.add(new DrugDepartmentReport(drugName, tmp.getDepartmentName(), name));
            }

            return departmentDrugReports;
        }else if ("drugAreaReport".equals(exportType)){
            orders = this.drugAreaReport(order);
            String drugName = drugService.getById(order.getDrugId()).getName();
            List<DrugAreaReport> departmentDrugReports = new ArrayList<>();
            for (Order tmp : orders) {
                BigDecimal totalMoney = tmp.getTotalMoney();
                if ("coefficient".equals(order.getReportType())){
                    totalMoney = tmp.getDrugCnt();
                }

                String name = totalMoney.toString() + ("total_money".equals(order.getReportType()) ? "元" : "");
                if (StringUtils.isNotBlank(tmp.getHospitalCity())){
                    tmp.setHospitalArea(tmp.getHospitalCity() + tmp.getHospitalArea());
                }
                departmentDrugReports.add(new DrugAreaReport(drugName, tmp.getHospitalArea(), name));
            }

            return departmentDrugReports;
        }else if ("hospitalDrugReport".equals(exportType)){
            orders = this.hospitalDrugReport(order);
            String drugName = drugService.getById(order.getDrugId()).getName();
            List<HospitalDrugReport> departmentDrugReports = new ArrayList<>();
            for (Order tmp : orders) {
                BigDecimal totalMoney = tmp.getTotalMoney();
                /*if ("coefficient".equals(order.getReportType())){
                    totalMoney = new BigDecimal(tmp.getDrugCnt());
                }*/

                String name = totalMoney.toString() + "元";
                departmentDrugReports.add(new HospitalDrugReport(drugName, tmp.getHospitalName(), name));
            }

            return departmentDrugReports;
        }else if ("drugHospitalReport".equals(exportType)){
            orders = this.drugHospitalReport(order);
            String hosName = hospitalService.getById(order.getHospitalId()).getName();
            List<DrugHospitalReport> departmentDrugReports = new ArrayList<>();
            for (Order tmp : orders) {
                BigDecimal totalMoney = tmp.getTotalMoney();
                /*if ("coefficient".equals(order.getReportType())){
                    totalMoney = new BigDecimal(tmp.getDrugCnt());
                }*/

                String name = totalMoney.toString() + "元";
                departmentDrugReports.add(new DrugHospitalReport(hosName, tmp.getDrugName(), name));
            }

            return departmentDrugReports;
        }

        return orderDao.drugHospitalReport(order);
    }

}
