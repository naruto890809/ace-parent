package com.ace.framework.util;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WuZhiWei
 * @since 2016-03-11 09:44
 */
public class Descartes {
    public static void run(List<List<String>> dimvalue,List<List<String>> result, int layer, List<String> curList)
    {
        //大于一个集合时：
        if (layer < dimvalue.size() - 1)
        {
            //大于一个集合时，第一个集合为空
            if (dimvalue.get(layer).size() == 0)
                run(dimvalue, result, layer + 1, curList);
            else
            {
                List<String> list=null;
                for (int i = 0; i < dimvalue.get(layer).size(); i++)
                {
                    list=new ArrayList<String>();
                    list.addAll(curList);
                    list.add(dimvalue.get(layer).get(i));
                    run(dimvalue, result, layer + 1, list);
                }
            }
        }
        //只有一个集合时：
        else if (layer == dimvalue.size() - 1)
        {
            //只有一个集合，且集合中没有元素
            if (dimvalue.get(layer).size() == 0)
                result.add(curList);
                //只有一个集合，且集合中有元素时：其笛卡尔积就是这个集合元素本身
            else
            {
                List<String> list=null;
                for (int i = 0; i < dimvalue.get(layer).size(); i++)
                {
                    list=new ArrayList<String>();
                    list.addAll(curList);
                    list.add(dimvalue.get(layer).get(i));

                    result.add(list);
                }

            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        List<List<String>> dimvalue = new ArrayList<List<String>>();
        List<String> v1 = new ArrayList<String>();
        v1.add("a");
        v1.add("b");
        List<String> v2 = new ArrayList<String>();
        v2.add("c");
        v2.add("d");
        v2.add("e");
        List<String> v3 = new ArrayList<String>();
        v3.add("f");
        v3.add("g");

        dimvalue.add(v1);
        dimvalue.add(v2);
        dimvalue.add(v3);
        /* dimvalue.add(v3);*/

        List<List<String>>result = new ArrayList();

        Descartes.run(dimvalue, result, 0, new ArrayList<String>());

        int i = 1;
        /*for (String s : result)
        {
            System.out.println(i++ + ":" +s);
        }*/
        for(List<String> list:result){
            for(String listStr:list){
                System.out.print(listStr);
                System.out.print(" ");

            }
            System.out.println("");
        }
    }

}