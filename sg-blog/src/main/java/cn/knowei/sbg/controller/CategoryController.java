package cn.knowei.sbg.controller;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
