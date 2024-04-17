package com.zrx.config.flex;

import com.mybatisflex.core.query.FunctionQueryColumn;
import com.mybatisflex.core.query.QueryColumn;

/**
 * Mybatis-Flex 其他函数
 *
 * @author zhang.rx
 * @since 2024/4/3
 */
public class MybatisFlexFunc {

	/**
	 * COALESCE 函数
	 */
	public static QueryColumn coalesce(QueryColumn... columns) {
		return new FunctionQueryColumn("COALESCE", columns);
	}

}
