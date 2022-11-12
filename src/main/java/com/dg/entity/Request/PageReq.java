package com.dg.entity.Request;

import lombok.Data;

/**
 * @Author TheFool
 */
@Data
public class PageReq extends CommonReq {
    private Integer page;
    private Integer size;
}
