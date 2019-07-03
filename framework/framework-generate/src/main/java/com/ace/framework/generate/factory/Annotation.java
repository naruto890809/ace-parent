package com.ace.framework.generate.factory;

/**
 * 注释
 *
 * @author WuZhiWei
 * @since 2015-11-10 16:26
 */
public class Annotation {

    /**
     * 作者名称
     */
    private String authorName;
    /**
     * 功能说明
     */
    private String declaration;
    /**
     * 日期
     */
    private String date;
    /**
     * 版本
     */
    private String version;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }
}