package com.linshiyi.interaction.converter;

import com.linshiyi.core.enums.EntityEnum;
import com.linshiyi.interaction.domain.dto.CommentCreateDTO;
import com.linshiyi.interaction.domain.po.Comment;
import com.linshiyi.interaction.domain.vo.CommentVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CommentMapStruct {

    /**
     * 创建评论：DTO → PO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted", constant = "0")
    Comment toPO(CommentCreateDTO dto);

    /**
     * 自定义转换：EntityEnum → String
     * MapStruct 会自动调用此方法处理 entityType 字段
     */
    CommentVO toVO(Comment comment);

    default String mapToValue(EntityEnum entityType) {
        return entityType != null ? entityType.getValue() : null;
    }

    default EntityEnum mapToEnum(String entityType) {
        return entityType != null ? EntityEnum.fromValue(entityType) : null;
    }
}