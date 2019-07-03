package com.ace.app.cms.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author WuZhiWei
 * @since 2016-03-20 13:23
 */
public class CodeGen {
    private static final String STR_FORMAT = "000";
    List<Integer> randomList=new ArrayList<Integer>();
    private Integer genRandom(){
        try {
            Integer  random=(int) (Math.random()*1000);
            if(randomList.contains(random)){
                return  genRandom();
            }else{
                randomList.add(random);
            }
            return random;
        } catch (StackOverflowError e) {
            randomList.clear();
            return  genRandom();
        }
    }
    public String gen(String type){
        StringBuffer sb=new StringBuffer();
        sb.append(type);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        sb.append(dateString);
        DecimalFormat df = new DecimalFormat(STR_FORMAT);

        sb.append(df.format(genRandom()));

        return sb.toString();
    }

    public static void main(String[] args) {

            CodeGen  gen=new CodeGen();
            while(true){
                System.out.println(gen.gen("G"));
            }
    }
}
