package com.dg.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dg.dao.DicDao;
import com.dg.entity.Domain.Dic;
import com.dg.entity.Request.DicQueryReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author TheFool
 */
@Service
public class DicService {

    @Autowired
    DicDao dicDao;
    public List<Dic> getSysDic(DicQueryReq req) {
        LambdaQueryWrapper<Dic> queryWrapper = new LambdaQueryWrapper<Dic>()
                .eq(StringUtils.isNoneBlank(req.getDicType()), Dic::getDicType, req.getDicType());
        return dicDao.selectList(queryWrapper);
    }
}
