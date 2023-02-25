package cn.knowei.sbg.controller;

import cn.knowei.sbg.domain.ResponseResult;
import cn.knowei.sbg.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 21:35 2023/2/24
 */
@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("upload")
    public ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }
}
