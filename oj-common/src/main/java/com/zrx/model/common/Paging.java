package com.zrx.model.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页基础类
 *
 * @author zhang.rx
 * @since 2024/2/20
 */
@Data
@Schema(description = "分页信息")
@NoArgsConstructor
public class Paging {

	/**
	 * 当前页码
	 */
	@Schema(description = "当前页码")
	private Integer pageNum = 1;

	/**
	 * 每页条数
	 */
	@Schema(description = "每页条数")
	private Integer pageSize = 10;

}