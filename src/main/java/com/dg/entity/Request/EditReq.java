package com.dg.entity.Request;

import lombok.Data;

import java.util.Map;

/**
 * @Author TheFool
 */
@Data
public class EditReq {
    private String tableName;
    private Map<String, Object> params;
    private String primaryKeyValue;
}
