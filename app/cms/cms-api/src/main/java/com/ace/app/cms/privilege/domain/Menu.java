package com.ace.app.cms.privilege.domain;

import com.ace.framework.base.BaseEntity;
import com.ace.framework.base.PrimaryKey;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* 菜单
*
* WuZhiWei v1.0
* @author 代码生成器 v1.0
* @since  2016-01-20 11:27:00
*/
public class Menu extends BaseEntity implements  Comparable{


    private static final long serialVersionUID = -1468672520277997864L;

    @PrimaryKey
	private String menuId;//   主键id

	private String parentId;//   

	private String defaultSubUrl;//   模块

	private String enTitle;//   

	private String icon;//   图标

	private Integer seq;//   排序

	private String target;//   

	private String tip;//   

	private String title;//   标题

	private String url;//

    private String module;//模块

    private String privilege;//权限值

    /////////////////////////////子菜单//////////////////////////////
    private List<Menu> subMenuList;

	public String getMenuId() {

	    return this.menuId;

	}

	public void setMenuId(String menuId) {

	    this.menuId=menuId;

	}

	public String getParentId() {

	    return this.parentId;

	}

	public void setParentId(String parentId) {

	    this.parentId=parentId;

	}

	public String getDefaultSubUrl() {

	    return this.defaultSubUrl;

	}

	public void setDefaultSubUrl(String defaultSubUrl) {

	    this.defaultSubUrl=defaultSubUrl;

	}

	public String getEnTitle() {

	    return this.enTitle;

	}

	public void setEnTitle(String enTitle) {

	    this.enTitle=enTitle;

	}

	public String getIcon() {

	    return this.icon;

	}

	public void setIcon(String icon) {

	    this.icon=icon;

	}

	public Integer getSeq() {

	    return this.seq;

	}

	public void setSeq(Integer seq) {

	    this.seq=seq;

	}

	public String getTarget() {

	    return this.target;

	}

	public void setTarget(String target) {

	    this.target=target;

	}

	public String getTip() {

	    return this.tip;

	}

	public void setTip(String tip) {

	    this.tip=tip;

	}

	public String getTitle() {

	    return this.title;

	}

	public void setTitle(String title) {

	    this.title=title;

	}

	public String getUrl() {

	    return this.url;

	}

	public void setUrl(String url) {

	    this.url=url;

	}

    public List<Menu> getSubMenuList() {
        return subMenuList;
    }

    public void setSubMenuList(List<Menu> subMenuList) {
        this.subMenuList = subMenuList;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    @Override
    public int compareTo(Object o) {
        Menu menu = (Menu)o;
        int seq=menu.getSeq();

        return this.seq>seq?0:1;
    }
}

