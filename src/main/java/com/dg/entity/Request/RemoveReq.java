package com.dg.entity.Request;

import lombok.Data;

import java.util.List;

/**
 * @Author TheFool
 */
@Data
public class RemoveReq {
    private String tableName;
    private List<Object> param;
}
