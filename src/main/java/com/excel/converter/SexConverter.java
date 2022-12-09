package com.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;

/**
 * @author yzy
 * @create 2022-12-09-12:09
 */
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
