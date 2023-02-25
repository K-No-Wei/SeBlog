package cn.knowei.sbg.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: knowei
 * @Description:
 * @Date: Create in 18:41 2023/2/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private String title;
    private String summary;
}
