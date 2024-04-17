package com.zrx.enums;

public enum ProblemJudgeResultEnum {

	ACCEPTED(0, "Accepted"),

	WRONG_ANSWER(1, "Wrong Answer"),

	COMPILE_ERROR(2, "Compile Error"),

	MEMORY_LIMIT_EXCEEDED(3, "out of Memory"), TIME_LIMIT_EXCEEDED(4, "Time Limit Exceeded"),

	RUNTIME_ERROR(5, "Runtime Error"),

	SYSTEM_ERROR(6, "System Error");

	private final Integer key;

	private final String value;

	ProblemJudgeResultEnum(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

	public static ProblemJudgeResultEnum getEnum(Integer key) {
		ProblemJudgeResultEnum[] enums = ProblemJudgeResultEnum.values();
		for (ProblemJudgeResultEnum enumItem : enums) {
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
