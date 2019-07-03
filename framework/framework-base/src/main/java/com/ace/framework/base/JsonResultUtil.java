package com.ace.framework.base;


/**
 * @author WuZhiWei
 * @since 2015-11-24 11:07:00
 */
public class JsonResultUtil {

    public static JsonResult err(String message){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(JsonStatus.ERROR);
        jsonResult.setMessage(message);
        return jsonResult;
    }

    public static JsonResult err(String message,Object data){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(JsonStatus.ERROR);
        jsonResult.setMessage(message);
        jsonResult.setData(data);
        return jsonResult;
    }


    public static JsonResult success(){
        JsonResult jsonResult= new JsonResult();
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    public static JsonResult success(Object data){
        JsonResult jsonResult= new JsonResult();
        jsonResult.setSuccess(true);
        jsonResult.setData(data);
        return jsonResult;
    }

    public static JsonResult login(){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(JsonStatus.RELOGIN);
        return jsonResult;
    }

    public static JsonResult location(String location){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setLocation(location);
        return jsonResult;
    }
}
