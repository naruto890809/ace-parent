package com.ace.framework.generate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ace.framework.generate.bytecode.ClazzTool;
import com.ace.framework.generate.def.CodeResourceUtil;
import com.ace.framework.generate.def.TableConvert;
import org.apache.commons.lang.StringUtils;

/**
 * @author WuZhiWei
 * @since 2015-11-10 16:19
 */
public class CreateBean {
    private Connection connection = null;
    static String url;
    static String username;
    static String password;
    static String rt = "\r\t";
    String SQLTables = "show tables";
    private String method;
    private String argv;
    static String selectStr;
    static String from;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        selectStr = "select ";
        from = " from ";
    }

    public void setMysqlInfo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public List<String> getTables() throws SQLException {
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(this.SQLTables);
        ResultSet rs = ps.executeQuery();
        List list = new ArrayList();
        while (rs.next()) {
            String tableName = rs.getString(1);
            list.add(tableName);
        }
        rs.close();
        ps.close();
        con.close();
        return list;
    }

    public List<ColumnData> getColumnDatas(String tableName)
            throws SQLException {
        String SQLColumns = "select column_name ,data_type,column_comment,0,0,character_maximum_length,is_nullable nullable from information_schema.columns where table_name =  '" + tableName + "' " + "and table_schema =  '" + CodeResourceUtil.DATABASE_NAME + "'";

        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(SQLColumns);
        List columnList = new ArrayList();
        ResultSet rs = ps.executeQuery();
        StringBuffer str = new StringBuffer();
        StringBuffer getset = new StringBuffer();
        while (rs.next()) {
            String name = rs.getString(1).toLowerCase();
            String type = rs.getString(2);
            String comment = rs.getString(3);
            String precision = rs.getString(4);
            String scale = rs.getString(5);
            String charmaxLength = rs.getString(6) == null ? "" : rs.getString(6);
            String nullable = TableConvert.getNullAble(rs.getString(7));
            Class<?> clazz = getClazz(type, precision, scale);

            type=getType(type, precision, scale);
            ColumnData cd = new ColumnData();
            cd.setColumnName(name);
            cd.setProColumnName(ClazzTool.nameChangeProName(name));
            cd.setDataType(type);
            cd.setDataClazz(clazz);
            cd.setColumnType(rs.getString(2));
            cd.setColumnComment(comment);
            cd.setPrecision(precision);
            cd.setScale(scale);
            cd.setCharmaxLength(charmaxLength);
            cd.setNullable(nullable);
            formatFieldClassType(cd);
            columnList.add(cd);
        }
        this.argv = str.toString();
        this.method = getset.toString();
        rs.close();
        ps.close();
        con.close();
        return columnList;
    }

    public String getBeanFeilds(String tableName)
            throws SQLException {
        List<ColumnData> dataList = getColumnDatas(tableName);
        StringBuffer str = new StringBuffer();
        StringBuffer getset = new StringBuffer();
        List<String>  fieldNames = new ArrayList<>(dataList.size());
        for (ColumnData d : dataList) {
            String name = d.getColumnName();
            if(name.equals("corp_code")||name.equals("create_by")||name.equals("create_time")||name.equals("last_modify_by")||name.equals("last_modify_time")||name.equals("parent_corp_code")){
                continue;
            }

            fieldNames.add(name);
            name= ClazzTool.nameChangeProName(name);
            String type = d.getDataType();
            String comment = d.getColumnComment();

            String maxChar = name.substring(0, 1).toUpperCase();
            if ("id".equals(name)){
                str.append("\n\r\t").append("@PrimaryKey");
            }
            str.append("\n\t").append("private ").append(type + " ").append(name).append(";//   ").append(comment);
            String method = maxChar + name.substring(1, name.length());
            getset.append("\n\t").append("public ").append(type + " ").append("get" + method + "() {\n\t");
            getset.append("    return this.").append(name).append(";\n\t}");
            getset.append("\n\t").append("public void ").append("set" + method + "(" + type + " " + name + ") {\n\t");
            getset.append("    this." + name + "=").append(name).append(";\n\t}");
        }

        if (fieldNames.contains("approve")){
            str.append("\n\r\t///////////////////////////////////////////").append("\n\r\tprivate String approveText;//   审核状态 审核中（APPROVING）、通过（PASSED） 驳回 REFUSED");
            getset.append("\n\r\tpublic String getApproveText() {" +
                    "\n\t\tif (CmsConstant.APPROVING.equals(approve)){" +
                    "\n\t\t    approveText = CmsConstant.APPROVING_TEXT;" +
                    "\n\t\t}else if (CmsConstant.PASSED.equals(approve)){" +
                    "\n\t\t    approveText = CmsConstant.PASSED_TEXT;" +
                    "\n\t\t}else if (CmsConstant.REFUSED.equals(approve)){" +
                    "\n\t\t    approveText = CmsConstant.REFUSED_TEXT;" +
                    "\n\t\t}else {" +
                    "\n\t\t    approveText = \"未定义\";" +
                    "\n\t\t}" +
                    "\n\t\treturn approveText;" +
                    "\n\t}" +
                    "\n\t\t" +
                    "\n\tpublic void setApproveText(String approveText) {" +
                    "\n\t\tthis.approveText = approveText;" +
                    "\n\t}");
        }

        this.argv = str.toString();
        this.method = getset.toString();
        return this.argv + this.method;
    }

    public List<ColumnData> getBeanDates(String tableName)
            throws SQLException {
        List<ColumnData> dataList = getColumnDatas(tableName);
        List<ColumnData> dataNewList = new ArrayList<ColumnData>();

        for (ColumnData d : dataList) {
            String name = d.getColumnName();
            if(name.equals("corp_code")||name.equals("create_by")||name.equals("create_time")||name.equals("last_modify_by")||name.equals("last_modify_time")||name.equals("parent_corp_code")){
                continue;
            }
            dataNewList.add(d);
        }
        return dataNewList;
    }



    private void formatFieldClassType(ColumnData columnt) {
        String fieldType = columnt.getColumnType();
        String scale = columnt.getScale();

        if ("N".equals(columnt.getNullable())) {
            columnt.setOptionType("required");
        }
        if (("datetime".equals(fieldType)) || ("time".equals(fieldType))) {
            columnt.setClassType("easyui-datetimebox");
        } else if ("date".equals(fieldType)) {
            columnt.setClassType("easyui-datebox");
        } else if ("int".equals(fieldType)) {
            columnt.setClassType("easyui-numberbox");
        } else if ("number".equals(fieldType)) {
            if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0)) {
                columnt.setClassType("easyui-numberbox");
                if (StringUtils.isNotBlank(columnt.getOptionType()))
                    columnt.setOptionType(columnt.getOptionType() + "," + "precision:2,groupSeparator:','");
                else
                    columnt.setOptionType("precision:2,groupSeparator:','");
            } else {
                columnt.setClassType("easyui-numberbox");
            }
        } else if (("float".equals(fieldType)) || ("double".equals(fieldType)) || ("decimal".equals(fieldType))) {
            columnt.setClassType("easyui-numberbox");
            if (StringUtils.isNotBlank(columnt.getOptionType()))
                columnt.setOptionType(columnt.getOptionType() + "," + "precision:2,groupSeparator:','");
            else
                columnt.setOptionType("precision:2,groupSeparator:','");
        } else {
            columnt.setClassType("easyui-validatebox");
        }
    }

    public Class<?> getClazz(String dataType, String precision, String scale) {
        Class<?> clazz=null;
        dataType = dataType.toLowerCase();
        if (dataType.contains("char"))
            clazz = String.class;
        else if (dataType.contains("int"))
            clazz = Integer.class;
        else if (dataType.contains("float"))
            clazz = Float.class;
        else if (dataType.contains("double"))
            clazz = Double.class;
        else if (dataType.contains("number")) {
            if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0))
                clazz = BigDecimal.class;
            else if ((StringUtils.isNotBlank(precision)) && (Integer.parseInt(precision) > 6))
                clazz = Long.class;
            else
                clazz = Integer.class;
        } else if (dataType.contains("decimal"))
            clazz = BigDecimal.class;
        else if (dataType.contains("date"))
            clazz =  Date.class;
        else if (dataType.contains("time"))
            clazz = java.sql.Timestamp.class;
        else if (dataType.contains("clob"))
            clazz = java.sql.Clob.class;
        else {
            clazz = Object.class;
        }
        return clazz;
    }
    public String getType(String dataType, String precision, String scale) {
        dataType = dataType.toLowerCase();
        if (dataType.contains("char"))
            dataType = "String";
        else if (dataType.contains("int"))
            dataType = "Integer";
        else if (dataType.contains("float"))
            dataType = "Float";
        else if (dataType.contains("double"))
            dataType = "Double";
        else if (dataType.contains("number")) {
            if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0))
                dataType = "java.math.BigDecimal";
            else if ((StringUtils.isNotBlank(precision)) && (Integer.parseInt(precision) > 6))
                dataType = "Long";
            else
                dataType = "Integer";
        } else if (dataType.contains("decimal"))
            dataType = "BigDecimal";
        else if (dataType.contains("date"))
            dataType = "Date";
        else if (dataType.contains("time"))
            dataType = "java.sql.Timestamp";
        else if (dataType.contains("clob"))
            dataType = "java.sql.Clob";
        else {
            dataType = "Object";
        }
        return dataType;
    }

    public void getPackage(int type, String createPath, String content, String packageName, String className, String extendsClassName, String[] importName) throws Exception {
        if (packageName == null) {
            packageName = "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("package ").append(packageName).append(";\r");
        sb.append("\r");
        for (int i = 0; i < importName.length; i++) {
            sb.append("import ").append(importName[i]).append(";\r");
        }
        sb.append("\r");
        sb.append("/**\r *  entity. @author wolf Date:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r */");
        sb.append("\r");
        sb.append("\rpublic class ").append(className);
        if (extendsClassName != null) {
            sb.append(" extends ").append(extendsClassName);
        }
        if (type == 1)
            sb.append(" ").append("implements java.io.Serializable {\r");
        else {
            sb.append(" {\r");
        }
        sb.append("\r\t");
        sb.append("private static final long serialVersionUID = 1L;\r\t");
        String temp = className.substring(0, 1).toLowerCase();
        temp = temp + className.substring(1, className.length());
        if (type == 1) {
            sb.append("private " + className + " " + temp + "; // entity ");
        }
        sb.append(content);
        sb.append("\r}");
        System.out.println(sb.toString());
        createFile(createPath, "", sb.toString());
    }

    public String getTablesNameToClassName(String tableName) {
        String[] split = tableName.split("_");
        if (split.length > 1) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < split.length; i++) {
                String tempTableName = split[i].substring(0, 1).toUpperCase() + split[i].substring(1, split[i].length());
                sb.append(tempTableName);
            }

            return sb.toString();
        }
        String tempTables = split[0].substring(0, 1).toUpperCase() + split[0].substring(1, split[0].length());
        return tempTables;
    }

    public void createFile(String path, String fileName, String str)
            throws IOException {
        FileWriter writer = new FileWriter(new File(path + fileName));
        writer.write(new String(str.getBytes("utf-8")));
        writer.flush();
        writer.close();
    }

    public Map<String, Object> getAutoCreateSql(String tableName)
            throws Exception {
        Map sqlMap = new HashMap();
        List columnDatas = getColumnDatas(tableName);
        String columns = getColumnSplit(columnDatas);
        String proColumns = getProColumnSplit(columnDatas);
        String[] columnList = getColumnList(columns);
        String[] proColumnList = getColumnList(proColumns);
        String columnFields = getColumnFields(columns);
        String insert = "insert into " + tableName + "(" + columns.replaceAll("\\|", ",") + ")\n values(#{" + proColumns.replaceAll("\\|", "},#{") + "})";
        String update = getUpdateSql(tableName, columnList,proColumnList);
        String updateSelective = getUpdateSelectiveSql(tableName, columnDatas);
        String selectById = getSelectByIdSql(tableName, columnList,proColumnList);
        String delete = getDeleteSql(tableName, columnList,proColumnList);
        sqlMap.put("columnList", columnList);
        sqlMap.put("columnFields", columnFields);
        sqlMap.put("insert", insert.replace("#{lastModifyTime}", "now()").replace("#{lastModifyTime}", "now()"));
//        sqlMap.put("update", update.replace("#{lastModifyTime}", "now()").replace("#{lastModifyTime}", "now()"));
        sqlMap.put("delete", delete);
        sqlMap.put("update", updateSelective);
        sqlMap.put("selectById", selectById);
        return sqlMap;
    }

    public String getDeleteSql(String tableName, String[] columnsList,String[] proColumnList)
            throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("delete ");
        sb.append("\t from ").append(tableName).append(" where ");
        sb.append(columnsList[0]).append(" = #{").append(proColumnList[0]).append("}");
        return sb.toString();
    }

    public String getSelectByIdSql(String tableName, String[] columnsList,String []proColumnList)
            throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("select <include refid=\"Base_Column_List\" /> \n");
        sb.append("\t from ").append(tableName).append(" where ");
        sb.append(columnsList[0]).append(" = #{").append(proColumnList[0]).append("}");
        return sb.toString();
    }

    public String getColumnFields(String columns)
            throws SQLException {
        String fields = columns;
        if ((fields != null) && (!"".equals(fields))) {
            fields = fields.replaceAll("[|]", ",");
        }
        return fields;
    }

    public String[] getColumnList(String columns)
            throws SQLException {
        String[] columnList = columns.split("[|]");
        return columnList;
    }

    public String getUpdateSql(String tableName, String[] columnsList,String[] proColumnList)
            throws SQLException {
        StringBuffer sb = new StringBuffer();

        for (int i = 1; i < columnsList.length; i++) {
            String column = columnsList[i];
            if ("CREATETIME".equals(column.toUpperCase())) {
                continue;
            }
            if ("UPDATETIME".equals(column.toUpperCase()))
                sb.append(column + "=now()");
            else {
                sb.append(column + "=#{" + proColumnList[i] + "}");
            }
            if (i + 1 < columnsList.length) {
                sb.append(",");
            }
        }
        String update = "update " + tableName + " set " + sb.toString() + " where " + columnsList[0] + "=#{" + proColumnList[0] + "}";
        return update;
    }

    public String getUpdateSelectiveSql(String tableName, List<ColumnData> columnList) throws SQLException {
        StringBuffer sb = new StringBuffer();
        ColumnData cd = (ColumnData) columnList.get(0);
        sb.append("\t<trim  suffixOverrides=\",\" >\n");
        for (int i = 1; i < columnList.size(); i++) {
            ColumnData data = (ColumnData) columnList.get(i);
            String columnName = data.getColumnName();
            if(columnName .equals("last_modify_time")){
                continue;
            }
            if(columnName .equals("create_by")){
                continue;
            }
            if(columnName .equals("create_time")){
                continue;
            }
            sb.append("\t<if test=\"").append(data.getProColumnName()).append(" != null ");

            if ("String" == data.getDataType()) {
                sb.append(" and ").append(data.getProColumnName()).append(" != ''");
            }
            sb.append(" \">\n\t\t");
            sb.append(columnName + "=#{" +  data.getProColumnName()+ "},\n");
            sb.append("\t</if>\n");
        }
        sb.append("\t\tlast_modify_time=now(),\n\t");
        sb.append("</trim>");
        String update = "update " + tableName + " set \n" + sb.toString() + "\n\t\t"
        +"where 1=1\n" +
                "        <if test=\"id != null and id != ''\">\n" +
                "            and id = #{ id }\n" +
                "        </if>\n" +
                "        <if test=\"ids != null\">\n" +
                "            and id in\n" +
                "            <foreach item=\"aId\" index=\"index\" collection=\"ids\"\n" +
                "                     open=\"(\" separator=\",\" close=\")\">\n" +
                "                #{aId}\n" +
                "            </foreach>\n" +
                "        </if>";
        return update;
    }

    public String getColumnSplit(List<ColumnData> columnList)
            throws SQLException {
        StringBuffer commonColumns = new StringBuffer();
        for (ColumnData data : columnList) {
            commonColumns.append(data.getColumnName() + "|");
        }
        return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
    }

    public String getProColumnSplit(List<ColumnData> columnList)
            throws SQLException {
        StringBuffer commonColumns = new StringBuffer();
        for (ColumnData data : columnList) {
            commonColumns.append(data.getProColumnName() + "|");
        }
        return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
    }
}
