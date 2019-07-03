package com.ace.app.cms.filter;


import com.ace.framework.base.JsonResult;
import com.ace.framework.util.PropertiesUtil;
import com.ace.framework.util.UUIDUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;


public class FileUploadFilter implements Filter {
    private static final int RAM_BUFFER_SIZE = 100 * 1024 * 1024;
    private static final Log LOG = LogFactory.getLog(FileUploadFilter.class);
    public  String FILE_PATH;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
        try {
            if(File.separator.equals("/")){
                FILE_PATH = PropertiesUtil.getEnv("file_path_linux");
            }else{
                FILE_PATH = PropertiesUtil.getEnv("file_path_windows");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest hRequest = (HttpServletRequest) request;
        HttpServletResponse hResponse = (HttpServletResponse) response;

        DiskFileItemFactory dFIFactory = new DiskFileItemFactory();
        dFIFactory.setSizeThreshold(RAM_BUFFER_SIZE);
        ServletFileUpload fileUpload = new ServletFileUpload(dFIFactory);
        List fileItemList = null;
        try {
            fileItemList = fileUpload.parseRequest(hRequest);
        } catch (Exception e) {
            LOG.error("Exception occurred when upload file!", e);
        }

        if (fileItemList != null) {
            processFileItemList(fileItemList, hRequest, hResponse);
        }
    }

    private void processFileItemList(List fileItemList,
                                     HttpServletRequest hRequest,
                                     HttpServletResponse hResponse) throws IOException {
        for (Object fi : fileItemList) {
            FileItem fileItem = (FileItem) fi;
            if (!fileItem.isFormField()) {
                InputStream is = fileItem.getInputStream();
                if (is != null) {
                    String fileItemName = fileItem.getName();
                    String fileType = fileItemName.substring(fileItemName.lastIndexOf("."), fileItemName.length());
                    String fileName = UUIDUtil.genUUID()+fileType;
                    try {
                        File f=new File(FILE_PATH+fileName);
                        inputStreamToFile(is, f);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    JsonResult jsonResult = new JsonResult();
                    String xml = jsonResult.getStatus()+","+ fileName;
//                    hResponse.setContentType("text/xml;charset=UTF-8");
                    hResponse.setHeader("Pragma", "No-cache");
                    hResponse.setHeader("Cache-Control", "no-cache");
                    hResponse.setDateHeader("Expires", 0);
                    hResponse.getWriter().write(xml);
                    hResponse.getWriter().flush();
                    return;
                }
            }
        }
    }

    public static void inputStreamToFile(InputStream ins,File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void destroy() {

    }
}