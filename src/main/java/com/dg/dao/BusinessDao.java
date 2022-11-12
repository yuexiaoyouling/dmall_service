package com.dg.dao;

import com.dg.dao.provider.BusinessProvider;
import com.dg.entity.DTO.CommonDto;
import com.dg.entity.DTO.EditDto;
import com.dg.entity.Request.AddReq;
import com.dg.entity.Request.CommonReq;
import com.dg.entity.Request.EditReq;
import com.dg.entity.VO.OrderVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author TheFool
 */
@Repository
@Mapper
public interface BusinessDao {
    @Select("SELECT * FROM ${tableName} where ${primaryKey} = #{primaryKeyValue}")
    Map<String,Object> select(@Param("tableName")String tableName,
                              @Param("primaryKey")String primaryKey,
                              @Param("primaryKeyValue")Object primaryKeyValue);

    @SelectProvider(type = BusinessProvider.class, method = "search")
    List<Map<String,Object>> query(@Param("req") CommonDto req);

    @UpdateProvider(type = BusinessProvider.class, method = "edit")
    Integer edit(@Param("req") EditDto req);

    @InsertProvider(type = BusinessProvider.class, method = "add")
    Integer add(@Param("req") AddReq req);

    @Delete("delete from ${tableName} where ${primaryKey} = #{primaryKeyValue}")
    Integer remove(@Param("tableName")String tableName,
                              @Param("primaryKey")String primaryKey,
                              @Param("primaryKeyValue")Object primaryKeyValue);

    @Select("select ${name} as name , count(*) as value from ${tableName} GROUP BY name")
    List<OrderVO> getStatisticsData(@Param("name")String name, @Param("tableName")String tableName );

    @Select("select * from ${elementTableName} where ${elementKey} not in (select ${elementKey} from ${relationTableName} where ${relationKey} = #{relationKeyValue}) ")
    List<Map<String,Object>> out(@Param("elementTableName")String elementTableName,
                                 @Param("elementKey")String elementKey,
                                 @Param("relationTableName")String relationTableName,
                                 @Param("relationKey")String relationKey,
                                 @Param("relationKeyValue")String relationKeyValue);
}
