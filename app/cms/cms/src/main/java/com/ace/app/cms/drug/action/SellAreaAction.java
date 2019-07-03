package com.ace.app.cms.drug.action;


import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.account.AccountService;
import com.ace.app.cms.drug.domain.SellArea;
import com.ace.app.cms.drug.model.SellAreaModel;
import com.ace.app.cms.drug.service.SellAreaService;
import com.ace.framework.base.BaseAction;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.base.Page;
import com.ace.framework.util.ExecutionContext;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import org.apache.commons.lang.StringUtils;

/**
* 销售区域
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-06 23:03:55
*/
@Scope("prototype")
public class SellAreaAction  extends BaseAction<SellAreaModel> {
    @Resource
    private SellAreaService sellAreaService;
    @Resource
    private AccountService accountService;

    /**
    * 首页UI
    * @return
    */
    public String index() {
        super.setTarget("index");
        return COMMON;
    }

    public String addOrEditIndex(){
        String id = model.getId();
        SellArea sellArea = null;
        if (StringUtils.isBlank(id)){
            sellArea = new SellArea();
        }else {
            sellArea = sellAreaService.getById(id);
        }

        model.setSellArea(sellArea);
        super.setTarget("addOrEdit");
        return COMMON;
    }

    public String addOrEdit(){
        SellArea sellArea = model.getSellArea();
        String province = sellArea.getProvince();
        String city = sellArea.getCity();
        String area = sellArea.getArea();
        if (StringUtils.isEmpty(province) ||StringUtils.isEmpty(city) ||StringUtils.isEmpty(area)){
            return renderJson(JsonResultUtil.err("省市区均不能为空"));
        }

        sellArea.setArea(sellArea.getArea().trim());
        String id = sellArea.getId();
        if (StringUtils.isBlank(id)){
            sellArea.setId(null);
            List<SellArea> list = sellAreaService.findList(sellArea);
            if (CollectionUtils.isNotEmpty(list)){
                return renderJson(JsonResultUtil.err("省市区已存在"));
            }

            sellArea.setApprove(CmsConstant.APPROVING);
            sellAreaService.save(sellArea);
        }else {
            SellArea db = sellAreaService.getById(id);
            if (!(db.getProvince()+db.getCity()+db.getArea()).equals(province + city + area)){
                SellArea sellAreaTmp = new SellArea(province,city,area);
                List<SellArea> list = sellAreaService.findList(sellAreaTmp);
                if (CollectionUtils.isNotEmpty(list)){
                    return renderJson(JsonResultUtil.err("省市区已存在"));
                }

            }

            sellAreaService.update(sellArea);
        }

        return renderJson(JsonResultUtil.location("sellArea.index.do"));
    }

    public String search() {
        SellArea sellArea = model.getSellArea();
        if(sellArea==null){
                 sellArea=new SellArea();
            sellArea.setProvinceSearch("浙江省");
        }

        sellArea.setCorpCode(ExecutionContext.getCorpCode());
        Page<SellArea>  page = model.getPage();
        sellArea.setProvince(sellArea.getProvinceSearch());
        sellArea.setCity(sellArea.getCitySearch());
        sellArea.setArea(sellArea.getAreaSearch());

        page = sellAreaService.search(sellArea, page);
        List<SellArea> rows = page.getRows();
        if (CollectionUtils.isEmpty(rows)){
            return renderJson(page);
        }

        Map<String,String> aidNameMap = accountService.getAidNameMap();
        for (SellArea row : rows) {
            row.setCreateByName(aidNameMap.get(row.getCreateBy()));
        }

        return renderJson(page);
    }


    /**
    * 删除
    * @return
    */
    public String remove() {
        sellAreaService.deleteById(model.getId());
        return renderJson(JsonResultUtil.location("sellArea.index.do"));
    }

    public String removeBatch() {

        String ids[]=request.getParameterValues("ids[]");
        if(ids.length>0){
            sellAreaService.batchRemove(ids,ExecutionContext.getCorpCode(),ExecutionContext.getParentCorpCode());
        }

        return renderJson(JsonResultUtil.location("sellArea.index.do"));
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
        SellArea sellArea = new SellArea();
        sellArea.setIds(ids);
        sellArea.setApprove(CmsConstant.PASSED);
        sellAreaService.update(sellArea);
        return renderJson(JsonResultUtil.location("sellArea.index.do"));
    }
}
