package com.dg.service;

import com.dg.dao.CommonDao;
import com.dg.dao.BusinessDao;
import com.dg.entity.DTO.CommonDto;
import com.dg.entity.DTO.EditDto;
import com.dg.entity.Domain.TableInfo;
import com.dg.entity.Request.*;
import com.dg.entity.VO.OrderVO;
import com.dg.utils.BeanHelper;
import com.dg.utils.PageUtils;
import com.dg.utils.PoiUtil;
import com.dg.utils.SnowflakeIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author TheFool
 */
@Service
public class BusinessService {
    @Value("${base.tableSchema}")
    String tableSchema;
    @Autowired
    CommonDao commonDao;
    @Autowired
    BusinessDao businessDao;

    public Map<String, Object> select(SelectReq req) {
        String primaryKey = commonDao.getPrimaryKey(req.getTableName(), tableSchema);
        return businessDao.select(req.getTableName(), primaryKey, req.getPrimaryKey());
    }

    public List<Map<String, Object>> query(CommonReq req) {
        String primaryKey = commonDao.getPrimaryKey(req.getTableName(), tableSchema);
        CommonDto copy = BeanHelper.copy(req, CommonDto.class);
        copy.setPrimaryKey(primaryKey);
        return businessDao.query(copy);
    }

    public PageUtils page(PageReq req) {
        CommonReq copy = BeanHelper.copy(req, CommonReq.class);
        List<Map<String, Object>> query = query(copy);
        PageUtils pageUtils = new PageUtils();
        pageUtils.setDataList(query);
        pageUtils.setCurrentPage(req.getPage());
        pageUtils.setPageSizes(req.getSize());
        return pageUtils.paging();
    }

    public void edit(EditReq req) {
        String primaryKey = commonDao.getPrimaryKey(req.getTableName(), tableSchema);
        EditDto copy = BeanHelper.copy(req, EditDto.class);
        copy.setPrimaryKey(primaryKey);
        businessDao.edit(copy);
    }

    public void add(AddReq req) {
        SnowflakeIdUtil idWorker = new SnowflakeIdUtil(0, 0);
        String primaryKey = commonDao.getPrimaryKey(req.getTableName(), tableSchema);
        Map<String, Object> params = req.getParams();
        params.put(primaryKey, idWorker.nextId());
        req.setParams(params);
        businessDao.add(req);
    }

    @Transactional(rollbackFor = Exception.class)
    public void remove(RemoveReq req) {
        String primaryKey = commonDao.getPrimaryKey(req.getTableName(), tableSchema);
        req.getParam().forEach(ele -> {
            businessDao.remove(req.getTableName(), primaryKey, ele);
        });
    }

    public void export(HttpServletRequest request, HttpServletResponse response, CommonReq req) {
        List<TableInfo> tableInfo = commonDao.getTableInfo(req.getTableName(), tableSchema);
        List<Map<String, Object>> query = query(req);
        List<String> columnNames = tableInfo.stream().map(TableInfo::getName).collect(Collectors.toList());
        List<String> keyList = tableInfo.stream().map(TableInfo::getLable).collect(Collectors.toList());
        String filename = req.getTableName() + ".xls";
        PoiUtil.downLoadExcel(columnNames, keyList, query, filename, response);
    }

    public Map<String, List<String>> statistics(StatisticsReq req) {
        List<OrderVO> orderVOS = businessDao.getStatisticsData(req.getKey(), req.getTableName());
        List<String> names = new ArrayList<>();
        List<String> values = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (OrderVO vo : orderVOS) {
            names.add(vo.getName());
            values.add(vo.getValue().toString());
        }
        map.put("name", names);
        map.put("value", values);
        return map;
    }

    public PageUtils out(OutReq req) {
        List<Map<String, Object>> out = businessDao.out(req.getElementTableName(),
                req.getElementKey(),
                req.getRelationTableName(),
                req.getRelationKey(),
                req.getRelationKeyValue());
        PageUtils pageUtils = new PageUtils();
        pageUtils.setDataList(out);
        pageUtils.setCurrentPage(req.getPage());
        pageUtils.setPageSizes(req.getSize());
        return pageUtils.paging();
    }
}
