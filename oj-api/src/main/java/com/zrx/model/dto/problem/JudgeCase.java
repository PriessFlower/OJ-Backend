package com.zrx.model.dto.problem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 题目用例
 */
@Data
public class JudgeCase {

    /**
     * 输入用例
     */
    @Schema(description = "输入用例")
    private String input;

    /**
     * 输出用例
     */
    @Schema(description = "输出用例")
    private String output;

}