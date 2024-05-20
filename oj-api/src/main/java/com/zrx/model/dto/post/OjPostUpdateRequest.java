package com.zrx.model.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 帖子 更新参数。
 *
 * @author zhang.rx
 * @since 2024/5/13
 */
@Data
@Schema(name = "OjPostUpdateRequest", description = "更新帖子")
public class OjPostUpdateRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@NotNull(message = "id 不能为空")
	@Schema(description = "id")
	private Long id;

	/**
	 * 标题
	 */
	@NotBlank(message = "请填写标题")
	@Schema(description = "标题")
	private String title;

	/**
	 * 内容
	 */
	@NotBlank(message = "请填写内容")
	@Schema(description = "内容")
	private String content;

	/**
	 * 分区
	 */
	@NotBlank(message = "请选择分区")
	@Schema(description = "分区")
	private String zone;

	/**
	 * 标签列表（json 数组）
	 */
	@NotNull(message = "请输入至少一个标签")
	@Schema(description = "标签列表（json 数组）")
	private List<String> tags;

}
