package com.excel.tool;

import com.excel.pojo.ExcelUser;
import com.excel.pojo.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yzy
 * @create 2022-12-09-12:21
 */
public class TransUserUtil {
    public static List<ExcelUser> getExcelBatchUsers(List<User> users){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (users==null){
            return null;
        }
        List<ExcelUser> excelUsers=new ArrayList<>();
        for (User user:users) {
            ExcelUser excelUser = new ExcelUser();
            excelUser.setAge(user.getAge());
            excelUser.setName(user.getName());
            excelUser.setCreateTime(simpleDateFormat.format(new Date()));
            excelUser.setSex(user.getSex());
            excelUser.setPassword(user.getPassword());
            excelUser.setTel(user.getTel());
            excelUsers.add(excelUser);
        }
      return excelUsers;
    }
}
