package cn.knowei.sbg.service;

import cn.knowei.sbg.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 21:36 2023/2/24
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
