package com.dg.utils;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author TheFool
 */
public class ParamFilterUtil {
    public static String searchParamFactory(String label, Object value) {
        if(label.indexOf("Name") != -1 || label.indexOf("name") != -1 ) {
            return NameUtil.HumpToUnderline(label) + " LIKE '%" + value + "%'";
        }else {
            return NameUtil.HumpToUnderline(label) + " = '"+ value +"'" ;
        }
    }
    public static String editParamFactory(String label, Object value) {
        return NameUtil.HumpToUnderline(label) + " = " + value;
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("1",1);
    }
}
