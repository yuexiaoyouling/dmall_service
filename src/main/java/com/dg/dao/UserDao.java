package com.dg.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dg.entity.Domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author TheFool
 */
@Repository
@Mapper
public interface UserDao extends BaseMapper<User> {
}
