package cn.knowei.sbg.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 17:11 2023/2/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelCategoryVo  {

    //分类名
    @ExcelProperty("分类名称")
    private String name;
    //描述
    @ExcelProperty("分类描述")
    private String description;
    @ExcelProperty("日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;



}