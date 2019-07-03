package com.ace.framework.base;

import java.util.List;

/**
 * 展示类别树用的树节点类
 * <p/>
 * WuZhiWei
 * 2014-5-19
 * 添加类说明以及属性注释
 */
public class TreeNode {
    /**
     * 节点id
     */
    private String id;
    /**
     * 父节点id
     */
    private String pId;
    /**
     * 节点内容
     */
    private String name;
    /**
     * 是否展开
     */
    private String open="false";


    private  boolean isParent=false;
    /**
     * 不可移动成为父节点
     */
    private boolean isEnd = false ;
    /**
     * 不可移动成为根节点
     */
    private boolean nocheck= false ;

    private int level=0;

    private boolean checked=false;

    private boolean halfCheck=false;

    private boolean chkDisabled=false;

    private String children=null;

    private int other=0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean isParent) {
        this.isParent = isParent;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isHalfCheck() {
        return halfCheck;
    }

    public void setHalfCheck(boolean halfCheck) {
        this.halfCheck = halfCheck;
    }

    public boolean isChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }
}