package com.excel.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.excel.converter.SexConverter;
import lombok.*;

import java.io.Serializable;

/**
 * @author yzy
 * @create 2022-12-09-12:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ExcelUser implements Serializable {
    @ExcelProperty("姓名")
    @ColumnWidth(10)
    private String name;
    @ExcelProperty("年龄")
    @ColumnWidth(10)
    private Integer age;
    /**
     * 映射规则是 0->男 1->女 2->未知
     */
    @ExcelProperty(value = "性别", converter = SexConverter.class)
    @ColumnWidth(10)
    private Integer sex;
    @ExcelProperty("密码")
    @ColumnWidth(30)
    private String password;
    @ExcelProperty("手机号")
    @ColumnWidth(30)
    private String tel;
    @ExcelProperty("创建时间")
    @ColumnWidth(30)
    private String createTime;
}
