package cn.knowei.sbg.controller;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.ArticleDto;
import cn.knowei.sbg.domain.vo.ArticleVo;
import cn.knowei.sbg.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 16:06 2023/2/25
 */
@RestController
@RequestMapping("content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping()
    public ResponseResult publish(@RequestBody ArticleVo articleVo){
        return articleService.publish(articleVo);
    }

    @GetMapping("list")
    public ResponseResult list(Long pageNum, Long pageSize, ArticleDto articleDto){
        return articleService.list(pageNum, pageSize, articleDto);
    }

    @GetMapping("{id}")
    public ResponseResult getOne(@PathVariable("id") Long id){
        return articleService.getOne(id);
    }

    @PutMapping()
    public ResponseResult update(@RequestBody ArticleVo articleVo){
        return articleService.update(articleVo);
    }

    @DeleteMapping("{id}")
    public ResponseResult dleete(@PathVariable Long id){
        return articleService.delete(id);
    }
}
