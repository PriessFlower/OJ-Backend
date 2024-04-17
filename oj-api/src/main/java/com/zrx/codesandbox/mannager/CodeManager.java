package com.zrx.codesandbox.mannager;

import com.zrx.codesandbox.CodeSandBox;
import com.zrx.codesandbox.enums.QuestionSubmitLanguageEnum;
import com.zrx.codesandbox.impl.docker.CDockerCodeSandbox;
import com.zrx.codesandbox.impl.docker.CPPDockerCodeSandbox;
import com.zrx.codesandbox.impl.docker.JavaDockerCodeSandbox;
import com.zrx.codesandbox.impl.docker.PythonDockerCodeSandbox;

public class CodeManager {

	public static CodeSandBox getCodeSandbox(String language) {
		QuestionSubmitLanguageEnum enumByValue = QuestionSubmitLanguageEnum.getEnumByValue(language);
		switch (enumByValue) {
			case JAVA:
				return new JavaDockerCodeSandbox();
			case C:
				return new CDockerCodeSandbox();
			case PYTHON:
				return new PythonDockerCodeSandbox();
			case CPLUSPLUS:
				return new CPPDockerCodeSandbox();
			default:
				return new JavaDockerCodeSandbox();
		}
	}

}
