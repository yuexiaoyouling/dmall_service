package com.dg.entity.Request;

import lombok.Data;

import java.util.Map;

/**
 * @Author TheFool
 */
@Data
public class AddReq {
    private String tableName;
    private Map<String, Object> params;
}
