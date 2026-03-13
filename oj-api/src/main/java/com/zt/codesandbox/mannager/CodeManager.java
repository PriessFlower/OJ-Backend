package com.zt.codesandbox.mannager;

import com.zt.codesandbox.CodeSandBox;
import com.zt.codesandbox.enums.QuestionSubmitLanguageEnum;
import com.zt.codesandbox.impl.docker.CDockerCodeSandbox;
import com.zt.codesandbox.impl.docker.CPPDockerCodeSandbox;
import com.zt.codesandbox.impl.docker.JavaDockerCodeSandbox;
import com.zt.codesandbox.impl.docker.PythonDockerCodeSandbox;

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
