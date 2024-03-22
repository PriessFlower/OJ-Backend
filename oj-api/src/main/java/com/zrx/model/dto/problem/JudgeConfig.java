package com.zrx.model.dto.problem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 判题配置
 */
@Data
@Schema(description = "判题配置")
public class JudgeConfig {

	/**
	 * 时间限制（ms）
	 */
	@Schema(description = "时间限制（ms）")
	private Long timeLimit;

	/**
	 * 内存限制（KB）
	 */
	@Schema(description = "内存限制（KB）")
	private Long memoryLimit;

	/**
	 * 堆栈限制（KB）
	 */
	@Schema(description = "堆栈限制（KB）")
	private Long stackLimit;

}
