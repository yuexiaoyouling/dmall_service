package com.dg.entity.Request;

import lombok.Data;

/**
 * @Author TheFool
 */
@Data
public class SelectReq {
    private String tableName;
    private Object primaryKey;
}
