package com.ace.framework.base;

/**
 * Ajax Post请求返回结果【保存、删除等】
 *
 * @author WuZhiWei
 * @since 2015-11-21 16:56:49
 */
public class JsonResult {
    /**
     * 状态
     */
    private JsonStatus status = JsonStatus.SUCCESS;
    /**
     * 消息
     */
    private String message;
    /**
     * 返回的数据
     */
    private Object data;

    private boolean success;
    /**
     * 页面挑战
     */
    private String location;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JsonStatus getStatus() {
        return status;
    }

    public void setStatus(JsonStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
