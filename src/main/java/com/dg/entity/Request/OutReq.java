package com.dg.entity.Request;

import lombok.Data;

/**
 * @Author TheFool
 */
@Data
public class OutReq {
    private String relationTableName;
    private String elementTableName;
    private String relationKey;
    private String relationKeyValue;
    private String elementKey;
    private Integer page;
    private Integer size;
}
