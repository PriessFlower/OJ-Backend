package com.zt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 帖子分区枚举
 *
 * @author zhang.rx
 * @since 2024/5/14
 */
@Getter
@AllArgsConstructor
public enum PostZoneEnums {

	SYNTHESIS("综合", "synthesis"), FRONTEND("前端", "frontend"), BACKEND("后端", "backend"), HARMONY("鸿蒙", "harmony"),
	AIGC("AIGC", "aigc"),DEEPAGENTS("deepAgents框架", "deepAgents"),LANGCHAIN("langChain框架","langChain"),LANGGRAPH("langGraph框架","langGraph"),
    MYSQL("MySQL数据库","MySQL");

	private final String text;

	private final String value;

	public static Boolean isValid(String value) {
		for (PostZoneEnums postZoneEnums : PostZoneEnums.values()) {
			if (postZoneEnums.getValue().equals(value)) {
				return true;
			}
		}
		return false;
	}

	public static String getTextByValue(String value) {
		for (PostZoneEnums postZoneEnums : PostZoneEnums.values()) {
			if (postZoneEnums.getValue().equals(value)) {
				return postZoneEnums.text;
			}
		}
		return null;
	}

}
