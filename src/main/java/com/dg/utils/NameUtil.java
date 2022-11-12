package com.dg.utils;

/**
 * 下划线命名&驼峰命名互换
 * @author TheFool
 */
public class NameUtil {
    /***
     * 下划线命名转为驼峰命名:(" hello_world ") == "helloWorld"
     */

    public static String UnderlineToHump(String para){
        StringBuilder result=new StringBuilder();
        String a[]=para.split("_");
        for(String s:a){
            if (!para.contains("_")) {
                result.append(s);
                continue;
            }
            if(result.length()==0){
                result.append(s.toLowerCase());
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = UnderlineToHump(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }


    /***
     * 驼峰命名转为下划线命名:("helloWorld") = "hello_world"
     *
     * @param para
     *        驼峰命名的字符串
     */

    public static String HumpToUnderline(String para){
        StringBuilder sb=new StringBuilder(para);
        int temp=0;//定位
        if (!para.contains("_")) {
            for(int i=0;i<para.length();i++){
                if(Character.isUpperCase(para.charAt(i))){
                    sb.insert(i+temp, "_");
                    temp+=1;
                }
            }
        }
        return sb.toString().toUpperCase();
    }
    public static String HumpToUnderline(Object para){
        StringBuilder sb=new StringBuilder(String.valueOf(para));
        int temp=0;//定位
        if (!String.valueOf(para).contains("_")) {
            for(int i=0;i<String.valueOf(para).length();i++){
                if(Character.isUpperCase(String.valueOf(para).charAt(i))){
                    sb.insert(i+temp, "_");
                    temp+=1;
                }
            }
        }
        return sb.toString().toUpperCase();
    }
}
