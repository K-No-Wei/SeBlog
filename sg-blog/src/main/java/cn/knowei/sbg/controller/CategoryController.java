package cn.knowei.sbg.controller;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.vo.ExcelCategoryVo;
import cn.knowei.sbg.entity.Category;
import cn.knowei.sbg.enums.AppHttpCodeEnum;
import cn.knowei.sbg.service.CategoryService;
import cn.knowei.sbg.utils.BeanCopyUtils;
import cn.knowei.sbg.utils.WebUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 16:48 2023/2/23
 */
@RestController
@RequestMapping("category")
public class CategoryController{

    @Autowired
    private CategoryService categoryService;

    @GetMapping("getCategoryList")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}
