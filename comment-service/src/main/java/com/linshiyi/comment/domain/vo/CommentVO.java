package com.linshiyi.comment.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "评论展示")
public class CommentVO {

    @Schema(description = "id")
    private Long id;
    @Schema(description = "文章id")
    private Long articleId;
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description = "父级评论id")
    private Long parentId;
    @Schema(description = "内容")
    private String content;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "用户是否点赞")
    private Boolean likeStatus = false;

}
