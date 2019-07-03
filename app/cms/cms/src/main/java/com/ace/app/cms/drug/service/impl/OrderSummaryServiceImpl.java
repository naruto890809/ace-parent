package com.ace.app.cms.drug.service.impl;

import com.ace.app.cms.account.AccountService;
import com.ace.app.cms.drug.dao.OrderDao;
import com.ace.app.cms.drug.domain.*;
import com.ace.app.cms.drug.service.*;
import com.ace.framework.base.BaseCrudServiceImpl;
import com.ace.framework.base.BaseDao;
import com.ace.framework.util.DateUtil;
import com.ace.framework.util.ExecutionContext;
import com.ace.framework.util.UUIDUtil;
import com.ace.framework.util.common.NumberUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
@Service("orderSummaryService")
public class OrderSummaryServiceImpl extends BaseCrudServiceImpl<Order> implements OrderSummaryService {
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDao orderDao;
    @Resource
    private DrugSpecService drugSpecService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private AccountService accountService;

    @Override
    public BaseDao<Order> getEntityDao() {
        return orderDao;
    }


    @Override
    public List findListForExport(OrderSummary condition) {
        Order order = new Order();
        order.setCompanyId(condition.getCompanyId());
        order.setSalesmanId(condition.getSalesmanId());
        order.setDrugSpecName(condition.getDrugSpecName());
        //System.out.println("我是药品"+condition.getDrugSpecName());
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
        //System.out.println(order.getDrugName()+order.getSpecName()+"---zo--"+order.getDrugSpecName()+order.getDrugId()+order.getSpecId());
        List<Order> list = orderService.findList(order);
        if (CollectionUtils.isEmpty(list)){
            return list;
        }

        orderService.packageInfoList(list);

        List<OrderSummary> summaries = new ArrayList<>(list.size());
        for (Order tmp : list) {
            summaries.add(new OrderSummary(tmp.getId(), tmp.getDrugName(), tmp.getSpecName(), tmp.getDrugSpecName(),
                    tmp.getDrugNum(), tmp.getDrugCnt(), tmp.getHospitalName(),
                    tmp.getOrderDate(), tmp.getOrderDateText(), tmp.getPrice(),tmp.getPriceText(),tmp.getCompanyName(),
                    tmp.getBranchCompanyName(), tmp.getYear(), tmp.getMonth(), tmp.getDay(), tmp.getCompanyId(), tmp.getCompanySellAreaId(),
                    tmp.getCompanyArea(), tmp.getCompanyDrugId(), tmp.getDeductionRate(), tmp.getBiddingPrice(), tmp.getBillingPrice(),
                    tmp.getHospitalId(), tmp.getDeptmentId(), tmp.getDepartmentName(), tmp.getHospitalArea(), tmp.getHospitalLavel(), tmp.getHospitalType(),
                    tmp.getRebateRate(), tmp.getRebateId(), tmp.getBrightPrice(), tmp.getDarkPrice(), tmp.getRebatePrice(), tmp.getExecutePrice(),
                    tmp.getStatus(), tmp.getSalesmanName(), tmp.getSalesmanId(), tmp.getActualPrice(), tmp.getTotalMoney(),
                    tmp.getDrugId(), tmp.getSpecId(), tmp.getStatusText(),tmp.getDrugCode(),tmp.getBranchCompany(),tmp.getPriceTopicName(), tmp.getHospitalPreName(), tmp.getHospitalPreAddre()));
        }
        return summaries;
    }

    @Override
    public String importBatch(List<OrderSummary> list, String extParam) {
        return null;
    }


}
