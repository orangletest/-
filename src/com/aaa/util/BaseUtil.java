package com.aaa.util;

import com.aaa.entity.Message;
import com.alibaba.fastjson.JSON;

import java.io.PrintWriter;

public class BaseUtil {
    public static int transFromStr(String source){
        if(null != source && source.trim().length()>0){
            return Integer.parseInt(source);
        }else{
            throw new RuntimeException("字符串转为整数异常");
        }


    }
    //把字符串数组转为int类型数组
    public static int[] fromStrArr(String[] strs){
        int[] result = new int[strs.length];
        if(null != strs && strs.length>0){
            for (int i = 0; i <strs.length ; i++) {
                result[i] = transFromStr(strs[i]);
            }

        }
        return result;

    }
    public static void printJson(PrintWriter out, Message msg){
        out.println(JSON.toJSONString(msg));

    }
}
