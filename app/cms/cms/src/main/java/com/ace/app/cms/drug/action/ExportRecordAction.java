package com.ace.app.cms.drug.action;


import com.ace.app.cms.drug.domain.ExportRecord;
import com.ace.app.cms.drug.domain.Order;
import com.ace.app.cms.drug.model.ExportRecordModel;
import com.ace.app.cms.drug.service.CompanyDrugErpService;
import com.ace.app.cms.drug.service.ExportRecordService;
import com.ace.app.cms.drug.service.OrderService;
import com.ace.framework.base.BaseAction;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.base.Page;
import com.ace.framework.util.ExecutionContext;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import com.ace.app.cms.CmsConstant;
import java.util.Arrays;
import com.ace.app.cms.account.AccountService;

/**
* 导入记录
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:33
*/
@Scope("prototype")
public class ExportRecordAction  extends BaseAction<ExportRecordModel> {
    @Resource
    private ExportRecordService exportRecordService;
    @Resource
    private AccountService accountService;
    @Resource
    private OrderService orderService;
    @Resource
    private CompanyDrugErpService companyDrugErpService;


    public String index() {
        super.setTarget("index");
        return COMMON;
    }

    public String addOrEditIndex(){
        String id = model.getId();
        ExportRecord exportRecord = null;
        if (StringUtils.isBlank(id)){
            exportRecord = new ExportRecord();
        }else {
            exportRecord = exportRecordService.getById(id);
        }

        model.setExportRecord(exportRecord);
        super.setTarget("addOrEdit");
        return COMMON;
    }

    public String addOrEdit(){
        ExportRecord exportRecord = model.getExportRecord();
        if (StringUtils.isBlank(exportRecord.getId())){
            exportRecord.setId(null);
            exportRecordService.save(exportRecord);
        }else {
            exportRecordService.update(exportRecord);
        }

        return renderJson(JsonResultUtil.location("exportRecord.index.do"));
    }

    public String search() {
        ExportRecord exportRecord = model.getExportRecord();
        if(exportRecord==null){
            exportRecord=new ExportRecord();
        }

        exportRecord.setCorpCode(ExecutionContext.getCorpCode());
        Page<ExportRecord>  page = model.getPage();
        page = exportRecordService.search(exportRecord, page);
        List<ExportRecord> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return renderJson(page);
        }

        Map<String,String> aidNameMap = accountService.getAidNameMap();
        for (ExportRecord row : rows) {
            row.setCreateByName(aidNameMap.get(row.getCreateBy()));
        }

        return renderJson(page);
    }


    /**
    * 删除
    * @return
    */
    public String remove() {
        String id = model.getId();
        exportRecordService.deleteById(id);
        Order orderTmp = new Order();
        orderTmp.setBranchCompanyName(id);
        List<Order> orders = orderService.findList(orderTmp);
        for (Order order : orders) {
            companyDrugErpService.subCnt(order.getCompanyId(),order.getDrugId(),order.getSpecId(), BigDecimal.ZERO,order.getDrugCnt());
            companyDrugErpService.subCnt(order.getHospitalId(),order.getDrugId(),order.getSpecId(),order.getDrugCnt(),BigDecimal.ZERO);
        }

        orderService.delete(orderTmp);
        return renderJson(JsonResultUtil.location("exportRecord.index.do"));
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
        ExportRecord exportRecord = new ExportRecord();
        exportRecord.setIds(ids);
//        exportRecord.setApprove(CmsConstant.PASSED);
        exportRecordService.update(exportRecord);
        return renderJson(JsonResultUtil.location("exportRecord.index.do"));
    }

}
