package com.dg.entity.Request;

import lombok.Data;
import sun.dc.pr.PRError;

import java.util.List;
import java.util.Map;

/**
 * @Author TheFool
 */
@Data
public class CommonReq {
    private String tableName;
    private Object params;
}
