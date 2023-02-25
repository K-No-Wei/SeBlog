package cn.knowei.sbg.entity;

import java.util.Date;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类表(Category)表实体类
 *
 * @author makejava
 * @since 2023-02-23 16:46:02
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_category")
public class Category  {
    @TableId
    private Long id;

    //分类名
    @ExcelProperty("分类名称")
    private String name;
    //父分类id，如果没有父分类为-1
    private Long pid;
    //描述
    @ExcelProperty("分类描述")
    private String description;
    //状态0:正常,1禁用
    private String status;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @ExcelProperty("日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    @TableLogic(value = "0", delval = "1")
    private Integer delFlag;



}

