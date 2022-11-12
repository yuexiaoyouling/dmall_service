package com.dg.dao;

import com.dg.entity.Domain.TableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 核心持久层工具类
 *
 * @Author TheFool
 */
@Repository
@Mapper
public interface CommonDao {

    /**
     * 根据数据库名称以及表名称获取主键字段名称
     * */
    @Select("SELECT column_name FROM INFORMATION_SCHEMA.`KEY_COLUMN_USAGE` " +
            "WHERE table_name=#{tableName} " +
            "AND constraint_name='PRIMARY' " +
            "AND TABLE_SCHEMA = #{tableSchema};")
    String getPrimaryKey(@Param("tableName")String tableName,@Param("tableSchema")String tableSchema);

    /**
     * 根据数据库名称以及表名称获取字段
     * */
    @Select("SELECT COLUMN_NAME AS name, DATA_TYPE AS type, COLUMN_COMMENT AS lable FROM INFORMATION_SCHEMA.COLUMNS " +
            "WHERE table_name=#{tableName}  " +
            "AND TABLE_SCHEMA = #{tableSchema} " +
            "ORDER BY ORDINAL_POSITION")
    List<TableInfo> getTableInfo(@Param("tableName")String tableName, @Param("tableSchema")String tableSchema);
}
