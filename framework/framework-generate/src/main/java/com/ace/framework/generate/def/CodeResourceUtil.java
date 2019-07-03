package com.ace.framework.generate.def;

import java.util.ResourceBundle;

/**
 * @author WuZhiWei
 * @since 2015-11-10 16:23
 */
public class CodeResourceUtil {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("ace/ace_database");
    private static final ResourceBundle bundlePath = ResourceBundle.getBundle("ace/ace_config");

    public static String DIVER_NAME = bundle.getString("diver_name");

    public static String URL = bundle.getString("url");

    public static String USERNAME = bundle.getString("username");

    public static String PASSWORD = bundle.getString("password");

    public static String DATABASE_NAME = bundle.getString("database_name");

    public static String DATABASE_TYPE = "mysql";
    public static String DATABASE_TYPE_MYSQL = "mysql";
    public static String DATABASE_TYPE_ORACLE = "oracle";

    public static String MYJ_UI_FIELD_REQUIRED_NUM = "4";

    public static String MYJ_UI_FIELD_SEARCH_NUM = "3";

    public static String web_root_package = "webapp";

    public static String source_root_package = "src";

    public static String bussiPackage = "sun";
    public static String bussiPackageUrl = "sun";

    public static String entity_package = "entity";

    public static String page_package = "page";
    public static String ENTITY_URL;
    public static String PAGE_URL;
    public static String ENTITY_URL_INX;
    public static String PAGE_URL_INX;
    public static String TEMPLATEPATH;
    public static String CODEPATH;
    public static String JSPPATH;
    public static String MYJ_GENERATE_TABLE_ID;
    //    public static String JEECG_GENERATE_UI_FILTER_FIELDS;
    public static String SYSTEM_ENCODING;

    static {
        DIVER_NAME = getDIVER_NAME();
        URL = getURL();
        USERNAME = getUSERNAME();
        PASSWORD = getPASSWORD();
        DATABASE_NAME = getDATABASE_NAME();

        SYSTEM_ENCODING = getSYSTEM_ENCODING();
        TEMPLATEPATH = getTEMPLATEPATH();
        source_root_package = getSourceRootPackage();
        web_root_package = getWebRootPackage();
        bussiPackage = getBussiPackage();
        bussiPackageUrl = bussiPackage.replace(".", "/");

        MYJ_GENERATE_TABLE_ID = getMYJ_GENERATE_TABLE_ID();

        MYJ_UI_FIELD_SEARCH_NUM = getMYJ_UI_SEARCH_FILED_NUM();

        if ((URL.indexOf("mysql") >= 0) || (URL.indexOf("MYSQL") >= 0))
            DATABASE_TYPE = DATABASE_TYPE_MYSQL;
        else if ((URL.indexOf("oracle") >= 0) || (URL.indexOf("ORACLE") >= 0)) {
            DATABASE_TYPE = DATABASE_TYPE_ORACLE;
        }

        source_root_package = source_root_package.replace(".", "/");
        web_root_package = web_root_package.replace(".", "/");

        ENTITY_URL = source_root_package + "/" + bussiPackageUrl + "/" + entity_package + "/";

        PAGE_URL = source_root_package + "/" + bussiPackageUrl + "/" + page_package + "/";

        ENTITY_URL_INX = bussiPackage + "." + entity_package + ".";

        PAGE_URL_INX = bussiPackage + "." + page_package + ".";

        CODEPATH = source_root_package + "/" + bussiPackageUrl + "/";

        JSPPATH = web_root_package + "/" + bussiPackageUrl + "/";
    }

    private void ResourceUtil() {
    }

    public static final String getDIVER_NAME() {
        return bundle.getString("diver_name");
    }

    public static final String getURL() {
        return bundle.getString("url");
    }

    public static final String getUSERNAME() {
        return bundle.getString("username");
    }

    public static final String getPASSWORD() {
        return bundle.getString("password");
    }

    public static final String getDATABASE_NAME() {
        return bundle.getString("database_name");
    }

    private static String getBussiPackage() {
        return bundlePath.getString("bussi_package");
    }

    public static final String getEntityPackage() {
        return bundlePath.getString("entity_package");
    }

    public static final String getPagePackage() {
        return bundlePath.getString("page_package");
    }

    public static final String getTEMPLATEPATH() {
        return bundlePath.getString("templatepath");
    }

    public static final String getSourceRootPackage() {
        return bundlePath.getString("source_root_package");
    }

    public static final String getWebRootPackage() {
        return bundlePath.getString("webroot_package");
    }

    public static final String getSYSTEM_ENCODING() {
        return bundlePath.getString("system_encoding");
    }

    public static final String getMYJ_GENERATE_TABLE_ID() {
        return bundlePath.getString("MYJ_GENERATE_TABLE_ID");
    }

    public static final String getMYJ_GENERATE_UI_FILTER_FIELDS() {
        return bundlePath.getString("MYJ_GENERATE_UI_FILTER_FIELDS");
    }

    public static final String getMYJ_UI_SEARCH_FILED_NUM() {
        return bundlePath.getString("MYJ_UI_SEARCH_FILED_NUM");
    }

    public static final String getMYJ_UI_FIELD_REQUIRED_NUM() {
        return bundlePath.getString("MYJ_UI_FIELD_REQUIRED_NUM");
    }
}