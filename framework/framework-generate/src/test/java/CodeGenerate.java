import com.ace.framework.generate.factory.CodeGenerateFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WuZhiWei
 * @since 2015-11-25 13:10
 */
public class CodeGenerate {
    public static void main(String[] args) throws  Exception{
        List<TableInfo> list = new ArrayList<>();
      /*  list.add(new TableInfo("t_cms_department_job","业务管理","Biz"));
        list.add(new TableInfo("t_cms_company","商业公司","Company"));
        list.add(new TableInfo("t_cms_company_drug","商业公司药品关联表","CompanyDrug"));
        list.add(new TableInfo("t_cms_department","部门","Department"));
        list.add(new TableInfo("t_cms_dic","数据字典","Dic"));
        list.add(new TableInfo("t_cms_drug","药品","Drug"));
        list.add(new TableInfo("t_cms_drug_spec","药品规格","DrugSpec"));
        list.add(new TableInfo("t_cms_export_record","导入记录","ExportRecord"));
        list.add(new TableInfo("t_cms_hospital","医院","Hospital"));
        list.add(new TableInfo("t_cms_order","流向清单","Order"));
        list.add(new TableInfo("t_cms_rebate","返利设置","Rebate"));*/
        list.add(new TableInfo("t_cms_department_job","任务管理","DepartmentJob"));
//        list.add(new TableInfo("t_cms_sell_area","销售区域","SellArea"));


        for (TableInfo tableInfo : list) {
            String tableName = tableInfo.getName(); //数据库中的表明
            String codeName = tableInfo.getComment();//中文注释
            String clazzName=tableInfo.getClassName();//实体类名

            String codeTarget="D:\\drug";//生成代码的路径，指定到模块
            String module="cms";//
            String logicModule ="drug";//逻辑模块。代码层模块

            CodeGenerateFactory.codeGenerate(tableName, codeName,codeTarget, module,logicModule ,clazzName,true);
        }
    }

    private static class TableInfo{
        private String name;
        private String comment;
        private String className;

        public TableInfo() {
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public TableInfo(String name, String comment, String className) {
            this.name = name;
            this.comment = comment;
            this.className = className;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

}
