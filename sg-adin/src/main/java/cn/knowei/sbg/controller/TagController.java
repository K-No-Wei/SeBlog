package cn.knowei.sbg.controller;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.TagListDto;
import cn.knowei.sbg.domain.vo.PageVo;
import cn.knowei.sbg.service.TagService;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 10:41 2023/2/25
 */
@RestController
@RequestMapping("content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.list(pageNum, pageSize, tagListDto);
    }

    @PostMapping("")
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
        return tagService.addTag(tagListDto);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteTag(@PathVariable Long id){
        return tagService.deleteTag(id);
    }

    @GetMapping("{id}")
    public ResponseResult getTag(@PathVariable Long id){
        return ResponseResult.okResult(tagService.getById(id));
    }

    @PutMapping()
    public ResponseResult updateTag(@RequestBody TagListDto tagListDto){
        return tagService.updateTag(tagListDto);
    }

    @GetMapping("listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }


}
