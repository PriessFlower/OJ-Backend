package com.zrx.codesandbox.model;

import lombok.Data;

@Data
public class JudgeInfo {

	/**
	 * 执行消息
	 */
	private String message;

	/**
	 * 消耗内存大小
	 */
	private Long memory;

	/**
	 * 消耗的时间
	 */
	private Long time;

}
