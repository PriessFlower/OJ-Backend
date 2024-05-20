package com.zrx.model.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 查询帖子参数。
 *
 * @author zhang.rx
 * @since 2024/5/13
 */
@Data
@Schema(name = "OjPostQueryRequest", description = "查询帖子参数")
public class OjPostQueryRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 用户Id
	 */
	@Schema(description = "用户Id")
	private String userId;

	/**
	 * 标题
	 */
	@Schema(description = "标题")
	private String title;

	/**
	 * 分区
	 */
	@Schema(description = "分区")
	private String zone;

	/**
	 * 标签列表（json 数组）
	 */
	@Schema(description = "标签列表（json 数组）")
	private List<String> tags;

}
