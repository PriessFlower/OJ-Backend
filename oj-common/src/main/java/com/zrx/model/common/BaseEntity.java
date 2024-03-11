package com.zrx.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 基础类
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@Data
public class BaseEntity {

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String creator;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新者
	 */
	@Schema(description = "更新者")
	private String updater;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

}
