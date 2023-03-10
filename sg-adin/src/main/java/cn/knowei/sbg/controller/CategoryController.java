package cn.knowei.sbg.controller;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.CategoryDto;
import cn.knowei.sbg.domain.vo.ExcelCategoryVo;
import cn.knowei.sbg.entity.Category;
import cn.knowei.sbg.enums.AppHttpCodeEnum;
import cn.knowei.sbg.service.CategoryService;
import cn.knowei.sbg.utils.BeanCopyUtils;
import cn.knowei.sbg.utils.WebUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 15:55 2023/2/25
 */
@RestController
@RequestMapping("content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.getCategoryList();
    }

    @GetMapping("list")
    public ResponseResult list(Long pageNum, Long pageSize, CategoryDto categoryDto){
        return categoryService.list(pageNum, pageSize, categoryDto);
    }

    @PostMapping("")
    public ResponseResult add(@RequestBody CategoryDto categoryDto){
        return categoryService.add(categoryDto);
    }

    @GetMapping("{id}")
    public ResponseResult getCate(@PathVariable Long id){
        return categoryService.getCate(id);
    }

    @PutMapping()
    public ResponseResult update(@RequestBody CategoryDto categoryDto){
        return categoryService.update(categoryDto);
    }

    @DeleteMapping("{id}")
    public ResponseResult delete(@PathVariable Long id){
        return categoryService.delete(id);
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("export")
    public void export(HttpServletResponse response){
        try {
            //??????????????????????????????
            WebUtils.setDownLoadHeader("??????.xlsx",response);
            //???????????????????????????
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //??????????????????Excel???
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("????????????")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //??????????????????????????????json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
}
