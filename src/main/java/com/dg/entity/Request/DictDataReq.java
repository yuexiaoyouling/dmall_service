package com.dg.entity.Request;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author TheFool
 */
@Data
public class DictDataReq {
    private List<Map<String,Object>> data;

    private String name;
}
