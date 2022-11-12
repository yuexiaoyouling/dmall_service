package com.dg.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dg.entity.Domain.Dic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author TheFool
 */
@Repository
@Mapper
public interface DicDao extends BaseMapper<Dic> {
    @Select("select ${fieldName} as 'dic_text' from ${tableName};")
    List<Dic> queryData(@Param("tableName")String tableName, @Param("fieldName")String fieldName);
}
