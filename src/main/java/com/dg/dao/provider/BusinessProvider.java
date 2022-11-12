package com.dg.dao.provider;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.dg.entity.DTO.CommonDto;
import com.dg.entity.DTO.EditDto;
import com.dg.entity.Request.AddReq;
import com.dg.entity.Request.CommonReq;
import com.dg.entity.Request.EditReq;
import com.dg.utils.ParamFilterUtil;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author TheFool
 */
public class BusinessProvider {
    public String search(Map<String, Object> params) {
        CommonDto dto = (CommonDto) params.get("req");
        Map<String,Object> map = JSON.parseObject((String) dto.getParams(), HashMap.class) ;
        return new SQL() {
            {
                SELECT("*");
                FROM(dto.getTableName());
                WHERE(" 1=1 ");
                if(map != null && map.size() >0) {
                    for(String key : map.keySet()) {
                        AND();
                        WHERE(ParamFilterUtil.searchParamFactory(key, map.get(key)));
                    }
                }
                ORDER_BY(dto.getPrimaryKey());
            }
        }.toString();

    }

    public String edit(Map<String, Object> params) {
        EditDto req = (EditDto) params.get("req");
        return new SQL() {
            {
                UPDATE(req.getTableName());
                if(req.getParams() != null && req.getParams().size() >0) {
                    for(String key : req.getParams().keySet()) {
                        if(req.getParams().get(key) != null && !req.getParams().get(key).equals("")) {
                            SET(ParamFilterUtil.editParamFactory(key, req.getParams().get(key)));
                        }
                    }
                }
                WHERE(ParamFilterUtil.searchParamFactory(req.getPrimaryKey(), req.getPrimaryKeyValue()));
            }
        }.toString();
    }

    public String add(Map<String, Object> params) {
        AddReq req = (AddReq) params.get("req");
        return new SQL() {
            {
                INSERT_INTO(req.getTableName());
                if(req.getParams() != null && req.getParams().size() >0) {
                    for(String key : req.getParams().keySet()) {
                        if(req.getParams().get(key) != null && !req.getParams().get(key).equals("")) {
                            VALUES(key, String.valueOf(req.getParams().get(key)));
                        }
                    }
                }
            }
        }.toString();
    }
}
