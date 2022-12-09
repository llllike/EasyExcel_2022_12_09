package com.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.excel.mapper.UserMapper;
import com.excel.pojo.ExcelUser;
import com.excel.pojo.User;
import com.excel.service.impl.UserServiceImpl;
import com.excel.tool.TransUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yzy
 * @create 2022-12-09-12:01
 */
@SpringBootTest
public class EasyExcelApplicationTest {
    @Resource
    private UserServiceImpl userService;
    @Test
    public void writeExcel(){
        List<User> list = userService.list();
        List<ExcelUser> excelBatchUsers = TransUserUtil.getExcelBatchUsers(list);
        URL resource = this.getClass().getClassLoader().getResource("test1.xlsx");
        EasyExcel.write(resource.getPath())
                .head(ExcelUser.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("用户表1")
                .doWrite(excelBatchUsers);
    }
    @Test
    public void readExcel(){
        URL resource = this.getClass().getClassLoader().getResource("test2.xlsx");
        List<ExcelUser> excelUsers = EasyExcel.read(resource.getPath())
                .head(ExcelUser.class)
                .sheet()
                .doReadSync();
        List<User> users=new ArrayList<>();
        for (ExcelUser e:excelUsers) {
            users.add(new User(e));
        }
        userService.saveBatch(users);
    }


}
