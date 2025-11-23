package com.linshiyi.interaction.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "评论展示")
public class CommentVO {

    @Schema(description = "id")
    private Long id;
    @Schema(description = "实体类型")
    private String entityType;
    @Schema(description = "实体id")
    private Long entityId;
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description = "用户昵称")
    private String nickName;
    @Schema(description = "用户头像")
    private String avatar;
    @Schema(description = "内容")
    private String content;
    @Schema(description = "父级评论id")
    private Long parentId;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "子评论")
    private CommentVO childComment;
    @Schema(description = "子评论总数")
    private Integer childCount;
    @Schema(description = "当前用户是否点赞")
    private Boolean isLike;
}
