package com.http.demo.user.mapper;

import com.http.demo.base.BaseMapper;
import com.http.demo.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User,Integer> {

}
