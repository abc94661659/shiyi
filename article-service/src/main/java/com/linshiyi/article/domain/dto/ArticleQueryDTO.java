package com.linshiyi.article.domain.dto;

import com.linshiyi.common.domain.dto.QueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(description = "文章分页查询参数")
@EqualsAndHashCode(callSuper = false)
public class ArticleQueryDTO extends QueryDTO {

    private String title;


}
