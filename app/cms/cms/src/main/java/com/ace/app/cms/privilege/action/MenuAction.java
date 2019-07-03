package com.ace.app.cms.privilege.action;


import com.ace.app.cms.CmsConstant;
import com.ace.app.cms.privilege.domain.Menu;
import com.ace.app.cms.privilege.domain.Role;
import com.ace.app.cms.privilege.domain.RoleMenuRel;
import com.ace.app.cms.privilege.model.MenuModel;
import com.ace.app.cms.privilege.service.MenuService;
import com.ace.app.cms.privilege.service.RoleMenuRelService;
import com.ace.app.cms.privilege.service.RoleService;
import com.ace.framework.base.BaseAction;
import com.ace.framework.base.JsonResultUtil;
import com.ace.framework.base.Page;
import com.ace.framework.base.TreeNode;
import com.ace.framework.util.ExecutionContext;
import com.ace.framework.util.UUIDUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 * WuZhiWei v1.0
 * @author 代码生成器 v1.0
 * @since  2016-01-20 11:27:00
 */
@Scope("prototype")
public class MenuAction  extends BaseAction<MenuModel> {
    @Resource
    private MenuService menuService;
    @Resource
    private RoleService roleService;

    private String roleId;

    @Resource
    private RoleMenuRelService roleMenuRelService;

    /**
     * 首页UI
     * @return
     */
    public String index() {
        super.setTarget("indexMenu");
        return COMMON;
    }


    public String addIndex(){
        super.setTarget("addMenu");
        return COMMON;
    }

    public String add(){
        menuService.save(model.getMenu());
        return renderJson(JsonResultUtil.location("menu.index.do"));
    }

    /**
     * role 列表
     * @return
     */
    public String searchRole() {
        Role role = new Role();
        Page<Role>  page = model.getPage();
        role.setParentCorpCode(ExecutionContext.getParentCorpCode());
        page = roleService.search(role, page);
        return renderJson(page);
    }

    /**
     * menu 列表
     * @return
     */
    public String setMenuIndex() {
        super.setTarget("setMenuIndex");
        return COMMON;
    }

    /**
     * menu 列表
     * @return
     */
    public String menuList() {

        List<Menu> menuList = menuService.findList(new Menu());

        List<TreeNode> treeNodes=new ArrayList<TreeNode>();
        TreeNode treeNode=null;
        String roleId=request.getParameter("roleId");
        if(StringUtils.isBlank(roleId)){
            return  renderJson(JsonResultUtil.err("操作错误"));
        }

        List<RoleMenuRel> listRoleMenuRel = roleMenuRelService.findListByRoleId(roleId);


        for(Menu menu:menuList){
            treeNode=new TreeNode();
            if(isContains(menu.getMenuId(),listRoleMenuRel)){
                treeNode.setChecked(true);
            }
            treeNode.setId(menu.getMenuId());
            treeNode.setPId(menu.getParentId());
            treeNode.setName(menu.getTitle());
            if(StringUtils.isBlank(menu.getModule())&&StringUtils.isBlank(menu.getPrivilege())){
                treeNode.setOther(1);
            }else{
                treeNode.setOther(0);
            }

            treeNodes.add(treeNode);
        }
//        Struts2Utils.render("application/json",JsonUtil.obj2Json(treeNodes, false));
        return renderJson(treeNodes);
    }

    private boolean isContains(String menuId,List<RoleMenuRel>listRoleMenuRel){

        for(RoleMenuRel roleMenuRel: listRoleMenuRel){
            if(roleMenuRel.getMenuId().equals(menuId)){
                return true;
            }
        }
        return false;
    }

    public String setMenu(){

        String menuList=request.getParameter("menuList");

        /*String ids[]=request.getParameterValues("ids[]");
        String idsMenu[]=request.getParameterValues("idsMenu[]");*/
        if(StringUtils.isBlank(menuList)|| StringUtils.isBlank(roleId)){
            return  renderJson(JsonResultUtil.err("操作错误"));
        }

        roleMenuRelService.deleteByRoId(roleId);

        List<RoleMenuRel> listRoleMenuRel = new ArrayList<RoleMenuRel>();

        JSONArray menuArray = JSONArray.fromObject(menuList);
        RoleMenuRel roleMenuRel=null;
        for(int i =0 ; i<menuArray.size(); i++){
            JSONObject menuJson= (JSONObject) menuArray.get(i);
            roleMenuRel=new RoleMenuRel();

            roleMenuRel.setCorpCode(ExecutionContext.getCorpCode());
            roleMenuRel.setParentCorpCode(ExecutionContext.getParentCorpCode());
            roleMenuRel.setMenuId(menuJson.getString("id"));
            roleMenuRel.setRoleId(roleId);
            roleMenuRel.setMenu(Integer.valueOf(menuJson.getString("other")));
            roleMenuRel.setRoleMenuRelId(UUIDUtil.genUUID());

            listRoleMenuRel.add(roleMenuRel);
        }


        roleMenuRelService.saveBatch(listRoleMenuRel);

        return  renderJson(JsonResultUtil.success());
    }


    public String search() {
        Menu menu = model.getMenu();
        if(menu==null){
            menu=new Menu();
        }
        menu.setCorpCode(ExecutionContext.getCorpCode());
        Page<Menu>  page = model.getPage();

        page = menuService.search(menu, page);

        return renderJson(page);
    }


    /**
     * 编辑UI
     * @return
     */
    public String editIndex() {

        model.setMenu(menuService.getById(model.getMenu().getMenuId()));
        super.setTarget("editMenu");
        return COMMON;
    }

    /**
     * 编辑
     * @return
     */
    public String edit() {
        menuService.update(model.getMenu());

        return renderJson(JsonResultUtil.location("menu.index.do"));
    }

    /**
     * 删除
     * @return
     */
    public String remove() {

        menuService.deleteById(model.getMenu().getMenuId());

        return renderJson(JsonResultUtil.location("menu.index.do"));

    }
    /**
     * 删除
     * @return
     */
    public String removeBatch() {

        String ids[]=request.getParameterValues("ids[]");

        menuService.deleteById(model.getMenu().getMenuId());

        return renderJson(JsonResultUtil.location("menu.index.do"));
    }


    //////////////////////////////////////////////////////////////

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
