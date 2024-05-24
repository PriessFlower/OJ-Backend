package com.zrx.model.dto.postComment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author zhang.rx
 * @since 2024/5/20
 */
@Data
public class PostCommentRequest {

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 父id
     */
    @Schema(description = "父id")
    private Long parentId;

    /**
     * 帖子id
     */
    @NotNull(message = "帖子id不能为空")
    @Schema(description = "帖子id")
    private Long postId;

    /**
     * 内容
     */
    @NotBlank(message = "请输入内容")
    @Schema(description = "内容")
    private String content;

    /**
     * 作者id
     */
    @Schema(description = "作者id")
    private Long authorId;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
