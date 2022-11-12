package com.dg.rest;

import com.dg.entity.Domain.Dic;
import com.dg.entity.Request.DicQueryReq;
import com.dg.entity.Request.DictDataReq;
import com.dg.entity.VO.Result;
import com.dg.service.DicService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author TheFool
 */
@RestController
@RequestMapping(value = "/v1/public/dic")
public class DicController {

    @Autowired
    DicService dicService;

    @GetMapping(value = "/query/by-type")
    public Result<List<Dic>> getSysDic(DicQueryReq req) {
        return Result.success(dicService.getSysDic(req));
    }

    @PostMapping(value = "/query/by-data")
    public Result<List<Dic>> getSysDic(@RequestBody DictDataReq req) {
        List<Dic> collect = req.getData().stream().map(a -> ( a.get(req.getName()))).collect(Collectors.toList()).stream().map(Dic::fromObject).collect(Collectors.toList());
        return Result.success(collect);
    }

}
