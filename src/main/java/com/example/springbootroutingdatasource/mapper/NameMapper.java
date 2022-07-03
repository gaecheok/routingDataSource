package com.example.springbootroutingdatasource.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface NameMapper {

    @Select("SELECT name FROM test")
    @Transactional(readOnly = false)
    String getNameMaster();

    @Select("SELECT name FROM test")
    @Transactional(readOnly = true)
    String getNameSlave();
}
