package com.zrx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 题目难度枚举
 *
 * @author zhang.rx
 * @since 2024/3/21
 */
@Getter
@AllArgsConstructor
public enum ProblemDifficultyEnum {

	EASY(0, "简单"), MIDDLE(1, "中等"), HARD(2, "困难");

	private final Integer value;

	private final String name;

	public static ProblemDifficultyEnum getEnum(Integer value) {
		ProblemDifficultyEnum[] enums = ProblemDifficultyEnum.values();
		for (ProblemDifficultyEnum enumItem : enums) {
			if (enumItem.getValue().equals(value)) {
				return enumItem;
			}
		}
		return null;
	}

}
