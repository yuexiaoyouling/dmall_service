package com.dg.rest;

import com.baomidou.mybatisplus.extension.api.R;
import com.dg.entity.Request.*;
import com.dg.entity.VO.Result;
import com.dg.service.BusinessService;
import com.dg.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author TheFool
 */
@RestController
@RequestMapping("v1/public/business")
public class BusinessController {

    @Autowired
    BusinessService businessService;

    /**
     * 根据主键获取单条数据
     *
     * @Author TheFool
     */
    @GetMapping("/select")
    public Result<Map<String, Object>> select(SelectReq req) {
        return Result.success(businessService.select(req));
    }

    /**
     * 根据查询条件获取多条数据
     *
     * @Author TheFool
     */
    @GetMapping("/query")
    public Result<List<Map<String, Object>>> query(CommonReq req) {
        return Result.success(businessService.query(req));
    }

    /**
     * 分页获取数据
     *
     * @Author TheFool
     */
    @GetMapping("/page")
    public Result<PageUtils> page(PageReq req) {
        return Result.success(businessService.page(req));
    }

    /**
     * 根据主键更新数据
     *
     * @Author TheFool
     */
    @PostMapping("/edit")
    public Result edit(@RequestBody EditReq req) {
        businessService.edit(req);
        return Result.success();
    }

    /**
     * 新增数据
     *
     * @Author TheFool
     */
    @PostMapping("/add")
    public Result add(@RequestBody AddReq req) {
        businessService.add(req);
        return Result.success();
    }

    /**
     * 删除数据
     *
     * @Author TheFool
     */
    @DeleteMapping("/remove")
    public Result remove(@RequestBody RemoveReq req) {
        businessService.remove(req);
        return Result.success();
    }

    /**
     * POI Excel数据导出
     *
     * @Author TheFool
     */
    @PostMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response, @RequestBody CommonReq req) throws Exception {
        businessService.export(request, response, req);
    }

    /**
     * echar统计图
     *
     * @Author TheFool
     */
    @GetMapping("/statistics")
    public Result<Map<String, List<String>>> statistics(StatisticsReq req) {
        return Result.success(businessService.statistics(req));
    }

    /**
     * 未关联元素获取
     *
     * @Author TheFool
     */
    @GetMapping("/out")
    public Result<PageUtils> out(OutReq req) {
        return Result.success(businessService.out(req));
    }
}

