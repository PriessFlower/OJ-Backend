package com.zrx.enums;

public enum ProblemSubmitStatusEnum {

	Submitting(0, "代码提交中"),

	Waiting(1, "等待中"),

	Judging(2, "判题中"),

	Completed(3, "判题完成");

	private final Integer key;

	private final String value;

	ProblemSubmitStatusEnum(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

	public static ProblemSubmitStatusEnum getEnum(Integer key) {
		ProblemSubmitStatusEnum[] enums = ProblemSubmitStatusEnum.values();
		for (ProblemSubmitStatusEnum enumItem : enums) {
			if (enumItem.getKey() == key) {
				return enumItem;
			}
		}
		return null;
	}

	public Integer getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
