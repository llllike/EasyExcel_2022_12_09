package com.excel.mapper;

import com.excel.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Lenovo
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-12-09 11:54:59
* @Entity com.excel.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




