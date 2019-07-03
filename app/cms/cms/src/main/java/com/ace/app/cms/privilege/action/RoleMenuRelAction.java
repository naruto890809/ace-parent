package com.ace.app.cms.privilege.action;


import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.privilege.domain.Role;
import com.ace.app.cms.privilege.domain.RoleMenuRel;
import com.ace.app.cms.privilege.model.RoleMenuRelModel;
import com.ace.app.cms.privilege.service.RoleMenuRelService;
import com.ace.app.cms.privilege.service.RoleService;
import com.ace.framework.base.BaseAction;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.base.Page;
import com.ace.framework.util.ExecutionContext;
import com.ace.framework.util.UUIDUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;

/**
 * 角色菜单
 * WuZhiWei v1.0
 * @author 代码生成器 v1.0
 * @since  2016-01-20 19:10:11
 */
@Scope("prototype")
public class RoleMenuRelAction  extends BaseAction<RoleMenuRelModel> {
    @Resource
    private RoleMenuRelService roleMenuRelService;
    @Resource
    private RoleService roleService;

    /**
     * 首页UI
     * @return
     */
    public String index() {
        super.setTarget("indexRoleMenuRel");
        return COMMON;
    }


    public String addIndex(){
        super.setTarget("addRoleMenuRel");
        return COMMON;
    }


    public String addRole(){
        Role role =model.getRole();
        if(role == null){
            return renderJson(JsonResultUtil.err("请认真填写！"));
        }
        role.setCorpCode(ExecutionContext.getCorpCode());
        role.setParentCorpCode(ExecutionContext.getParentCorpCode());
        role.setVisible(1);
        String roleId = UUIDUtil.genUUID();
        role.setRoleCode(roleId);
        role.setRoleId(roleId);
        roleService.add(role);
        return renderJson(JsonResultUtil.location("menu.index.do"));
    }

    public String add(){
        roleMenuRelService.save(model.getRoleMenuRel());
        return renderJson(JsonResultUtil.location("roleMenuRel.index.do"));
    }

    public String search() {
        RoleMenuRel roleMenuRel = model.getRoleMenuRel();
        if(roleMenuRel==null){
            roleMenuRel=new RoleMenuRel();
        }
        roleMenuRel.setCorpCode(ExecutionContext.getCorpCode());
        Page<RoleMenuRel>  page = model.getPage();

        page = roleMenuRelService.search(roleMenuRel, page);

        return renderJson(page);
    }

    public String editRoleIndex() {

        String roleId = model.getRole().getRoleId();
        if(roleId==null||roleId.equals("")){
            return renderJson(JsonResultUtil.err("传参错误"));
        }

        Role role = roleService.getObject(roleId);

        model.setRole(role);
        super.setTarget("editRoleMenuRel");
        return COMMON;
    }

    /**
     * 编辑
     * @return
     */
    public String editRole() {
        Role role = model.getRole();
        role.setLastModifyBy(ExecutionContext.getUserId());
        roleService.update(role);

        return renderJson(JsonResultUtil.location("menu.index.do"));
    }


    /**
     * 编辑UI
     * @return
     */
    public String editIndex() {

        model.setRoleMenuRel(roleMenuRelService.getById(model.getRoleMenuRel().getRoleMenuRelId()));
        super.setTarget("editRoleMenuRel");
        return COMMON;
    }

    /**
     * 编辑
     * @return
     */
    public String edit() {
        roleMenuRelService.update(model.getRoleMenuRel());

        return renderJson(JsonResultUtil.location("roleMenuRel.index.do"));
    }

    /**
     * 修改状态
     * @return
     */
    public String commend() {
        Role role = model.getRole();
        Role oRole = getBean();
        if(oRole ==null){
            return renderJson(JsonResultUtil.err("传参错误！"));
        }

        role.setLastModifyBy(ExecutionContext.getUserId());
        roleService.update(role);

        return renderJson(JsonResultUtil.location("menu.index.do"));
    }


    /**
     * 删除
     * @return
     */
    public String remove() {

        roleMenuRelService.deleteById(model.getRoleMenuRel().getRoleMenuRelId());

        return renderJson(JsonResultUtil.location("roleMenuRel.index.do"));

    }
    /**
     * 删除
     * @return
     */
    public String removeBatch() {

        String ids[]=request.getParameterValues("ids[]");

        roleMenuRelService.deleteById(model.getRoleMenuRel().getRoleMenuRelId());

        return renderJson(JsonResultUtil.location("roleMenuRel.index.do"));
    }
    /**
     * 获取bean
     * @return
     */
    private Role getBean(){
        String id=model.getRole().getRoleId();
        if(StringUtils.isBlank(id)){
            return null;
        }
        Role entity = roleService.getObject(id);
        if(entity==null){
            return null;
        }
        //暂时注释
        /*if(StringUtils.isBlank(entity.getParentCorpCode())){ //为了兼容之前版本没有ParentCorpCode
            if(!ExecutionContext.getCorpCode().equals(entity.getCorpCode())){
                return null;
            }
        }else if(!ExecutionContext.getParentCorpCode().equals(entity.getParentCorpCode())){
            return null;
        }*/
        return entity;
    }
}
