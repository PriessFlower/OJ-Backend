package com.zrx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemSubmitStatusEnum {

	Submitting(0, "待判题"),

	Waiting(1, "判题中"),

	Judging(2, "成功"),

	Completed(3, "失败");

	private final Integer key;

	private final String value;

	public static ProblemSubmitStatusEnum getEnum(Integer key) {
		ProblemSubmitStatusEnum[] enums = ProblemSubmitStatusEnum.values();
		for (ProblemSubmitStatusEnum enumItem : enums) {
			if (enumItem.getKey() == key) {
				return enumItem;
			}
		}
		return null;
	}

}
