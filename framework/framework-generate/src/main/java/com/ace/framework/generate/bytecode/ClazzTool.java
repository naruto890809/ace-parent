package com.ace.framework.generate.bytecode;

import com.ace.framework.base.BaseEntity;
import com.ace.framework.generate.ColumnData;

import java.io.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author WuZhiWei
 * @since 2016-05-27 10:31
 */
public class ClazzTool {
    public static String bytes2HexString(byte[] b) {
        StringBuffer result = new StringBuffer();
        String hex;
        for (int i = 0; i < b.length; i++) {
            hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            result.append(hex.toUpperCase());
        }
        return result.toString();
    }
    /**
     * the traditional io way
     * @param filename
     * @return
     * @throws java.io.IOException
     */
    public static byte[] toByteArray(String filename) throws IOException {

        File f = new File(filename);
        if(!f.exists()){
            throw new FileNotFoundException(filename);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int)f.length());
        BufferedInputStream in = null;
        try{
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while(-1 != (len = in.read(buffer,0,buf_size))){
                bos.write(buffer,0,len);
            }
            return bos.toByteArray();
        }catch (IOException e) {
            e.printStackTrace();
            throw e;
        }finally{
            try{
                in.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }
    public static String nameChangeProName(String columnName){

        String [] columnNameSplit=columnName.split("_");
        StringBuffer cbt=new StringBuffer(columnNameSplit[0]);
        for(int i=1;i<columnNameSplit.length;i++){
            String columnShort=columnNameSplit[i];
            cbt.append(columnShort.substring(0,1).toUpperCase()).append(columnShort.substring(1).toLowerCase());
        }

        return cbt.toString();
    }
    public static void main(String[] args) throws Exception{
        ClassGenerator cg = ClassGenerator.newInstance();
        cg.setClassName("com.a.Test");
//      cg.addField("private static final long serialVersionUID =-2L;");
        cg.addField("private static final int a=321;");

        cg.addInterface(Serializable.class);

        cg.setSuperClass(BaseEntity.class);

        cg.addField("private String accountId;");
        cg.addField("time",2, Date.class);

        cg.addMethod("setName",2,void.class,new Class[]{String.class},null,"this.accountId = accountId;");
        cg.addMethod("getName",2,String.class,new Class[]{String.class},null,"return accountId;");


        Class<?> aClass = cg.toClass();

        Object o= aClass.newInstance();

        FileOutputStream fos = new FileOutputStream("D://temp.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(o);
        oos.flush();
        oos.close();

        byte[] bytes = toByteArray("D://temp.out");
        String context=bytes2HexString(bytes);
        String serialVersionUIDStr=context.substring(36, 52);
        System.out.println(serialVersionUIDStr);
//        System.out.println(Long.toHexString(-2));
        BigInteger bigInteger=new BigInteger(serialVersionUIDStr,16);
        System.out.println(bigInteger.longValue());

    }
    public static Class<?> builderClazz(List<ColumnData> beanDates,String clazzName,String packageName ){
        ClassGenerator cg = ClassGenerator.newInstance();
        cg.setClassName(packageName+"."+clazzName);
//      cg.addField("private static final long serialVersionUID =-2L;");
//        cg.addInterface(Serializable.class);
        cg.setSuperClass(BaseEntity.class);

        for (ColumnData beanDate : beanDates) {
            String name =nameChangeProName(beanDate.getColumnName());
            cg.addField(name,2,beanDate.getDataClazz());
            String comment =beanDate.getColumnComment();

            String maxChar = name.substring(0, 1).toUpperCase();

            String method = maxChar + name.substring(1, name.length());

            cg.addMethod("get"+method,1,beanDate.getDataClazz(),new Class[0],null,"return "+name+";");
            cg.addMethod("set"+method,1,void.class,new Class[]{beanDate.getDataClazz()},new String[]{name},"this."+name+" = "+name+";");

        }

        Class<?> aClass = cg.toClass();
        return aClass;
    }

    public static Long getSerialVersionUID(Class<?> clazz){
        try {
            Object o= clazz.newInstance();

            FileOutputStream fos = new FileOutputStream("D://temp.out");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
            oos.flush();
            oos.close();

            byte[] bytes = toByteArray("D://temp.out");
            String context=bytes2HexString(bytes);
//            System.out.println(context);
            String clazzLengthStr=context.substring(12, 16); //获取class name的长度
            BigInteger clazzLength=new BigInteger(clazzLengthStr,16);
//            System.out.println(clazzLength);
            String serialVersionUIDStr=context.substring(16+(clazzLength.intValue())*2, 16+(clazzLength.intValue())*2+16);
//            System.out.println(serialVersionUIDStr);
//           System.out.println(Long.toHexString(-2));
            BigInteger bigInteger=new BigInteger(serialVersionUIDStr,16);
//            System.out.println(bigInteger.longValue());
            return bigInteger.longValue();
        }catch (Exception e){
            return 1L;
        }

    }

}
