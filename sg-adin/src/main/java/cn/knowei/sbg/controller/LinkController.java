package cn.knowei.sbg.controller;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.domain.dto.LinkDto;
import cn.knowei.sbg.domain.dto.LinkAllDto;
import cn.knowei.sbg.service.LinkService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.memory.UserAttribute;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 8:08 2023/2/26
 */
@RestController
@RequestMapping("content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("list")
    public ResponseResult list(Long pageNum, Long pageSize, LinkDto linkDto){
        return linkService.list(pageNum, pageSize, linkDto);
    }

    @PostMapping()
    public ResponseResult add(@RequestBody LinkAllDto userAddDto){
        return linkService.add(userAddDto);
    }

    @GetMapping("{id}")
    public ResponseResult getOne(@PathVariable Long id){
        return linkService.getOne(id);
    }

    @PutMapping()
    public ResponseResult update(@RequestBody LinkAllDto linkAllDto){
        return linkService.update(linkAllDto);
    }

    @DeleteMapping("{id}")
    public ResponseResult delete(@PathVariable Long id){
        linkService.getBaseMapper().deleteById(id);
        return ResponseResult.okResult();
    }
}
