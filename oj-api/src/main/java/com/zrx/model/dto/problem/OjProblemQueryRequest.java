package com.zrx.model.dto.problem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.List;

/**
 * 题目请求体
 *
 * @author zhang.rx
 * @since 2024/3/20
 */
@Data
public class OjProblemQueryRequest {

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 标签列表
     */
    @Schema(description = "标签列表")
    private List<String> tags;

    /**
     * 题目难度，0简单，1中等，2困难
     */
    @Min(0)
    @Max(2)
    @Schema(description = "题目难度，0简单，1中等，2困难")
    private Integer difficulty;

}
