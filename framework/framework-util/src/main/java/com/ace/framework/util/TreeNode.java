package com.ace.framework.util;

import java.util.List;

/**
 * @author WuZhiWei
 * @since 2016-07-15 14:23:21
 */
public class TreeNode {
    /**
     * 节点id
     */
    private String id;
    /**
     * 节点内容
     */
    private String text;
    /**
     * 节点图标
     */
    private String imgUrl;
    /**
     * 是否有子节点
     */
    private Boolean dir;
    /**
     * 子节点
     */
    private List<TreeNode> children;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public Boolean getDir() {
        return dir;
    }

    public void setDir(Boolean dir) {
        this.dir = dir;
    }
}
