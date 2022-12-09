package com.excel;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yzy
 * @create 2022-12-09-11:53
 */
@MapperScan("com.excel.mapper")
@SpringBootApplication
public class EasyExcelApplication {
}
