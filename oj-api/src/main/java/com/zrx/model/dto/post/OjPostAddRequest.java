package com.zrx.model.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 帖子 添加参数。
 *
 * @author zhang.rx
 * @since 2024/5/13
 */
@Data
@Schema(name = "OjPostAddRequest", description = "新增帖子")
public class OjPostAddRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 标题
	 */
	@NotBlank(message = "请填写标题")
	@Schema(description = "标题")
	private String title;

	/**
	 * 分区
	 */
	@NotBlank(message = "请选择分区")
	@Schema(description = "分区")
	private String zone;

	/**
	 * 内容
	 */
	@NotBlank(message = "请填写内容")
	@Schema(description = "内容")
	private String content;

	/**
	 * 标签列表（json 数组）
	 */
	@NotNull(message = "请输入至少一个标签")
	@Schema(description = "标签列表（json 数组）")
	private List<String> tags;

}
