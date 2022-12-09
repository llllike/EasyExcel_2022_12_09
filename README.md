# 如何内容对你有帮助的话，点一个免费的star吧，非常感谢!
# 使用EasyExcel做excel表格的导出导入

## 1、导入依赖

```xml
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>3.17</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>3.17</version>
        </dependency>
        <dependency>
            <groupId>net.sf.saxon</groupId>
            <artifactId>saxon-dom</artifactId>
            <version>8.7</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>easyexcel</artifactId>
            <version>3.0.5</version>
        </dependency>
        <dependency>
            <groupId>cglib</groupId>
            <artifactId>cglib-nodep</artifactId>
            <version>3.3.0</version>
        </dependency>
```

poi poi-ooxml saxon-dom easyexcel是用来处理excel表格的依赖

sax是一种文档处理模式

百度的解释是这样的

> SAX，全称Simple API for XML，既是一种接口，也是一种[软件包](https://baike.baidu.com/item/软件包/10508451?fromModule=lemma_inlink)。它是一种XML解析的替代方法。SAX不同于DOM解析，它逐行扫描文档，一边扫描一边解析。由于应用程序只是在读取数据时检查数据，因此不需要将数据存储在内存中，这对于大型文档的解析是个巨大优势。

或许这就是EasyExcel如此高效的原因

> 64M内存20秒读取75M（46W行25列）

![large.png](https://hhuhahaha.oss-cn-hangzhou.aliyuncs.com/img/large.png)

还有一个核心依赖需要引入

```xml
         <dependency>
            <groupId>cglib</groupId>
            <artifactId>cglib-nodep</artifactId>
            <version>3.3.0</version>
        </dependency>
```

第一次操作的时候，因为没引入这个依赖，报了这样的错

![Snipaste_2022-12-09_12-44-18.png](https://hhuhahaha.oss-cn-hangzhou.aliyuncs.com/img/Snipaste_2022-12-09_12-44-18.png)

其他依赖

```xml
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
        <!--引入mybatis-plus依赖-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.2</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.3</version>
        </dependency>
        <!--连接mysql的驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!--引入Junit5测试依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <!--web开发-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```

父类

```xml
    <parent>
        <artifactId>spring-boot-parent</artifactId>
        <groupId>org.springframework.boot</groupId>
        <version>2.2.2.RELEASE</version>
    </parent>
```

java版本1.8

## 2、创建表

```sql
create table mybatisplus_db.user
(
    id       int auto_increment,
    name     varchar(255)     not null,
    password varchar(255)     not null,
    age      int(5)           not null,
    tel      varchar(40)      not null,
    deleted  int(1) default 0 not null,
    version  int(20)          not null,
    sex      int(1)           not null,
    constraint user_id_uindex
        unique (id),
    constraint user_tel_uindex
        unique (tel)
);

alter table mybatisplus_db.user
    add primary key (id);
```

## 3、书写实体类

一键生成mapper pojo service

![Snipaste_2022-12-09_13-26-02.png](https://hhuhahaha.oss-cn-hangzhou.aliyuncs.com/img/Snipaste_2022-12-09_13-26-02.png)

使用的插件是mybatisX,在IDEA插件市场就有，免费安装，超级好用

![Snipaste_2022-12-09_13-27-30.png](https://hhuhahaha.oss-cn-hangzhou.aliyuncs.com/img/Snipaste_2022-12-09_13-27-30.png)书写excel表的实体类

```java
package com.excel.pojo;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.excel.converter.SexConverter;
import lombok.*;
import java.io.Serializable;
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
```

- @ExcelProperty 代表表头值
- @ColumnWidth 代表列宽
- converter 是映射策略

在数据中性别存储的是0 1 2 ，分别对应excel中的男 女 未知

SexConverter 是映射策略类

```java
package com.excel.converter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;
public class SexConverter implements Converter<Integer> {
    /**
     * java中需要转换的数据类型
     * @return Class<Integer>
     */
    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }
    /**
     * excel中的数据类型
     * @return WriteCellData<String>
     */
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }
    /**
     * 从excel转换成java类型，的转换规则
     * @param context ReadConverterContext<String>
     * @return Integer
     * @throws Exception
     */
    @Override
    public Integer convertToJavaData(ReadConverterContext<?> context) throws Exception {
        //CellData转对象属性
        String sex = context.getReadCellData().getStringValue();
        switch (sex){
            case "男":
                return 0;
            case "女":
                return 1;
            default:
                return 2;
        }
    }
    /**
     * 从Java类型到excel的转换规则
     * @param context WriteConverterContext<Integer>
     * @return WriteCellData<String>
     * @throws Exception
     */
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) throws Exception {
        Integer sex = context.getValue();
        switch (sex){
            case 1:
                return new WriteCellData<>("女");
            case 0:
                return new WriteCellData<>("男");
            default:
                return new WriteCellData<>("未知");
        }
    }
}
```

User和ExcelUser之间的转换

下面用了两种方法，一种的构造器另一种是工具类

个人比较推荐构造器方法

```java
public User(ExcelUser excelUser){
        this.age=excelUser.getAge();
        this.sex=excelUser.getSex();
        this.name=excelUser.getName();
        this.password=excelUser.getPassword();
        this.tel=excelUser.getTel();
        this.deleted=0;
        this.version=1;
    }
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
```

## 4、导出表

```java
    @Resource
    private UserServiceImpl userService;
    @Test
    public void writeExcel(){
        List<User> list = userService.list();
        List<ExcelUser> excelBatchUsers = TransUserUtil.getExcelBatchUsers(list);
        URL resource = this.getClass().getClassLoader().getResource("test1.xlsx")
        EasyExcel.write(resource.getPath())
                .head(ExcelUser.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("用户表1")
                .doWrite(excelBatchUsers);
    }
```

导出的表格test1.xlsx：

## ![Snipaste_2022-12-09_13-37-24.png](https://hhuhahaha.oss-cn-hangzhou.aliyuncs.com/img/Snipaste_2022-12-09_13-37-24.png)5、导入表

```java
    @Resource
    private UserServiceImpl userService;
    @Test
    public void readExcel(){
        URL resource = this.getClass().getClassLoader().getResource("test2.xlsx")
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
```

导入的表格test2.xlsx:

![Snipaste_2022-12-09_13-40-10.png](https://hhuhahaha.oss-cn-hangzhou.aliyuncs.com/img/Snipaste_2022-12-09_13-40-10.png)数据库新增数据:

## ![Snipaste_2022-12-09_12-59-21.png](https://hhuhahaha.oss-cn-hangzhou.aliyuncs.com/img/Snipaste_2022-12-09_12-59-21.png)
