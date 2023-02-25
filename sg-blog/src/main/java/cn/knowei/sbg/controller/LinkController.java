package cn.knowei.sbg.controller;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 18:11 2023/2/23
 */
@RestController
@RequestMapping("link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("getAllLink")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}
