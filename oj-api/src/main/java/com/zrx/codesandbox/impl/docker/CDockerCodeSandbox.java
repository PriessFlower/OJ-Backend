package com.zrx.codesandbox.impl.docker;

import com.zrx.codesandbox.impl.template.CodeSandboxTemplate;
import com.zrx.codesandbox.model.ExecuteCodeRequest;
import com.zrx.codesandbox.model.ExecuteCodeResponse;

import java.util.Arrays;
import java.util.List;

public class CDockerCodeSandbox extends CodeSandboxTemplate {

	List<String> blackList = Arrays.asList("", "");

	public CDockerCodeSandbox() {
		super();
		super.GLOBAL_CODE_FILE_NAME = "main.c";
		super.ImageName = "gcc:latest";
		super.volumnName = "/c";
	}

	@Override
	public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
		return super.executeCode(executeCodeRequest);
	}

}
