package com.ace.app.cms.drug.action;


import com.ace.app.cms.drug.domain.Dic;
import com.ace.app.cms.drug.model.DicModel;
import com.ace.app.cms.drug.service.DicService;
import com.ace.framework.base.BaseAction;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.base.Page;
import com.ace.framework.util.ExecutionContext;
import javax.annotation.Resource;
import java.util.*;

import org.springframework.context.annotation.Scope;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.account.AccountService;

/**
* 数据字典
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2018-09-12 07:52:32
*/
@Scope("prototype")
public class DicAction  extends BaseAction<DicModel> {
    @Resource
    private DicService dicService;
    @Resource
    private AccountService accountService;


    public String index() {
        super.setTarget("index");
        return COMMON;
    }

    public String addOrEditIndex(){
        String code = model.getCode();
        List<Dic> dics = new ArrayList<>();
        if (StringUtils.isNotBlank(code)){
            Dic entity = new Dic();
            entity.setCode(code);
            dics = dicService.findList(entity);
        }else {
            code = "";
        }

        if (CollectionUtils.isEmpty(dics)){
            dics.add(new Dic());
        }

        model.setCode(code);
        model.setDics(dics);
        super.setTarget("addOrEdit");
        return COMMON;
    }

    public String addOrEdit(){
        String nameModel = model.getName();
        Set<String> dicNamesModel = new HashSet<>(Arrays.asList(nameModel.split(",")));

        String code = model.getCode();
        Dic entity = new Dic();
        entity.setCode(code);
        List<Dic> list = dicService.findList(entity);

        Set<String> dicNamesDb = new HashSet<>();
        for (Dic dic : list) {
            String name = dic.getName();
            dicNamesDb.add(name);
            if (!dicNamesModel.contains(name)){
                dicService.delete(new Dic(code,name));
            }
        }

        List<Dic> save = new ArrayList<>();
        for (String name : dicNamesModel) {
            if (!dicNamesDb.contains(name)){
                save.add(new Dic(code,name));
            }
        }

        if (CollectionUtils.isNotEmpty(save)){
            dicService.saveBatch(save);
        }

        return renderJson(JsonResultUtil.location("dic.index.do"));
    }

    public String search() {
        Page<Dic> page = new Page<Dic>();
        List<Dic> rows = new ArrayList<>();
        rows.add(new Dic(CmsConstant.DIC_TYPE_COMPANY_CHANNEL,CmsConstant.DIC_TYPE_TEXT_COMPANY_CHANNEL,"在商业公司中使用"));
        rows.add(new Dic(CmsConstant.DIC_TYPE_HOSPITAL_LEVEL,CmsConstant.DIC_TYPE_TEXT_HOSPITAL_LEVEL,"在医院信息中使用"));
        rows.add(new Dic(CmsConstant.DIC_TYPE_HOSPITAL_TYPE,CmsConstant.DIC_TYPE_TEXT_HOSPITAL_TYPE,"在医院信息中使用"));
        rows.add(new Dic(CmsConstant.DIC_TYPE_REBATE_DIF_TYPE,CmsConstant.DIC_TYPE_TEXT_REBATE_DIF_TYPE,"在返利信息中使用"));
        rows.add(new Dic(CmsConstant.DIC_TYPE_REBATE_PRICE_TOPIC,CmsConstant.DIC_TYPE_TEXT_REBATE_PRICE_TOPIC,"在返利信息中使用"));
        rows.add(new Dic(CmsConstant.DIC_TYPE_REBATE_STYLE,CmsConstant.DIC_TYPE_TEXT_REBATE_STYLE,"在返利信息中使用"));
        page.setRows(rows);
        return renderJson(page);
    }


    /**
    * 删除
    * @return
    */
    public String remove() {
        dicService.deleteById(model.getId());
        return renderJson(JsonResultUtil.location("dic.index.do"));
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
        Dic dic = new Dic();
        dic.setIds(ids);
//        dic.setApprove(CmsConstant.PASSED);
        dicService.update(dic);
        return renderJson(JsonResultUtil.location("dic.index.do"));
    }

}
